package com.funcy.g01.base.bo.fight;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.testbed.framework.TestbedController;

import com.ebo.synframework.nettybase.protoPool.ProtoBuilderPoolManager;
import com.ebo.synframework.nettybase.protoPool.ReusedProtoBuilder;
import com.funcy.g01.base.bo.achieve.AchievementType;
import com.funcy.g01.base.bo.achieve.UpdateAchievement;
import com.funcy.g01.base.bo.chest.ChestSlot;
import com.funcy.g01.base.bo.fight.FightSkillTrigger.ChangePhysicsObjResult;
import com.funcy.g01.base.bo.friend.Friend;
import com.funcy.g01.base.bo.friend.RoleFriendInfo;
import com.funcy.g01.base.bo.friend.RoleRecentPartnerInfo;
import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.bo.map.ServerMapInfo;
import com.funcy.g01.base.bo.npc.NpcTaskType;
import com.funcy.g01.base.bo.npc.RoleNpcsInfo;
import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.bo.serverconfig.ServerInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.base.dao.redis.FightRepo;
import com.funcy.g01.base.dao.redis.FriendRepo;
import com.funcy.g01.base.dao.redis.NpcRepo;
import com.funcy.g01.base.dao.redis.RoleRepo;
import com.funcy.g01.base.data.BattleResultData;
import com.funcy.g01.base.data.BattleResultValueProperty;
import com.funcy.g01.base.data.CheckpointData;
import com.funcy.g01.base.data.CheckpointProperty;
import com.funcy.g01.base.data.DropGroupData;
import com.funcy.g01.base.data.ItemData;
import com.funcy.g01.base.data.ItemProperty;
import com.funcy.g01.base.data.NpcData;
import com.funcy.g01.base.data.PhysicsConfigProperty;
import com.funcy.g01.base.data.PhysicsNailData;
import com.funcy.g01.base.data.PhysicsObjData;
import com.funcy.g01.base.data.PhysicsObjProperty;
import com.funcy.g01.base.data.RoleData;
import com.funcy.g01.base.data.RoleProperty;
import com.funcy.g01.base.data.SpecMouseTypeData;
import com.funcy.g01.base.data.SpecMouseTypeProperty;
import com.funcy.g01.base.global.BusinessPool;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.net.CmdStatus;
import com.funcy.g01.base.net.DispatchServer;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleHallInfoProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ChatEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.CommonEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ConjurePhysicsObjEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.DispatcherRoomProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.EmptyEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.FakeIndependentNailProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.FightMapInfoProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.FightRoomInfoProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.FightSkillObjEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.PeroidUpdateRoomInfoProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.PhysicsObjProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.PlayerChangeDressProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.PlayerEnterProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.SpeakEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.UseSkillEventProto;
import com.funcy.g01.base.proto.dispatcher.DispatcherRespCmdProtoBuffer.StartFightGetFightRoomRespProto;
import com.funcy.g01.base.proto.service.AccountServiceRespProtoBuffer.NotifyRoleInfoChangeMsgProto;
import com.funcy.g01.base.proto.service.HallServiceRespProtoBuffer.HallGetFightRoomInfoRespProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.LongStrProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.StrLongProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.StrProto;
import com.funcy.g01.base.proto.service.SynFightRespProtoBuffer.FightEndInfoProto;
import com.funcy.g01.base.proto.service.SynFightRespProtoBuffer.FightEndPlayerResultProto;
import com.funcy.g01.base.proto.service.SynFightRespProtoBuffer.FightRestartInfoProto;
import com.funcy.g01.base.proto.service.SynFightRespProtoBuffer.PeriodUpdateObjInfoProto;
import com.funcy.g01.base.proto.service.SynFightRespProtoBuffer.PeriodUpdateObjInfoProto.Builder;
import com.funcy.g01.base.proto.service.SynFightRespProtoBuffer.PrepareEndProto;
import com.funcy.g01.base.util.CalUtil;
import com.funcy.g01.base.util.ProbabilityGenerator;
import com.funcy.g01.dispatcher.bo.RoomType;
import com.funcy.g01.hall.service.AchievementService;
import com.funcy.g01.hall.service.ActiveService;
import com.funcy.g01.hall.service.ItemDomainService;
import com.funcy.g01.room.util.box2d.JoglTestbedMain;

public class FightRoom extends SingleThreadRoom {
	
	private static Logger logger = Logger.getLogger(FightRoom.class);
	
	private static final float screen_width = 133.4f;
	
	private static final float screen_height = 75;
	
	private List<FightPlayer> fightPlayers = new ArrayList<FightPlayer>();
	
	private long roomCreateTime = System.currentTimeMillis();
	
	private AtomicInteger physicsObjIdProducer = new AtomicInteger();
	
	private Map<Integer, PhysicsObj> physicsObjs = new TreeMap<Integer, PhysicsObj>();
	
	private TreeMap<Long, FightUnit> units = new TreeMap<Long, FightUnit>();
	
	private PhysicsConfigProperty physicsConfigProperty;
	
	private Random random = new Random();
	
	private World world;
	
	private int fightType = 1;
	
	private ServerInfoData serverInfoData;
	
	private PhysicsObjData physicsObjData;
	
	private PhysicsNailData physicsNailData;
	
	private BusinessPool businessPool;
	
	private FightRepo fightRepo;
	
	private RoleRepo roleRepo;
	
	private FriendRepo friendRepo;
	
	private AchievementService achievementService;
	
	private RoleData roleData;
	
	private ItemDomainService itemDomainService;
	
	private BattleResultData battleResultData;
	
	private DropGroupData dropGroupData;
	
	private ActiveService activeService;
	
	private ServerContext serverContext;
	
	private ItemData itemData;
	
	private float width;
	
	private float height;
	
	private PhysicsObj backgroundPhysicsObj;
	
	private DeadChecker deadChecker;
	
	private ServerMapInfo mapInfo;
	
	public static final long single_fight_frames = FightConfig.max_fight_frames;
	
	private List<PhysicsObj> mouseBorns = new ArrayList<PhysicsObj>();
	
	private List<PhysicsObj> specMouseBorns = new ArrayList<PhysicsObj>();
	
	private List<PhysicsObj> waterObjs = new ArrayList<PhysicsObj>();
	
	private long maxFightTime;
	
	private int curStateStartFrameIndex;
	
	private long curStateStartTime;
	
	private FightRoomState fightRoomState;
	
	private RoomType roomType;
	
	private TestbedController controller;
	
	private boolean isRestart = false;
	
	private int unitWinNum = 0;
	
	private List<FightPlayer> specFightPlayers = new ArrayList<FightPlayer>();
	
	private FightResult fightingResult;
	
	private FightResult fightWinResult;
	
	private int roomFightIndex = 1;
	
	private LinkedList<Long> lastPlayMapIds = new LinkedList<Long>(); //记录上5张地图id
	
	private HallTransferDoorConfig[] hallTransferDoorConfigs = {new HallTransferDoorConfig(0, 0, 0, 1)};
	
	private NpcRepo npcRepo;
	
	private NpcData npcData;
	
	private CheckpointData checkpointData;
	
	private SpecMouseTypeData specMouseTypeData;
	
	private boolean lastMapIsEasy = false;
	
	private final int[] normal_room_map_star_rate = {10, 30, 60};
	
	private final int[] normal_room_map_star_rate_without_easy = {30, 60};
	
	private final int[] hard_room_map_star_rate = {30, 70};
	
	private long prepareDestroyTime;
	
	private long lastHallTryMergeTime;
	
	private String channelName;
	
	public FightRoom(long roomId, PhysicsConfigProperty physicsConfigProperty, RoomType roomType) {
		super(roomId);
		this.physicsConfigProperty = physicsConfigProperty;
		this.roomType = roomType;
		this.width = 133.4f;
		this.height = 75;
		this.maxFightTime = single_fight_frames;
		if(this.roomType == RoomType.hall) {
			this.fightRoomState = FightRoomState.fighting;
		} else {
			logger.info("fight(roomId:"+this.getRoomId()+") preparing");
			this.fightRoomState = FightRoomState.preparing;
		}
		if(roomType== RoomType.hall){
			this.channelName = String.format(ServerConfig.channelNamePattern, roomId);
		}
	}
	
	public void init(PhysicsObjData physicsObjData, PhysicsNailData physicsNailData, FightRepo fightRepo, RoleRepo roleRepo,
			RoleData roleData, BattleResultData battleResultData, DropGroupData dropGroupData, ItemDomainService itemService,
			AchievementService	achievementService, BusinessPool businessPool, ServerContext serverContext, NpcRepo npcRepo, 
			NpcData npcData, CheckpointData checkpointData, ItemData itemData, ActiveService activeService, 
			SpecMouseTypeData specMouseTypeData, ServerInfoData serverInfoData, FriendRepo friendRepo) {
		this.friendRepo = friendRepo;
		this.physicsObjData = physicsObjData;
		this.physicsNailData = physicsNailData;
		this.fightRepo = fightRepo;
		this.businessPool = businessPool;
		this.serverContext = serverContext;
		this.roleRepo = roleRepo;
		this.achievementService = achievementService;
		this.roleData = roleData;
		this.battleResultData = battleResultData;
		this.dropGroupData = dropGroupData;
		this.itemDomainService = itemService;
		this.npcRepo = npcRepo;
		this.npcData = npcData;
		this.checkpointData = checkpointData;
		this.itemData = itemData;
		this.activeService = activeService;
		this.serverInfoData = serverInfoData;
		this.initPhysics();
		this.deadChecker = new DeadChecker(this);
		this.specMouseTypeData = specMouseTypeData;
		if(this.roomType != RoomType.hall) {
			new JoglTestbedMain().init(world, this);
		}
	}
	
	public void initPhysics() {
		this.world = new World(new Vec2(0, this.physicsConfigProperty.getGravityY()));
		this.world.setContactListener(new PhysicsContactHandler());
		this.world.setAutoClearForces(false);
	}
	
	public FightRoomState getFightRoomState() {
		return fightRoomState;
	}
	
	public void setFightRoomState(FightRoomState fightRoomState) {
		this.fightRoomState = fightRoomState;
	}
	
	public long calRoomLiveTime() {
		return System.currentTimeMillis() - this.getActRoomCreateTime();
	}
	
	public long calFightLiveTime() {
		return System.currentTimeMillis() - this.roomCreateTime;
	}
	
	public void initMap(ServerMapInfo serverMapInfo) {
		switchMap(serverMapInfo);
	}
	
	public void newComerReborn(UnitDeadReasonType reasonType, FightUnit fightUnit) {
		Role role = fightUnit.getFightPlayer().getRole();
		List<PhysicsObj> bornObjs = findPhysicsObjsBySpecType(PhysicsObjSpecType.mouseBorn);
		Vec2 pos;
		if(bornObjs.size() > 0) {
			pos = bornObjs.get(0).getBody().getPosition();
		} else {
			pos = new Vec2(FightPlayer.init_pos_x * 1f / 10f, FightPlayer.init_pos_y * 1f / 10f);
		}
		fightUnit.getBody().setTransform(pos, fightUnit.getBody().getAngle());
		fightUnit.getBody().setLinearVelocity(new Vec2(0, 0));
		fightUnit.setNewComerHadReborn(true);
		fightUnit.setSkipRefreshPos(true);
		businessPool.schedule(new Runnable() {
			@Override
			public void run() {
				fightUnit.setSkipRefreshPos(false);
			}
		}, 1, TimeUnit.SECONDS);
		if(role.getNewComerDayRebornLeftNum() > 0) {
			role.setNewComerDayRebornLeftNum(role.getNewComerDayRebornLeftNum() - 1);
			fightUnit.getFightPlayer().sendUnitDeadEvent(reasonType, true, Role.newComerRebornSkillId);
			businessPool.execute(new Runnable() {
				@Override
				public void run() {
					roleRepo.saveRole(role);
				}
			});
		}
	}
	
	public void switchMap(ServerMapInfo serverMapInfo){
		this.mapInfo = serverMapInfo;
		if(this.lastPlayMapIds.size() >= 30) {
			this.lastPlayMapIds.pop();
		}
		this.lastPlayMapIds.add(serverMapInfo.getMapId());
		FightMapInfoProto mapInfo = serverMapInfo.getMapInfo();
		this.mouseBorns.clear();
		this.specMouseBorns.clear();
		this.waterObjs.clear();
		this.setFightingResult(new FightResult(this, battleResultData, dropGroupData));
		this.removeCurrentMapPhysicsObjs();
		this.world.setGravity(new Vec2(this.physicsConfigProperty.getGravityY() / 3 * mapInfo.getSceneSettingInfo().getWindDirectionPercent() / 100, this.physicsConfigProperty.getGravityY() * mapInfo.getSceneSettingInfo().getGravityPercent() / 100));
		this.width = screen_width * mapInfo.getSceneSettingInfo().getSceneWidthPercent() / 100 + this.getMapInfo().getCombinedMapInfos().size() * FightConfig.scene_width;
		this.height = screen_height * mapInfo.getSceneSettingInfo().getSceneHeightPercent() / 100;
		this.maxFightTime = mapInfo.getSceneSettingInfo().getSceneLimitInfo().getFightSeconds() * FightConfig.fps;
//		this.maxFightTime = (random.nextInt(3) + 3) * FightConfig.fps;
		this.removeBackgroundPhysicsObj();
		this.backgroundPhysicsObj = PhysicsObj.createBackground(this);
		this.initPhysicsObj(serverMapInfo);
		this.initIndepententNails(serverMapInfo);
		this.initHallTranseferDoor();
		this.deadChecker = new DeadChecker(this);
		for(PhysicsObj physicsObj : this.physicsObjs.values()) {
			if(physicsObj.getPhysicsObjProperty().getSpecType() == PhysicsObjSpecType.specMouseBorn) {
				this.specMouseBorns.add(physicsObj);
			} else if(physicsObj.getPhysicsObjProperty().getSpecType() == PhysicsObjSpecType.mouseBorn) {
				this.mouseBorns.add(physicsObj);
			} else if(physicsObj.getPhysicsObjProperty().getSpecType() == PhysicsObjSpecType.dropItem){
				this.getFightingResult().checkReward(physicsObj.getPhysicsObjProperty());
			}
			if(physicsObj.getPhysicsObjProperty().getSpecType() == PhysicsObjSpecType.water) {
				this.waterObjs.add(physicsObj);
			}
		}
		this.fightWinResult = new FightResult(this, battleResultData, dropGroupData);
		//TODO
//		this.fightWinResult.fightEndReward(100000);
	}
	
	public void initHallTranseferDoor() {
		if(this.roomType == RoomType.hall) {
			List<PhysicsObj> doorAs = this.findPhysicsObjsBySpecType(PhysicsObjSpecType.transferDoorA);
			List<PhysicsObj> doorBs = this.findPhysicsObjsBySpecType(PhysicsObjSpecType.transferDoorB);
			for(HallTransferDoorConfig config : hallTransferDoorConfigs) {
				if(config.doorAIndex >= doorAs.size() || config.doorBIndex >= doorBs.size()) {
					return;
				}
				PhysicsObj doorA = doorAs.get(config.doorAIndex);
				PhysicsObj doorB = doorBs.get(config.doorBIndex);
				doorA.setHallTransferDoorTarget(doorB);
				doorB.setHallTransferDoorTarget(doorA);
			}
		}
	}
	
	
	public List<PhysicsObj> getWaterObjs() {
		return waterObjs;
	}

	private void initIndepententNails(ServerMapInfo serverMapInfo) {
		List<FakeIndependentNailProto> independentNailProtos = new ArrayList<FakeIndependentNailProto>(serverMapInfo.getMapInfo().getIndependentNailsList());
		int combinedMapIndex = 1;
		for(FightMapInfoProto combinedMapInfo : serverMapInfo.getCombinedMapInfos()) {
			for(FakeIndependentNailProto fakeIndependentNailProto : combinedMapInfo.getIndependentNailsList()) {
				FakeIndependentNailProto.Builder builder = fakeIndependentNailProto.toBuilder();
				builder.setId(combinedMapIndex * 1000 + fakeIndependentNailProto.getId());
				builder.setPosX((int) (screen_width * serverMapInfo.getMapInfo().getSceneSettingInfo().getSceneWidthPercent() / 100 * 1000 + (combinedMapIndex - 1) * FightConfig.scene_width * 1000 + builder.getPosX()));
				fakeIndependentNailProto = builder.build();
				independentNailProtos.add(fakeIndependentNailProto);
			}
			combinedMapIndex++;
		}
		for(FakeIndependentNailProto independentNailProto : independentNailProtos) {
			FakeIndependentNail fakeIndependentNail = new FakeIndependentNail(this, independentNailProto);
			fakeIndependentNail.activate();
		}
	}

	public void removeBackgroundPhysicsObj(){
		if (this.backgroundPhysicsObj != null) {
			this.world.destroyBody(this.backgroundPhysicsObj.getBody());
			this.backgroundPhysicsObj = null;
		}
	}
	
	public void removeCurrentMapPhysicsObjs(){
		this.physicsObjs.clear();
//		Iterator<Map.Entry<Integer, PhysicsObj>> it =  this.physicsObjs.entrySet().iterator();  
//        while(it.hasNext()){  
////        	Map.Entry<Integer, PhysicsObj> entry = it.next();  
////            PhysicsObj physicsObj = (PhysicsObj)entry.getValue();  
////        	this.world.destroyBody(physicsObj.getBody());
//        	it.remove();
//        }  
	}
	
	public void initPhysicsObj(ServerMapInfo serverMapInfo){
		List<PhysicsObjProto> physicsObjProtos = new ArrayList<PhysicsObjProto>(serverMapInfo.getMapInfo().getPhysicsObjsList());
		int combinedMapIndex = 1;
		for(FightMapInfoProto combinedMapInfo : serverMapInfo.getCombinedMapInfos()) {
			for (PhysicsObjProto physicsObjProto : combinedMapInfo.getPhysicsObjsList()) {
				PhysicsObjProto.Builder builder = physicsObjProto.toBuilder();
				builder.setId(combinedMapIndex * 1000 + physicsObjProto.getId());
				builder.setPosX((int) (screen_width * serverMapInfo.getMapInfo().getSceneSettingInfo().getSceneWidthPercent() / 100 * 1000 + (combinedMapIndex - 1) * FightConfig.scene_width * 1000 + builder.getPosX()));
				physicsObjProtos.add(builder.build());
//				logger.info("add combined obj:" + builder.getId());
			}
			combinedMapIndex++;
		}
		
		//先初始化有id的，再初始化没id的
		for (PhysicsObjProto physicsObjProto : physicsObjProtos) {
			PhysicsObjProperty physicsObjProperty = this.physicsObjData.getPhysicsObjProperty(physicsObjProto.getXmlId());
			if(physicsObjProperty == null) {
				continue;
			}
			if (physicsObjProperty.getSpecType() == PhysicsObjSpecType.dropItem) {
				if (this.fightingResult.getItems().size() == 0) {
					continue;
				}
			}
			if(physicsObjProto.getId() == 0) {
				continue;
			}
			PhysicsObj physicsObj = PhysicsObj.createNewPhysicsObj(this, ServerConfig.system_role_id, physicsObjProperty, physicsObjProto, true);
			this.physicsObjs.put(physicsObj.getId(), physicsObj);
		}
		//初始化没id的
		for (PhysicsObjProto physicsObjProto : physicsObjProtos) {
			PhysicsObjProperty physicsObjProperty = this.physicsObjData.getPhysicsObjProperty(physicsObjProto.getXmlId());
			if(physicsObjProperty == null) {
				continue;
			}
			if (physicsObjProperty.getSpecType() == PhysicsObjSpecType.dropItem) {
				if (this.fightingResult.getItems().size() == 0) {
					continue;
				}
			}
			if(physicsObjProto.getId() != 0) {
				continue;
			}
			PhysicsObj physicsObj = PhysicsObj.createNewPhysicsObj(this, ServerConfig.system_role_id, physicsObjProperty, physicsObjProto, true);
			this.physicsObjs.put(physicsObj.getId(), physicsObj);
		}
		for (PhysicsObj physicsObj : this.physicsObjs.values()) {
			for (PhysicsNail nail : physicsObj.getNails()) {
				nail.activate();
			}
		}
	}
	
	public PhysicsObj getBackgroundPhysicsObj() {
		return backgroundPhysicsObj;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public World getWorld() {
		return world;
	}
	
	public void addFightUnit(FightUnit fightUnit) {
		this.units.put(fightUnit.getRoleId(), fightUnit);
	}

	public FightPlayer findFightPlayer(long roleId) {
		for (FightPlayer fightPlayer : fightPlayers) {
			if(fightPlayer.getRoleId() == roleId) {
				return fightPlayer;
			}
		}
		return null;
	}
	
	public void removePhysicsObj(PhysicsObj physicsObj) {
		this.physicsObjs.remove(physicsObj.getId());
	}
	
	public int getFightType() {
		return fightType;
	}
	
	public void playerReenter(FightPlayer fightPlayer) {
		if(fightPlayer.getFightUnit() != null) {
			CommonEventProto.Builder builder = CommonEventProto.newBuilder();
			builder.setEventType(EventType.unit_init.getCode());
			builder.setFrameIndex(this.getFrameIndex());
			builder.setRoleId(fightPlayer.getRoleId());
			builder.setUnitInfo(fightPlayer.getFightUnit().buildProto(true));
			fightPlayer.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder);
		}
		
		//同步unit
		for (FightPlayer otherPlayer : fightPlayers) {
			if(otherPlayer.getFightUnit() == null || otherPlayer.getRoleId() == fightPlayer.getRoleId()) {
				continue;
			}
			CommonEventProto.Builder builder = CommonEventProto.newBuilder();
			builder.setRoleId(otherPlayer.getRoleId());
			builder.setFrameIndex(this.getFrameIndex());
			builder.setEventType(EventType.unit_init.getCode());
			builder.setUnitInfo(otherPlayer.getFightUnit().buildProto(true));
			fightPlayer.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder);
		}
	}
	
	public void playerEnterSendMsg(FightPlayer fightPlayer) {
		if(fightPlayer.getFightUnit() != null) {
			CommonEventProto.Builder builder2 = CommonEventProto.newBuilder();
			builder2.setEventType(EventType.unit_init.getCode());
			builder2.setFrameIndex(this.getFrameIndex());
			builder2.setRoleId(fightPlayer.getRoleId());
			builder2.setUnitInfo(fightPlayer.getFightUnit().buildProto(true));
			fightPlayer.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder2);
		}
		
		//同步unit
		for (FightPlayer otherPlayer : fightPlayers) {
			if(otherPlayer.getFightUnit() == null || otherPlayer.getRoleId() == fightPlayer.getRoleId()) {
				continue;
			}
			CommonEventProto.Builder builder = CommonEventProto.newBuilder();
			builder.setRoleId(otherPlayer.getRoleId());
			builder.setFrameIndex(this.getFrameIndex());
			builder.setEventType(EventType.unit_init.getCode());
			builder.setUnitInfo(otherPlayer.getFightUnit().buildProto(true));
			fightPlayer.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder);
		}
	}
	
	public void playerEnter(FightPlayer fightPlayer) {
		playerEnter(fightPlayer, null);
	}

	public void playerEnter(FightPlayer fightPlayer, RoleHallInfoProto roleHallInfoProto) {
		FightPlayer thePlayer = findFightPlayer(fightPlayer.getRoleId());
		if(thePlayer == null) {
			this.fightPlayers.add(fightPlayer);
		}
		fightPlayer.enterRoom(this);
		if(this.roomType == RoomType.hall) {
			fightPlayer.initUnit(false, roleHallInfoProto);
		}
		notifyOtherPlayerThePlayerEnter(fightPlayer);
	}
	
	public void notifyOtherPlayerThePlayerEnter(FightPlayer fightPlayer) {
		notifyPlayerEnter(fightPlayer, false);
		if(fightPlayer.getFightUnit() != null) {
			CommonEventProto.Builder builder2 = CommonEventProto.newBuilder();
			builder2.setEventType(EventType.unit_init.getCode());
			builder2.setFrameIndex(this.getFrameIndex());
			builder2.setRoleId(fightPlayer.getRoleId());
			builder2.setUnitInfo(fightPlayer.getFightUnit().buildProto(true));
			for (FightPlayer player : fightPlayers) {
				if(player.getRoleId() == fightPlayer.getRoleId()) {
					continue;
				}
				if(roomType == RoomType.hall && player.getHallPlayerStayRoomType() != RoomType.hall) {
					continue;
				}
				player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder2);
			}
		}
	}

	public void notifyPlayerEnter(FightPlayer fightPlayer, boolean sendToSelf) {
		PlayerEnterProto.Builder builder = fightPlayer.getPlayerEnterProto().toBuilder();
		builder.setFrameIndex(this.getFrameIndex());
		for (FightPlayer player : fightPlayers) {
			if(player.getRoleId() == fightPlayer.getRoleId() && !sendToSelf) {
				continue;
			}
			if(roomType == RoomType.hall && player.getHallPlayerStayRoomType() != RoomType.hall) {
				continue;
			}
			if(player.getGamePlayer() != null && player.getGamePlayer().isWebSocket() && roomType != RoomType.hall ) {
				player.respond("fightRoom.notifyOtherPlayerThePlayerEnter", CmdStatus.notDecrypt, builder);
			} else {
				player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder);
			}
		}
	}
	
	public void broadcastCommonEvent(CommonEventProto.Builder protoBuilder, FightPlayer fightPlayer) {
		for (FightPlayer player : fightPlayers) {
			if(player.getRoleId() == fightPlayer.getRoleId()) {
				continue;
			}
			if(roomType == RoomType.hall && player.getHallPlayerStayRoomType() != RoomType.hall) {
				continue;
			}
			player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, protoBuilder);
		}
	}
	
	public void broadcastChatEvent(ChatEventProto.Builder protoBuilder, FightPlayer fightPlayer) {
		for (FightPlayer player : fightPlayers) {
			if(player.getRoleId() == fightPlayer.getRoleId()) {
				continue;
			}
			if(roomType == RoomType.hall && player.getHallPlayerStayRoomType() != RoomType.hall) {
				continue;
			}
			player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, protoBuilder);
		}
	}
	
	public void broadcastSpeakEvent(SpeakEventProto.Builder protoBuilder, FightPlayer fightPlayer) {
		for (FightPlayer player : fightPlayers) {
			if(player.getRoleId() == fightPlayer.getRoleId() || (protoBuilder.getSpeakType() == SpeakState.speak_running.getCode() && !player.getRole().isEnableSpeech()) ) {
				continue;
			}
			player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, protoBuilder);
		}
	}
	
	public void broadcastSpeakStateEvent(SpeakEventProto.Builder protoBuilder, FightPlayer fightPlayer) {
		for (FightPlayer player : fightPlayers) {
			player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, protoBuilder);
		}
	}
	public void broadChangeDressEvent(PlayerChangeDressProto.Builder protoBuilder, FightPlayer fightPlayer) {
		for (FightPlayer player : fightPlayers) {
			if(player.getRoleId() == fightPlayer.getRoleId()) {
				continue;
			}
			if(roomType == RoomType.hall && player.getHallPlayerStayRoomType() != RoomType.hall) {
				continue;
			}
			player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, protoBuilder);
		}
	}
	public void broadcastUseSkillEvent(UseSkillEventProto.Builder protoBuilder, FightPlayer fightPlayer) {
		for (FightPlayer player : fightPlayers) {
			player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, protoBuilder);
		}
	}
	
	public List<FightPlayer> getFightPlayers() {
		return fightPlayers;
	}

	public void broadcastPlayerQuitEvent(FightPlayer quitPlayer, EmptyEventProto emptyEventProto) {
		EmptyEventProto.Builder builder = emptyEventProto.toBuilder();
		builder.setEventType(EventType.quit_event_type.getCode());
		builder.setFrameIndex(this.getFrameIndex());
		builder.setRoleId(quitPlayer.getRoleId());
		for (FightPlayer fightPlayer : fightPlayers) {
			if(fightPlayer.getRoleId() == quitPlayer.getRoleId()) {
				continue;
			}
			if(roomType == RoomType.hall && fightPlayer.getHallPlayerStayRoomType() != RoomType.hall) {
				continue;
			}
			fightPlayer.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder);
		}
	}

	public void playerQuit(FightPlayer fightPlayer, EmptyEventProto emptyEventProto) {
		if(fightPlayer == null) {
			return;
		}
		
		if(this.roomType == RoomType.hall) {
			FightUnit fightUnit = fightPlayer.getFightUnit();
			if(fightUnit != null) {
				final long roleId = fightPlayer.getRoleId();
				final int posX = (int) (fightUnit.getBody().getPosition().x * 1000);
				final int posY = (int) (fightUnit.getBody().getPosition().y * 1000);
				this.businessPool.execute(new Runnable() {
					@Override
					public void run() {
						RoleHallInfoProto.Builder builder = RoleHallInfoProto.newBuilder();
						builder.setPosX(posX);
						builder.setPosY(posY);
						roleRepo.saveRoleHallInfo(builder.build(), roleId);
					}
				});
			}
		}
		fightPlayer.unitDead(UnitDeadReasonType.playerQuit);
		fightPlayer.quitFight();
		removeFightPlayer(fightPlayer);
		broadcastPlayerQuitEvent(fightPlayer, emptyEventProto);
		System.out.println("room("+this.roomType.toString()+"):" + this.getRoomId() + " ,player quit:" + fightPlayer.getRoleId() + " room player num:" + this.fightPlayers.size());
	}
	
	public void playerOffline4FightRoom(FightPlayer fightPlayer) {
		if(fightPlayer == null) {
			return;
		}
		fightPlayer.offLine();
		fightPlayer.fightRoomQuitFight();
		System.out.println("fightroom:" + this.getRoomId() + " ,player quit:" + fightPlayer.getRoleId() + " room player num:" + this.fightPlayers.size());
	}
	
	/**
	 * 更新本局一起玩的伙伴信息
	 */
	private void updateRecentPartner(){
		for(FightPlayer fightPlayer: fightPlayers){
			businessPool.execute(new Runnable() {
				@Override
				public void run() {
					RoleRecentPartnerInfo partnerInfo = friendRepo.getRoleRecentPartnerInfo(fightPlayer.getRoleId());
					RoleFriendInfo roleFriendInfo = friendRepo.getRoleFriendInfo(fightPlayer.getRoleId());
					List<Long> playerIds = new ArrayList<Long>();
					for(FightPlayer otherPlayer : fightPlayers){
						if(otherPlayer.getRoleId()!=fightPlayer.getRoleId()){
							boolean isFriend = false;
							for(Friend friend: roleFriendInfo.getFriends()){
								if(friend.getRoleId()==otherPlayer.getRoleId()){
									isFriend = true;
								}
							}
							if(!isFriend){
								playerIds.add(otherPlayer.getRoleId());
							}
						}
					}
					partnerInfo.updateRecentPartner(playerIds, System.currentTimeMillis());
					friendRepo.saveRoleRecentPartnerInfo(partnerInfo);
				}
			});
		}
	}

	private void removeFightPlayer(FightPlayer fightPlayer) {
		Iterator<FightPlayer> it = this.fightPlayers.iterator();
		while(it.hasNext()) {
			FightPlayer otherFightPlayer = it.next();
			if(otherFightPlayer.getRoleId() == fightPlayer.getRoleId()) {
				it.remove();
				break;
			}
		}
	}

	public FightRoomInfoProto.Builder buildProto() {
		FightRoomInfoProto.Builder builder = FightRoomInfoProto.newBuilder();
		builder.setCurFrameIndex(this.getFrameIndex());
		builder.setRoomFightIndex(this.roomFightIndex);
		builder.setRoomCreateTime(this.roomCreateTime);
		builder.setRoomId((int)this.getRoomId());
		for (PhysicsObj physicsObj : this.physicsObjs.values()) {
			builder.addPhysicsObjs(physicsObj.buildProto(true));
		}
		builder.setRoomState(this.fightRoomState.ordinal());
		builder.setCurStateStartFrame(this.curStateStartFrameIndex);
		builder.setFightingResult(this.fightingResult.copyTo());
		if(mapInfo != null) {
			builder.setSceneSettingInfo(mapInfo.getMapInfo().getSceneSettingInfo());
			builder.setMapId(mapInfo.getMapInfo().getMapId());
		}
		for(FightMapInfoProto combinedMapInfo : this.mapInfo.getCombinedMapInfos()) {
			builder.addCombinedMapInfos(combinedMapInfo);
		}
		builder.setRoomFightIndex(this.roomFightIndex);
		for(FightPlayer fightPlayer : this.fightPlayers) {
			builder.addPlayers(fightPlayer.buildPlayerInfoProto4Front());
		}
		builder.setRoomType(this.roomType.getCode());
		builder.setMaxFightSeconds((int) (this.maxFightTime / 60));
		return builder;
	}
	
	public void setPhysicsObjId(int objId) {
		this.physicsObjIdProducer.set(objId);
	}
	
	public int getCurPhysicsObjIdProducerId() {
		return this.physicsObjIdProducer.get();
	}
	
	
	public int produceNewPhysicsObjId() {
		return this.physicsObjIdProducer.incrementAndGet();
	}
	
	public void broadcastConjurePhysicsObjEvent(ConjurePhysicsObjEventProto.Builder builder,
			FightPlayer fightPlayer, boolean isSendToSelf) {
		for (FightPlayer player : fightPlayers) {
			if(!isSendToSelf && fightPlayer.getRoleId() == player.getRoleId()) {
				continue;
			}
			player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder);
		}
	}
	public void broadcastFightSkillObjEvent(FightSkillObjEventProto.Builder builder,
			FightPlayer fightPlayer) {
		for (FightPlayer player : fightPlayers) {
			if(fightPlayer.getRoleId() == player.getRoleId()) {
				continue;
			}
			player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder);
		}
	}
	
	public void broadcastConjurePhysicsObjEvent(ConjurePhysicsObjEventProto.Builder builder,
			FightPlayer fightPlayer) {
		broadcastConjurePhysicsObjEvent(builder, fightPlayer, true);
	}
	
	
	public void handlePhysicsObjEvent(final ConjurePhysicsObjEventProto conjurePhysicsObjEventProto, final FightPlayer fightPlayer) {
		EventType eventType = EventType.getEventTypeFromCode(conjurePhysicsObjEventProto.getEventType());
		switch (eventType) {
		case conjure_physicsobj_cancel_event_type:
			break;
		case conjure_physicsobj_confirm_event_type:
			if(fightPlayer.getFightUnit() == null) {
				return;
			}
			final PhysicsObjProperty physicsObjProperty = FightRoom.this.physicsObjData.getPhysicsObjProperty(conjurePhysicsObjEventProto.getPhysicsObj().getXmlId());
			float delayTime = physicsConfigProperty.getConjureObjDelay();
			if(fightPlayer.getFightUnit() != null) {
				delayTime = fightPlayer.getFightUnit().calPrepareConjureTime(physicsObjProperty, physicsConfigProperty, false);
			}
			this.performAtFixFrameIndex(new FixFrameEvent(fightPlayer.getRoleId(), ServerEventType.init_physicsobj, this.getFrameIndex() + (int) (delayTime * FightConfig.fps)) {
				@Override
				public void executeEvent() {
					PhysicsObjProperty physicsObjProperty = FightRoom.this.physicsObjData.getPhysicsObjProperty(conjurePhysicsObjEventProto.getPhysicsObj().getXmlId());
					if(physicsObjProperty.getSpecType() == PhysicsObjSpecType.bomb) {
						handlerBombInit(conjurePhysicsObjEventProto, fightPlayer);
					} else if(physicsObjProperty.getSpecType() == PhysicsObjSpecType.transferDoorA || physicsObjProperty.getSpecType() == PhysicsObjSpecType.transferDoorB) {
						Iterator<PhysicsObj> it = FightRoom.this.physicsObjs.values().iterator();
						while(it.hasNext()) {
							PhysicsObj physicsObj = it.next();
							if(physicsObj.getPhysicsObjProperty().getSpecType() == physicsObjProperty.getSpecType()) {
								physicsObj.dead();
								it.remove();
								break;
							}
						}
						conjureCommonObj(conjurePhysicsObjEventProto, fightPlayer, physicsObjProperty);
					} else {
						conjureCommonObj(conjurePhysicsObjEventProto, fightPlayer, physicsObjProperty);
					}
				}

				private void conjureCommonObj(
						final ConjurePhysicsObjEventProto conjurePhysicsObjEventProto,
						final FightPlayer fightPlayer,
						PhysicsObjProperty physicsObjProperty) {
					PhysicsObj physicsObj = PhysicsObj.createNewPhysicsObj(FightRoom.this, fightPlayer.getRoleId(), physicsObjProperty, conjurePhysicsObjEventProto.getPhysicsObj(), false);
					physicsObj.setConjured(true);
					FightRoom.this.physicsObjs.put(physicsObj.getId(), physicsObj);
					ConjurePhysicsObjEventProto.Builder builder = ConjurePhysicsObjEventProto.newBuilder();
					builder.setEventType(EventType.conjure_physicsobj_init_event_type.getCode());
					builder.setFrameIndex(this.getFrameIndex());
					FightSkillTrigger.conjurePhysicsObjTriggerSkill(FightRoom.this, physicsObj, fightPlayer);
					PhysicsObjProto.Builder physicsObjBuilder = physicsObj.buildProto(true);
					builder.setRoleId(fightPlayer.getRoleId());
					builder.setPhysicsObj(physicsObjBuilder);
					broadcastConjurePhysicsObjEvent(builder, fightPlayer);
				}
			});
			break;
		case conjure_physicsobj_conjuring_init_event_type:
			break;
		case conjure_physicsobj_conjuring_update_event_type:
			break;
		case conjure_physicsobj_update_event_type:
			break;
		case contact_syn_unit_info_event_type:
			break;
		default:
			break;
		}
	}

	public PhysicsConfigProperty getPhysicsConfigProperty() {
		return physicsConfigProperty;
	}

	public PhysicsNailData getPhysicsNailData() {
		return physicsNailData;
	}
	
	public Map<Integer, PhysicsObj> getPhysicsObjs() {
		return physicsObjs;
	}
	
	public Map<Long, FightUnit> getUnits() {
		return units;
	}
	
	public void fightActualStart() {
		this.initPlayerUnitsAndDestinationSamanRoleId();
		
		for(FightPlayer fightPlayer : this.specFightPlayers) {
			FightSkillTrigger.startFightTriggerSkill(this, fightPlayer.getFightUnit());
		}
		
		for (FightPlayer fightPlayer : fightPlayers) {
			if(fightPlayer.getFightUnit() != null) {
				CommonEventProto.Builder builder2 = CommonEventProto.newBuilder();
				builder2.setEventType(EventType.unit_init.getCode());
				builder2.setFrameIndex(this.getFrameIndex());
				builder2.setRoleId(fightPlayer.getRoleId());
				builder2.setUnitInfo(fightPlayer.getFightUnit().buildProto(true));
				for (FightPlayer player : fightPlayers) {
					player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder2);
				}
			}
		}
	}

	@Override
	public void doSomething() {
		if(this.getFrameIndex() % FightConfig.check_players_offline_period_frame_num == 0) {
			this.deadChecker.checkOfflinePlayerAndMakeDead();
		}
//		if(this.getFrameIndex() % 600 == 0) {
//			long curTime = System.nanoTime();
//			long passTime = curTime - this.roomStartTime;
//			logger.info(String.format("room(roomId:%d) fps is %f", this.getRoomId(), this.getFrameIndex() * 1f / (passTime / 1000000000f)));
//		}
//		long doSomethingStartTime = System.nanoTime();
		if(this.fightRoomState == FightRoomState.fighting) {
			if(this.getFrameIndex() % FightConfig.syn_physics_obj_period_frame_num == 0) {
				ReusedProtoBuilder reusedProtoBuilder = ProtoBuilderPoolManager.getBuilder(PeriodUpdateObjInfoProto.class);
				PeriodUpdateObjInfoProto.Builder builder = (Builder) reusedProtoBuilder.getBuilder();
				builder.setEventType(EventType.conjure_physicsobj_update_event_type.getCode());
				builder.setFrameIndex(this.getFrameIndex());
				for (PhysicsObj physicsObj : this.physicsObjs.values()) {
					if(physicsObj.getBody().getType() != BodyType.STATIC) {
						builder.addObjs(physicsObj.buildProto(false));
					}
				}
				if(builder.getObjsCount() > 0) {
					int playerNum = 0;
					for (FightPlayer player : fightPlayers) {
						if(roomType == RoomType.hall && player.getHallPlayerStayRoomType() != RoomType.hall) {
							continue;
						}
						playerNum++;
					}
					if(playerNum == 0) {
						reusedProtoBuilder.release();
					} else {
						reusedProtoBuilder.setReleaseCounter(playerNum);
						for (FightPlayer player : fightPlayers) {
							if(roomType == RoomType.hall && player.getHallPlayerStayRoomType() != RoomType.hall) {
								continue;
							}
							//for循环返回时，必须是最后一个返回才能将builder回收
							player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, reusedProtoBuilder);
						}
					}
					
				}else{
					reusedProtoBuilder.release();
				}
			}
			if(this.getFrameIndex() % FightConfig.check_obj_dead_period_frame_num == 0) {
				this.deadChecker.checkObjAndMakeDead();
			}
			if(this.getFrameIndex() % FightConfig.check_unit_dead_period_frame_num == 0) {
				this.deadChecker.checkUnitAndMakeDead();
			}
		}
		
		//每秒检查一遍
		if(this.getFrameIndex() % 60 == 0) {
			if(this.roomType != RoomType.hall) {
				switch (this.fightRoomState) {
				case preparing:
					if(this.getFrameIndex() >= FightConfig.fight_prepare_frames) {
						logger.info("fight(roomId:"+this.getRoomId()+") prepare countdown");
						this.fightRoomState = FightRoomState.prepare_countdown;
						this.curStateStartFrameIndex = getFrameIndex();
						this.curStateStartTime = System.currentTimeMillis();
						//初始化玩家的单位
						this.fightActualStart();
						PrepareEndProto.Builder builder = PrepareEndProto.newBuilder();
						for (FightPlayer player : fightPlayers) {
							builder.addPlayerInfos(player.buildPlayerInfoProto4Front());
						}
						for (FightPlayer player : fightPlayers) {
							player.respond("fightRoom.prepareEnd", CmdStatus.notDecrypt, builder);
						}
					}
					break;
				case prepare_countdown:
					if(this.getFrameIndex() >= this.curStateStartFrameIndex + FightConfig.fight_prepare_countdown_frames) {
						logger.info("fight(roomId:"+this.getRoomId()+") fighting");
						this.fightRoomState = FightRoomState.fighting;
						this.curStateStartFrameIndex = getFrameIndex();
						this.curStateStartTime = System.currentTimeMillis();
						EmptyEventProto.Builder builder = EmptyEventProto.newBuilder();
						builder.setEventType(EventType.fight_act_start.getCode());
						builder.setFrameIndex(this.getFrameIndex());
						for (FightPlayer player : fightPlayers) {
							player.respond("fightRoom.actStartFight", CmdStatus.notDecrypt, builder);
						}
					}
					break;
				case fighting:
					if(checkCanEnd()) {
						this.curStateStartFrameIndex = getFrameIndex();
						this.curStateStartTime = System.currentTimeMillis();
						this.fightEnd();
						if(fightRoomTryMerge(true)) {
							this.destroy();
							return;
						}
						break;
					}
					break;
				case ending:
					if(this.fightPlayers.size() == 0) {
						this.destroy();
						return;
					}
					if(fightRoomTryMerge()) {
						this.destroy();
						return;
					}
					if(this.getFrameIndex() >= this.curStateStartFrameIndex + FightConfig.fight_ending_frames) {
						this.fightRestart();
					}
					break;
				case ended:
					break;
				default:
					break;
				}
			} else {
				if(this.fightRoomState == FightRoomState.fighting) {
					long time = System.currentTimeMillis() - this.roomCreateTime;
					boolean mergeSuc = false;
					if(time >= 60000) {
						mergeSuc = hallTryMerge();
					}
					if(mergeSuc) {
						this.destroy();
						return;
					}
					if(this.fightPlayers.size() == 0) {
						long now = System.currentTimeMillis();
						if(this.prepareDestroyTime == 0) {
							this.prepareDestroyTime = now;
						}
						if(now - this.prepareDestroyTime >= 10000) { //10秒结束
							this.destroy();
							return;
						}
					} else {
						this.prepareDestroyTime = 0;
					}
				}
			}
		} 
		if(this.getFrameIndex() % FightConfig.check_force_period_frame_num == 0) {
			for (FightUnit fightUnit : this.units.values()) {
				fightUnit.update();
			}
			for(PhysicsObj physicsObj : this.physicsObjs.values()) {
				physicsObj.update();
			}
		}
		
		if(this.getFrameIndex() % FightConfig.check_balloon_period_frame_num == 0) {
			for(PhysicsObj physicsObj : this.physicsObjs.values()) {
				physicsObj.balloonCheck();
			}
		}
		
		if(this.getFrameIndex() % FightConfig.syn_fight_time_frames == 0) {
			PeroidUpdateRoomInfoProto.Builder simpleRoomInfoBuilder = PeroidUpdateRoomInfoProto.newBuilder();
			simpleRoomInfoBuilder.setFrameIndex(this.getFrameIndex());
			for(FightPlayer fightPlayer : this.fightPlayers) {
				fightPlayer.respond("fightRoom.periodSynRoomInfo", CmdStatus.notDecrypt, simpleRoomInfoBuilder);
			}
		}
//		long doSomethingEndTime = System.nanoTime();
//		long passTime = doSomethingEndTime - doSomethingStartTime;
//		if(passTime > 1000000) {
//			System.out.println(this.roomType + " do some thing time:" + (passTime / 1000000));
//		}
	}
	
	private boolean hallTryMerge() {
		if(this.fightPlayers.size() > ServerConfig.hall_room_merge_threshold) {
			return false;
		}
		long now = System.currentTimeMillis();
		if(now - lastHallTryMergeTime < 60000) { //每分钟只尝试一次
			return false;
		}
		this.lastHallTryMergeTime = now;
		DispatchServer dispatchServer = this.serverContext.borrowHallDispatchServer();
		try {
			LongStrProto.Builder builder = LongStrProto.newBuilder();
			builder.setParams1(this.getRoomId());
			builder.setParams2(this.roomType.toString());
			StartFightGetFightRoomRespProto resp = (StartFightGetFightRoomRespProto)dispatchServer.sendAndWaitResp("roomDispatchService.hallTryMerge", builder);
			if(resp.getRoomId() == 0 && resp.getServerId() == 0) {}
			else {
				HallGetFightRoomInfoRespProto.Builder resultBuilder = HallGetFightRoomInfoRespProto.newBuilder();
				resultBuilder.setRoomId(resp.getRoomId());
				ServerInfo serverInfo = serverInfoData.getServerInfo(resp.getServerId());
				resultBuilder.setServerInfo(serverInfo.buildHallServerInfo());
				for (FightPlayer fightPlayer : fightPlayers) {
					fightPlayer.respond("fightRoom.hallMerge", CmdStatus.notDecrypt, resultBuilder);
				}
				return true;
			}
		} finally {
			this.serverContext.addHallDispatchServer4Send(dispatchServer);
		}
		return false;
	}

	private boolean fightRoomTryMerge() {
		return fightRoomTryMerge(false);
	}
	
	private boolean fightRoomTryMerge(boolean isForceTry) {
		if(!isForceTry && this.getFrameIndex() - this.curStateStartFrameIndex >= 5 * FightConfig.fps) {
			return false;
		}
		if(this.fightPlayers.size() <= ServerConfig.fight_room_merge_threshold) {
			DispatchServer dispatchServer = this.serverContext.borrowRoomDispatchServer();
			try {
				LongStrProto.Builder builder = LongStrProto.newBuilder();
				builder.setParams1(this.getRoomId());
				builder.setParams2(this.roomType.toString());
				StartFightGetFightRoomRespProto resp = (StartFightGetFightRoomRespProto)dispatchServer.sendAndWaitResp("roomDispatchService.fightRoomTryMerge", builder);
				if(resp.getRoomId() == 0 && resp.getServerId() == 0) {}
				else {
					HallGetFightRoomInfoRespProto.Builder resultBuilder = HallGetFightRoomInfoRespProto.newBuilder();
					resultBuilder.setRoomId(resp.getRoomId());
					ServerInfo serverInfo = serverInfoData.getServerInfo(resp.getServerId());
					resultBuilder.setServerInfo(serverInfo.buildFightRoomServerInfo());
					for (FightPlayer fightPlayer : fightPlayers) {
						fightPlayer.respond("fightRoom.fightRoomMerge", CmdStatus.notDecrypt, resultBuilder);
					}
					return true;
				}
			} finally {
				this.serverContext.addRoomDispatchServer4Send(dispatchServer);
			}
		}
		return false;
	}

	private boolean checkCanEnd() {
		if(this.roomType == RoomType.hall) {
			return false;
		}
		if(this.getFrameIndex() - this.curStateStartFrameIndex >= this.maxFightTime) {
			logger.info("time over fight end.cur frame:" + this.getFrameIndex() + ",start frame:" + this.curStateStartFrameIndex);
			return true;
		}
		if(this.fightPlayers.size() == 0) {
			logger.info("player quit fight end.cur frame:" + this.getFrameIndex() + ",start frame:" + this.curStateStartFrameIndex);
			return true;
		}
		boolean isAllUnitNotExist = true;
		for (FightPlayer fightPlayer : fightPlayers) {
			if(fightPlayer.isUnitExist()) {
				isAllUnitNotExist = false;
				return false;
			}
		}
		if(isAllUnitNotExist) {
			logger.info("not unit exist fight end.cur frame:" + this.getFrameIndex() + ",start frame:" + this.curStateStartFrameIndex);
			return true;
		}
		return true;
	}
	
	private void destroy() {
		this.fightRoomState = FightRoomState.ended;
		this.curStateStartFrameIndex = 0;
		this.curStateStartTime = System.currentTimeMillis();
		this.stop();
		this.serverContext.removeFightRoom(this);
		DispatchServer dispatchServer = null;
		if(this.roomType == RoomType.hall) {
			dispatchServer = this.serverContext.borrowHallDispatchServer();
		} else {
			dispatchServer = this.serverContext.borrowRoomDispatchServer();
		}
		try {
			dispatchServer.sendAndWaitResp("roomDispatchService.fightRoomDestroy", StrLongProto.newBuilder().setParams1(this.roomType.name()).setParams2(this.getRoomId()));
		} finally {
			if(this.roomType == RoomType.hall) {
				this.serverContext.addHallDispatchServer4Send(dispatchServer);
			} else {
				this.serverContext.addRoomDispatchServer4Send(dispatchServer);
			}
		}
		if(this.roomType == RoomType.hall) {
			logger.info(String.format("hall(roomId:%d) destroy", this.getRoomId()));
		} else {
			logger.info(String.format("fight(roomId:%d) destroy", this.getRoomId()));
		}
		this.businessPool.schedule(new Runnable() {
			@Override
			public void run() {
				for(FightPlayer fightPlayer : fightPlayers) {
					fightPlayer.getGamePlayer().destroy();
				}
			}
		}, 10, TimeUnit.SECONDS);
	}
	
	private void fightEnd() {
		this.fightRoomState = FightRoomState.ending;
		logger.info(String.format("fight(roomId:%d) end", this.getRoomId()));
		for (FightUnit fightUnit : this.units.values()) {
			fightUnit.dead(true, UnitDeadReasonType.fightEnd);
		}
		
		updateRecentPartner();
		
		//询问dispatch是否需要合并
		//是，则推送给玩家要切换到的服务器，房间id
		//否，则刷新地图，刷新玩家
		
		performAtFixFrameIndex(new FixFrameEvent(ServerConfig.system_role_id, ServerEventType.delay_fight_end, this.getFrameIndex() + 60) {
			@Override
			public void executeEvent() {
				sendFightEndResult();
				physicsObjs.clear();
				units.clear();
				Iterator<FightPlayer> it = fightPlayers.iterator();
				while(it.hasNext()) {
					FightPlayer fightPlayer = it.next();
					if(!fightPlayer.isOnline()) {
						it.remove();
						fightPlayer.quitFight();
					}
				}
				
				//完成npc任务
				completeNpcTask();
				
				Iterator<FightPlayer> it2 = fightPlayers.iterator();
				while(it2.hasNext()) {
					FightPlayer fightPlayer = it2.next();
					if(fightPlayer.isOnline()) {
						fightPlayer.reset();
					}
				}
			}
		});
		
		
	}

	private void completeNpcTask() {
		final List<FightPlayer> playersCopy = new ArrayList<FightPlayer>(this.fightPlayers);
		this.businessPool.execute(new Runnable() {
			@Override
			public void run() {
				for(FightPlayer fightPlayer : playersCopy) {
					RoleNpcsInfo roleNpcsInfo = npcRepo.getRoleNpcsInfo(fightPlayer.getRoleId());
					if(roleNpcsInfo != null) {
						boolean isChange = roleNpcsInfo.updateTypeTaskNumAndNotify(NpcTaskType.fight, 1, npcData, fightPlayer.getGamePlayer());
						if(fightPlayer.isUnitIsSpec()) {
							boolean temp = roleNpcsInfo.updateTypeTaskNumAndNotify(NpcTaskType.saman, 1, npcData, fightPlayer.getGamePlayer());
							isChange = isChange || temp;
						}
						if(fightPlayer.getRanking() > 0 && fightPlayer.getRanking() <= 3 && fightPlayer.getRanking() != 0) {
							boolean temp = roleNpcsInfo.updateTypeTaskNumAndNotify(NpcTaskType.top3, 1, npcData, fightPlayer.getGamePlayer());
							isChange = isChange || temp;
						}
						if(isChange) {
							npcRepo.saveRoleNpcsInfo(roleNpcsInfo);
						}
					}
					
					if(fightPlayer.getWantedRoomType() == RoomType.easy) {
						Role role = roleRepo.getRole(fightPlayer.getRoleId());
						if(role != null) {
							if(role.getCheckpointId() <= checkpointData.getCheckpointNum()) {
								CheckpointProperty checkpointProperty = checkpointData.getCheckpointProperty(role.getCheckpointId());
								role.setCheckpointId(role.getCheckpointId() + 1);
								roleRepo.saveRole(role);
								if(checkpointProperty.getAwards().size() != 0) {
									itemDomainService.addItems(checkpointProperty.getAwards(), fightPlayer.getRoleId());
								}
							}
						}
					}
				}
			}
		});
	}

	private void sendFightEndResult() {
		List<FightPlayer> sortFightPlayers = new ArrayList<FightPlayer>(this.fightPlayers);
		Collections.sort(sortFightPlayers, new Comparator<FightPlayer>() {
			@Override
			public int compare(FightPlayer o1, FightPlayer o2) {
				if(o1.getWinFrame() == o2.getWinFrame()) {
					return 0;
				} else if(o1.getWinFrame() == 0) {
					return 1;
				} else if(o2.getWinFrame() == 0) {
					return -1;
				} else if(o1.getWinFrame() < o2.getWinFrame()) {
					return -1;
				} else if(o1.getWinFrame() > o2.getWinFrame()) {
					return 1;
				}
				return 0;
			}
		});
		
		FightEndInfoProto.Builder builder = FightEndInfoProto.newBuilder();
		builder.setTotalFrames(this.getFrameIndex());
		builder.setFrameIndex(this.getFrameIndex());
		builder.setEventType(EventType.fight_end.getCode());
		for (int i = 0; i < sortFightPlayers.size(); i++) {
			FightPlayer fightPlayer = sortFightPlayers.get(i);
			FightEndPlayerResultProto.Builder playerBuilder = FightEndPlayerResultProto.newBuilder();
			int ranking = i + 1;
			if(fightPlayer.getWinFrame() == 0) {
				ranking = -1;
			} 
			playerBuilder.setRanking(ranking);
			fightPlayer.setRanking(ranking);
			playerBuilder.setRoleId(fightPlayer.getRoleId());
			playerBuilder.setWinFrame(fightPlayer.getWinFrame());
			playerBuilder.setCheeseNum(fightPlayer.getGotCheeseNum()); //连续获取奶酪数量
			
			RoleWeekFightInfo roleWeekFightInfo = roleRepo.getRoleWeekFightInfo(fightPlayer.getRoleId());//TODO deal before
			Role role = roleRepo.getRole(fightPlayer.getRoleId()); //TODO deal before
			RoleProperty roleProperty = roleData.findRoleLevelUpProperty(role.getRoleLevel());
			FightPlayerRewardResult rewardResult = fightPlayer.getRewardResult();
			//奶酪
			playerBuilder.setAddCheeseNum(rewardResult.getAddCheeseNum());
			playerBuilder.setExtAddCheeseNum(rewardResult.getNormalExtCheese() + rewardResult.getAdvanceExtCheese());
			//当前奶酪数，上限数
			playerBuilder.setWeekCheeseNum(roleWeekFightInfo.getCheese());
			playerBuilder.setMaxWeekCheeseNum(roleProperty.getMaxWeekCheese());
			playerBuilder.setBeforeLevel(rewardResult.getBeforeLevel());
			playerBuilder.setBeforeExp(rewardResult.getBeforeExp());
			playerBuilder.setAfterLevel(rewardResult.getAfterLevel());
			playerBuilder.setAfterExp(rewardResult.getAfterExp());
			
			//药水数
			final int normal_exp_potion_xmlId = 3003006;
			final int advance_exp_potion_xmlId = 3003007;
			final int normal_cheese_potion_xmlId = 3003008;
			final int advance_cheese_potion_xmlId = 3003009;
			playerBuilder.setNormalExpPotionNum(role.getUseItemLeftCount(normal_exp_potion_xmlId));
			playerBuilder.setAdvanceExpPotionNum(role.getUseItemLeftCount(advance_exp_potion_xmlId));
			playerBuilder.setNormalCheesePotionNum(role.getUseItemLeftCount(normal_cheese_potion_xmlId));
			playerBuilder.setAdvanceCheesePotionNum(role.getUseItemLeftCount(advance_cheese_potion_xmlId));
			
			//经验
			playerBuilder.setAddExp(rewardResult.getAddExpNum());
			playerBuilder.setExtAddExp(rewardResult.getNormalExtExp() + rewardResult.getAdvanceExtExp());
			playerBuilder.setRankingAddExp(rewardResult.getRankingExtExp());
			//当前经验数，上限数
			playerBuilder.setWeekExp(roleWeekFightInfo.getExp());
			playerBuilder.setMaxWeekExp(roleProperty.getMaxWeekExp());
			playerBuilder.setWeekRankingExp(roleWeekFightInfo.getAddExp());
			playerBuilder.setMaxWeekRankingExp(roleProperty.getMaxWeekAddExp());
			
			playerBuilder.setRankingNo1Num(fightPlayer.getRankingNo1Num());
			playerBuilder.setShammanExp(fightPlayer.getHelpOtherMiceSuccessNum() * battleResultData.getBattleResultValueProperty(1).getHelpMiceExp());
			if (fightPlayer.getChestSlot() != null) {
				playerBuilder.setChestSlot(fightPlayer.getChestSlot().copyTo());
				playerBuilder.setIsDropChest(true);
			}
			builder.addPlayerResults(playerBuilder);
		}
		
		for (FightPlayer fightPlayer : sortFightPlayers) {
			fightPlayer.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder);
		}
	}
	
	private void clearFightInfo() {
		this.physicsObjIdProducer.set(0);
		this.world = null;
		this.backgroundPhysicsObj = null;
		this.specFightPlayers.clear();
		this.setUnitWinNum(0);
	}
	
	public Vec2 getInitPos(FightPlayer fightPlayer, boolean isSpec) {
		if(isSpec) {
			if(this.specMouseBorns.size() > 0) {
				for(PhysicsObj bornObj : this.specMouseBorns) {
					if(bornObj.getPhysicsObjProperty().getCampType() == fightPlayer.getCampType()) {
						Vec2 vec2 = new Vec2(bornObj.getBody().getPosition());
						vec2.y -= 1.9f;
						vec2.x += 0.4f;
						return vec2;
					}
				}
				PhysicsObj bornObj = this.specMouseBorns.get(0);
				Vec2 vec2 = new Vec2(bornObj.getBody().getPosition());
				vec2.y -= 1.9f;
				vec2.x += 0.4f;
				return vec2;
			} else if(this.mouseBorns.size() > 0) {
				for(PhysicsObj bornObj : this.mouseBorns) {
					if(bornObj.getPhysicsObjProperty().getCampType() == fightPlayer.getCampType()) {
						Vec2 vec2 = new Vec2(bornObj.getBody().getPosition());
						vec2.y -= 1.9f;
						vec2.x += 0.4f;
						return vec2;
					}
				}
				PhysicsObj bornObj = this.mouseBorns.get(0);
				Vec2 vec2 = new Vec2(bornObj.getBody().getPosition());
				vec2.y -= 1.9f;
				vec2.x += 0.4f;
				return vec2;
			}
		} else {
			if(this.mouseBorns.size() > 0) {
				PhysicsObj bornObj = this.mouseBorns.get(0);
				Vec2 vec2 = new Vec2(bornObj.getBody().getPosition());
				vec2.y -= 0.9f;
				return vec2;
			}
		}
		return null;
	}

	@Override
	public void update() {
		try {
			if(this.fightRoomState == FightRoomState.fighting) {
//				long updateStartTime = System.nanoTime();
				this.world.step(1f / FightConfig.fps, 1, 2);
//				long updateEndTime = System.nanoTime();
//				long passTime = updateEndTime - updateStartTime;
//				if(passTime > 1000000) {
//					System.out.println("room update time:" + (passTime / 1000000));
//				}
				if(this.controller != null) {
					Method method;
					try {
						method = this.controller.getClass().getDeclaredMethod("stepAndRender");
						method.setAccessible(true);
						method.invoke(this.controller);
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		} catch(Exception e) {
			logger.error(String.format("room Id:%d, frameIndex: %d, mapId%d, mapName:%s:", this.getRoomId(), this.getFrameIndex(), this.mapInfo.getMapId(), this.mapInfo.getMapInfo().getSceneSettingInfo().getName()) , e);
			e.printStackTrace();
		}
	}

	public void removeUnit(FightUnit fightUnit) {
		this.world.destroyBody(fightUnit.getBody());
		this.units.remove(fightUnit.getRoleId());
	}

	public ServerMapInfo getMapInfo() {
		return mapInfo;
	}
	
	public TestbedController getController() {
		return controller;
	}

	public void setController(TestbedController controller) {
		this.controller = controller;
	}
	
	public GameSpecType getGameSpecType() {
		return GameSpecType.getGameSpecTypeByCode(this.mapInfo.getMapInfo().getSceneSettingInfo().getGameType());
	}
	
	

	private void initPlayerUnitsAndDestinationSamanRoleId() {
		//初始化萨满是谁
		GameSpecType gameSpecType = getGameSpecType();
		if(fightPlayers.size() > 0) {
			List<Integer> specMouseesIndex = new ArrayList<Integer>();
			switch (gameSpecType) {
			case common:
				randomSpecMousePlayer(specMouseesIndex, 1);
				break;
			case double_saman_cooperation:
			case double_saman_pk:
			case team_compete:
				if(this.fightPlayers.size() >= 2) {
					randomSpecMousePlayer(specMouseesIndex, this.fightPlayers.size());
				} else {
					randomSpecMousePlayer(specMouseesIndex, 1);
				}
				break;
			case no_shaman:
				break;
			case partner:
				break;
			default:
				break;
			}
			for (FightPlayer fightPlayer : specFightPlayers) {
				fightPlayer.setUnitIsSpec(true);
			}
			Collections.shuffle(this.specFightPlayers);
		}
		
		//萨满的阵营匹配
		if(gameSpecType == GameSpecType.double_saman_pk) {
			int specIndex = 0;
			for(PhysicsObj physicsObj : this.physicsObjs.values()) {
				if(physicsObj.getPhysicsObjProperty().getSpecType() == PhysicsObjSpecType.cheeseDestination) {
					if(this.specFightPlayers.size() > 0) {
						FightPlayer fightPlayer = this.specFightPlayers.get(specIndex);
						physicsObj.setDestinationSamanRoleId(fightPlayer.getRoleId());
						fightPlayer.setCampType(physicsObj.getPhysicsObjProperty().getCampType());
						logger.info(String.format("set campType, fightPlayer(roleId:%d), campType:%s", fightPlayer.getRoleId(), fightPlayer.getCampType()));
						if(specIndex < this.specFightPlayers.size() - 1) {
							specIndex++;
						}
					} else {
						physicsObj.setDestinationSamanRoleId(0);
					}
				}
			}
		}
		businessPool.execute(new Runnable() {
			@Override
			public void run() {
				RoomType roomType = FightRoom.this.getRoomType();
				List<UpdateAchievement> updateAchievements = new ArrayList<UpdateAchievement>();
				for (FightPlayer fightPlayer : specFightPlayers) {
					if (roomType == RoomType.advance) {
						updateAchievements.add(new UpdateAchievement(fightPlayer.getRoleId(), AchievementType.be_saman_quality_senior, 1));
					} else if (roomType == RoomType.normal){
						updateAchievements.add(new UpdateAchievement(fightPlayer.getRoleId(), AchievementType.be_saman_quality_medium, 1));
					}
					achievementService.updateAchievements(fightPlayer.getRoleId(), updateAchievements);
				}
			}
		});
		
		//初始化老鼠
		for (FightPlayer fightPlayer : fightPlayers) {
			Vec2 pos = getInitPos(fightPlayer, fightPlayer.isUnitIsSpec());
			fightPlayer.initUnit(pos, fightPlayer.isUnitIsSpec());
		}
		
		
		//伴侣模式初始化关节
		//伴侣模式，两两钉钉子
		if(gameSpecType == GameSpecType.partner) { 
			FightUnit unit1 = null;
			FightUnit unit2 = null;
			int index  = 0;
			for(FightUnit fightUnit : this.units.values()) {
				if(index % 2 == 1) {
					unit2 = fightUnit;
					unit1.connectWithOtherUnit(unit2);
				} else {
					unit1 = fightUnit;
				}
				index++;
			}
		}
	}
	
	public void randomSpecMousePlayer(List<Integer> specMouseesIndex, int num) {
		refreshPlayerRoleInfo();
		List<FightPlayer> useSamanPotionPlayers = new ArrayList<FightPlayer>();
		for(FightPlayer fightPlayer : fightPlayers) {
			if(!fightPlayer.isLastFightIsNotAction() && fightPlayer.getRole().getUseItemLeftCount(Item.saman_potion_xml_id) > 0) {
				useSamanPotionPlayers.add(fightPlayer);
			}
		}
		if(num > useSamanPotionPlayers.size()) { 
			for(FightPlayer fightPlayer : useSamanPotionPlayers) {
				this.specFightPlayers.add(fightPlayer);
			}
			
			//再从普通人中随
			int times = 0;
			while(true) {
				times++;
				if(this.specFightPlayers.size() >= num) {
					break;
				}
				int index = random.nextInt(fightPlayers.size());
				FightPlayer fightPlayer = fightPlayers.get(index);
				boolean isContain = false;
				for(FightPlayer tempFightPlayer : this.specFightPlayers) {
					if(fightPlayer == tempFightPlayer) {
						isContain = true;
						break;
					}
				}
				if(!isContain) {
					if(times <= 20 && (fightPlayer.isLastFightIsNotAction() || fightPlayer.getGamePlayer() == null || fightPlayer.getGamePlayer().isWebSocket())) {
						continue;
					}
					this.specFightPlayers.add(fightPlayer);
				}
				
			}
		} else { //只从使用过药水的人中随
			while(true) {
				if(this.specFightPlayers.size() >= num) {
					break;
				}
				int index = random.nextInt(useSamanPotionPlayers.size());
				FightPlayer fightPlayer = useSamanPotionPlayers.get(index);
				boolean isContain = false;
				for(FightPlayer tempFightPlayer : this.specFightPlayers) {
					if(fightPlayer == tempFightPlayer) {
						isContain = true;
						break;
					}
				}
				if(!isContain) {
					this.specFightPlayers.add(fightPlayer);
				}
			}
		}
		
		for (FightPlayer fightPlayer : specFightPlayers) {
			Role role = fightPlayer.getRole();
			if(role.getUseItemLeftCount(Item.saman_potion_xml_id) > 0) {
				role.changeUseItemLeftCount(Item.saman_potion_xml_id, -1);
				roleRepo.saveRole(role);
			}
		}
	}

	public RoomType getRoomType() {
		return roomType;
	}

	private void fightRestart() {
		this.isRestart = true;
		this.clearFightInfo();
		this.clearState();
		if(fightPlayers.size() == 0) {
			destroy();
			return;
		}
		//推送给前端，战斗重新开始
		FightRoom.this.initPhysics();
		this.fightRoomState = FightRoomState.prepare_countdown;
		if(roomType != RoomType.tryPlay) {
			ServerMapInfo serverMapInfo = randomMap();
			switchMap(serverMapInfo);
		} else {
			switchMap(mapInfo);
		}
		
		roomCreateTime = System.currentTimeMillis();
		this.roomFightIndex++;
		
		this.initPlayerUnitsAndDestinationSamanRoleId();
		FightRestartInfoProto.Builder builder = FightRestartInfoProto.newBuilder();
		builder.setRoomInfo(FightRoom.this.buildProto());
		builder.setMapInfo(FightRoom.this.getMapInfo().getMapInfo());
		
		for(FightPlayer fightPlayer : this.specFightPlayers) {
			FightSkillTrigger.startFightTriggerSkill(this, fightPlayer.getFightUnit());
		}
		
		for (FightPlayer fightPlayer : fightPlayers) {
			fightPlayer.respond("fightRoom.fightRestart", CmdStatus.notDecrypt, builder);
		}
		this.curStateStartFrameIndex = this.getFrameIndex();
		this.curStateStartTime = System.currentTimeMillis();
		
		//初始化玩家的单位
//		for (FightPlayer fightPlayer : fightPlayers) {
//			notifyPlayerEnter(fightPlayer, true);
//		}
		
		for (FightPlayer fightPlayer : fightPlayers) {
			fightPlayer.setLastActionFrame(this.getFrameIndex());
			if(fightPlayer.getFightUnit() != null) {
				CommonEventProto.Builder builder2 = CommonEventProto.newBuilder();
				builder2.setEventType(EventType.unit_init.getCode());
				builder2.setFrameIndex(this.getFrameIndex());
				builder2.setRoleId(fightPlayer.getRoleId());
				builder2.setUnitInfo(fightPlayer.getFightUnit().buildProto(true));
				for (FightPlayer player : fightPlayers) {
					player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder2);
				}
			}
		}
		logger.info(String.format("fight(roomId:%d) restart", getRoomId()));
	}
	
	public void refreshPlayerRoleInfo() {
		for (FightPlayer fightPlayer : fightPlayers) {
			Role role = roleRepo.getRole(fightPlayer.getRoleId());
			fightPlayer.setRole(role);
		}
	}

	public ServerMapInfo randomMap() {
//		if(true) {
//			List<ServerMapInfo> list = serverContext.getServerMapInfos().get(3);
//			for(ServerMapInfo serverMapInfo : list) {
//				if(serverMapInfo.getMapId() == 600511) {
//					return serverMapInfo;
//				}
//			}
//		}
		int easyTypeNum = 0;
		for(FightPlayer fightPlayer : fightPlayers) {
			if(fightPlayer.getWantedRoomType() == RoomType.easy) {
				easyTypeNum++;
			}
		}
		float easyPercent = 0;
		if(fightPlayers.size() == 0) {
			easyPercent = 0;
		} else {
			easyPercent = easyTypeNum * 1f / fightPlayers.size();
		}
		float specDealMapStar1Percent = 0f;
		if(easyPercent > 0.8) {
			specDealMapStar1Percent = 0.4f;
		} else if(easyPercent > 0.5) {
			specDealMapStar1Percent = 0.3f;
		} else if(easyPercent > 0.3) {
			specDealMapStar1Percent = 0.2f;
		}
		int star = 2;
		if(specDealMapStar1Percent > 0 && random.nextFloat() < specDealMapStar1Percent) {
			star = 1;
		} else {
			if(this.roomType == RoomType.advance) {
				star = ProbabilityGenerator.getRandomChoiceWithRatioArr(hard_room_map_star_rate) + 1 + 3;
			} else {
				star = ProbabilityGenerator.getRandomChoiceWithRatioArr(normal_room_map_star_rate) + 1;
			}
		}
		if(star == 1 && this.lastMapIsEasy) {
			star = ProbabilityGenerator.getRandomChoiceWithRatioArr(normal_room_map_star_rate_without_easy) + 1 + 1;
		}
		
		if(star == 1) {
			this.lastMapIsEasy = true;
		} else {
			this.lastMapIsEasy = false;
		}
		logger.info("random map star:" + star);
		return randomMap0(star);
	}
	
	private ServerMapInfo randomMap0(int star) {
		List<Long> limitMapIds = new LinkedList<Long>();
		limitMapIds.addAll(this.lastPlayMapIds);
		List<ServerMapInfo> serverMapInfos = serverContext.getServerMapInfos().get(star);
		if(serverMapInfos.size() == 0) { //预防出错
			serverMapInfos = serverContext.getServerMapInfos().get(2);
		}
		ServerMapInfo serverMapInfo = null;
		
		int count = 0; //最多尝试20次
		while(true) {
			count++;
			serverMapInfo = serverMapInfos.get(random.nextInt(serverMapInfos.size()));
			if(count <= 20 && limitMapIds.contains(serverMapInfo.getMapId())) {
				continue;
			}
			if(serverMapInfo.getMapInfo().getSceneSettingInfo().getGameType() == GameSpecType.partner.getCode()) { //伴侣模式，检查人数
				if(this.fightPlayers.size() == 0 || this.fightPlayers.size() % 2 != 0) {
					limitMapIds.add(serverMapInfo.getMapId());
					continue;
				}
			}
			if(serverMapInfo.getMapInfo().getSceneSettingInfo().getGameType() == GameSpecType.double_saman_pk.getCode() || serverMapInfo.getMapInfo().getSceneSettingInfo().getGameType() == GameSpecType.double_saman_cooperation.getCode()) {
				if(this.fightPlayers.size() < 2) {
					limitMapIds.add(serverMapInfo.getMapId());
					continue;
				}
			}
//			if(serverMapInfo.getMapInfo().getSceneSettingInfo().getGameType() != GameSpecType.no_shaman.getCode()) {
//				boolean isAllWebSocket = true;
//				for(FightPlayer fightPlayer: this.fightPlayers) {
//					if(!fightPlayer.getGamePlayer().isWebSocket()) {
//						isAllWebSocket = false;
//					}
//				}
//				if(isAllWebSocket) {
//					continue;
//				}
//			}
			break;
		}
		for(long combinedMapId : serverMapInfo.getMapInfo().getSceneSettingInfo().getCombinedMapIdsList()) {
			ServerMapInfo combinedMapInfo = fightRepo.getServerMapInfo(combinedMapId);
			serverMapInfo.addCombinedMapInfo(combinedMapInfo.getMapInfo());
		}
		return serverMapInfo;
	}
	
	public List<FightPlayer> getMyselfSideSpecFightPlayers(long destinationRoleId){
		final List<FightPlayer> myselfSideSpecFightPlayers = new ArrayList<FightPlayer>();
		GameSpecType gameSpecType = GameSpecType.getGameSpecTypeByCode(this.getMapInfo().getMapInfo().getSceneSettingInfo().getGameType());
		switch (gameSpecType) {
		case common:
			myselfSideSpecFightPlayers.add(this.specFightPlayers.get(0));
			break;
		case no_shaman:
			break;
		case double_saman_cooperation:
			myselfSideSpecFightPlayers.addAll(this.specFightPlayers);
			break;
		case double_saman_pk:
			for(FightPlayer fightPlayer : this.specFightPlayers) {
				if(destinationRoleId == fightPlayer.getRoleId()) {
					myselfSideSpecFightPlayers.add(fightPlayer);
					break;
				}
			}
			break;
		case partner:
			break;
		case team_compete:
			for(FightPlayer fightPlayer : this.specFightPlayers) {
				if(destinationRoleId == fightPlayer.getRoleId()) {
					myselfSideSpecFightPlayers.add(fightPlayer);
					break;
				}
			}
			break;
		default:
			break;
		}
		return myselfSideSpecFightPlayers;
	}
	
	public boolean isSpecByRoleId(long roleId){
		boolean temp = false;
		for(FightPlayer fightPlayer : this.specFightPlayers) {
			if(roleId == fightPlayer.getRoleId()) {
				temp = true;
			}
		}
		return temp;
	}
	
	public void unitWin(final long roleId, final long destinationRoleId){
		this.unitWinNum++;
		final List<FightPlayer> needAddExpSpecFightPlayers = this.getMyselfSideSpecFightPlayers(destinationRoleId);
		final boolean isSpec = this.isSpecByRoleId(roleId);
		if(this.roomType != RoomType.tryPlay && this.roomType != RoomType.hall) {
			businessPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						RoomType roomType = FightRoom.this.getRoomType();
						RoleWeekFightInfo roleWeekFightInfo = roleRepo.getRoleWeekFightInfo(roleId);
						BattleResultValueProperty battleResultValueProperty = battleResultData.getBattleResultValueProperty(1);
						Role role = roleRepo.getRole(roleId);
						RoleProperty roleProperty = roleData.findRoleLevelUpProperty(role.getRoleLevel());
						
						//计算奶酪
						FightPlayerRewardResult rewardResult = findFightPlayer(roleId).getRewardResult();
						boolean canGetReward = true;
						if(roomType == RoomType.easy) {
							if(role.getRoleLevel() > 20) {
								canGetReward = false;
							}
						} else if(roomType == RoomType.normal) {
							if(role.getRoleLevel() > 65) {
								canGetReward = false;
							}
						}
						if(canGetReward) {
							calCheeseReward(roleWeekFightInfo, battleResultValueProperty, role, roleProperty, rewardResult);
							//基础经验
							calExpReward(isSpec, roomType, roleWeekFightInfo, battleResultValueProperty, role, roleProperty, rewardResult);
							
							//修改数量
							roleWeekFightInfo.addCheese(rewardResult.getAddCheeseNum() + rewardResult.getNormalExtCheese());
							role.addCheese(rewardResult.getAddCheeseNum() + rewardResult.getNormalExtCheese() + rewardResult.getAdvanceExtCheese());
							roleWeekFightInfo.addExp(rewardResult.getAddExpNum() + rewardResult.getNormalExtExp());
							roleWeekFightInfo.addAddExp(rewardResult.getRankingExtExp());
							rewardResult.setBeforeLevel(role.getRoleLevel());
							rewardResult.setBeforeExp(role.getCurrentExp());
							role.addExp(rewardResult.getAddExpNum() + rewardResult.getNormalExtExp() + rewardResult.getAdvanceExtExp() + rewardResult.getRankingExtExp(), roleData);
							rewardResult.setAfterLevel(role.getRoleLevel());
							rewardResult.setAfterExp(role.getCurrentExp());
						} else {
							rewardResult.setBeforeLevel(role.getRoleLevel());
							rewardResult.setBeforeExp(role.getCurrentExp());
							rewardResult.setAfterLevel(role.getRoleLevel());
							rewardResult.setAfterExp(role.getCurrentExp());
						}
						
						//处理萨满排位
						if(!isSpec) {
							for(FightPlayer specFightPlayer : needAddExpSpecFightPlayers) {
								specFightPlayer.addHelpOtherMiceSuccessNum();
								SpecMouseTypeProperty specMouseTypeProperty = specMouseTypeData.getSpecMouseTypeProperty(specFightPlayer.getRole().getSpecMouseTypeId());
								if(specFightPlayer.getRole().getShammanLevel() >= specMouseTypeProperty.getLimitSamanLevel()) {
									continue;
								}
								Role shammanRole = roleRepo.getRole(specFightPlayer.getRoleId());
								shammanRole.addShammanExp(specMouseTypeProperty.getPerHelpPoint(), roleData);
								shammanRole.incRescueNum(specMouseTypeProperty.getPerHelpPoint());
								roleRepo.saveRole(shammanRole);
							}
						}
						
						dealWithAchievement(roleId, needAddExpSpecFightPlayers, isSpec, roomType, battleResultValueProperty, role, rewardResult);
						
						FightPlayer fightPlayer = FightRoom.this.findFightPlayer(roleId);
						ChestSlot chestSlot = activeService.fightWin(roleId);
						fightPlayer.setChestSlot(chestSlot);
						
						if(isSpec) {
							role.addSamanNum();
							role.addSamanWinNum();
						} else {
							role.addMouseFightNum();
							role.addMouseWinNum();
						}
						
						roleRepo.saveRole(role);
						roleRepo.saveRoleWeekFightInfo(roleWeekFightInfo);
						
						itemDomainService.addItems(getFightWinResult().getItems(), roleId);
						
						NotifyRoleInfoChangeMsgProto.Builder builder = NotifyRoleInfoChangeMsgProto.newBuilder();
						builder.setRoleCheese(role.getCheese());
						builder.setRoleExp(role.getCurrentExp());
						builder.setRoleLevel(role.getRoleLevel());
						builder.setWeekCheese(roleWeekFightInfo.getCheese());
						builder.setWeekExp(roleWeekFightInfo.getExp());
						builder.setWeekAddExp(roleWeekFightInfo.getAddExp());
						fightPlayer.getGamePlayer().respond("notifyRoleInfoChange", CmdStatus.notDecrypt, builder);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	private void calExpReward(final boolean isSpec,
			RoomType roomType, RoleWeekFightInfo roleWeekFightInfo,
			BattleResultValueProperty battleResultValueProperty,
			Role role, RoleProperty roleProperty,
			FightPlayerRewardResult rewardResult) {
		int addExpNum = 0;
		int normalExtExp = 0;
		int advanceExtExp = 0;
		int rankingExtExp = 0;
		if(roleWeekFightInfo.getExp() < roleProperty.getMaxWeekExp()) {
			addExpNum += isSpec ? battleResultValueProperty.getShammanBaseWinExp() : battleResultValueProperty.getBaseWinExp();
			if(roomType == RoomType.normal) {
				addExpNum += battleResultValueProperty.getNormalTypeExp();
			} else if(roomType == RoomType.advance) {
				addExpNum += battleResultValueProperty.getAdvanceTypeExp();
			}
			
			//普通经验药水
			if(role.getUseItemLeftCount(Item.base_exp_book_xml_id) > 0) {
				ItemProperty itemProperty = itemData.getItemProperty(Item.base_exp_book_xml_id);
				normalExtExp += itemProperty.getUseAddParam();
				role.changeUseItemLeftCount(Item.base_exp_book_xml_id, -1);
			}
		}
		
		//名次经验
		if(!isSpec) {
			if(roleWeekFightInfo.getAddExp() < roleProperty.getMaxWeekAddExp()) {
				int ranking = FightRoom.this.unitWinNum;
				if(ranking <= 3) {
					rankingExtExp += battleResultValueProperty.getRankingAddExpList().get(ranking - 1);
				}
			}
		}
		
		//高级经验药水
		if(role.getUseItemLeftCount(Item.advance_exp_book_xml_id) > 0) {
			ItemProperty itemProperty = itemData.getItemProperty(Item.advance_exp_book_xml_id);
			advanceExtExp += itemProperty.getUseAddParam();
			role.changeUseItemLeftCount(Item.advance_exp_book_xml_id, -1);
		}
		rewardResult.setRankingExtExp(rankingExtExp);
		rewardResult.setAddExpNum(addExpNum);
		rewardResult.setNormalExtExp(normalExtExp);
		rewardResult.setAdvanceExtExp(advanceExtExp);
	}

	private void calCheeseReward(RoleWeekFightInfo roleWeekFightInfo, BattleResultValueProperty battleResultValueProperty,
			Role role, RoleProperty roleProperty, FightPlayerRewardResult rewardResult) {
		int addCheeseNum = 0;
		int normalExtCheese = 0;
		int advanceExtCheese = 0;
		
		//小于上限
		if(roleWeekFightInfo.getCheese() < roleProperty.getMaxWeekCheese()) {
			//基础值
			addCheeseNum += battleResultValueProperty.getBaseWinCheese();
			
			//普通药水
			if(role.getUseItemLeftCount(Item.base_cheese_book_xml_id) > 0) {
				ItemProperty itemProperty = itemData.getItemProperty(Item.base_cheese_book_xml_id);
				normalExtCheese += itemProperty.getUseAddParam();
				role.changeUseItemLeftCount(Item.base_cheese_book_xml_id, -1);
			}
		}
		//高级药水
		if(role.getUseItemLeftCount(Item.advance_cheese_book_xml_id) > 0) {
			ItemProperty itemProperty = itemData.getItemProperty(Item.advance_cheese_book_xml_id);
			advanceExtCheese += itemProperty.getUseAddParam();
			role.changeUseItemLeftCount(Item.advance_cheese_book_xml_id, -1);
		}
		
		rewardResult.setAddCheeseNum(addCheeseNum);
		rewardResult.setNormalExtCheese(normalExtCheese);
		rewardResult.setAdvanceExtCheese(advanceExtCheese);
	}
	
	private void dealWithAchievement(final long roleId,
			final List<FightPlayer> needAddExpSpecFightPlayers,
			final boolean isSpec, RoomType roomType,
			BattleResultValueProperty battleResultValueProperty,
			Role role, FightPlayerRewardResult rewardResult) {
		List<UpdateAchievement> updateAchievements = new ArrayList<UpdateAchievement>();
		if (!isSpec) {
			//处理萨满
			boolean isAllSpecFightPlayerDead = true;
			for(FightPlayer specFightPlayer : needAddExpSpecFightPlayers) {
				Role shammanRole = roleRepo.getRole(specFightPlayer.getRoleId());
				if (!specFightPlayer.isDead()) {
					isAllSpecFightPlayerDead = false;
				}
				List<UpdateAchievement> updateSamanAchievements = new ArrayList<UpdateAchievement>();
				updateSamanAchievements.add(new UpdateAchievement(shammanRole.getId(), AchievementType.saman_level_up, role.getShammanLevel()));
				if (roomType == RoomType.advance) {
					updateSamanAchievements.add(new UpdateAchievement(shammanRole.getId(), AchievementType.save_mice_quality_senior, 1));
				}else if (roomType == RoomType.normal){
					updateSamanAchievements.add(new UpdateAchievement(shammanRole.getId(), AchievementType.save_mice_quality_medium, 1));
				}
				achievementService.updateAchievements(shammanRole.getId(), updateSamanAchievements);
			} 
			if (isAllSpecFightPlayerDead) {
				if (roomType == RoomType.advance) {
					updateAchievements.add(new UpdateAchievement(roleId, AchievementType.saman_dead_transfor_cheese_senior, 1));
				}else if (roomType == RoomType.normal){
					updateAchievements.add(new UpdateAchievement(roleId, AchievementType.saman_dead_transfor_cheese_medium, 1));
				}
			}
			//处理老鼠
			int unitWinNum = FightRoom.this.getUnitWinNum();
			if (unitWinNum <= 3) {
				if (unitWinNum == 1) {
					if (roomType == RoomType.advance) {
						updateAchievements.add(new UpdateAchievement(roleId, AchievementType.champion_senior, 1));
					}else if (roomType == RoomType.normal){
						updateAchievements.add(new UpdateAchievement(roleId, AchievementType.champion_medium, 1));
					}
					role.incChampionNum(1);
				}
				if (unitWinNum == 2) {
					if (roomType == RoomType.advance) {
						updateAchievements.add(new UpdateAchievement(roleId, AchievementType.second_place_senior, 1));
					}else if (roomType == RoomType.normal){
						updateAchievements.add(new UpdateAchievement(roleId, AchievementType.second_place_medium, 1));
					}
				}
				if (unitWinNum == 3) {
					if (roomType == RoomType.advance) {
						updateAchievements.add(new UpdateAchievement(roleId, AchievementType.third_place_senior, 1));
					}else if (roomType == RoomType.normal){
						updateAchievements.add(new UpdateAchievement(roleId, AchievementType.third_place_medium, 1));
					}
				}
			}
		}
		if (roomType == RoomType.advance) {
			updateAchievements.add(new UpdateAchievement(roleId, AchievementType.transfor_cheese_senior, 1));
		}else if (roomType == RoomType.normal){
			updateAchievements.add(new UpdateAchievement(roleId, AchievementType.transfor_cheese_senior, 1));
		}
		updateAchievements.add(new UpdateAchievement(roleId, AchievementType.role_level_up, role.getRoleLevel()));
		updateAchievements.add(new UpdateAchievement(roleId, AchievementType.fight_get_cheese, rewardResult.getAddCheeseNum()));
		achievementService.updateAchievements(roleId, updateAchievements);
	}
	
	public void unitTransforDropItem(final long roleId){
		if(this.roomType != RoomType.tryPlay && this.roomType != RoomType.hall) {
			businessPool.execute(new Runnable() {
				@Override
				public void run() {
					logger.info(String.format("unitTransforDropItem:%d", roleId));
					itemDomainService.addItems((List<Item>) getFightingResult().getItems(), roleId);
				}
			});
		}
	}
	
	public void unitDead(final FightPlayer fightPlayer){
		final long roleId = fightPlayer.getRoleId();
		final List<FightPlayer> specFightPlayers = this.getSelfSideSpecFightPlayerByCampType(fightPlayer.getCampType());
		final boolean isSpec = this.isSpecByRoleId(roleId);
		if(this.roomType != RoomType.tryPlay && this.roomType != RoomType.hall) {
			businessPool.execute(new Runnable() {
				@Override
				public void run() {
					RoomType roomType = FightRoom.this.getRoomType();
					List<UpdateAchievement> updateAchievements = new ArrayList<UpdateAchievement>();
					int initAddExpNum = battleResultData.getBattleResultValueProperty(1).getDeadExp();
					RoleWeekFightInfo roleWeekFightInfo = roleRepo.getRoleWeekFightInfo(roleId);
					Role role = roleRepo.getRole(roleId);
					RoleProperty roleProperty = roleData.findRoleLevelUpProperty(role.getRoleLevel());
					FightPlayerRewardResult rewardResult = fightPlayer.getRewardResult();
					if(!fightPlayer.isLastFightIsNotAction() && roleWeekFightInfo.getExp() < roleProperty.getMaxWeekExp()) {
						boolean canGetReward = true;
						if(roomType == RoomType.easy) {
							if(role.getRoleLevel() > 20) {
								canGetReward = false;
							}
						} else if(roomType == RoomType.normal) {
							if(role.getRoleLevel() > 65) {
								canGetReward = false;
							}
						}
						if(canGetReward) {
							rewardResult.setAddExpNum(initAddExpNum);
						}
					}
					if(rewardResult.getAddExpNum() > 0) {
						roleWeekFightInfo.addExp(rewardResult.getAddExpNum());
						rewardResult.setBeforeLevel(role.getRoleLevel());
						rewardResult.setBeforeExp(role.getCurrentExp());
						role.addExp(rewardResult.getAddExpNum(), roleData);
						rewardResult.setAfterLevel(role.getRoleLevel());
						rewardResult.setAfterExp(role.getCurrentExp());
						roleRepo.saveRoleWeekFightInfo(roleWeekFightInfo);
					} else {
						rewardResult.setBeforeLevel(role.getRoleLevel());
						rewardResult.setBeforeExp(role.getCurrentExp());
						rewardResult.setAfterLevel(role.getRoleLevel());
						rewardResult.setAfterExp(role.getCurrentExp());
					}
					
					if(isSpec) {
						role.addSamanNum();
					} else {
						role.addMouseFightNum();
					}
					roleRepo.saveRole(role);
					if (roomType == RoomType.advance) {
						updateAchievements.add(new UpdateAchievement(roleId, AchievementType.dead_quality_senior, 1));
					} else if(roomType == RoomType.normal) {
						updateAchievements.add(new UpdateAchievement(roleId, AchievementType.dead_quality_medium, 1));
					}
					if (!isSpec) {
						boolean isAllSpecDead = true;
						for (FightPlayer fightPlayer2 : specFightPlayers) {
							if (!fightPlayer2.isDead()) {
								isAllSpecDead = false;
								break;
							}
						}
						if (isAllSpecDead) {
							updateAchievements.add(new UpdateAchievement(roleId, AchievementType.saman_dead_kill_self, 1));
						}
					}else{
						for (FightPlayer fightPlayer3 : specFightPlayers) {
							if (!fightPlayer3.isDead()) {
								if (roomType == RoomType.advance) {
									achievementService.updateOneAchievement(new UpdateAchievement(fightPlayer3.getRoleId(), AchievementType.kill_mice_quality_senior, 1));
								}else if(roomType == RoomType.normal){
									achievementService.updateOneAchievement(new UpdateAchievement(fightPlayer3.getRoleId(), AchievementType.kill_mice_quality_medium, 1));
								}
							}
						}
					}
					updateAchievements.add(new UpdateAchievement(roleId, AchievementType.role_level_up, role.getRoleLevel()));
					achievementService.updateAchievements(roleId, updateAchievements);
					
					NotifyRoleInfoChangeMsgProto.Builder builder = NotifyRoleInfoChangeMsgProto.newBuilder();
					builder.setRoleCheese(role.getCheese());
					builder.setRoleExp(role.getCurrentExp());
					builder.setRoleLevel(role.getRoleLevel());
					builder.setWeekCheese(roleWeekFightInfo.getCheese());
					builder.setWeekExp(roleWeekFightInfo.getExp());
					builder.setWeekAddExp(roleWeekFightInfo.getAddExp());
					fightPlayer.getGamePlayer().respond("notifyRoleInfoChange", CmdStatus.notDecrypt, builder);
				}
			});
		}
	}
	
	public int getRoomFightIndex() {
		return roomFightIndex;
	}

	public FightResult getFightingResult() {
		return fightingResult;
	}

	public void setFightingResult(FightResult fightResult) {
		this.fightingResult = fightResult;
	}

	public FightResult getFightWinResult() {
		return fightWinResult;
	}

	public void setFightWinResult(FightResult fightWinResult) {
		this.fightWinResult = fightWinResult;
	}

	public int getUnitWinNum() {
		return unitWinNum;
	}

	public void setUnitWinNum(int unitWinNum) {
		this.unitWinNum = unitWinNum;
	}

	private void handlerBombInit(
			final ConjurePhysicsObjEventProto conjurePhysicsObjEventProto,
			final FightPlayer fightPlayer) {
		ConjurePhysicsObjEventProto.Builder builder = ConjurePhysicsObjEventProto.newBuilder();
		builder.setEventType(EventType.conjure_physicsobj_init_event_type.getCode());
		builder.setFrameIndex(this.getFrameIndex());
		builder.setRoleId(fightPlayer.getRoleId());
		broadcastConjurePhysicsObjEvent(builder, fightPlayer);
		//气功，炸弹
		float posX = conjurePhysicsObjEventProto.getPhysicsObj().getPosX() / 1000f;
		float posY = conjurePhysicsObjEventProto.getPhysicsObj().getPosY() / 1000f;
		ChangePhysicsObjResult useSkillResult = FightSkillTrigger.conjureBombTriggerSkill(this, fightPlayer);
		boolean changeQigongSkipSelf = useSkillResult.changeQigongSkipSelf;
		float radius = PhysicsObj.default_qigong_explode_range * (1 + useSkillResult.changeQigongRangeRate);
		Vec2 baseImpulse = new Vec2(-physicsConfigProperty.getBombImpulse(), 0f);
		bombExplode(fightPlayer, posX, posY, changeQigongSkipSelf, radius, baseImpulse);
	}

	public void bombExplode(final FightPlayer conjurePlayer, float posX,
			float posY, boolean changeQigongSkipSelf, float radius, Vec2 baseImpulse) {
		float maxLength = radius * 2;
		float calVal = maxLength - radius;
		Vec2 pos1 = new Vec2(posX, posY);
		for (FightUnit fightUnit : FightRoom.this.units.values()) {
			if(changeQigongSkipSelf && fightUnit == conjurePlayer.getFightUnit()) {
				continue;
			}
			Vec2 pos2 = fightUnit.getBody().getPosition();
			float distance = CalUtil.pDistance(pos1, pos2);
			if(distance > maxLength) {
				continue;
			}
			float angle = CalUtil.calAngle(pos1, pos2);
			Vec2 impulse = CalUtil.pRotate(baseImpulse, angle);
			if(distance <= radius) {
				fightUnit.getBody().applyLinearImpulse(impulse, pos2, true);
			} else {
				fightUnit.getBody().applyLinearImpulse(impulse.mul((distance - radius) / calVal), pos2, true);
			}
			CommonEventProto.Builder builder2 = CommonEventProto.newBuilder();
			builder2.setRoleId(fightUnit.getRoleId());
			builder2.setEventType(EventType.unit_reset_velocity.getCode());
			builder2.setFrameIndex(this.getFrameIndex());
			builder2.setUnitInfo(fightUnit.buildProto());
			for (FightPlayer player : fightPlayers) {
				player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder2);
			}
		}
		
		ReusedProtoBuilder reusedProtoBuilder = ProtoBuilderPoolManager.getBuilder(PeriodUpdateObjInfoProto.class);
		PeriodUpdateObjInfoProto.Builder builder3 = (PeriodUpdateObjInfoProto.Builder)reusedProtoBuilder.getBuilder();
		builder3.setEventType(EventType.conjure_physicsobj_update_event_type.getCode());
		builder3.setFrameIndex(this.getFrameIndex());
		for (PhysicsObj physicsObj : FightRoom.this.physicsObjs.values()) {
			Vec2 pos2 = physicsObj.getBody().getPosition();
			float distance = CalUtil.pDistance(pos1, pos2);
			if(distance > maxLength || physicsObj.getBody().getType() == BodyType.STATIC) {
				continue;
			}
			float angle = CalUtil.calAngle(pos1, pos2);
			Vec2 impulse = CalUtil.pRotate(baseImpulse, angle);
			if(distance <= radius) {
				physicsObj.getBody().applyLinearImpulse(impulse, pos2, true);
			} else {
				physicsObj.getBody().applyLinearImpulse(impulse.mul((distance - radius) / calVal), pos2, true);
			}
			
			builder3.addObjs(physicsObj.buildProto(false));
		}
		if(builder3.getObjsCount() > 0 && fightPlayers.size() > 0) {
			reusedProtoBuilder.setReleaseCounter(fightPlayers.size());
			for (FightPlayer player : fightPlayers) {				
				player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, reusedProtoBuilder);
			}
		}else{
			reusedProtoBuilder.release();
		}
	}
	
	public void addMaxFightTime(int second) {
		this.maxFightTime += second * FightConfig.fps;
	}

	public List<PhysicsObj> findPhysicsObjsBySpecType(PhysicsObjSpecType specType) {
		List<PhysicsObj> result = new LinkedList<PhysicsObj>();
		for(PhysicsObj physicsObj : this.physicsObjs.values()) {
			if(physicsObj.getPhysicsObjProperty().getSpecType() == specType) {
				result.add(physicsObj);
			}
		}
		return result;
	}

	public BusinessPool getBusinessPool() {
		return businessPool;
	}

	public void setBusinessPool(BusinessPool businessPool) {
		this.businessPool = businessPool;
	}
	
	public List<FightUnit> getSelfSideSaman(CampType campType) {
		List<FightUnit> result = new ArrayList<FightUnit>();
		for(FightPlayer fightPlayer : this.specFightPlayers) {
			if(fightPlayer.getCampType() == campType) {
				if(fightPlayer.getFightUnit() != null) {
					result.add(fightPlayer.getFightUnit());
				}
			}
		}
		return result;
	}
	
	public List<FightPlayer> getSelfSideSpecFightPlayerByCampType(CampType campType) {
		List<FightPlayer> result = new ArrayList<FightPlayer>();
		for(FightPlayer fightPlayer : this.specFightPlayers) {
			if(fightPlayer.getCampType() == campType) {
				result.add(fightPlayer);
			}
		}
		return result;
	}
	public List<FightUnit> getSelfSideFightPlayer(CampType campType) {
		List<FightUnit> result = new ArrayList<FightUnit>();
		for(FightPlayer fightPlayer : this.fightPlayers) {
			if(fightPlayer.getCampType() == campType) {
				if(fightPlayer.getFightUnit() != null) {
					result.add(fightPlayer.getFightUnit());
				}
			}
		}
		return result;
	}
	
	public PhysicsObj findCampDestination(CampType campType){
		for (PhysicsObj physicsObj : physicsObjs.values()) {
			if (physicsObj.getPhysicsObjProperty().getSpecType() == PhysicsObjSpecType.cheeseDestination && physicsObj.getPhysicsObjProperty().getCampType() == campType) {
				return physicsObj;
			}
		}
		return null;
	}

	public PhysicsObjData getPhysicsObjData() {
		return physicsObjData;
	}

	public void addPhysicsObj(PhysicsObj physicsObj) {
		this.physicsObjs.put(physicsObj.getId(), physicsObj);
	}

	public void setActiveService(ActiveService activeService) {
		this.activeService = activeService;
	}
	
	/**
	 * 
	 * @param type 0:更新状态 1:更新包含用户
	 * @return
	 */
	public DispatcherRoomProto.Builder buildDispatcherRoomInfo(int type) {
		DispatcherRoomProto.Builder builder = DispatcherRoomProto.newBuilder();
		builder.setRoomId(this.getRoomId());
		if(type == 1) {
			for(FightPlayer fightPlayer : this.fightPlayers) {
				builder.addUsers(fightPlayer.getRoleId());
			}
		}
		builder.setState(this.fightRoomState.getCode());
		builder.setCurStateStartTime(this.curStateStartTime);
		return builder;
	}

	public void setServerInfoData(ServerInfoData serverInfoData) {
		this.serverInfoData = serverInfoData;
	}
	
	public String getChannelName(){
		return this.channelName;
	}
}
