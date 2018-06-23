package com.funcy.g01.base.service;

import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebo.synframework.nettybase.protoPool.ProtoBuilderPoolManager;
import com.ebo.synframework.nettybase.protoPool.ReusedProtoBuilder;
import com.funcy.g01.base.bo.ForceQuitType;
import com.funcy.g01.base.bo.achieve.AchievementType;
import com.funcy.g01.base.bo.achieve.UpdateAchievement;
import com.funcy.g01.base.bo.fight.EventType;
import com.funcy.g01.base.bo.fight.FightPlayer;
import com.funcy.g01.base.bo.fight.FightRoom;
import com.funcy.g01.base.bo.fight.FightSkill;
import com.funcy.g01.base.bo.fight.FightSkillBuilder;
import com.funcy.g01.base.bo.fight.FightSkillBuilder.BuildFightSkillResult;
import com.funcy.g01.base.bo.fight.FightConfig;
import com.funcy.g01.base.bo.fight.FightSkillTrigger;
import com.funcy.g01.base.bo.fight.FightUnit;
import com.funcy.g01.base.bo.fight.FixFrameEvent;
import com.funcy.g01.base.bo.fight.PhysicsObj;
import com.funcy.g01.base.bo.fight.PhysicsObjType;
import com.funcy.g01.base.bo.fight.ServerEventType;
import com.funcy.g01.base.bo.fight.SpeakState;
import com.funcy.g01.base.bo.fight.SynEvent;
import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.bo.item.RoleItemInfo;
import com.funcy.g01.base.bo.npc.NpcTaskType;
import com.funcy.g01.base.bo.npc.RoleNpcsInfo;
import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.bo.role.RoleNetInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.base.bo.totem.RoleSkillInfo;
import com.funcy.g01.base.bo.totem.SkillVo;
import com.funcy.g01.base.dao.redis.ItemRepo;
import com.funcy.g01.base.dao.redis.NpcRepo;
import com.funcy.g01.base.dao.redis.RoleRepo;
import com.funcy.g01.base.dao.redis.TotemRepo;
import com.funcy.g01.base.data.FightSkillData;
import com.funcy.g01.base.data.NpcData;
import com.funcy.g01.base.data.PhysicsConfigData;
import com.funcy.g01.base.data.PhysicsConfigProperty;
import com.funcy.g01.base.data.PhysicsObjData;
import com.funcy.g01.base.data.PhysicsObjProperty;
import com.funcy.g01.base.data.UnitData;
import com.funcy.g01.base.global.BusinessPool;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.net.CmdStatus;
import com.funcy.g01.base.net.GamePlayer;
import com.funcy.g01.base.net.PlayerState;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ChatEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.CommonEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ConjurePhysicsObjEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.EmptyEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.FightSkillObjEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.PlayerEnterProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.SpeakEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.UseSkillEventProto;
import com.funcy.g01.base.proto.service.AccountServiceRespProtoBuffer.ForceQuitRespProto;
import com.funcy.g01.base.proto.service.AccountServiceRespProtoBuffer.NotifyItemNotEnoughMsgProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.IntReqProto;
import com.funcy.g01.base.proto.service.SynFightRespProtoBuffer.RefreshRoomInfoProto;
import com.funcy.g01.base.proto.service.SynFightRespProtoBuffer.SelfPlayerEnterProto;
import com.funcy.g01.dispatcher.bo.RoomType;
import com.funcy.g01.hall.service.AchievementService;
import com.funcy.g01.hall.service.ItemDomainService;
import com.google.protobuf.Message.Builder;

@Service
public class SynFightingService {
	
	private static final Logger logger = Logger.getLogger(SynFightingService.class);
	
	@Autowired
	private PhysicsConfigData physicsConfigData;
	
	@Autowired
	private UnitData unitData;
	
	@Autowired
	private PhysicsObjData physicsObjData;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private TotemRepo totemRepo;
	
	@Autowired
	private ItemRepo itemRepo;
	
	@Autowired
	private ServerContext serverContext;
	
	@Autowired
	private BusinessPool businessPool;
	
	@Autowired
	private ServerInfoData serverInfoData;
	
	@Autowired
	private FightSkillBuilder fightSkilllBuilder;
	
	@Autowired
	private FightSkillData fightSkillData;
	
	@Autowired
	private AchievementService achievementService;
	
	@Autowired
	private ItemDomainService itemDomainService;
	
	@Autowired
	private NpcRepo npcRepo;
	
	@Autowired
	private NpcData npcData;
	
	private long crrentSpeakerRoleId = -1;
	
	
	public void playerEnterFight(String platformId, String token, final PlayerEnterProto playerEnterProto, int roomId, String roomTypeStr, final GamePlayer gamePlayer) {
		final String serviceAndMethod = "synFightingService.playerEnterFight";
		final RoomType roomType = RoomType.valueOf(roomTypeStr);
		
		//check token
		String[] temp = token.split(";");
		long tokenCreateTime = Long.parseLong(temp[0]);
		String actToken = temp[1];
		String serverToken = DigestUtils.md5Hex(platformId + tokenCreateTime + ServerConfig.login_token_ext);
		if(!serverToken.equals(actToken)) {
			throw new RuntimeException();
		}
		
		final Role role = roleRepo.getRole(platformId);
		
		//检查重复登录
		final GamePlayer logonGamePlayer = serverContext.findFightLogonPlayer(role.getId());
		FightRoom beforeRoom = null;
		if(logonGamePlayer != null) {
			FightPlayer fightPlayer = logonGamePlayer.getFightPlayer();
			if(fightPlayer != null && fightPlayer.getFightRoom() != null && serverContext.getFightRoom(fightPlayer.getFightRoom().getRoomId()) != null) { //房间存在，优先进之前的房间
				//替换该fightPlayer
				logonGamePlayer.setFightPlayer(null);
				logonGamePlayer.setSynRoom(null);
				beforeRoom = fightPlayer.getFightRoom();
			}
			
			ForceQuitRespProto.Builder forceQuitBuilder = ForceQuitRespProto.newBuilder();
			forceQuitBuilder.setReasonType(ForceQuitType.mutiLogin.ordinal());//另一个设备
			logonGamePlayer.forceRespond("accountService.forceQuitFight", CmdStatus.notDecrypt,forceQuitBuilder);
			businessPool.schedule(new Runnable() {
				@Override
				public void run() {
					serverContext.removeConnectedPlayer(logonGamePlayer);
					serverContext.removeFightLogonPlayer(logonGamePlayer);
					logonGamePlayer.destroy();
				}
			}, 300, TimeUnit.MILLISECONDS);
		}
		
		//查看房间是否存在
		final FightRoom fightRoom = beforeRoom != null ? beforeRoom : serverContext.getFightRoom(roomId);
		if(fightRoom == null) {
			SelfPlayerEnterProto.Builder builder = SelfPlayerEnterProto.newBuilder();
			builder.setLoginState(1);
			gamePlayer.respond(serviceAndMethod, CmdStatus.notDecrypt, builder);
			logger.info("fightroom not exist so need reconnect");
			businessPool.schedule(new Runnable() {
				@Override
				public void run() {
					serverContext.removeConnectedPlayer(gamePlayer);
					serverContext.removeFightLogonPlayer(gamePlayer);
					gamePlayer.destroy();
				}
			}, 300, TimeUnit.MILLISECONDS);
			return;
		}
		
		RoleNetInfo roleNetInfo = roleRepo.getRoleNetInfo(role.getId());
		if(roleNetInfo.getFightServerId() != -1 && roleNetInfo.getFightServerId() != serverInfoData.getSelfServerId()) { //当前在另一台服务器上，退出重试
			SelfPlayerEnterProto.Builder builder = SelfPlayerEnterProto.newBuilder();
			builder.setLoginState(1);
			gamePlayer.respond(serviceAndMethod, CmdStatus.notDecrypt, builder);
			logger.info("serverId not right so need reconnect");
			businessPool.schedule(new Runnable() {
				@Override
				public void run() {
					serverContext.removeConnectedPlayer(gamePlayer);
					serverContext.removeFightLogonPlayer(gamePlayer);
					gamePlayer.destroy();
				}
			}, 300, TimeUnit.MILLISECONDS);
			return;
		}
		
		roleNetInfo.setFightServerId(serverInfoData.getSelfServerId());
		roleNetInfo.setFightRoomId(roomId);
		roleRepo.saveRoleNetInfo(roleNetInfo);
		gamePlayer.setRoleId(role.getId());
		gamePlayer.setPlatformId(platformId);
		serverContext.addFightLogonPlayer(gamePlayer);
		gamePlayer.setState(PlayerState.logon);
		final BuildFightSkillResult buildFightSkillResult = fightSkilllBuilder.buildFightSkill(role.getId());
		final PlayerEnterProto.Builder playerEnterBuilder = playerEnterProto.toBuilder().setPlayerInfo(role.buildPlayerInfoProto4Front());
		fightRoom.executeRightNow(new SynEvent(gamePlayer.getRoleId(), serviceAndMethod) {
			@Override
			public void executeEvent() {
				playerEnterFight0(playerEnterBuilder.build(), gamePlayer, fightRoom, role, buildFightSkillResult, roomType);
			}
		});
	}
	
	private void playerEnterFight0(
			final PlayerEnterProto playerEnterProto,
			final GamePlayer gamePlayer, final FightRoom fightRoom, Role role, BuildFightSkillResult buildFightSkillResult, RoomType roomType) {
		gamePlayer.setSynRoom(fightRoom);
		SelfPlayerEnterProto.Builder builder = SelfPlayerEnterProto.newBuilder();
		FightPlayer fightPlayer = fightRoom.findFightPlayer(gamePlayer.getRoleId());
		builder.setIsReenter(fightPlayer != null);
		if(fightPlayer == null) {
			fightPlayer = new FightPlayer(gamePlayer, playerEnterProto);
			fightPlayer.setWantedRoomType(roomType);
			fightPlayer.initSkills(buildFightSkillResult);
			fightPlayer.setRole(role);
			fightPlayer.init(physicsConfigData, unitData, physicsObjData);
			fightPlayer.setLastActionFrame(fightRoom.getFrameIndex());
			gamePlayer.setFightPlayer(fightPlayer);
			fightRoom.playerEnter(fightPlayer);
		} else {
			gamePlayer.setFightPlayer(fightPlayer);
			fightPlayer.setGamePlayer(gamePlayer);
			fightPlayer.reconnect();
			fightPlayer.setLastActionFrame(fightRoom.getFrameIndex());
		}
		builder.setRoomInfo(fightRoom.buildProto());
		gamePlayer.respond("synFightingService.playerEnterFight", CmdStatus.notDecrypt, builder);
	}
	
	public Builder finishLoad(boolean isReenter, GamePlayer gamePlayer) {
		FightPlayer fightPlayer = gamePlayer.getFightPlayer();
		if(fightPlayer == null) {
			return null;
		}
		FightRoom fightRoom = fightPlayer.getFightRoom();
		if(fightRoom == null) {
			return null;
		}
		if(isReenter) {
			fightRoom.playerReenter(fightPlayer);
		} else {
			fightRoom.playerEnterSendMsg(fightPlayer);
		}
		fightPlayer.setLastActionFrame(fightRoom.getFrameIndex());
		IntReqProto.Builder builder = IntReqProto.newBuilder();
		builder.setIndex(fightRoom.getFrameIndex());
		return builder;
	}
	
	public Object getRefreshRoomInfo(GamePlayer gamePlayer) {
		FightRoom fightRoom = (FightRoom)gamePlayer.getSynRoom();
		ReusedProtoBuilder reusedProtoBuilder = ProtoBuilderPoolManager.getBuilder(RefreshRoomInfoProto.class);
		RefreshRoomInfoProto.Builder builder = (RefreshRoomInfoProto.Builder) reusedProtoBuilder.getBuilder();
		if(fightRoom == null) {
			builder.setState(1);
			return reusedProtoBuilder;
		}
		builder.setRoomId(fightRoom.getRoomId());
		builder.setRoomFightIndex(fightRoom.getRoomFightIndex());
		builder.setFrameIndex(fightRoom.getFrameIndex());
		builder.setFightState(fightRoom.getFightRoomState().getCode());
		for(FightPlayer fightPlayer : fightRoom.getFightPlayers()) {
			builder.addPlayers(fightPlayer.buildWithUnitProto());
		}
		for(PhysicsObj physicsObj : fightRoom.getPhysicsObjs().values()) {
			if(physicsObj.getPhysicsObjProperty().getType() == PhysicsObjType.unitObj) {
				builder.addObjs(physicsObj.buildProto(false));
			}
		}
		return reusedProtoBuilder;
	}
	
	public void playerQuitFight(EmptyEventProto emptyEventProto, GamePlayer gamePlayer) {
		FightRoom fightRoom = (FightRoom)gamePlayer.getSynRoom();
		if(fightRoom == null) {
			return;
		}
		EmptyEventProto.Builder builder = emptyEventProto.toBuilder().setRoleId(gamePlayer.getRoleId());
//		builder.setEventType(EventType.quit_event_type.getCode());
		builder.setFrameIndex(fightRoom.getFrameIndex());
		fightRoom.playerQuit(gamePlayer.getFightPlayer(), builder.build());
	}
	
	public void playerSendConjureObjEvent(ConjurePhysicsObjEventProto conjurePhysicsObjEventProto, GamePlayer gamePlayer) {
		FightRoom fightRoom = (FightRoom)gamePlayer.getSynRoom();
		if(fightRoom == null) {
			return;
		}
		FightUnit fightUnit = gamePlayer.getFightPlayer().getFightUnit();
		if(fightUnit == null || fightUnit.isDead()) {
			return;
		}
		gamePlayer.getFightPlayer().setLastActionFrame(fightRoom.getFrameIndex());
		fightRoom.handlePhysicsObjEvent(conjurePhysicsObjEventProto, gamePlayer.getFightPlayer());
		gamePlayer.getFightPlayer().setLastActionFrame(fightRoom.getFrameIndex());
		ConjurePhysicsObjEventProto.Builder builder = conjurePhysicsObjEventProto.toBuilder().setRoleId(gamePlayer.getRoleId());
		builder.setFrameIndex(fightRoom.getFrameIndex());
		EventType eventType = EventType.getEventTypeFromCode(conjurePhysicsObjEventProto.getEventType());
		if(eventType == EventType.conjure_physicsobj_conjuring_init_event_type || eventType == EventType.conjure_physicsobj_conjuring_update_event_type 
				|| eventType == EventType.conjure_physicsobj_confirm_event_type || eventType == EventType.conjure_physicsobj_cancel_event_type) {
			if(eventType == EventType.conjure_physicsobj_confirm_event_type) {
				final PhysicsObjProperty physicsObjProperty = physicsObjData.getPhysicsObjProperty(conjurePhysicsObjEventProto.getPhysicsObj().getXmlId());
				PhysicsConfigProperty physicsConfigProperty = fightRoom.getPhysicsConfigProperty();
				builder.setPrepareConjureTime((int) (fightUnit.calPrepareConjureTime(physicsObjProperty, physicsConfigProperty, true) * 100f));
			}
			fightRoom.broadcastConjurePhysicsObjEvent(builder, gamePlayer.getFightPlayer(), false);
		} else {
			fightRoom.broadcastConjurePhysicsObjEvent(builder, gamePlayer.getFightPlayer());
		}
	}

	public void playerSendCommonEvent(CommonEventProto commonEventProto, GamePlayer gamePlayer) {
		FightRoom fightRoom = (FightRoom)gamePlayer.getSynRoom();
		if(fightRoom == null) {
			return;
		}
		if(gamePlayer.getFightPlayer()==null){
			return;
		}
		FightUnit fightUnit = gamePlayer.getFightPlayer().getFightUnit();
		if(fightUnit == null || fightUnit.isDead()) {
			return;
		}
		FightPlayer fightPlayer = gamePlayer.getFightPlayer();
		int delayFrame = fightPlayer.getDelayFrame();
		if(delayFrame < FightConfig.ave_net_delay_frame) {
			delayFrame = FightConfig.ave_net_delay_frame;
		}
		int frameIndex = commonEventProto.getFrameIndex() + delayFrame / 2;
		fightRoom.performAtFixFrameIndex(new FixFrameEvent(gamePlayer.getRoleId(), ServerEventType.common_action, frameIndex) {
			@Override
			public void executeEvent() {
				fightPlayer.unitRunAction(commonEventProto);
			}
		});
		if(commonEventProto.getEventType() != EventType.period_syn_unit_info_event_type.getCode()) {
			fightPlayer.setLastActionFrame(fightRoom.getFrameIndex());
		} 
		CommonEventProto.Builder builder = commonEventProto.toBuilder().setRoleId(gamePlayer.getRoleId());
		builder.setFrameIndex(commonEventProto.getFrameIndex() + delayFrame / 2 + FightConfig.ave_net_delay_frame_half);
		fightRoom.broadcastCommonEvent(builder, gamePlayer.getFightPlayer());
	}
	
	public void playerSendChatEvent(ChatEventProto chatEventProto, final GamePlayer gamePlayer) {
		FightRoom fightRoom = (FightRoom)gamePlayer.getSynRoom();
		if(fightRoom == null) {
			return;
		}
		FightUnit fightUnit = gamePlayer.getFightPlayer().getFightUnit();
		if(fightUnit == null || fightUnit.isDead()) {
			return;
		}
		ChatEventProto.Builder builder = chatEventProto.toBuilder().setRoleId(gamePlayer.getRoleId());
		builder.setFrameIndex(fightRoom.getFrameIndex());
		gamePlayer.getFightPlayer().setLastActionFrame(fightRoom.getFrameIndex());
		fightRoom.broadcastChatEvent(builder, gamePlayer.getFightPlayer());
		if (chatEventProto.getChatType() == 1 && !gamePlayer.getFightPlayer().isUsedEmote()) {
			gamePlayer.getFightPlayer().setUsedEmote(true);
			achievementService.updateOneAchievement(new UpdateAchievement(gamePlayer.getRoleId(), AchievementType.fight_use_emote, 1));
		}
		this.businessPool.execute(new Runnable() {
			@Override
			public void run() {
				RoleNpcsInfo roleNpcsInfo = npcRepo.getRoleNpcsInfo(gamePlayer.getRoleId());
				boolean isChange = roleNpcsInfo.updateTypeTaskNumAndNotify(NpcTaskType.chatWithExpression, 1, npcData, gamePlayer);
				if(isChange) {
					npcRepo.saveRoleNpcsInfo(roleNpcsInfo);
				}
			}
		});
	}
	
	public void playerSendSpeakEvent(SpeakEventProto speakEventProto, final GamePlayer gamePlayer) {
		final FightRoom fightRoom = (FightRoom)gamePlayer.getSynRoom();
		if(fightRoom == null) {
			return;
		}
		if (crrentSpeakerRoleId == -1) {
			crrentSpeakerRoleId = gamePlayer.getRoleId();
			SpeakEventProto.Builder builder2 = SpeakEventProto.newBuilder();
			builder2.setEventType(EventType.speak.getCode());
			builder2.setSpeakType(SpeakState.speak_start.getCode());
			builder2.setRoleId(gamePlayer.getRoleId());
			builder2.setFrameIndex(fightRoom.getFrameIndex());
			fightRoom.broadcastSpeakStateEvent(builder2, gamePlayer.getFightPlayer());
			businessPool.schedule(new Runnable() {
				@Override
				public void run() {
					SpeakEventProto.Builder builder2 = SpeakEventProto.newBuilder();
					builder2.setEventType(EventType.speak.getCode());
					builder2.setSpeakType(SpeakState.speak_colddown.getCode());
					builder2.setRoleId(gamePlayer.getRoleId());
					builder2.setFrameIndex(fightRoom.getFrameIndex());
					fightRoom.broadcastSpeakStateEvent(builder2, gamePlayer.getFightPlayer());
				}
			}, 5, TimeUnit.SECONDS);
			businessPool.schedule(new Runnable() {
				@Override
				public void run() {
					SpeakEventProto.Builder builder2 = SpeakEventProto.newBuilder();
					builder2.setEventType(EventType.speak.getCode());
					builder2.setSpeakType(SpeakState.speak_stop.getCode());
					builder2.setRoleId(gamePlayer.getRoleId());
					builder2.setFrameIndex(fightRoom.getFrameIndex());
					fightRoom.broadcastSpeakStateEvent(builder2, gamePlayer.getFightPlayer());
					crrentSpeakerRoleId = -1;
					
					businessPool.execute(new Runnable() {
						@Override
						public void run() {
							RoleNpcsInfo roleNpcsInfo = npcRepo.getRoleNpcsInfo(gamePlayer.getRoleId());
							boolean isChange = roleNpcsInfo.updateTypeTaskNumAndNotify(NpcTaskType.chat, 1, npcData, gamePlayer);
							if(isChange) {
								npcRepo.saveRoleNpcsInfo(roleNpcsInfo);
							}
						}
					});
				}
			}, 6, TimeUnit.SECONDS);
		}
		if (crrentSpeakerRoleId != gamePlayer.getRoleId()) {
			return; 
		}
		FightUnit fightUnit = gamePlayer.getFightPlayer().getFightUnit();
		if(fightUnit == null || fightUnit.isDead()) {
			return;
		}
		gamePlayer.getFightPlayer().setLastActionFrame(fightRoom.getFrameIndex());
		SpeakEventProto.Builder builder = speakEventProto.toBuilder().setRoleId(gamePlayer.getRoleId());
		builder.setFrameIndex(fightRoom.getFrameIndex());
		builder.setSpeakType(SpeakState.speak_running.getCode());
		fightRoom.broadcastSpeakEvent(builder, gamePlayer.getFightPlayer());
		if (!gamePlayer.getFightPlayer().isUsedSpeak()) {
			gamePlayer.getFightPlayer().setUsedSpeak(true);
			achievementService.updateOneAchievement(new UpdateAchievement(gamePlayer.getRoleId(), AchievementType.fight_use_speak, 1));
		}
	}
	
	public void playerUseSkillEvent(final UseSkillEventProto useSkillEventProto, final GamePlayer gamePlayer) {
		final FightRoom fightRoom = (FightRoom)gamePlayer.getSynRoom();
		if(fightRoom == null) {
			return;
		}
		FightUnit fightUnit = gamePlayer.getFightPlayer().getFightUnit();
		if(fightUnit == null || fightUnit.isDead()) {
			return;
		}
		gamePlayer.getFightPlayer().setLastActionFrame(fightRoom.getFrameIndex());
		if (useSkillEventProto.getIsUseSkillItem()) {
			RoleSkillInfo roleSkillInfo = totemRepo.getRoleSkillInfo(gamePlayer.getRoleId());
			SkillVo  skillVo = roleSkillInfo.findSkillVoByXmlId(useSkillEventProto.getXmlId());
			if (skillVo == null) {
				return;
			}
			FightSkill fightSkill = gamePlayer.getFightPlayer().findSamanSkillById(useSkillEventProto.getFightSkillId());
			FightSkillTrigger.dragReleaseTriggerSkill(fightRoom, fightSkill , gamePlayer.getFightPlayer(), useSkillEventProto);
			UseSkillEventProto.Builder builder = useSkillEventProto.toBuilder().setRoleId(gamePlayer.getRoleId());
			builder.setFrameIndex(fightRoom.getFrameIndex());
			fightRoom.broadcastUseSkillEvent(builder, gamePlayer.getFightPlayer());

		}else{
			//TODO 检查物品是否够了
			businessPool.execute(new Runnable() {
				
				@Override
				public void run() {
					try {
						itemDomainService.consumeItem(useSkillEventProto.getXmlId(), 1, gamePlayer.getRoleId()); 
						fightRoom.executeRightNow(new SynEvent(gamePlayer.getRoleId(), "synFightingService.playerUseSkillEvent") {
							
							@Override
							public void executeEvent() {
								FightSkill fightSkill = new FightSkill(fightSkillData.findFightSkillProperty(useSkillEventProto.getFightSkillId()), 1);
								FightSkillTrigger.dragReleaseTriggerSkill(fightRoom, fightSkill , gamePlayer.getFightPlayer(), useSkillEventProto);
								UseSkillEventProto.Builder builder = useSkillEventProto.toBuilder().setRoleId(gamePlayer.getRoleId());
								builder.setFrameIndex(fightRoom.getFrameIndex());
								fightRoom.broadcastUseSkillEvent(builder, gamePlayer.getFightPlayer());
							}
						});
					} catch (Exception e) {
						// TODO: handle exception
						NotifyItemNotEnoughMsgProto.Builder builder = NotifyItemNotEnoughMsgProto.newBuilder();
						builder.setType(1);
						RoleItemInfo roleItemInfo = itemRepo.getRoleItemInfo(gamePlayer.getRoleId());
						for(Item item : roleItemInfo.getItems()) {
							builder.addItems(item.copyTo());
						}
						gamePlayer.respond("notifyItemNotEnough", CmdStatus.notDecrypt, builder);
					}
				}
			});
			
		}
	}
	

	public void playerSendFightSkillObjEvent(FightSkillObjEventProto fightSkillObjEventProto, GamePlayer gamePlayer) {
		FightRoom fightRoom = (FightRoom)gamePlayer.getSynRoom();
		if(fightRoom == null) {
			return;
		}
		FightUnit fightUnit = gamePlayer.getFightPlayer().getFightUnit();
		if(fightUnit == null || fightUnit.isDead()) {
			return;
		}
		gamePlayer.getFightPlayer().setLastActionFrame(fightRoom.getFrameIndex());
		FightSkillObjEventProto.Builder builder = fightSkillObjEventProto.toBuilder().setRoleId(gamePlayer.getRoleId());
		builder.setFrameIndex(fightRoom.getFrameIndex());
		fightRoom.broadcastFightSkillObjEvent(builder, gamePlayer.getFightPlayer());
	}

}
