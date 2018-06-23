package com.funcy.g01.dispatcher.bo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.funcy.g01.base.bo.friend.Friend;
import com.funcy.g01.base.bo.friend.RoleFriendInfo;
import com.funcy.g01.base.bo.serverconfig.ServerState;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.net.DispatchClientType;
import com.funcy.g01.base.proto.dispatcher.DispatcherRespCmdProtoBuffer.CreateNewRoomRespProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.StrLongLongProto;
import com.funcy.g01.base.util.ProbabilityGenerator;

public class BaseTypeRoomManager {
	
	public static final Logger logger = Logger.getLogger(BaseTypeRoomManager.class);

	private List<DispatcherRoom> rooms = new LinkedList<DispatcherRoom>();
	
	private ChangeTrendType changeTrendType = ChangeTrendType.normal;
	
	private RoomType roomType;
	
	private ServerContext serverContext;
	
	private AllRoomManager allRoomManager;
	
	private DispatchServerContext dispatchServerContext;
	
	private int baseNum;
	
	private int maxNum;
	
	private int friendMaxNum;
	
	private int mergeThreshold;
	
	public BaseTypeRoomManager(RoomType roomType) {
		this.roomType = roomType;
		if(roomType == RoomType.hall) {
			this.baseNum = ServerConfig.hall_room_base_player_num;
			this.maxNum = ServerConfig.hall_room_max_player_num;
			this.friendMaxNum = ServerConfig.hall_room_with_friend_player_num;
			this.mergeThreshold = ServerConfig.hall_room_merge_threshold;
		} else {
			this.baseNum = ServerConfig.fight_room_base_player_num;
			this.maxNum = ServerConfig.fight_room_max_player_num;
			this.friendMaxNum = ServerConfig.fight_room_with_friend_max_num;
			this.mergeThreshold = ServerConfig.fight_room_merge_threshold;
		}
	}
	
	public void init(ServerContext serverContext, AllRoomManager allRoomManager, DispatchServerContext dispatchServerContext) {
		this.serverContext = serverContext;
		this.allRoomManager = allRoomManager;
		this.dispatchServerContext = dispatchServerContext;
	}
	
	public DispatcherRoom findRoom(int serverId, long roomId) {
		for (DispatcherRoom dispatcherRoom : this.rooms) {
			if(dispatcherRoom.getServerId() == serverId && dispatcherRoom.getRoomId() == roomId) {
				return dispatcherRoom;
			}
		}
		return null;
	}
	
	public DispatcherRoom findRoom(long roomId) {
		for (DispatcherRoom dispatcherRoom : this.rooms) {
			if(dispatcherRoom.getRoomId() == roomId) {
				return dispatcherRoom;
			}
		}
		return null;
	}

	public List<DispatcherRoom> getRooms() {
		return rooms;
	}
	
	public synchronized void roomDestroy(long roomId) {
		Iterator<DispatcherRoom> it = this.rooms.iterator();
		while(it.hasNext()) {
			DispatcherRoom dispatcherRoom = it.next();
			if(dispatcherRoom.getRoomId() == roomId) {
				it.remove();
				break;
			}
		}
	}
	
	public synchronized DispatchResult playerTryPlayMapDispatch(String platformId, int mapId) {
		ServerState serverState = serverContext.selectNotBusyServer(this.roomType);
		DispatchClientType dispatchClientType = DispatchClientType.room;
		String serviceAndMethod = "roomServiceInRoom4Dispatcher.createNewRoom";
		DispatchClient client = dispatchServerContext.borrowClient(serverState.getServerId(), dispatchClientType);
		try {
			long roomId = this.allRoomManager.nextRoomId();
			StrLongLongProto.Builder builder = StrLongLongProto.newBuilder();
			builder.setParams1(this.roomType.name());
			builder.setParams2(roomId);
			builder.setParams3(mapId);
			CreateNewRoomRespProto result = (CreateNewRoomRespProto)client.sendAndWaitResp(serviceAndMethod, builder);
			if(result.getIsSuccess()) {
				DispatcherRoom dispatcherRoom = new DispatcherRoom(roomId, serverState.getServerId(), this.roomType);
				rooms.add(dispatcherRoom);
				allRoomManager.addFightDispatcherRoom(dispatcherRoom);
				return new DispatchResult(dispatcherRoom, true);
			} else {
				logger.error("try play create new room fail, serverId:" + serverState.getServerId());
				return null;
			}
		} finally {
			dispatchServerContext.addDispatchClient(client);
		}
		
	}
	
	public synchronized DispatchResult dispatch(String platformId, int mapId, RoleFriendInfo roleFriendInfo) {
		//多重优先级选择，先选好友多的，并且人数未到好友人数上限的
		int maxFriendNum = 0;
		DispatcherRoom resultRoom = null;
		if(roleFriendInfo != null) {
			for(DispatcherRoom dispatcherRoom : rooms) {
				if(changeTrendType == ChangeTrendType.decrease) { //低于最低阈值不再分配
					if(dispatcherRoom.getUserNum() <= this.mergeThreshold) {
						continue;
					}
				}
				if(dispatcherRoom.getUserNum() > this.friendMaxNum) {
					continue;
				}
				int count = 0;
				for(Friend friend : roleFriendInfo.getFriends()) {
					for(long roleId : dispatcherRoom.getUsers()) {
						if(roleId == friend.getRoleId()) {
							count++;
						}
					}
				}
				if(count > 0) {
					if(resultRoom == null) {
						resultRoom = dispatcherRoom;
						maxFriendNum = count;
					} else if(count > maxFriendNum) {
						maxFriendNum = count;
						resultRoom = dispatcherRoom;
					}
				}
			}
		}
		
		if(resultRoom != null) {
			return new DispatchResult(resultRoom, false);
		}
		//再选人数未到基本人数上限的
		int[] roomsPriority = new int[rooms.size()];
		int totalPriority = 0;
		//如果是增长趋势，优先人少的
		//如果是正常趋势，按正常平均
		//如果是减少趋势，优先人多的
		//尝试2次。第一次，挑选战斗结束中的，第二次，挑选正常的，
		for(int count = 0; count < 2; count++) {
			for (int i = 0; i < rooms.size(); i ++) {
				DispatcherRoom room = rooms.get(i);
				if(count == 0) {
					if(!room.isInRestarting3Second()) {
						continue;
					}
				}
				if(changeTrendType == ChangeTrendType.increase) {
					int priority = baseNum -room.getUserNum();
					if(priority < 0) {
						priority = 0;
					}
					priority = (int) Math.pow(priority, 1.2);
					totalPriority += priority;
					roomsPriority[i] = priority;
				} else if(changeTrendType == ChangeTrendType.normal) {
					int priority = baseNum -room.getUserNum();
					if(priority < 0) {
						priority = 0;
					}
					totalPriority += priority;
					roomsPriority[i] = priority;
				} else if(changeTrendType == ChangeTrendType.decrease) {
					int priority = room.getUserNum();
					if(priority >= baseNum) {
						priority = 0;
					} 
					if(room.getUserNum() <= mergeThreshold) {
						priority = 0;
					}
					totalPriority += priority;
					roomsPriority[i] = priority;
				}
			}
		}
		//如果2次还没有一个房间，当前是下降趋势，则选择一个人数最多的房间（预防人数都少于最低人数时，会出现问题），下降趋势不应该创建房间的
		if(totalPriority <= 0) {
			if(changeTrendType == ChangeTrendType.decrease) {
				int maxUserNum = 0;
				DispatcherRoom resultRoom0 = null;
				for(DispatcherRoom dispatcherRoom : rooms) {
					if(resultRoom0 == null) {
						resultRoom0 = dispatcherRoom;
						maxUserNum = dispatcherRoom.getUserNum();
					} else if(dispatcherRoom.getUserNum() > maxUserNum) {
						resultRoom0 = dispatcherRoom;
						maxUserNum = dispatcherRoom.getUserNum();
					}
				}
				if(resultRoom0 != null) {
					return new DispatchResult(resultRoom, false);
				}
			}
		}
		if(totalPriority <= 0) {
			ServerState serverState = serverContext.selectNotBusyServer(this.roomType);
			DispatchClientType dispatchClientType = null;
			String serviceAndMethod = null;
			if(this.roomType == RoomType.hall) {
				dispatchClientType = DispatchClientType.hall;
				serviceAndMethod = "roomService4Dispatcher.createNewRoom";
			} else {
				dispatchClientType = DispatchClientType.room;
				serviceAndMethod = "roomServiceInRoom4Dispatcher.createNewRoom";
			}
			DispatchClient client = null;
			try {
				client = dispatchServerContext.borrowClient(serverState.getServerId(), dispatchClientType);
				long roomId;
				if(dispatchClientType == DispatchClientType.hall) {
					roomId = this.allRoomManager.nextHallId();
				} else {
					roomId = this.allRoomManager.nextRoomId();
				}
				StrLongLongProto.Builder builder = StrLongLongProto.newBuilder();
				builder.setParams1(this.roomType.name());
				builder.setParams2(roomId);
				builder.setParams3(mapId);
				CreateNewRoomRespProto result = (CreateNewRoomRespProto)client.sendAndWaitResp(serviceAndMethod, builder);
				if(result.getIsSuccess()) {
					DispatcherRoom dispatcherRoom = new DispatcherRoom(roomId, serverState.getServerId(), this.roomType);
					rooms.add(dispatcherRoom);
					if(roomType == RoomType.hall) {
						this.allRoomManager.addHallDispatcherRoom(dispatcherRoom);
					} else {
						this.allRoomManager.addFightDispatcherRoom(dispatcherRoom);
					}
					return new DispatchResult(dispatcherRoom, true);
				} else {
					logger.error("dispatch create new room fail, serverId:" + serverState.getServerId());
					return null;
				}
			} finally {
				if(client != null) {
					dispatchServerContext.addDispatchClient(client);
				}
			}
		} else {
			int index = ProbabilityGenerator.getRandomChoiceWithRatioArr(roomsPriority);
			return new DispatchResult(rooms.get(index), false);
		}
	}

	public synchronized DispatchResult dispatch(String platformId, RoleFriendInfo roleFriendInfo) {
		return dispatch(platformId, -1, roleFriendInfo);
	}
	
	public ChangeTrendType getChangeTrendType() {
		return changeTrendType;
	}

	public void setChangeTrendType(ChangeTrendType changeTrendType) {
		this.changeTrendType = changeTrendType;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public synchronized DispatcherRoom findMergeRoom(long roomId) {
		int maxUserNum = 0;
		DispatcherRoom room = null;
		for(DispatcherRoom dispatcherRoom : rooms) {
			if(dispatcherRoom.getRoomId() == roomId) {
				continue;
			}
			if(dispatcherRoom.isBeingMerged()) {
				continue;
			}
			int userNum = dispatcherRoom.getUserNum();
			if(dispatcherRoom.getRoomType() == RoomType.hall || dispatcherRoom.isInRestarting3Second()) {
				if(userNum < baseNum) {
					if(maxUserNum == 0) {
						maxUserNum = userNum;
						room = dispatcherRoom;
					} else {
						if(userNum > maxUserNum) {
							maxUserNum = userNum;
							room = dispatcherRoom;
						}
					}
				}
			}
		}
		return room;
	}

}
