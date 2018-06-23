package com.funcy.g01.dispatcher.bo.selectChannel;

import com.funcy.g01.base.global.ServerConfig;

public class SelectChannelInfo {
	
	private final long roomId;
	
	private int serverId;
	
	private String channelName;
	
	private int friendsNum;

	public SelectChannelInfo(long roomId, int serverId, int friendsNum) {
		this.roomId = roomId;
		this.serverId = serverId;
		this.channelName = String.format(ServerConfig.channelNamePattern, roomId);
		this.friendsNum = friendsNum;
	}

	public int getFriendsNum() {
		return friendsNum;
	}

	public void setFriendsNum(int friendsNum) {
		this.friendsNum = friendsNum;
	}

	public long getRoomId() {
		return roomId;
	}
	
	public String getChannelName(){
		return channelName;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	
	
}
