package com.funcy.g01.base.bo.signin;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleSigninInfoProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class RoleSigninInfo implements ProtobufSerializable {
	
	private transient long roleId;
	
	private int currentSigninDay;
	
	private boolean isTodaySignin;
	
	private int signinRewardId = 0;
	
	public RoleSigninInfo(){
		this.currentSigninDay = 1;
		this.signinRewardId = 0;
		this.isTodaySignin = false;
	}
	
	public RoleSigninInfo(byte[] bytes){
		parseFrom(bytes);
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleSigninInfoProto proto = (RoleSigninInfoProto) message;
		this.currentSigninDay = proto.getCurrentSigninDay();
		this.isTodaySignin = proto.getIsTodaySignin();
		this.signinRewardId = proto.getSigninRewardId();
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleSigninInfoProto.Builder buidler = RoleSigninInfoProto.newBuilder();
		buidler.setCurrentSigninDay(currentSigninDay);
		buidler.setIsTodaySignin(isTodaySignin);
		buidler.setSigninRewardId(signinRewardId);
		return buidler.build();
	}
	

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleSigninInfoProto roleSigninInfoProto = RoleSigninInfoProto.parseFrom(bytes);
			copyFrom(roleSigninInfoProto);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public boolean isTodaySignin() {
		return isTodaySignin;
	}

	public void setTodaySignin(boolean isTodaySignin) {
		this.isTodaySignin = isTodaySignin;
	}

	public int getCurrentSigninDay() {
		return currentSigninDay;
	}

	public void setCurrentSigninDay(int currentSigninDay) {
		this.currentSigninDay = currentSigninDay;
	}

	public int getSigninRewardId() {
		return signinRewardId;
	}

	public void setSigninRewardId(int signinRewardId) {
		this.signinRewardId = signinRewardId;
	}
}
