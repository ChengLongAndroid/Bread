package com.funcy.g01.dispatcher.task;

import java.util.List;

import org.apache.log4j.Logger;

import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.dispatcher.bo.BaseTypeRoomManager;
import com.funcy.g01.dispatcher.bo.ChangeTrendType;
import com.funcy.g01.dispatcher.bo.DispatcherRoom;
import com.funcy.g01.dispatcher.bo.DispatcherRoomState;
import com.funcy.g01.dispatcher.bo.RoomType;

public class RefreshChangeTrendTask implements Runnable {
	
	public static final Logger logger = Logger.getLogger(RefreshChangeTrendTask.class);
	
	private BaseTypeRoomManager roomManager;
	
	private int baseNum;

	public RefreshChangeTrendTask(BaseTypeRoomManager roomManager) {
		this.roomManager = roomManager;
		if(roomManager.getRoomType() == RoomType.hall) {
			this.baseNum = ServerConfig.hall_room_base_player_num;
		} else {
			this.baseNum = ServerConfig.fight_room_base_player_num;
		}
	}
	
	@Override
	public void run() {
		try {
			if(roomManager.getRooms().size() < 10) {
				return;
			}
			long time = System.currentTimeMillis();
			synchronized (roomManager) {
				int totalUserNum = 0;
				int roomNum = 0;
				List<DispatcherRoom> rooms = roomManager.getRooms();
				for (DispatcherRoom dispatcherRoom : rooms) {
					if(dispatcherRoom.getState() == DispatcherRoomState.normal) {
						int userNum = dispatcherRoom.getUserNum();
						if(userNum > this.baseNum) {
							userNum = this.baseNum;
						}
						totalUserNum += userNum;
						roomNum++;
					} else if(dispatcherRoom.getState() == DispatcherRoomState.fresh) {
						if(time - dispatcherRoom.getCreateTime() >= ServerConfig.dispatch_room_fresh_time) {
							dispatcherRoom.setState(DispatcherRoomState.normal);
							int userNum = dispatcherRoom.getUserNum();
							if(userNum > this.baseNum) {
								userNum = this.baseNum;
							}
							totalUserNum += userNum;
							roomNum++;
						} else {
							continue;
						}
					}
				}
				if(roomNum == 0) {
					return;
				}
				float rate = totalUserNum * 1f / (roomNum * this.baseNum);
				if(rate >= ServerConfig.increase_border_rate) {
					roomManager.setChangeTrendType(ChangeTrendType.increase);
				} else if(rate <= ServerConfig.decrease_border_rate) {
					roomManager.setChangeTrendType(ChangeTrendType.decrease);
				} else {
					roomManager.setChangeTrendType(ChangeTrendType.normal);
				}
				logger.info("roomType:" + roomManager.getRoomType() + " change trend type is:" + roomManager.getChangeTrendType());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
