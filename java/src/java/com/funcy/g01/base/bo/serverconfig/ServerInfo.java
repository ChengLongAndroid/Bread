package com.funcy.g01.base.bo.serverconfig;

import java.util.Date;

import com.funcy.g01.base.bo.CommonConstant;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ServerInfoVo4FrontProto;

public class ServerInfo {

	private int id;

	private int serverId;

	private String serverType;
	
	private String netIp;

	private String ip;

	private int httpPort;

	private int roomServerPort;

	private int hallServerPort;

	private String secretKey;

	private int serverState;

	private String name;

	private Date openTime;
	
	private int dispatcherServerPort;
	
	private int h5RoomServerPort;
	
	private int h5HallServerPort;
	
	private int rankingServerPort;

	public ServerInfo() {
		id = CommonConstant.NO_EXIST_PRIMARY_ID;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getServerType() {
		return serverType;
	}
	
	public ServerType getServerType0() {
		return ServerType.valueOf(this.serverType);
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public String getNetIp() {
		return netIp;
	}

	public void setNetIp(String netIp) {
		this.netIp = netIp;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getHttpPort() {
		return httpPort;
	}

	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
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

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public int getServerState() {
		return serverState;
	}

	public void setServerState(int serverState) {
		this.serverState = serverState;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public int getDispatcherServerPort() {
		return dispatcherServerPort;
	}

	public void setDispatcherServerPort(int dispatcherServerPort) {
		this.dispatcherServerPort = dispatcherServerPort;
	}
	
	public FrontServerInfo buildFrontServerInfo() {
		FrontServerInfo frontServerInfo = new FrontServerInfo();
		frontServerInfo.setHallServerPort(this.hallServerPort);
		frontServerInfo.setIp(this.netIp);
		frontServerInfo.setName(this.name);
		frontServerInfo.setRoomServerPort(this.roomServerPort);
		frontServerInfo.setServerId(this.serverId);
		return frontServerInfo;
	}
	
	public ServerInfoVo4FrontProto.Builder buildFightRoomServerInfo() {
		ServerInfoVo4FrontProto.Builder builder = ServerInfoVo4FrontProto.newBuilder();
		builder.setIp(this.netIp);
		builder.setName(this.name);
		builder.setPort(this.roomServerPort);
		builder.setServerId(this.serverId);
		return builder;
	}
	
	public ServerInfoVo4FrontProto.Builder buildHallServerInfo() {
		ServerInfoVo4FrontProto.Builder builder = ServerInfoVo4FrontProto.newBuilder();
		builder.setIp(this.netIp);
		builder.setName(this.name);
		builder.setPort(this.hallServerPort);
		builder.setServerId(this.serverId);
		return builder;
	}
	
	public boolean isClose() {
		Date currentDate = new Date(System.currentTimeMillis());
		return currentDate.before(openTime) || serverState == ServerStateType.close.getValue();
	}

	public int getH5RoomServerPort() {
		return h5RoomServerPort;
	}

	public void setH5RoomServerPort(int h5RoomServerPort) {
		this.h5RoomServerPort = h5RoomServerPort;
	}

	public int getH5HallServerPort() {
		return h5HallServerPort;
	}

	public void setH5HallServerPort(int h5HallServerPort) {
		this.h5HallServerPort = h5HallServerPort;
	}

	public int getRankingServerPort() {
		return rankingServerPort;
	}

	public void setRankingServerPort(int rankingServerPort) {
		this.rankingServerPort = rankingServerPort;
	}
	
}