package com.funcy.g01.dispatcher.task;

import org.apache.log4j.Logger;

import com.ebo.synframework.nettybase.protoPool.ProtoBuilderPoolManager;
import com.ebo.synframework.nettybase.protoPool.ReusedProtoBuilder;
import com.funcy.g01.base.net.DispatchClientType;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.DispatcherRoomProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.IntReqProto;
import com.funcy.g01.base.proto.service.SynFightRespProtoBuffer.GetServerRoomsInfoRespProto;
import com.funcy.g01.dispatcher.bo.AllRoomManager;
import com.funcy.g01.dispatcher.bo.DispatchClient;
import com.funcy.g01.dispatcher.bo.DispatchServerContext;
import com.funcy.g01.dispatcher.bo.DispatcherRoom;

public class RefreshServerDispatchRoomStateTask implements Runnable {
	
	public static final Logger logger = Logger.getLogger(RefreshServerDispatchRoomStateTask.class);
	
	private int serverId;
	
	private DispatchClientType type;
	
	private AllRoomManager allRoomManager;
	
	private DispatchServerContext dispatchServerContext;
	
	private int count = 0;
	
	public static final int refreshUserRate = 10;
	
//	private long initTime = System.currentTimeMillis();
	
	public RefreshServerDispatchRoomStateTask(AllRoomManager allRoomManager, int serverId, DispatchServerContext dispatchServerContext, DispatchClientType dispatchClientType) {
		this.serverId = serverId;
		this.allRoomManager = allRoomManager;
		this.dispatchServerContext = dispatchServerContext;
		this.type = dispatchClientType;
	}

	@Override
	public void run() {
//		long now = System.currentTimeMillis();
//		if(now - initTime <= 20000) { //开服前20秒钟不处理
//			return;
//		}
		DispatchClient dispatchClient = null;
		try {
			dispatchClient = dispatchServerContext.borrowClient(serverId, type);
			String serviceAndMethod = null;
			if(type == DispatchClientType.hall) {
				serviceAndMethod = "roomService4Dispatcher.getServerRoomsInfo";
			} else {
				serviceAndMethod = "roomServiceInRoom4Dispatcher.getServerRoomsInfo";
			}
			ReusedProtoBuilder reusedProtoBuilder = ProtoBuilderPoolManager.getBuilder(IntReqProto.class);
			IntReqProto.Builder builder = (IntReqProto.Builder) reusedProtoBuilder.getBuilder();
			int getInfoType = 0;
			if(count % refreshUserRate == 0) {
				getInfoType = 1;
			}
			builder.setIndex(getInfoType);
			GetServerRoomsInfoRespProto proto = (GetServerRoomsInfoRespProto)dispatchClient.sendAndWaitResp(serviceAndMethod, reusedProtoBuilder);
			for(DispatcherRoomProto roomProto : proto.getRoomsList()) {
				if(type == DispatchClientType.hall) {
					DispatcherRoom dispatcherRoom = allRoomManager.getHallDispatcherRoom(roomProto.getRoomId());
					if(dispatcherRoom == null) {
						logger.error("can't find the hall room:" + roomProto.getRoomId());
					} else {
						if(getInfoType == 0) {
							dispatcherRoom.refreshStateByProto(roomProto);
						} else {
							dispatcherRoom.refreshAllByProto(roomProto);
						}
					}
				} else {
					DispatcherRoom dispatcherRoom = allRoomManager.getFightDispatcherRoom(roomProto.getRoomId());
					if(dispatcherRoom == null) {
						logger.error("can't find the fight room:" + roomProto.getRoomId());
					} else {
						if(getInfoType == 0) {
							dispatcherRoom.refreshStateByProto(roomProto);
						} else {
							dispatcherRoom.refreshAllByProto(roomProto);
						}
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(dispatchClient != null) {
				dispatchServerContext.addDispatchClient(dispatchClient);
			}
		}
		count++;
	}
	
}
