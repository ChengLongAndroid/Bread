package com.funcy.g01.hall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funcy.g01.base.bo.map.RoleMapInfo;
import com.funcy.g01.base.bo.map.ServerMapInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.base.dao.redis.FightRepo;
import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.net.DispatchServer;
import com.funcy.g01.base.net.GamePlayer;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.FightMapInfoProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto;
import com.funcy.g01.base.proto.dispatcher.DispatcherRespCmdProtoBuffer.StartFightGetFightRoomRespProto;
import com.funcy.g01.base.proto.service.HallServiceRespProtoBuffer.HallGetFightRoomInfoRespProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.LongReqProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.StrIntProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.StrLongProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.StrStrProto;
import com.google.protobuf.Message.Builder;

@Service
public class RoomService {
	
	@Autowired
	private ServerContext serverContext;
	
	@Autowired
	private ServerInfoData serverInfoData;
	
	@Autowired
	private FightRepo fightRepo;
	
	@Autowired
	private MapService mapService;
	
	public Builder tryPlayGetFightRoomInfo(ServerMapInfoProto serverMapInfoProto, GamePlayer gamePlayer) {
		LongReqProto.Builder mapbuilder = mapService.saveMap(serverMapInfoProto, gamePlayer);
		ServerMapInfo serverMapInfo = fightRepo.getServerMapInfo(mapbuilder.getIndex());
		StrLongProto.Builder builder = StrLongProto.newBuilder();
		builder.setParams1(gamePlayer.getPlatformId());
		builder.setParams2(serverMapInfo.getMapId());
		DispatchServer dispatchServer = serverContext.borrowHallDispatchServer();
		try {
			StartFightGetFightRoomRespProto resp = (StartFightGetFightRoomRespProto)dispatchServer.sendAndWaitResp("roomDispatchService.tryPlayGetFightRoom", builder);
			HallGetFightRoomInfoRespProto.Builder resultBuilder = HallGetFightRoomInfoRespProto.newBuilder();
			resultBuilder.setRoomId(resp.getRoomId());
			ServerInfo serverInfo = serverInfoData.getServerInfo(resp.getServerId());
			resultBuilder.setServerInfo(serverInfo.buildFightRoomServerInfo());
			return resultBuilder;
		} finally {
			serverContext.addHallDispatchServer4Send(dispatchServer);
		}
	}
	
	public Builder h5TryPlayGetFightRoomInfo(long mapId, GamePlayer gamePlayer) {
		ServerMapInfo serverMapInfo = fightRepo.getServerMapInfo(mapId);
		StrLongProto.Builder builder = StrLongProto.newBuilder();
		builder.setParams1(gamePlayer.getPlatformId());
		builder.setParams2(serverMapInfo.getMapId());
		DispatchServer dispatchServer = serverContext.borrowHallDispatchServer();
		try {
			StartFightGetFightRoomRespProto resp = (StartFightGetFightRoomRespProto)dispatchServer.sendAndWaitResp("roomDispatchService.tryPlayGetFightRoom", builder);
			HallGetFightRoomInfoRespProto.Builder resultBuilder = HallGetFightRoomInfoRespProto.newBuilder();
			resultBuilder.setRoomId(resp.getRoomId());
			ServerInfo serverInfo = serverInfoData.getServerInfo(resp.getServerId());
			resultBuilder.setServerInfo(serverInfo.buildFightRoomServerInfo());
			return resultBuilder;
		} finally {
			serverContext.addHallDispatchServer4Send(dispatchServer);
		}
	}
	
	public Builder getFightRoomInfo(String type, GamePlayer gamePlayer) {
		StrStrProto.Builder builder = StrStrProto.newBuilder();
		builder.setParams1(gamePlayer.getPlatformId());
		builder.setParams2(type);
		DispatchServer dispatchServer = serverContext.borrowHallDispatchServer();
		try {
			StartFightGetFightRoomRespProto resp = (StartFightGetFightRoomRespProto)dispatchServer.sendAndWaitResp("roomDispatchService.startFightGetFightRoom", builder);
			HallGetFightRoomInfoRespProto.Builder resultBuilder = HallGetFightRoomInfoRespProto.newBuilder();
			resultBuilder.setRoomId(resp.getRoomId());
			ServerInfo serverInfo = serverInfoData.getServerInfo(resp.getServerId());
			resultBuilder.setServerInfo(serverInfo.buildFightRoomServerInfo());
			return resultBuilder;
		} finally {
			serverContext.addHallDispatchServer4Send(dispatchServer);
		}
	}
	
}
