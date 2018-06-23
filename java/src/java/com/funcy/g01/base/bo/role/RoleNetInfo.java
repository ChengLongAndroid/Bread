package com.funcy.g01.base.bo.role;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleNetInfoProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class RoleNetInfo implements ProtobufSerializable {

	//-1意味着没进入，玩家断线后设置为-1
	//进入后即为该服务器对应的id
	//重进后看该值是否为-1，不为-1则让玩家连该id的服务器，由服务器去处理踢上一个下线，该玩家重进的逻辑
	
	private int hallServerId;
	
	private long hallRoomId;
	
	private int fightServerId;
	
	private long fightRoomId;
	
	private String token;
	
	private long tokenTimestamp;
	
	private transient long roleId;
	
	public RoleNetInfo(byte[] bytes) {
		parseFrom(bytes);
	}
	
	public RoleNetInfo() {
		this.hallServerId = -1;
		this.fightServerId = -1;
		this.token = "";
		this.tokenTimestamp = 0;
	}

	public int getHallServerId() {
		return hallServerId;
	}

	public void setHallServerId(int hallServerId) {
		this.hallServerId = hallServerId;
	}

	public int getFightServerId() {
		return fightServerId;
	}

	public void setFightServerId(int fightServerId) {
		this.fightServerId = fightServerId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getTokenTimestamp() {
		return tokenTimestamp;
	}

	public void setTokenTimestamp(long tokenTimestamp) {
		this.tokenTimestamp = tokenTimestamp;
	}

	public long getHallRoomId() {
		return hallRoomId;
	}

	public void setHallRoomId(long hallRoomId) {
		this.hallRoomId = hallRoomId;
	}

	public long getFightRoomId() {
		return fightRoomId;
	}

	public void setFightRoomId(long fightRoomId) {
		this.fightRoomId = fightRoomId;
	}
	
	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleNetInfoProto proto = (RoleNetInfoProto) message;
		this.fightServerId = proto.getFightServerId();
		this.hallServerId = proto.getHallServerId();
		this.token = proto.getToken();
		this.tokenTimestamp = proto.getTokenTimestamp();
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleNetInfoProto.Builder builder = RoleNetInfoProto.newBuilder();
		builder.setFightServerId(this.fightServerId);
		builder.setHallServerId(this.hallServerId);
		builder.setToken(this.token);
		builder.setTokenTimestamp(this.tokenTimestamp);
		return builder.build();
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleNetInfoProto proto = RoleNetInfoProto.parseFrom(bytes);
			copyFrom(proto);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}
	
}
