package com.funcy.g01.hall.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebo.synframework.nettybase.protoPool.ProtoBuilderPoolManager;
import com.ebo.synframework.nettybase.protoPool.ReusedProtoBuilder;
import com.funcy.g01.base.bo.Currency;
import com.funcy.g01.base.bo.CurrencyType;
import com.funcy.g01.base.bo.ForceQuitType;
import com.funcy.g01.base.bo.achieve.AchievementType;
import com.funcy.g01.base.bo.achieve.RoleAchieveInfo;
import com.funcy.g01.base.bo.achieve.UpdateAchievement;
import com.funcy.g01.base.bo.chest.ChestType;
import com.funcy.g01.base.bo.email.Email;
import com.funcy.g01.base.bo.email.EmailType;
import com.funcy.g01.base.bo.fight.EventType;
import com.funcy.g01.base.bo.fight.FightPlayer;
import com.funcy.g01.base.bo.fight.FightRoom;
import com.funcy.g01.base.bo.fight.FightRoomState;
import com.funcy.g01.base.bo.fight.FightSkillBuilder.BuildFightSkillResult;
import com.funcy.g01.base.bo.fight.SynEvent;
import com.funcy.g01.base.bo.friend.Friend;
import com.funcy.g01.base.bo.friend.FriendMessage;
import com.funcy.g01.base.bo.friend.FriendNpc;
import com.funcy.g01.base.bo.friend.FriendReq;
import com.funcy.g01.base.bo.friend.RoleFriendInfo;
import com.funcy.g01.base.bo.npc.RoleNpcsInfo;
import com.funcy.g01.base.bo.ranking.RoleRankingInfo;
import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.bo.role.RoleNetInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.base.bo.totem.RoleSkillInfo;
import com.funcy.g01.base.bo.totem.SkillVo;
import com.funcy.g01.base.bo.user.FrontVersion;
import com.funcy.g01.base.dao.redis.AchievementRepo;
import com.funcy.g01.base.dao.redis.DressRepo;
import com.funcy.g01.base.dao.redis.EmailRepo;
import com.funcy.g01.base.dao.redis.FriendRepo;
import com.funcy.g01.base.dao.redis.ItemRepo;
import com.funcy.g01.base.dao.redis.NpcRepo;
import com.funcy.g01.base.dao.redis.PhotoFrameRepo;
import com.funcy.g01.base.dao.redis.RoleRepo;
import com.funcy.g01.base.dao.redis.TotemRepo;
import com.funcy.g01.base.data.ItemData;
import com.funcy.g01.base.data.ItemProperty;
import com.funcy.g01.base.data.NpcData;
import com.funcy.g01.base.data.PhysicsConfigData;
import com.funcy.g01.base.data.PhysicsObjData;
import com.funcy.g01.base.data.ShopData;
import com.funcy.g01.base.data.SkillData;
import com.funcy.g01.base.data.SkillProperty;
import com.funcy.g01.base.data.SpecialCityData;
import com.funcy.g01.base.data.UnitData;
import com.funcy.g01.base.data.WordData;
import com.funcy.g01.base.data.WordPosition;
import com.funcy.g01.base.data.WordProperty;
import com.funcy.g01.base.global.BusinessPool;
import com.funcy.g01.base.global.PlatformConfig;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.net.CmdStatus;
import com.funcy.g01.base.net.GamePlayer;
import com.funcy.g01.base.net.PlayerState;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.GlobalChatMsgProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleAchievementInfoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleDressInfoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleFrameInfoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleHallInfoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleItemInfoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleNpcsInfoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleSkillInfoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleTotemInfoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleWeekFightInfoProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.PlayerEnterProto;
import com.funcy.g01.base.proto.service.AccountServiceRespProtoBuffer.ForceQuitRespProto;
import com.funcy.g01.base.proto.service.AccountServiceRespProtoBuffer.HallLoginResp;
import com.funcy.g01.base.proto.service.AccountServiceRespProtoBuffer.PingRespProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.EmptyReqProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.StrIntProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.StrProto;
import com.funcy.g01.base.util.DirtyUtil;
import com.funcy.g01.base.util.HttpUtils;
import com.funcy.g01.base.util.MapUtil;
import com.funcy.g01.base.util.TimeUtil;
import com.funcy.g01.base.util.YunpianHelper;
import com.funcy.g01.dispatcher.bo.RoomType;
import com.funcy.g01.login.bo.admin.WhiteListData;
import com.google.protobuf.Message.Builder;

@Service
public class AccountService {
	
	@Autowired
	private ServerContext serverContext;
	
	@Autowired
	private ServerInfoData serverInfoData;
	
	@Autowired
	private BusinessPool businessPool;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private AchievementRepo achievementRepo;
	
	@Autowired
	private PhysicsConfigData physicsConfigData;
	
	@Autowired
	private UnitData unitData;
	
	@Autowired
	private PhysicsObjData physicsObjData;
	
	@Autowired
	private TotemRepo totemRepo;
	
	@Autowired
	private PlatformConfig platformConfig;
	
	@Autowired
	private ItemRepo itemRepo;
	
	@Autowired
	private DressRepo dressRepo;
	
	@Autowired
	private PhotoFrameRepo photoFrameRepo;
	
	@Autowired
	private AchievementService achievementService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ActiveService activeService;
	
	@Autowired
	private WordData wordData;
	
	@Autowired
	private EmailRepo emailRepo;
	
	@Autowired
	private ShopData shopData;
	
	@Autowired
	private NpcRepo npcRepo;
	
	@Autowired
	private NpcData npcData;
	
	@Autowired
	private ItemDomainService itemDomainService;
	
	@Autowired
	private ItemData itemData;
	
	@Autowired
	private SkillData skillData;
	
	@Autowired
	private FriendRepo friendInfoRepo;
	
	@Autowired
	private FriendService friendService;
	
	@Autowired
	private SpecialCityData specialCityData;
	
	@Autowired
	private YunpianHelper yunpianHelper;
	
	@Autowired
	private WhiteListData whiteListData;
	
	private static Logger logger = Logger.getLogger(AccountService.class);
	
	private static class InitRoleResult {
		Role role;
		boolean isNew = false;
	}
	
	public void loginAndEnterHall(String platformId, String token, String ext, int channelId, int roomId, String version, String installVersion, final GamePlayer gamePlayer) {
		final String serviceAndMethod = "accountService.loginAndEnterHall";
//		String[] temp = token.split(";");
//		long tokenCreateTime = Long.parseLong(temp[0]);
//		
//		//check token
//		String actToken = temp[1];
//		String serverToken = DigestUtils.md5Hex(platformId + tokenCreateTime + ServerConfig.login_token_ext);
//		if(!serverToken.equals(actToken)) {
//			throw new RuntimeException();
//		}
		// 检查版本号
		if (!"".equals(version) && version != null) {
			String[] nums = version.split("\\.");
			int versionNum1 = Integer.parseInt(nums[0]);
			int versionNum2 = Integer.parseInt(nums[1]);

			FrontVersion curVersion = serverContext.getCurVersion();
			if(!curVersion.getIsInTest() || (curVersion.getIsInTest() && (whiteListData.isContainTheIp(gamePlayer.getIp()) || whiteListData.isInWhiteList(platformId)))){
				if (versionNum1 < curVersion.getVersionNum1()
						|| (versionNum1 == curVersion.getVersionNum1() && versionNum2 < curVersion.getVersionNum2())) {
					HallLoginResp.Builder builder = HallLoginResp.newBuilder().setLoginState(-1);// 需要更新版本
					gamePlayer.respond(serviceAndMethod, CmdStatus.notDecrypt, builder);
					return;
				}
			}
		}
		//检查重复登录
		long roleId = roleRepo.getRoleIdByPlatformId(platformId);
		final GamePlayer logonGamePlayer = serverContext.findHallLogonPlayer(roleId);
		FightRoom beforeRoom = null;
		if(logonGamePlayer != null) {
			FightPlayer fightPlayer = logonGamePlayer.getFightPlayer();
			if(fightPlayer != null && fightPlayer.getFightRoom() != null && serverContext.getHallFightRoom(fightPlayer.getFightRoom().getRoomId()) != null) { //房间存在，优先进之前的房间
				//替换该fightPlayer
				logonGamePlayer.setFightPlayer(null);
				logonGamePlayer.setSynRoom(null);
				beforeRoom = fightPlayer.getFightRoom();
			}
			
			//之前的玩家强制退出
			serverContext.removeHallLogonPlayer(logonGamePlayer);
			serverContext.removeConnectedPlayer(logonGamePlayer);
			
			ForceQuitRespProto.Builder forceQuitBuilder = ForceQuitRespProto.newBuilder();
			forceQuitBuilder.setReasonType(ForceQuitType.mutiLogin.ordinal());//另一个设备
			logonGamePlayer.forceRespond("accountService.forceQuit", CmdStatus.notDecrypt,forceQuitBuilder);
			businessPool.schedule(new Runnable() {
				@Override
				public void run() {
					serverContext.removeConnectedPlayer(logonGamePlayer);
					serverContext.removeHallLogonPlayer(logonGamePlayer);
					logonGamePlayer.destroy();
				}
			}, 300, TimeUnit.MILLISECONDS);
		}
		
		//查看房间是否存在
		final FightRoom fightRoom = beforeRoom != null ? beforeRoom : serverContext.getHallFightRoom(roomId);
		if(fightRoom == null || fightRoom.getFightRoomState() == FightRoomState.ended) {
			HallLoginResp.Builder builder = HallLoginResp.newBuilder();
			builder.setLoginState(1);//1重试
			logger.info("room not exist so need reconnect");
			gamePlayer.respond(serviceAndMethod, CmdStatus.notDecrypt, builder);
			businessPool.schedule(new Runnable() {
				@Override
				public void run() {
					serverContext.removeConnectedPlayer(gamePlayer);
					serverContext.removeHallLogonPlayer(gamePlayer);
					gamePlayer.destroy();
				}
			}, 300, TimeUnit.MILLISECONDS);
			return;
		}
		
		final HallLoginResp.Builder builder = HallLoginResp.newBuilder();
		//初始化用户
		InitRoleResult initRoleResult = loadOrCreateRole(platformId, gamePlayer, token, builder);
		final Role role = initRoleResult.role;
		RoleNetInfo roleNetInfo = roleRepo.getRoleNetInfo(role.getId());
		if(roleNetInfo.getHallServerId() != -1 && roleNetInfo.getHallServerId() != serverInfoData.getSelfServerId()) { //当前在另一台服务器上，退出重试
			HallLoginResp.Builder builder0 = HallLoginResp.newBuilder();
			builder0.setLoginState(1);//1重试
			logger.info("serverId not right so need reconnect");
			gamePlayer.respond(serviceAndMethod, CmdStatus.notDecrypt, builder0);
			businessPool.schedule(new Runnable() {
				@Override
				public void run() {
					serverContext.removeConnectedPlayer(gamePlayer);
					serverContext.removeHallLogonPlayer(gamePlayer);
					gamePlayer.destroy();
				}
			}, 300, TimeUnit.MILLISECONDS);
			return;
		}
		
		refreshLastDay(role);
		roleNetInfo.setHallServerId(serverInfoData.getSelfServerId());
		roleNetInfo.setHallRoomId(roomId);
		roleRepo.saveRoleNetInfo(roleNetInfo);
		
		builder.setKey(gamePlayer.getKey());
		builder.setNewComerStep(role.getNewComerStep());
		RoleTotemInfoProto roleTotemInfoProto = (RoleTotemInfoProto) totemRepo.getRoleTotemInfo(role.getId()).copyTo();
		builder.addAllTotems(roleTotemInfoProto.getTotemsList());
		RoleSkillInfoProto roleSkillInfoProto = (RoleSkillInfoProto) totemRepo.getRoleSkillInfo(role.getId()).copyTo();
		builder.addAllSkills(roleSkillInfoProto.getSkillsList());
		RoleDressInfoProto roleDressInfoProto = (RoleDressInfoProto) dressRepo.getRoleDressInfo(role.getId()).copyTo();
		builder.addAllDresses(roleDressInfoProto.getDressesList());
		RoleItemInfoProto itemInfoProto = (RoleItemInfoProto) itemRepo.getRoleItemInfo(role.getId()).copyTo();
		builder.addAllItems(itemInfoProto.getItemsList());
		RoleWeekFightInfoProto roleWeekFightInfoProto = (RoleWeekFightInfoProto) roleRepo.getRoleWeekFightInfo(role.getId()).copyTo();
		builder.setRoleWeekFightInfo(roleWeekFightInfoProto);
		builder.setRoleNpcsInfo((RoleNpcsInfoProto) npcRepo.getRoleNpcsInfo(roleId).copyTo());
		
		RoleAchieveInfo roleAchieveInfo = achievementRepo.getRoleAchieveInfo(roleId);
		if (roleAchieveInfo.checkNewAchievements()) {
			achievementRepo.saveRoleAchieveInfo(roleAchieveInfo);
		}
		builder.setRoleAchievementInfo((RoleAchievementInfoProto) roleAchieveInfo.copyTo());
		
		builder.setActiveInfo(activeService.getActiveInfoProto(roleId));
		
		List<Email> emails = emailService.getEmailList(gamePlayer);
		if(emails != null && emails.size() > 0){
			for (Email email : emails) {
				builder.addEmails(email.copyTo());
			}
		}
		
		RoleFrameInfoProto roleFrameInfoProto = (RoleFrameInfoProto) photoFrameRepo.getRoleFrameInfo(roleId).copyTo();
		builder.addAllPhotoFrames(roleFrameInfoProto.getFramesList());
		builder.setRoleVo(role.buildFrontProto());
		builder.setServerTime(System.currentTimeMillis());
		initFriends(builder, roleId);
		
		final RoleHallInfoProto roleHallInfoProto = roleRepo.getRoleHallInfo(gamePlayer.getRoleId());
		
		fightRoom.executeRightNow(new SynEvent(gamePlayer.getRoleId(), serviceAndMethod) {
			@Override
			public void executeEvent() {
				gamePlayer.setSynRoom(fightRoom);
				FightPlayer fightPlayer = fightRoom.findFightPlayer(gamePlayer.getRoleId());
				builder.setIsReenter(fightPlayer != null);
				if(fightPlayer == null) {
					PlayerEnterProto.Builder enterProtoBuilder = PlayerEnterProto.newBuilder();
					enterProtoBuilder.setEventType(EventType.enter_fight_event_type.getCode());
					enterProtoBuilder.setFrameIndex(fightRoom.getFrameIndex());
					enterProtoBuilder.setPlayerInfo(role.buildPlayerInfoProto4Front());
					fightPlayer = new FightPlayer(gamePlayer, enterProtoBuilder.build());
					fightPlayer.initSkills(new BuildFightSkillResult());
					fightPlayer.setRole(role);
					fightPlayer.init(physicsConfigData, unitData, physicsObjData);
					gamePlayer.setFightPlayer(fightPlayer);
					fightRoom.playerEnter(fightPlayer, roleHallInfoProto);
				} else {
					gamePlayer.setFightPlayer(fightPlayer);
					fightPlayer.setGamePlayer(gamePlayer);
					fightPlayer.reconnect();
				}
				
				builder.setSelfUnitInfo(fightPlayer.getFightUnit().buildProto());
				builder.setRoomInfo(fightRoom.buildProto());
				gamePlayer.respond(serviceAndMethod, CmdStatus.notDecrypt, builder);
			}
		});
	}
	
	private void initFriends(HallLoginResp.Builder builder, long roleId){
		RoleFriendInfo roleFriendInfo = friendInfoRepo.getRoleFriendInfo(roleId);
		builder.addFriendRoleIds(FriendNpc.npc_friend_id);
		if(roleFriendInfo != null) {
			for(Friend friend : roleFriendInfo.getFriends()) {
				builder.addFriendRoleIds(friend.getRoleId());
			}
		}
		/*填充好友申请列表*/
		List<FriendReq> friendReqs = friendService.getFriendReqList(roleId);
		if(friendReqs != null && friendReqs.size() > 0){
			for (FriendReq friendReq : friendReqs) {
				builder.addFriendReqs(friendReq.build4Front());
			}
		}
		/*填充好友离线消息列表*/
		List<FriendMessage> friendMsgs = friendService.getOfflineFriendMessageList(roleId);
		if(friendMsgs != null && friendMsgs.size() > 0){
			for (FriendMessage friendMessage : friendMsgs) {
				builder.addOfflineFriendMsgs(friendMessage.copyTo());
			}
		}
	}
	
	public void reportRocation(String location, GamePlayer gamePlayer) {
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		role.setCity(location);
		roleRepo.saveRole(role);
	}
	
	private InitRoleResult loadOrCreateRole(String platformId, GamePlayer gamePlayer, String token, HallLoginResp.Builder builder) {
		long roleId = roleRepo.getRoleIdByPlatformId(platformId);
		Role role = roleRepo.getRole(roleId);
		InitRoleResult initRoleResult = new InitRoleResult();
		if (role == null) {
			role = new Role(roleId, platformId, gamePlayer.getIp());
			role.setRegIp(gamePlayer.getIp());
			initData(role);
			initRoleResult.isNew = true;
			
			FriendMessage friendNpcMessage = new FriendMessage(FriendNpc.npc_friend_id, roleId, FriendNpc.login_talking_words, System.currentTimeMillis());
			builder.addOfflineFriendMsgs(friendNpcMessage.copyTo());
		} 
		
		//发短信通知陪玩 TODO 正式服需要删除
  		//规则，半小时内，最多只发一次通知
  		//玩家玩的盘数，小于20场，注册时间小于48小时，短信包含内容，玩的场次，注册时间，玩家名字，玩家id，玩家等级，玩家地址
		//消息：登陆通知：新用户/老用户%s|名字|%d级|已注册%d时%d分|上次登录%时%d分前|地址是%s|萨满 %d/%d|兔子 %d/%d
		long curTime = System.currentTimeMillis();
		if((initRoleResult.isNew || curTime - role.getLoginTime() > TimeUnit.MINUTES.toMillis(30)) && platformConfig.getPlatformType().equals("onlinecheck") && !whiteListData.isContainTheIp(gamePlayer.getIp())) {
			if(role.getMouseFightNum() + role.getSamanNum() < 20) {
				if(curTime - role.getCreateTime() < TimeUnit.HOURS.toMillis(48)) {
					businessPool.schedule(new Runnable() {
						@Override
						public void run() {
							try {
								long curTime2 = System.currentTimeMillis();
								Role curRole = roleRepo.getRole(roleId);
								String isNewStr = initRoleResult.isNew ? "新用户" : "老用户";
								long regTime = curTime2 - curRole.getCreateTime();
								int regHour = (int) (regTime / TimeUnit.HOURS.toMillis(1));
								int regMin = (int) ((regTime % TimeUnit.HOURS.toMillis(1)) / TimeUnit.MINUTES.toMillis(1));
								long loginTime = curTime2 - curRole.getLoginTime();
								int loginHour = (int) (loginTime / TimeUnit.HOURS.toMillis(1));
								int loginMin = (int) ((loginTime % TimeUnit.HOURS.toMillis(1)) / TimeUnit.MINUTES.toMillis(1));
								String msg = String.format("【新月狼人杀】%s|名字 %s|%d级|已注册%d时%d分|上次登录%d时%d分前|地址%s|萨满%d/%d|兔子%d/%d", isNewStr, curRole.getRoleName(), curRole.getRoleLevel(), regHour, regMin, loginHour, loginMin
										, curRole.getCity(), curRole.getSamanWinNum(), curRole.getSamanNum(), curRole.getMouseWinNum(), curRole.getMouseFightNum());
								yunpianHelper.sendSms("18684750035", msg);
								yunpianHelper.sendSms("15620630657", msg);
							} catch(Exception e) {
								e.printStackTrace();
							}
						}
					}, 5, TimeUnit.SECONDS);
				}
			}
		}
		
		initRoleResult.role = role;
		
		if (role.getDresses().size() == 0) {
			role.initDresses();
		}
		
		if (role.getShammanLevel() == 0) {
			role.setShammanLevel(1);
		}
		
		gamePlayer.setPlatformId(platformId);
		String key = DigestUtils.md5Hex(token + ServerConfig.key_ext);
		gamePlayer.setKey(key.substring(8, 24));
		
		this.sencondDayLoginRefresh(role, initRoleResult.isNew);
		
		role.setLoginIp(gamePlayer.getIp());
		role.setLoginTime(System.currentTimeMillis());
		gamePlayer.setRoleId(roleId);
        serverContext.addHallLogonPlayer(gamePlayer);
        gamePlayer.casState(PlayerState.connected, PlayerState.logon);
        serverContext.removeConnectedPlayer(gamePlayer);
        roleRepo.saveRole(role);
        
        if (initRoleResult.isNew) {
			activeService.addChest(roleId, 1, ChestType.xinshou1.getCode());
		}
        
        RoleNpcsInfo roleNpcsInfo = npcRepo.getRoleNpcsInfo(roleId);
        if(roleNpcsInfo == null) {
        	roleNpcsInfo = new RoleNpcsInfo(roleId);
        	roleNpcsInfo.init(npcData);
        } else {
        	roleNpcsInfo.refresh(npcData);
        }
        npcRepo.saveRoleNpcsInfo(roleNpcsInfo);
        
		return initRoleResult;
	}
	
	//新一天登录做处理 累计登录 签到  年月卡奖励发送
	public void sencondDayLoginRefresh(Role role, boolean isNew){
		long roleId = role.getId();
		if (System.currentTimeMillis() >= role.getLoginTime()) {
			Date lastLoginDate = new Date(role.getLoginTime());
			Date nowDate = new Date(System.currentTimeMillis());
			if (isNew || lastLoginDate.getDate() != nowDate.getDate()) {
				if (System.currentTimeMillis() - role.getLoginTime() > 24*60*60*1000) {
					role.setContinueLogin(1);
				}else{
					role.setContinueLogin(role.getContinueLogin() + 1);
				}
				role.setCumulativeLogin(role.getCumulativeLogin() + 1);
				this.checkMonthCardAndYearCardReward(role);
				activeService.resetSigninState(roleId);
			}
			List<UpdateAchievement> updateAchievements = new ArrayList<UpdateAchievement>();
			updateAchievements.add(new UpdateAchievement(roleId, AchievementType.continue_login, role.getContinueLogin()));
			updateAchievements.add(new UpdateAchievement(roleId, AchievementType.cumulative_login, role.getCumulativeLogin()));
			achievementService.updateAchievements(role.getId(), updateAchievements);
		}
	}
	
	public void checkMonthCardAndYearCardReward(Role role){
		long currentTime = System.currentTimeMillis();
		if (role.getMonthCardExpirationTime() >= currentTime) {
			WordProperty word = wordData.getWordProperty(WordPosition.firstRecharge.getCode());
			String[] words = word.getWord().split("\\*");
			int emailId = emailRepo.newEmailId();
			Email email = new Email(emailId,words[0], words[1]
					, ServerConfig.system_role_id, words[2], EmailType.SINGLE);
			email.setAccepterId(role.getId());
			email.setRewards(shopData.getMonthcardShopProperty().getReward());
			emailService.sendRoleEmail(email);
		}
		if (role.getYearCardExpirationTime() >= currentTime) {
			WordProperty word = wordData.getWordProperty(WordPosition.firstRecharge.getCode());
			String[] words = word.getWord().split("\\*");
			int emailId = emailRepo.newEmailId();
			Email email = new Email(emailId,words[0], words[1]
					, ServerConfig.system_role_id, words[2], EmailType.SINGLE);
			email.setAccepterId(role.getId());
			email.setRewards(shopData.getYearcardShopProperty().getReward());
			emailService.sendRoleEmail(email);
		}
	}

	private void initData(Role role) {
		
	}

	private void refreshLastDay(Role role) {
		if(TimeUtil.getTodayIntValue() > role.getLastSyncAt()) {
//			role.setLastSyncAt(TimeUtil.getTodayIntValue());
//			
//			long createTime = role.getCreateTime();
//			long curTime = System.currentTimeMillis();
//			if(Math.ceil((curTime - createTime) * 1f / TimeUnit.DAYS.toMillis(1)) <= 3) {
//				role.setNewComerDayRebornLeftNum(Role.defaultNewComerDayRebornNum);
//			}
//			roleRepo.saveRole(role);
		}
	}
	
	public void sendLogonPlayerSystemEmail(int emailId, int roleId, int emailTypeCode){
		emailService.sendLogonPlayerEmail(emailId, roleId, emailTypeCode);
	}
	
	public void useBtnPanelItem(int xmlId, GamePlayer gamePlayer) {
		ItemProperty itemProperty = itemData.getItemProperty(xmlId);
		if(!itemProperty.isBtnLayerUse()) {
			throw new RuntimeException();
		}
		
		itemDomainService.consumeItem(xmlId, 1, gamePlayer.getRoleId());
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		role.changeUseItemLeftCount(xmlId, itemProperty.getUseAddNum());
		roleRepo.saveRole(role);
	}
	
	public void recordNewComerStep(int step, GamePlayer gamePlayer) {
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		role.setNewComerStep(step);
		roleRepo.saveRole(role);
	}
	
	/**
	 * 根据经纬度坐标获得城市信息
	 */
	public Builder getCityByCoordinate(String latitude,String longitude,GamePlayer gamePlayer){
		String city = MapUtil.getAddressInformation(latitude, longitude, specialCityData);
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		role.setCity(city);
		roleRepo.saveRole(role);
		return StrProto.newBuilder().setParams1(city);
	}
		
	public Object ping(int lastPingTime, GamePlayer gamePlayer) {
		ReusedProtoBuilder reusedProtoBuilder = ProtoBuilderPoolManager.getBuilder(PingRespProto.class);
		PingRespProto.Builder builder = (PingRespProto.Builder) reusedProtoBuilder.getBuilder();
		if(lastPingTime < 0) { //第一次
			builder.setAvePingTime(gamePlayer.getPingTime());
		} else {
			builder.setAvePingTime(gamePlayer.addPingTime(lastPingTime));
		}
		return reusedProtoBuilder;
	}
	
	public Object tick(GamePlayer gamePlayer) {
		ReusedProtoBuilder reusedProtoBuilder = ProtoBuilderPoolManager.getBuilder(EmptyReqProto.class);
		return reusedProtoBuilder;
	}
	
	public Builder globalChat(String msg, int type, GamePlayer gamePlayer) {
		//resp StrIntProto params2为code code == 0成功 code == 1道具不足 code == 2钻石不足
		final int xmlId = 400004;
		final int consumeCheese = 5;
		if(type == 0) { //使用道具
			try {
				itemDomainService.consumeItem(xmlId, 1, gamePlayer.getRoleId());
			} catch(RuntimeException runtimeException) {
				return StrIntProto.newBuilder().setParams2(1); 
			}
		} else { //使用奶酪
			Role role = roleRepo.getRole(gamePlayer.getRoleId());
			if(role.getCheese() < consumeCheese) {
				return StrIntProto.newBuilder().setParams2(2); 
			}
			role.spend(new Currency(CurrencyType.cheese, consumeCheese));;
			roleRepo.saveRole(role);
		}
		/*只允许60个中文字*/
		int toIndex = 60;
		if(msg.length() < toIndex) {
			toIndex = msg.length();
		}
		msg = msg.substring(0, toIndex);
		/*敏感词过滤*/
		final String filterdMsg = DirtyUtil.replace(msg);
		
		notifyGlobalMsg(gamePlayer.getRoleId(), filterdMsg);
		
		return StrIntProto.newBuilder().setParams1(filterdMsg);
	}
	
	public void changeCurRoomType(int type, GamePlayer gamePlayer) {
		RoomType roomType = RoomType.valueOf(type);
		if(gamePlayer.getFightPlayer() != null && roomType != RoomType.tryPlay) {
			gamePlayer.getFightPlayer().setHallPlayerStayRoomType(roomType);
		}
	}
	
	public void notifyGlobalMsg(long roleId, String msg) {
		GlobalChatMsgProto.Builder builder = GlobalChatMsgProto.newBuilder();
		builder.setMsg(msg);
		Role role = roleRepo.getRole(roleId);
		builder.setCity(role.getCity());
		builder.setPhotoFrame(role.getPhotoFrame());
		builder.setRoleId(roleId);
		builder.setRoleLevel(role.getRoleLevel());
		builder.setRoleName(role.getRoleName());
		builder.setSex(role.getSex());
		builder.setShammanLevel(role.getShammanLevel());
		builder.setPhoto(role.getPhoto());

		for(GamePlayer gamePlayer : serverContext.getHallLogonPlayers().values()) {
			gamePlayer.respond("notifyGlobalMsg", CmdStatus.notDecrypt, builder);
		}
	}
}
