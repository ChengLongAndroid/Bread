package com.funcy.g01.base.bo.map;

import java.util.ArrayList;
import java.util.List;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.FightMapInfoProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class ServerMapInfo implements ProtobufSerializable {

	private long mapId;
	
	private long createTime;
	
	private long createRoleId;
	
	private long lastModifyTime;
	
	private FightMapInfoProto mapInfo;
	
	private transient List<FightMapInfoProto> combinedMapInfos = new ArrayList<FightMapInfoProto>();
	
	@SuppressWarnings("unused")
	private ServerMapInfo() {
	}
	
	public ServerMapInfo(FightMapInfoProto mapInfo, long roleId) {
		this.mapId = 0;
		this.createTime = System.currentTimeMillis();
		this.lastModifyTime = System.currentTimeMillis();
		this.createRoleId = roleId;
		this.mapInfo = mapInfo;
	}
	
	public ServerMapInfo(byte[] bytes) {
		parseFrom(bytes);
	}

	public long getMapId() {
		return mapId;
	}

	public void setMapId(long mapId) {
		this.mapId = mapId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getCreateRoleId() {
		return createRoleId;
	}

	public void setCreateRoleId(long createRoleId) {
		this.createRoleId = createRoleId;
	}

	public long getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(long lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public FightMapInfoProto getMapInfo() {
		return mapInfo;
	}

	public void setMapInfo(FightMapInfoProto mapInfo) {
		this.mapInfo = mapInfo;
	}
	
	public void addCombinedMapInfo(FightMapInfoProto mapInfo) {
		this.combinedMapInfos.add(mapInfo);
	}
	
	public List<FightMapInfoProto> getCombinedMapInfos() {
		return combinedMapInfos;
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		ServerMapInfoProto proto = (ServerMapInfoProto) message;
		this.createRoleId = proto.getCreateRoleId();
		this.createTime = proto.getCreateTime();
		this.lastModifyTime = proto.getLastModifyTime();
		this.mapInfo = proto.getMapInfo();
		this.mapId = proto.getMapId();
	}

	@Override
	public GeneratedMessage copyTo() {
		ServerMapInfoProto.Builder builder = ServerMapInfoProto.newBuilder();
		builder.setCreateRoleId(this.createRoleId);
		builder.setCreateTime(this.createTime);
		builder.setLastModifyTime(this.lastModifyTime);
		builder.setMapInfo(this.mapInfo);
		builder.setMapId(this.mapId);
		return builder.build();
	}
	
	public GeneratedMessage copyBaseTo() {
		ServerMapInfoProto.Builder builder = ServerMapInfoProto.newBuilder();
		builder.setCreateRoleId(this.createRoleId);
		builder.setCreateTime(this.createTime);
		builder.setLastModifyTime(this.lastModifyTime);
		builder.setMapId(this.mapId);
		builder.setMapName(this.getMapInfo().getSceneSettingInfo().getName());
		return builder.build();
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			ServerMapInfoProto proto = ServerMapInfoProto.parseFrom(bytes);
			copyFrom(proto);
		} catch (InvalidProtocolBufferException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}
}
