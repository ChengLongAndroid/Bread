package com.funcy.g01.base.bo.role;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleStateInfoProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class RoleStateInfo implements ProtobufSerializable {
	
	private RoleState state;
	
	private long lastGameOpenTime;
	
	private int privateRoomId;
	
	public RoleStateInfo(byte[] bytes) {
		parseFrom(bytes);
	}
	
	public RoleStateInfo() {
		this.state = RoleState.ONLINE;
		this.lastGameOpenTime = 0;
	}

	public RoleState getState() {
		return state;
	}

	public void setState(RoleState state) {
		this.state = state;
	}

	public long getLastGameOpenTime() {
		return lastGameOpenTime;
	}

	public void setLastGameOpenTime(long lastGameOpenTime) {
		this.lastGameOpenTime = lastGameOpenTime;
	}
	
	public int getPrivateRoomId() {
		return privateRoomId;
	}

	public void setPrivateRoomId(int privateRoomId) {
		this.privateRoomId = privateRoomId;
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleStateInfoProto proto = (RoleStateInfoProto) message;
		this.state = RoleState.valueOf(proto.getState());
		this.lastGameOpenTime = proto.getLastGameOpenTime();
		this.privateRoomId = proto.getPrivateRoomId();
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleStateInfoProto.Builder builder = RoleStateInfoProto.newBuilder();
		builder.setState(this.state.getCode());
		builder.setLastGameOpenTime(this.lastGameOpenTime);
		builder.setPrivateRoomId(this.privateRoomId);
		return builder.build();
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleStateInfoProto proto = RoleStateInfoProto.parseFrom(bytes);
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
