package com.funcy.g01.dispatcher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebo.synframework.nettybase.protoPool.ProtoBuilderPoolManager;
import com.ebo.synframework.nettybase.protoPool.ReusedProtoBuilder;
import com.funcy.g01.base.bo.friend.RoleFriendInfo;
import com.funcy.g01.base.dao.redis.FriendRepo;
import com.funcy.g01.base.dao.redis.RoleRepo;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.EmptyEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.SelectChannelInfoProto;
import com.funcy.g01.base.proto.dispatcher.DispatcherRespCmdProtoBuffer.GetSelectChannelInfoRespProto;
import com.funcy.g01.base.proto.dispatcher.DispatcherRespCmdProtoBuffer.LoginGetHallRespProto;
import com.funcy.g01.base.proto.dispatcher.DispatcherRespCmdProtoBuffer.StartFightGetFightRoomRespProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.BoolReqProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.IntReqProto;
import com.funcy.g01.dispatcher.bo.AllRoomManager;
import com.funcy.g01.dispatcher.bo.BaseTypeRoomManager;
import com.funcy.g01.dispatcher.bo.DispatchClient;
import com.funcy.g01.dispatcher.bo.DispatchResult;
import com.funcy.g01.dispatcher.bo.DispatcherRoom;
import com.funcy.g01.dispatcher.bo.RoomType;
import com.funcy.g01.dispatcher.bo.selectChannel.RoleSelectChannelInfo;
import com.funcy.g01.dispatcher.bo.selectChannel.SelectChannelContext;
import com.funcy.g01.dispatcher.bo.selectChannel.SelectChannelInfo;
import com.google.protobuf.Message.Builder;

@Service
public class RoomDispatchService {
	
	@Autowired
	private AllRoomManager allRoomManager;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private FriendRepo friendInfoRepo;
	
	@Autowired
	private SelectChannelContext selectChannelContext;
	
	public Builder isTheRoomExist(int serverId, long roomId, DispatchClient dispatchClient) {
		DispatcherRoom dispatcherRoom = null;
		for (BaseTypeRoomManager roomManager : allRoomManager.getRoomManagers().values()) {
			dispatcherRoom = roomManager.findRoom(serverId, roomId);
			if(dispatcherRoom != null) {
				break;
			}
		}
		BoolReqProto.Builder builder = BoolReqProto.newBuilder();
		if(dispatcherRoom != null) {
			builder.setParams1(true);
		}
		return builder;
	}
	
	public Builder loginGetHall(String platformId, DispatchClient dispatchClient) {
		BaseTypeRoomManager baseTypeRoomManager = allRoomManager.getBaseTypeRoomManager(RoomType.hall);
		long roleId = roleRepo.getRoleIdByPlatformId(platformId);
		RoleFriendInfo roleFriendInfo = friendInfoRepo.getRoleFriendInfo(roleId);
		DispatchResult dispatchResult = baseTypeRoomManager.dispatch(platformId, roleFriendInfo);
		DispatcherRoom dispatcherRoom = dispatchResult.getDispatcherRoom();
		LoginGetHallRespProto.Builder builder = LoginGetHallRespProto.newBuilder();
		builder.setIsNew(dispatchResult.isNew());
		builder.setRoomId(dispatcherRoom.getRoomId());
		builder.setServerId(dispatcherRoom.getServerId());
		return builder;
	}
	
	public Builder startFightGetFightRoom(String platformId, String roomTypeStr, DispatchClient dispatchClient) {
		RoomType roomType = RoomType.valueOf(roomTypeStr);
		if(roomType == RoomType.easy) {
			roomType = RoomType.normal;
		}
		long roleId = roleRepo.getRoleIdByPlatformId(platformId);
		RoleFriendInfo roleFriendInfo = friendInfoRepo.getRoleFriendInfo(roleId);
		BaseTypeRoomManager baseTypeRoomManager = allRoomManager.getBaseTypeRoomManager(roomType);
		DispatchResult dispatchResult = baseTypeRoomManager.dispatch(platformId, roleFriendInfo);
		DispatcherRoom dispatcherRoom = dispatchResult.getDispatcherRoom();
		StartFightGetFightRoomRespProto.Builder builder = StartFightGetFightRoomRespProto.newBuilder();
		builder.setRoomId(dispatcherRoom.getRoomId());
		builder.setServerId(dispatcherRoom.getServerId());
		return builder;
	}
	
	public Builder tryPlayGetFightRoom(String platformId, int mapId, DispatchClient dispatchClient) {
		RoomType roomType = RoomType.tryPlay;
		BaseTypeRoomManager baseTypeRoomManager = allRoomManager.getBaseTypeRoomManager(roomType);
		DispatchResult dispatchResult = baseTypeRoomManager.playerTryPlayMapDispatch(platformId, mapId);
		DispatcherRoom dispatcherRoom = dispatchResult.getDispatcherRoom();
		StartFightGetFightRoomRespProto.Builder builder = StartFightGetFightRoomRespProto.newBuilder();
		builder.setRoomId(dispatcherRoom.getRoomId());
		builder.setServerId(dispatcherRoom.getServerId());
		return builder;
	}
	
	public Builder fightRoomDestroy(String roomTypeStr, long roomId, DispatchClient dispatchClient) {
		RoomType roomType = RoomType.valueOf(roomTypeStr);
		BaseTypeRoomManager baseTypeRoomManager = allRoomManager.getBaseTypeRoomManager(roomType);
		baseTypeRoomManager.roomDestroy(roomId);
		if(roomType == RoomType.hall) {
			allRoomManager.removeHallDispatcherRoom(roomId);
		} else {
			allRoomManager.removeFightDispatcherRoom(roomId);
		}
		return EmptyEventProto.newBuilder();
	}
	
	public Builder fightRoomTryMerge(long roomId, String roomTypeStr, DispatchClient dispatchClient) {
		RoomType roomType = RoomType.valueOf(roomTypeStr);
		if(roomType == RoomType.easy) {
			roomType = RoomType.normal;
		}
		BaseTypeRoomManager baseTypeRoomManager = allRoomManager.getBaseTypeRoomManager(roomType);
		DispatcherRoom room = baseTypeRoomManager.findRoom(roomId);
		StartFightGetFightRoomRespProto.Builder builder = StartFightGetFightRoomRespProto.newBuilder();
		if(room.isBeingMerged()) {
			return builder;
		}
		DispatcherRoom dispatcherRoom = baseTypeRoomManager.findMergeRoom(roomId);
		if(dispatcherRoom != null) {
			dispatcherRoom.setMerge();
			room.setMerge();
			builder.setRoomId(dispatcherRoom.getRoomId());
			builder.setServerId(dispatcherRoom.getServerId());
		}
		return builder;
	}
	
	public Builder hallTryMerge(long roomId, String roomTypeStr, DispatchClient dispatchClient) {
		BaseTypeRoomManager baseTypeRoomManager = allRoomManager.getBaseTypeRoomManager(RoomType.hall);
		DispatcherRoom room = baseTypeRoomManager.findRoom(roomId);
		StartFightGetFightRoomRespProto.Builder builder = StartFightGetFightRoomRespProto.newBuilder();
		if(room.isBeingMerged()) {
			return builder;
		}
		DispatcherRoom dispatcherRoom = baseTypeRoomManager.findMergeRoom(roomId);
		if(dispatcherRoom != null) {
			dispatcherRoom.setMerge();
			room.setMerge();
			builder.setRoomId(dispatcherRoom.getRoomId());
			builder.setServerId(dispatcherRoom.getServerId());
		}
		return builder;
	}
	
	public Object getRoomServerId(long roomId, DispatchClient dispatchClient){
		ReusedProtoBuilder reusedProtoBuilder = ProtoBuilderPoolManager.getBuilder(IntReqProto.class);
		IntReqProto.Builder resp = (IntReqProto.Builder) reusedProtoBuilder.getBuilder();
		DispatcherRoom room = allRoomManager.getHallDispatcherRoom(roomId);
		if(room!=null){
			resp.setIndex(room.getServerId());
		}else{
			resp.setIndex(-1);
		}
		return reusedProtoBuilder;
	}
	
	public Builder getDispatcherRoomInfo(long roleId, int pageIndex, DispatchClient dispatchClient){
		GetSelectChannelInfoRespProto.Builder resp = GetSelectChannelInfoRespProto.newBuilder();
		RoleSelectChannelInfo roleSelectChannelInfo = selectChannelContext.getRoleSelectChannelInfo(roleId);
		resp.setPageCount(roleSelectChannelInfo.getPageCount());
		for(SelectChannelInfo channel:roleSelectChannelInfo.getChannelInfoByPage(pageIndex, RoleSelectChannelInfo.pageSize)){
			SelectChannelInfoProto.Builder info = SelectChannelInfoProto.newBuilder();
			info.setRoomId(channel.getRoomId());
			info.setServerId(channel.getServerId());
			info.setFriendsNum(channel.getFriendsNum());
			info.setChannelName(channel.getChannelName());
			resp.addChannelInfos(info);
		}
		return resp;
	}
}
