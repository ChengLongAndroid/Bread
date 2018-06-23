package com.funcy.g01.base.bo.serverconfig;

public class FrontServerInfo {

	private int serverId;
	
	private String ip;
	
	private int roomServerPort;
	
	private int hallServerPort;
	
	private String name;

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getRoomServerPort() {
		return roomServerPort;
	}

	public void setRoomServerPort(int roomServerPort) {
		this.roomServerPort = roomServerPort;
	}

	public int getHallServerPort() {
		return hallServerPort;
	}

	public void setHallServerPort(int hallServerPort) {
		this.hallServerPort = hallServerPort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
