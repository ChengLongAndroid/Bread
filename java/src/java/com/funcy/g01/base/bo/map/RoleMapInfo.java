package com.funcy.g01.base.bo.map;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.RoleMapInfoProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class RoleMapInfo implements ProtobufSerializable {

	private long roleId;
	
	private List<Long> mapIds;
	
	public static final Random random = new Random();
	
	private int mapIndex = 0;
	
	@SuppressWarnings("unused")
	private RoleMapInfo() {
	}
	
	public RoleMapInfo(long roleId) {
		this.roleId = roleId;
		this.mapIds = new ArrayList<Long>();
	}

	public RoleMapInfo(byte[] bytes) {
		parseFrom(bytes);
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public List<Long> getMapIds() {
		return mapIds;
	}

	public void setMapIds(List<Long> mapIds) {
		this.mapIds = mapIds;
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleMapInfoProto proto = (RoleMapInfoProto) message;
		this.roleId = proto.getRoleId();
		this.mapIndex = proto.getMapIndex();
		this.mapIds = new ArrayList<Long>();
		for (long mapId : proto.getMapIdsList()) {
			this.mapIds.add(mapId);
		}
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleMapInfoProto.Builder builder = RoleMapInfoProto.newBuilder();
		builder.setRoleId(this.roleId);
		builder.setMapIndex(this.mapIndex);
		if(this.mapIds != null) {
			for (Long mapId : mapIds) {
				builder.addMapIds(mapId);
			}
		}
		return builder.build();
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleMapInfoProto roleMapInfoProto = RoleMapInfoProto.parseFrom(bytes);
			copyFrom(roleMapInfoProto);
		} catch (InvalidProtocolBufferException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}

	public void addMapId(long mapId) {
		this.mapIds.add(mapId);
	}
	public int getMapIndex() {
		return mapIndex;
	}

	public void setMapIndex(int mapIndex) {
		this.mapIndex = mapIndex;
	}
	
	public long randomOneMapId(List<Long> lastPlayMapIds, List<Long> limitMapIds) {
		if(mapIds.size() == 0) {
			throw new RuntimeException();
		}
		if(mapIds.size() == 1) {
			return mapIds.get(0);
		}
		List<Long> newList = new LinkedList<Long>(lastPlayMapIds);
		for(long mapId : limitMapIds) {
			if(!newList.contains(mapId)) {
				newList.add(mapId);
			}
		}
		if(mapIds.size() <= newList.size()) {
			while(true) {
				int resultIndex = random.nextInt(mapIds.size());
				long result = mapIds.get(resultIndex);
				boolean found = false;
				for(long mapId : limitMapIds) {
					if(mapId == result) {
						found = true;
						break;
					}
				}
				if(!found) {
					return result;
				}
			}
		}
		while(true) {
			int resultIndex = random.nextInt(mapIds.size());
			long result = mapIds.get(resultIndex);
			boolean found = false;
			for(long mapId : newList) {
				if(mapId == result) {
					found = true;
					break;
				}
			}
			if(!found) {
				return result;
			}
		}
	}
}
