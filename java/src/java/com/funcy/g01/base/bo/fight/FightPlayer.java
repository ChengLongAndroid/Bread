package com.funcy.g01.base.bo.fight;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.ibatis.jdbc.Null;
import org.apache.log4j.Logger;
import org.jbox2d.common.Vec2;

import com.funcy.g01.base.bo.chest.ChestSlot;
import com.funcy.g01.base.bo.email.RewardCodeData;
import com.funcy.g01.base.bo.fight.FightSkillBuilder.BuildFightSkillResult;
import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.data.PhysicsConfigData;
import com.funcy.g01.base.data.PhysicsConfigProperty;
import com.funcy.g01.base.data.PhysicsObjData;
import com.funcy.g01.base.data.UnitData;
import com.funcy.g01.base.data.UnitProperty;
import com.funcy.g01.base.net.CmdStatus;
import com.funcy.g01.base.net.GamePlayer;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleHallInfoProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.CommonEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.EmptyEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.PlayerEnterProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.PlayerInfoProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.PlayerInfoWithUnitProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.UnitInfoProto;
import com.funcy.g01.dispatcher.bo.RoomType;
import com.google.protobuf.Message.Builder;

public class FightPlayer {
	
	private static final Logger logger = Logger.getLogger(FightPlayer.class);
	
	private FightRoom fightRoom;
	
	private GamePlayer gamePlayer;
	
	private long roleId;
	
	private PlayerEnterProto playerEnterProto;
	
	private PhysicsConfigData physicsConfigData;
	
	private UnitData unitData;
	
	private PhysicsObjData physicsObjData;
	
	private FightUnit fightUnit;
	
	private int winFrame;
	
	private int gotCheeseNum;
	
	private int rankingNo1Num;
	
	private volatile int helpOtherMiceSuccessNum;
	
	private boolean haveGotDropItem = false;
	
	private boolean isOnline = true;
	
	private int offLineStartFrameIndex;
	
	public static final int init_pos_x = 667;
	
	public static final int init_pos_y = 300;
	
	private int lastActionFrame = 0;
	
	private transient Role role;
	
	private boolean unitIsSpec = false;
	
	private CampType campType = CampType.none;
	
	private List<FightSkill> samanSkills;
	
	private List<FightSkill> mouseSkills;
	
	private boolean isUsedSpeak = false;
	
	private boolean isUsedEmote = false;
	
	private boolean isDead = false;
	
	private ChestSlot chestSlot;
	
	private int ranking;
	
	private RoomType wantedRoomType; //注意，不一定与所在房间的roomtype相同
	
	private FightPlayerRewardResult rewardResult = new FightPlayerRewardResult();
	
	private boolean hadWarnActionPrepareTimeout = false;
	
	private boolean lastFightIsNotAction = false;
	
	private int delayFrame = 20;
	
	private LinkedList<Integer> delayFrames = new LinkedList<Integer>();
	
	private volatile RoomType hallPlayerStayRoomType = RoomType.hall;
	
	public FightPlayer(GamePlayer gamePlayer, PlayerEnterProto playerEnterProto) {
		this.gamePlayer = gamePlayer;
		this.roleId = gamePlayer.getRoleId();
		this.playerEnterProto = playerEnterProto;
	}
	
	public void init(PhysicsConfigData physicsConfigData, UnitData unitData, PhysicsObjData physicsObjData) {
		this.physicsConfigData = physicsConfigData;
		this.unitData = unitData;
		this.physicsObjData = physicsObjData;
	}
	
	public void enterRoom(final FightRoom fightRoom) {
		this.fightRoom = fightRoom;
	}
	
	public void initUnit(boolean isSpec, RoleHallInfoProto roleHallInfoProto) {
		if(roleHallInfoProto == null) {
			this.initUnit(new Vec2(init_pos_x * 1f / 10f, init_pos_y * 1f / 10f), isSpec);
		} else {
			this.initUnit(new Vec2(roleHallInfoProto.getPosX() / 1000f, roleHallInfoProto.getPosY() / 1000f), isSpec);
		}
	}
	
	public void initUnit(Vec2 initPos, boolean isSpec) {
		if(initPos == null) {
			initPos = new Vec2(init_pos_x * 1f / 10f, init_pos_y * 1f / 10f);
		}
		int xmlId = 10004; //单位默认xmld
		UnitProperty unitProperty = unitData.getUnitProperty(xmlId);
		PhysicsConfigProperty physicsConfigProperty = physicsConfigData.getPhysicsConfigProperty(1);
		FightUnit fightUnit = FightUnit.createNewFightUnit(this.fightRoom, roleId, fightRoom.getWorld(), unitProperty, physicsConfigProperty, initPos, isSpec);
		this.unitIsSpec = isSpec;
		fightUnit.setFightPlayer(this);
		this.fightUnit = fightUnit;
		this.fightRoom.addFightUnit(fightUnit);
	}
	
	public void reset() {
		this.fightUnit = null;
		this.winFrame = 0;
		this.ranking = 0;
		this.helpOtherMiceSuccessNum = 0;
		this.unitIsSpec = false;
		if(this.samanSkills != null) {
			for(FightSkill fightSkill : this.samanSkills) {
				fightSkill.resetUseCount();
			}
		}
		if(this.mouseSkills != null) {
			for(FightSkill fightSkill : this.mouseSkills) {
				fightSkill.resetUseCount();
			}
		}
		this.chestSlot = null;
		this.rewardResult = new FightPlayerRewardResult();
	}
	
	public RoomType getWantedRoomType() {
		return wantedRoomType;
	}

	public void setWantedRoomType(RoomType wantedRoomType) {
		this.wantedRoomType = wantedRoomType;
	}

	public void offLine() {
		this.isOnline = false;
		this.offLineStartFrameIndex = this.fightRoom.getFrameIndex();
	}
	
	public boolean isOnline() {
		return this.isOnline;
	}
	
	public PlayerEnterProto getPlayerEnterProto() {
		return playerEnterProto;
	}

	public FightRoom getFightRoom() {
		return fightRoom;
	}

	public void setFightRoom(FightRoom fightRoom) {
		this.fightRoom = fightRoom;
	}

	public long getRoleId() {
		return roleId;
	}
	
	public void respond(String serviceAndMethod, CmdStatus cmdStatus, Object builder) {
		if(this.gamePlayer != null) {
			this.gamePlayer.respond(serviceAndMethod, cmdStatus, builder);
		}
	}
	
	public int getLastActionFrame() {
		return lastActionFrame;
	}

	public void setLastActionFrame(int lastActionFrame) {
		this.lastActionFrame = lastActionFrame;
		if(hadWarnActionPrepareTimeout) {
			hadWarnActionPrepareTimeout = false;
		}
		if(this.lastFightIsNotAction) {
			this.lastFightIsNotAction = false;
		}
	}
	
//	public void win(int curFrame) {
//		this.winFrame = curFrame;
//		this.sendUnitWinEvent(); //TODO
//		this.fightUnit = null;
//	}
	
	public boolean isHadWarnActionPrepareTimeout() {
		return hadWarnActionPrepareTimeout;
	}

	public void setHadWarnActionPrepareTimeout(boolean hadWarnActionPrepareTimeout) {
		this.hadWarnActionPrepareTimeout = hadWarnActionPrepareTimeout;
	}

	public GamePlayer getGamePlayer() {
		return gamePlayer;
	}

	public void sendUnitWinEvent() { //TODO
		CommonEventProto.Builder builder = CommonEventProto.newBuilder();
		builder.setEventType(EventType.unit_dead.getCode());
		builder.setFrameIndex(this.fightRoom.getFrameIndex());
		builder.setUnitInfo(this.fightUnit.buildProto());
		builder.setRoleId(this.roleId);
		for (FightPlayer player : this.fightRoom.getFightPlayers()) {
			player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder);
		}
	}
	
	public int getWinFrame() {
		return winFrame;
	}

	public boolean isUnitExist() {
		return this.fightUnit != null;
	}
	
	public void unitWin(long destinationRoleId) {
		this.addGotCheeseNum();
		this.fightRoom.unitWin(roleId, destinationRoleId);
		this.fightUnit = null;
		this.winFrame = this.fightRoom.getFrameIndex();
	}
	
	public void unitDead(UnitDeadReasonType unitDeadReasonType) {
		this.sendUnitDeadEvent(unitDeadReasonType);
		if(this.fightRoom != null) {
			this.fightRoom.unitDead(this);
		}
		this.isDead = true;
		this.fightUnit = null;
		this.gotCheeseNum = 0;
	}
	
	public void unitTransforDropItem(){
		this.fightRoom.unitTransforDropItem(roleId);
		this.haveGotDropItem = true;
		CommonEventProto.Builder builder = CommonEventProto.newBuilder();
		builder.setEventType(EventType.transfor_dropItem.getCode());
		builder.setFrameIndex(this.fightRoom.getFrameIndex());
		builder.setUnitInfo(this.fightUnit.buildProto());
		builder.setRoleId(this.roleId);
		for (FightPlayer player : this.fightRoom.getFightPlayers()) {
			player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder);
		}
	}
	
	public void sendUnitDeadEvent(UnitDeadReasonType unitDeadReasonType) {
		this.sendUnitDeadEvent(unitDeadReasonType, false, 0);
	}
	
	public void sendUnitDeadEvent(UnitDeadReasonType unitDeadReasonType, boolean canReborn, int rebornSkillXmlId) {
		if(this.fightUnit == null) {
			return;
		}
		CommonEventProto.Builder builder = CommonEventProto.newBuilder();
		builder.setEventType(EventType.unit_dead.getCode());
		builder.setFrameIndex(this.fightRoom.getFrameIndex());
		builder.setUnitInfo(this.fightUnit.buildProto());
		builder.setRoleId(this.roleId);
		builder.setDeadReason(unitDeadReasonType.ordinal());
		if(canReborn) {
			builder.setRebornSkillXmlId(rebornSkillXmlId);
			if(rebornSkillXmlId == Role.newComerRebornSkillId) {
				builder.setNewComerLeftRebornNum(this.getRole().getNewComerDayRebornLeftNum());
			}
		}
		for (FightPlayer player : this.fightRoom.getFightPlayers()) {
			player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder);
		}
	}
	
	public FightUnit getFightUnit() {
		return fightUnit;
	}
	
	public void quitFight() {
		if(this.fightUnit != null) {
			this.fightUnit.quitFight();
		}
		this.fightRoom = null;
		if(this.gamePlayer != null) {
			this.gamePlayer.setFightPlayer(null);
			this.gamePlayer.setSynRoom(null);
			this.gamePlayer = null;
		}
	}
	
	public void fightRoomQuitFight() {
		if(this.gamePlayer != null) {
			this.gamePlayer.setFightPlayer(null);
			this.gamePlayer.setSynRoom(null);
			this.gamePlayer = null;
		}
	}
	
	public void setGamePlayer(GamePlayer gamePlayer) {
		this.gamePlayer = gamePlayer;
	}

	public void unitRunAction(CommonEventProto commonEventProto) {
		EventType eventType = EventType.getEventTypeFromCode(commonEventProto.getEventType());
		if(eventType==null){
			return;
		}
		UnitInfoProto unitInfoProto = commonEventProto.getUnitInfo();
//		logger.info("receive unit action event:" + eventType.toString());
		if(this.fightUnit==null){
			return;
		}
		this.fightUnit.setLastSynInfoFrameIndex(this.fightRoom.getFrameIndex());
		switch (eventType) {
		case contact_syn_unit_info_event_type:
			this.fightUnit.refreshByUnitInfo(unitInfoProto);
			break;
		case start_moving_left_event_type:
			this.fightUnit.refreshByUnitInfo(unitInfoProto);
			this.fightUnit.startMoving(false, unitInfoProto);
			break;
		case start_moving_right_event_type:
			this.fightUnit.refreshByUnitInfo(unitInfoProto);
			this.fightUnit.startMoving(true, unitInfoProto);
			break;
		case end_moving_left_event_type:
			this.fightUnit.refreshByUnitInfo(unitInfoProto);
			this.fightUnit.stopMoving();
			break;
		case end_moving_right_event_type:
			this.fightUnit.refreshByUnitInfo(unitInfoProto);
			this.fightUnit.stopMoving();
			break;
		case jump_event_type:
			this.fightUnit.jump(unitInfoProto);
			break;
		case period_syn_unit_info_event_type:
			this.fightUnit.refreshByUnitInfo(unitInfoProto);
			break;
		case fly_event_type:
			this.fightUnit.fly();
			this.fightUnit.refreshByUnitInfo(unitInfoProto);
			break;
		case fly_jump_event_type:
			this.fightUnit.flyJump();
			this.fightUnit.refreshByUnitInfo(unitInfoProto);
			break;
		case stop_fly_event_type:
			this.fightUnit.stopFly();
			this.fightUnit.refreshByUnitInfo(unitInfoProto);
			break;
		case start_dive:
			this.fightUnit.startDive();
			this.fightUnit.refreshByUnitInfo(unitInfoProto);
			break;
		case stop_dive:
			this.fightUnit.stopDive();
			this.fightUnit.refreshByUnitInfo(unitInfoProto);
			break;
		default:
//			logger.error("player receive unsupport unit do action event type:" + eventType.toString());
			break;
		}
	}
	
	
	public void addGotCheeseNum(){
		gotCheeseNum += 1;
	}
	
	public void addRankingNo1Num(){
		rankingNo1Num += 1;
	}

	public int getGotCheeseNum() {
		return gotCheeseNum;
	}

	public void setGotCheeseNum(int cheeseNum) {
		this.gotCheeseNum = cheeseNum;
	}

	public int getRankingNo1Num() {
		return rankingNo1Num;
	}

	public void setRankingNo1Num(int rankingNo1Num) {
		this.rankingNo1Num = rankingNo1Num;
	}

	public void reconnect() {
		this.isOnline = true;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public PlayerInfoWithUnitProto.Builder buildWithUnitProto() {
		PlayerInfoWithUnitProto.Builder builder = PlayerInfoWithUnitProto.newBuilder();
		builder.setRoleId(this.getRoleId());
		builder.setRoleVo(this.role.buildFrontProto());
		if(this.fightUnit != null) {
			builder.setUnit(this.fightUnit.buildProto(true));
		}
		if (this.unitIsSpec) {
			if (helpOtherMiceSuccessNum != 0) {
				builder.setHelpOtherMiceSuccessNum(helpOtherMiceSuccessNum);
			}
		}
		return builder;
	}
	
	public PlayerInfoProto.Builder buildPlayerInfoProto4Front() {
		PlayerInfoProto.Builder builder = PlayerInfoProto.newBuilder();
		builder.setRoleId(this.roleId);
		builder.setRoleVo(this.getRole().buildFrontProto());
		builder.setUnitIsSpec(this.unitIsSpec);
		if (this.unitIsSpec) {
			if (helpOtherMiceSuccessNum != 0) {
				builder.setHelpOtherMiceSuccessNum(helpOtherMiceSuccessNum);
			}
		}
		return builder;
	}

	public boolean isOffLineTimeout(int frameIndex) {
		if(this.isOnline) {
			return false;
		}
		if(frameIndex - this.offLineStartFrameIndex <= FightConfig.fightplayer_offline_timeout_frames) {
			return false;
		}
		return true;
	}
	
	public void addHelpOtherMiceSuccessNum(){
		this.helpOtherMiceSuccessNum += 1;
	}

	public int getHelpOtherMiceSuccessNum() {
		return helpOtherMiceSuccessNum;
	}

	public void setHelpOtherMiceSuccessNum(int helpOtherMiceSuccessNum) {
		this.helpOtherMiceSuccessNum = helpOtherMiceSuccessNum;
	}

	public boolean isUnitIsSpec() {
		return unitIsSpec;
	}

	public void setUnitIsSpec(boolean unitIsSpec) {
		this.unitIsSpec = unitIsSpec;
	}

	public CampType getCampType() {
		return campType;
	}

	public void setCampType(CampType campType) {
		this.campType = campType;
	}

	public void initSkills(BuildFightSkillResult buildFightSkillResult) {
		this.samanSkills = buildFightSkillResult.samanSkills;
		this.mouseSkills = buildFightSkillResult.skills;
	}

	public List<FightSkill> getSamanSkills() {
		return samanSkills;
	}
	
	public FightSkill findSamanSkillById(int id){
		for (FightSkill fightSkill : samanSkills) {
			if (fightSkill.getFightSkillProperty().getId() == id) {
				return fightSkill;
			}
		}
		return null;
	}

	public List<FightSkill> getMouseSkills() {
		return mouseSkills;
	}

	public boolean isUsedSpeak() {
		return isUsedSpeak;
	}

	public void setUsedSpeak(boolean isUsedSpeak) {
		this.isUsedSpeak = isUsedSpeak;
	}

	public boolean isUsedEmote() {
		return isUsedEmote;
	}

	public void setUsedEmote(boolean isUsedEmote) {
		this.isUsedEmote = isUsedEmote;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	
	public ChestSlot getChestSlot() {
		return chestSlot;
	}

	public void setChestSlot(ChestSlot chestSlot) {
		this.chestSlot = chestSlot;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public int getRanking() {
		return ranking;
	}

	public FightPlayerRewardResult getRewardResult() {
		return rewardResult;
	}

	public void setRewardResult(FightPlayerRewardResult rewardResult) {
		this.rewardResult = rewardResult;
	}

	public void setPlayerEnterProto(PlayerEnterProto playerEnterProto) {
		this.playerEnterProto = playerEnterProto;
	}

	public void warnActionPrepareTimeout() {
		if(this.fightRoom == null) {
			return;
		}
		EmptyEventProto.Builder builder = EmptyEventProto.newBuilder();
		builder.setFrameIndex(this.fightRoom.getFrameIndex());
		builder.setRoleId(this.roleId);
		builder.setEventType(EventType.action_prepare_timeout_warn.getCode());
		respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder);
	}

	public boolean isLastFightIsNotAction() {
		return lastFightIsNotAction;
	}

	public void setLastFightIsNotAction(boolean lastFightIsNotAction) {
		this.lastFightIsNotAction = lastFightIsNotAction;
	}
	
	public void refreshDelayFrame(int pingTime) {
		int curDelayFrame = Math.round(pingTime * 1f / FightConfig.fps);
		if(this.delayFrames.size() < 30) {
			this.delayFrames.add(curDelayFrame);
		} else {
			this.delayFrames.removeFirst();
			this.delayFrames.addLast(curDelayFrame);
		}
		int maxDelay = 0;
		for(int delay : this.delayFrames) {
			if(delay > maxDelay) {
				maxDelay = delay;
			}
		}
		if(maxDelay > this.delayFrame) {
			this.delayFrame = maxDelay;
		} else if(maxDelay < this.delayFrame)  {
			this.delayFrame -= 2;
		}
		if(this.delayFrame > 40) {
			this.delayFrame = 40;
		}
	}

	public int getDelayFrame() {
		return delayFrame;
	}

	public RoomType getHallPlayerStayRoomType() {
		return hallPlayerStayRoomType;
	}

	public void setHallPlayerStayRoomType(RoomType hallPlayerStayRoomType) {
		this.hallPlayerStayRoomType = hallPlayerStayRoomType;
	}
}
