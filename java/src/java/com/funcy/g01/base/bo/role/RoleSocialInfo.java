package com.funcy.g01.base.bo.role;

import java.util.ArrayList;
import java.util.List;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleSocialBaseInfoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleSocialInfoProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message.Builder;

public class RoleSocialInfo implements ProtobufSerializable  {

	private transient long roleId;
	
	private List<Long> follows;
	
	private List<Long> fans;
	
	private int likes;
	
	@SuppressWarnings("unused")
	private RoleSocialInfo() {
	}
	
	public RoleSocialInfo(long roleId){
		this.roleId = roleId;
		this.follows = new ArrayList<Long>();
		this.fans = new ArrayList<Long>();
		this.likes = 0;
	}
	
	public RoleSocialInfo(byte[] bytes){
		parseFrom(bytes);
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleSocialInfoProto roleSocialInfoProto = (RoleSocialInfoProto) message;
		this.follows = new ArrayList<Long>(roleSocialInfoProto.getFollowsList());
		this.fans = new ArrayList<Long>(roleSocialInfoProto.getFansList());
		this.likes = roleSocialInfoProto.getLikes();
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleSocialInfoProto.Builder builder = RoleSocialInfoProto.newBuilder();
		builder.addAllFans(fans);
		builder.addAllFollows(follows);
		builder.setLikes(likes);
		return builder.build();
	}
	
	public Builder copyBaseInfo() {
		RoleSocialBaseInfoProto.Builder builder = RoleSocialBaseInfoProto.newBuilder();
		builder.setFans(fans.size());
		builder.setFollows(follows.size());
		builder.setLikes(likes);
		return builder;
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleSocialInfoProto roleSocialInfoProto = RoleSocialInfoProto.parseFrom(bytes);
			copyFrom(roleSocialInfoProto);
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

	public List<Long> getFollows() {
		return follows;
	}

	public void setFollows(List<Long> follows) {
		this.follows = follows;
	}

	public List<Long> getFans() {
		return fans;
	}

	public void setFans(List<Long> fans) {
		this.fans = fans;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}
	

}
