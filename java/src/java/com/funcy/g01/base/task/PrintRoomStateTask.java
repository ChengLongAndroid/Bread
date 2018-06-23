package com.funcy.g01.base.task;

import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ebo.synframework.synroom.tools.ConcurrentHashMap;
import com.funcy.g01.base.bo.fight.FightRoom;
import com.funcy.g01.base.bo.fight.SynEvent;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.global.ServerContext;

@Component
public class PrintRoomStateTask implements Runnable {
	
	private static final Logger logger = Logger.getLogger(PrintRoomStateTask.class);
	
	private static final String printText = "room(id:%d,type:%s) state is %s, player num:%d, room run seconds:%d, fight run seconds:%d";
	
	@Autowired
	private ServerContext serverContext;

	@Override
	public void run() {
		ConcurrentHashMap<Long, FightRoom> rooms = serverContext.getFightRooms();
		printRoomsInfo(rooms);
		
		ConcurrentHashMap<Long, FightRoom> hallRooms = serverContext.getHallFightRooms();
		printRoomsInfo(hallRooms);
	}

	private void printRoomsInfo(ConcurrentHashMap<Long, FightRoom> rooms) {
		for(Entry<Long, FightRoom> entry : rooms.entrySet()) {
			final FightRoom fightRoom = entry.getValue();
			fightRoom.executeRightNow(new SynEvent(ServerConfig.system_role_id, "PrintRoomStateTask.run") {
				@Override
				public void executeEvent() {
					logger.info(String.format(printText, fightRoom.getRoomId(), fightRoom.getRoomType().toString(), fightRoom.getFightRoomState().toString(), fightRoom.getFightPlayers().size(), fightRoom.calRoomLiveTime(), fightRoom.calFightLiveTime()));
				}
			});
		}
	}

}
