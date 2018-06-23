package com.funcy.g01.dispatcher.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.funcy.g01.base.bo.fight.FightConfig;
import com.funcy.g01.base.bo.fight.FightRoomState;
import com.funcy.g01.base.net.DispatchClientType;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.DispatcherRoomProto;

public class DispatcherRoom {

	private volatile List<Long> users;
	
	private long roomId;
	
	private int serverId;
	
	private long createTime;
	
	private DispatcherRoomState state;
	
	private FightRoomState fightRoomState;
	
	private long curStateStartTime;
	
	private long lastUpdateTime;
	
	private RoomType roomType;
	
	public static final long restarting_check_val = TimeUnit.SECONDS.toMillis(FightConfig.fight_ending_seconds - 3);
	
	public static final long maxNotUpdateTime = TimeUnit.MINUTES.toMillis(1);
	
	private long mergeStartTime;
	
	public static final long merge_time = 10000;
	
	public DispatcherRoom(long roomId, int serverId, RoomType roomType) {
		this.roomId = roomId;
		this.serverId = serverId;
		this.createTime = System.currentTimeMillis();
		this.state = DispatcherRoomState.fresh;
		this.fightRoomState = FightRoomState.preparing;
		this.users = new ArrayList<Long>();
		this.lastUpdateTime = System.currentTimeMillis();
		this.roomType = roomType;
	}

	public int getUserNum() {
		return this.users.size();
	}
	
	public long getRoomId() {
		return roomId;
	}

	public int getServerId() {
		return serverId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public DispatcherRoomState getState() {
		return state;
	}

	public void setState(DispatcherRoomState state) {
		this.state = state;
	}
	
	public void refreshAllByProto(DispatcherRoomProto roomProto) {
		this.fightRoomState = FightRoomState.valueOf(roomProto.getState());
		this.curStateStartTime = roomProto.getCurStateStartTime();
		ArrayList<Long> users = new ArrayList<Long>();
		users.addAll(roomProto.getUsersList());
		this.users = users;
		this.lastUpdateTime = System.currentTimeMillis();
	}
	
	public void refreshStateByProto(DispatcherRoomProto roomProto) {
		this.fightRoomState = FightRoomState.valueOf(roomProto.getState());
		this.curStateStartTime = roomProto.getCurStateStartTime();
		this.lastUpdateTime = System.currentTimeMillis();
	}

	public FightRoomState getFightRoomState() {
		return fightRoomState;
	}

	public long getCurStateStartTime() {
		return curStateStartTime;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}
	
	public boolean isUpdateTimeout() {
		long now = System.currentTimeMillis();
		if(now - this.lastUpdateTime >= maxNotUpdateTime) {
			return true;
		}
		return false;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public List<Long> getUsers() {
		return users;
	}

	public boolean isInRestarting3Second() {
		if(this.fightRoomState == FightRoomState.ending) {
			long now = System.currentTimeMillis();
			if(now - curStateStartTime <= restarting_check_val) {
				return true;
			}
		}
		return false;
	}
	
	public float calCurStateTime() {
		long now = System.currentTimeMillis();
		long time = now - curStateStartTime;
		time = time / 100 * 100;
		return time / 1000f;
	}

	public long getMergeStartTime() {
		return mergeStartTime;
	}

	public void setMergeStartTime(long mergeStartTime) {
		this.mergeStartTime = mergeStartTime;
	}

	public boolean isBeingMerged() {
		if(System.currentTimeMillis() - this.mergeStartTime <= merge_time) {
			return true;
		} 
		return false;
	}

	public void setMerge() {
		this.mergeStartTime = System.currentTimeMillis();
	}
	
}
