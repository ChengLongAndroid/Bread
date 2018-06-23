package com.funcy.g01.base.bo.fight;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleWeekFightInfoProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class RoleWeekFightInfo implements ProtobufSerializable {
	
	private long roleId;
	
	private int exp;
	
	private int addExp;
	
	private int cheese;
	
	@SuppressWarnings("unused")
	private RoleWeekFightInfo() {
	}
	
	public RoleWeekFightInfo(long roleId){
		this.roleId = roleId;
		this.exp = 0;
		this.addExp = 0;
		this.cheese = 0;
	}
	
	public RoleWeekFightInfo(byte[] bytes){
		parseFrom(bytes);
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleWeekFightInfoProto proto = (RoleWeekFightInfoProto) message;
		this.roleId = proto.getRoleId();
		this.exp = proto.getExp();
		this.addExp = proto.getAddExp();
		this.cheese = proto.getCheese();
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleWeekFightInfoProto.Builder builder = RoleWeekFightInfoProto.newBuilder();
		builder.setRoleId(roleId);
		builder.setExp(exp);
		builder.setAddExp(addExp);
		builder.setCheese(cheese);
		return builder.build();
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleWeekFightInfoProto roleWeekFightInfoProto = RoleWeekFightInfoProto.parseFrom(bytes);
			copyFrom(roleWeekFightInfoProto);
		} catch (InvalidProtocolBufferException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}
	
	public void addExp(int num){
		this.exp += num;
	}
	
	public void addCheese(int num){
		this.cheese += num;
	}
	
	public void addAddExp(int num){
		this.addExp += num;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getAddExp() {
		return addExp;
	}

	public void setAddExp(int addExp) {
		this.addExp = addExp;
	}

	public int getCheese() {
		return cheese;
	}

	public void setCheese(int cheese) {
		this.cheese = cheese;
	}
	
}
