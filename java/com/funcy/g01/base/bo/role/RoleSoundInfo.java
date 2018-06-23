package com.funcy.g01.base.bo.role;

import java.util.ArrayList;
import java.util.List;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleSocialInfoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleSoundInfoProto;
import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class RoleSoundInfo implements ProtobufSerializable  {

	private transient long roleId;
	
	private List<ByteString> sounds;
	
	private int soundTime;
	
	@SuppressWarnings("unused")
	private RoleSoundInfo() {
	}
	
	public RoleSoundInfo(long roleId){
		this.roleId = roleId;
		this.sounds = new ArrayList<ByteString>();
		this.soundTime = 0;
	}
	
	public RoleSoundInfo(byte[] bytes){
		parseFrom(bytes);
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleSoundInfoProto roleSocialInfoProto = (RoleSoundInfoProto) message;
		this.sounds = roleSocialInfoProto.getSoundsList();
		this.soundTime = roleSocialInfoProto.getSoundTime();
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleSoundInfoProto.Builder builder = RoleSoundInfoProto.newBuilder();
		builder.addAllSounds(sounds);
		builder.setSoundTime(soundTime);
		return builder.build();
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleSoundInfoProto roleSoundInfoProto = RoleSoundInfoProto.parseFrom(bytes);
			copyFrom(roleSoundInfoProto);
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

	public List<ByteString> getSounds() {
		return sounds;
	}

	public void setSounds(List<ByteString> sounds) {
		this.sounds = sounds;
	}

	public int getSoundTime() {
		return soundTime;
	}

	public void setSoundTime(int soundTime) {
		this.soundTime = soundTime;
	}

}
