package com.funcy.g01.dispatcher.task;

import java.sql.Date;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.funcy.g01.dispatcher.bo.AllRoomManager;
import com.funcy.g01.dispatcher.bo.BaseTypeRoomManager;
import com.funcy.g01.dispatcher.bo.DispatcherRoom;
import com.funcy.g01.dispatcher.bo.RoomType;

@Component
public class CheckTimeoutDispatcherRoomTask implements Runnable {
	
	private static final Logger logger = Logger.getLogger(CheckTimeoutDispatcherRoomTask.class);
	
	@Autowired
	private AllRoomManager allRoomManager;
	
	@Override
	public void run() {
		try {
			long now = System.currentTimeMillis();
			for(DispatcherRoom dispatcherRoom : allRoomManager.getHallRooms().values()) {
				if(dispatcherRoom.isUpdateTimeout()) {
					String curTimeStr = new Date(now).toString();
					String lastUpdateTimeStr = new Date(dispatcherRoom.getLastUpdateTime()).toString();
					logger.info("room update timeout, roomId:" + dispatcherRoom.getRoomId() + ",serverId:" + dispatcherRoom.getServerId() + ",curTime:" + curTimeStr + ",last update time:" + lastUpdateTimeStr);
					BaseTypeRoomManager baseTypeRoomManager = allRoomManager.getBaseTypeRoomManager(dispatcherRoom.getRoomType());
					baseTypeRoomManager.roomDestroy(dispatcherRoom.getRoomId());
					if(dispatcherRoom.getRoomType() == RoomType.hall) {
						allRoomManager.removeHallDispatcherRoom(dispatcherRoom.getRoomId());
					} else {
						allRoomManager.removeFightDispatcherRoom(dispatcherRoom.getRoomId());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
