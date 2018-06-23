package com.funcy.g01.dispatcher.bo;

import java.util.EnumMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ebo.synframework.synroom.tools.ConcurrentHashMap;
import com.funcy.g01.base.global.BusinessPool;
import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.dispatcher.task.RefreshChangeTrendTask;

@Component
public class AllRoomManager {

	@Autowired
	private ServerContext serverContext;
	
	public static final long initRoomIdVal = 5272; 
	
	private AtomicLong roomIdProducer = new AtomicLong(initRoomIdVal);
	
	public static final int initHallIdVal = 1281;
	
	private AtomicLong hallIdProducer = new AtomicLong(initHallIdVal);
	
	@Autowired
	private DispatchServerContext dispatchServerContext;
	
	@Autowired
	private BusinessPool businessPool;
	
	private EnumMap<RoomType, BaseTypeRoomManager> roomManagers = new EnumMap<RoomType, BaseTypeRoomManager>(RoomType.class);
	
	private ConcurrentHashMap<Long, DispatcherRoom> hallRooms = new ConcurrentHashMap<Long, DispatcherRoom>();
	
	private ConcurrentHashMap<Long, DispatcherRoom> fightRooms = new ConcurrentHashMap<Long, DispatcherRoom>();
 	
	public long nextHallId() {
		long result = hallIdProducer.incrementAndGet();
		if(result >= 9000) {
			hallIdProducer.compareAndSet(result, 1);
		}
		return result;
	}
	
	public long nextRoomId() {
		long result = roomIdProducer.incrementAndGet();
		if(result >= 100000) {
			roomIdProducer.compareAndSet(result, 1);
		}
		return result;
	}
	
	public void init() {
		for (RoomType roomType : RoomType.values()) {
			if(roomType == RoomType.easy) {
				continue;
			}
			BaseTypeRoomManager baseTypeRoomManager = initRoomManager(roomType);
			if(roomType == RoomType.hall || roomType == RoomType.normal || roomType == RoomType.advance) {
				RefreshChangeTrendTask refreshChangeTrendTask = new RefreshChangeTrendTask(baseTypeRoomManager);
				businessPool.scheduleAtFixedRate(refreshChangeTrendTask, 1, 1, TimeUnit.MINUTES);
			}
		}
	}
	
	public EnumMap<RoomType, BaseTypeRoomManager> getRoomManagers() {
		return roomManagers;
	}

	public BaseTypeRoomManager getBaseTypeRoomManager(RoomType roomType) {
		return roomManagers.get(roomType);
	}

	private BaseTypeRoomManager initRoomManager(RoomType roomType) {
		BaseTypeRoomManager roomManager = new BaseTypeRoomManager(roomType);
		roomManager.init(serverContext, this, dispatchServerContext);
		roomManagers.put(roomType, roomManager);
		return roomManager;
	}
	
	public DispatcherRoom getHallDispatcherRoom(long roomId) {
		return hallRooms.get(roomId);
	}
	
	public DispatcherRoom getFightDispatcherRoom(long roomId) {
		return this.fightRooms.get(roomId);
	}
	
	public void addHallDispatcherRoom(DispatcherRoom dispatcherRoom) {
		this.hallRooms.put(dispatcherRoom.getRoomId(), dispatcherRoom);
	}
	
	public void removeHallDispatcherRoom(long roomId) {
		this.hallRooms.remove(roomId);
	}
	
	public void removeFightDispatcherRoom(long roomId) {
		this.fightRooms.remove(roomId);
	}
	
	public void addFightDispatcherRoom(DispatcherRoom dispatcherRoom) {
		this.fightRooms.put(dispatcherRoom.getRoomId(), dispatcherRoom);
	}

	public ConcurrentHashMap<Long, DispatcherRoom> getHallRooms() {
		return hallRooms;
	}

	public ConcurrentHashMap<Long, DispatcherRoom> getFightRooms() {
		return fightRooms;
	}
	
}
