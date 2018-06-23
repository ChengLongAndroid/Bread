package com.funcy.g01.hall.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebo.synframework.synroom.tools.ConcurrentHashMap;
import com.funcy.g01.base.bo.fight.FightRoom;
import com.funcy.g01.base.bo.map.ServerMapInfo;
import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.base.dao.redis.FightRepo;
import com.funcy.g01.base.dao.redis.FriendRepo;
import com.funcy.g01.base.dao.redis.NpcRepo;
import com.funcy.g01.base.dao.redis.RoleRepo;
import com.funcy.g01.base.data.BattleResultData;
import com.funcy.g01.base.data.CheckpointData;
import com.funcy.g01.base.data.DropGroupData;
import com.funcy.g01.base.data.ItemData;
import com.funcy.g01.base.data.NpcData;
import com.funcy.g01.base.data.PhysicsConfigData;
import com.funcy.g01.base.data.PhysicsNailData;
import com.funcy.g01.base.data.PhysicsObjData;
import com.funcy.g01.base.data.RoleData;
import com.funcy.g01.base.data.SpecMouseTypeData;
import com.funcy.g01.base.global.BusinessPool;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.net.DispatchServer;
import com.funcy.g01.base.proto.dispatcher.DispatcherRespCmdProtoBuffer.CreateNewRoomRespProto;
import com.funcy.g01.base.proto.service.SynFightRespProtoBuffer.GetServerRoomsInfoRespProto;
import com.funcy.g01.dispatcher.bo.RoomType;
import com.google.protobuf.Message.Builder;

@Service
public class RoomService4Dispatcher {

	@Autowired
	private ServerContext serverContext;
	
	@Autowired
	private PhysicsConfigData physicsConfigData;
	
	@Autowired
	private PhysicsObjData physicsObjData;
	
	@Autowired
	private PhysicsNailData physicsNailData;
	
	@Autowired
	private FightRepo fightRepo;
	
	@Autowired 
	private RoleRepo roleRepo;
	
	@Autowired
	private RoleData roleData;

	@Autowired
	private BattleResultData battleResultData;
	
	@Autowired
	private DropGroupData dropGroupData;
	
	@Autowired
	private ItemDomainService itemService;
	
	@Autowired
	private AchievementService achievementService;
	
	@Autowired
	private BusinessPool businessPool;
	
	@Autowired
	private NpcRepo npcRepo;
	
	@Autowired
	private NpcData npcData;
	
	@Autowired
	private CheckpointData checkpointData;
	
	@Autowired
	private ItemData itemData;
	
	@Autowired
	private ActiveService activeService;
	
	@Autowired
	private SpecMouseTypeData specMouseTypeData;
	
	@Autowired
	private ServerInfoData serverInfoData;
	
	@Autowired
	private FriendRepo friendRepo;
	
	public Builder getServerRoomsInfo(int type, DispatchServer dispatchServer) {
		GetServerRoomsInfoRespProto.Builder builder = GetServerRoomsInfoRespProto.newBuilder();
		ConcurrentHashMap<Long, FightRoom> rooms = serverContext.getHallFightRooms();
		for(FightRoom fightRoom : rooms.values()) {
			builder.addRooms(fightRoom.buildDispatcherRoomInfo(type));
		}
		return builder;
	}
	
	public Builder createNewRoom(String roomType, long roomId, long mapId, DispatchServer dispatchServer) {
		CreateNewRoomRespProto.Builder builder = CreateNewRoomRespProto.newBuilder();
		FightRoom fightRoom = new FightRoom(roomId, physicsConfigData.getPhysicsConfigProperty(1), RoomType.hall);
		fightRoom.init(physicsObjData, physicsNailData, fightRepo, roleRepo, roleData, battleResultData, dropGroupData, itemService
				, achievementService, businessPool, this.serverContext, npcRepo, npcData, checkpointData, itemData, activeService
				, specMouseTypeData, serverInfoData, friendRepo);
		List<Long> maps = fightRepo.getRoleMapInfo(ServerConfig.edit_main_city_map_rold_id).getMapIds();
		Long randomMapId = maps.get((int)Math.random() * maps.size());
		ServerMapInfo serverMapInfo = fightRepo.getServerMapInfo(randomMapId);
		List<Long> combinedMapIds = serverMapInfo.getMapInfo().getSceneSettingInfo().getCombinedMapIdsList();
		for(long combinedMapId : combinedMapIds) {
			ServerMapInfo combinedMapInfo = fightRepo.getServerMapInfo(combinedMapId);
			serverMapInfo.addCombinedMapInfo(combinedMapInfo.getMapInfo());
		}
		fightRoom.initMap(serverMapInfo);
		fightRoom.startUpdate();
		serverContext.addHallFightRoom(fightRoom);
		builder.setIsSuccess(true);
		return builder;
	}
	
}
