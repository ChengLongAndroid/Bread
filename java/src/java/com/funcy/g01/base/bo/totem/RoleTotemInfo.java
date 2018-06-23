package com.funcy.g01.base.bo.totem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.data.TotemData;
import com.funcy.g01.base.data.TotemProperty;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleTotemInfoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.TotemVoProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class RoleTotemInfo implements ProtobufSerializable {
	
	
	private long roleId;
	
	private List<TotemVo> totems = null;
	
	private TotemData totemData;
	
	@SuppressWarnings("unused")
	private RoleTotemInfo() {
	}
	
	public RoleTotemInfo(long roleId,TotemData totemData){
		this.roleId = roleId;
		totems = new ArrayList<TotemVo>();
		this.totemData = totemData;
		Map<Integer, TotemProperty> totemsInfoMap = totemData.getTotemsInfoMap();
		for (Map.Entry<Integer, TotemProperty> entry : totemsInfoMap.entrySet()) {  
		    TotemProperty totemProperty = entry.getValue();
		    totems.add(new TotemVo(totemProperty.getId(), totemProperty.getId(), -1));
		}  
	}
	
	public RoleTotemInfo(byte[] bytes,TotemData totemData) {
		this.totemData = totemData;
		parseFrom(bytes);
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleTotemInfoProto proto = (RoleTotemInfoProto) message;
		this.roleId = proto.getRoleId();
		this.totems = new ArrayList<TotemVo>();
		for (TotemVoProto totemVoProto : proto.getTotemsList()) {
			this.totems.add(new TotemVo(totemVoProto.getId(), totemVoProto.getXmlId(), totemVoProto.getSkillId()));
		}
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleTotemInfoProto.Builder buidler = RoleTotemInfoProto.newBuilder();
		buidler.setRoleId(roleId);
		for (TotemVo totemVo : totems) {
			buidler.addTotems(totemVo.copyTo());
		}
		return buidler.build();
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleTotemInfoProto roleTotemInfoProto = RoleTotemInfoProto.parseFrom(bytes);
			copyFrom(roleTotemInfoProto);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}
	
	public TotemVo findTotemVoById(int id){
		for (TotemVo totemVo : totems) {
			if (totemVo.getId() == id) {
				return totemVo;
			}
		}
		return null;
	}
	
	public TotemVo findTotemVoBySkillId(int skillId){
		for (TotemVo totemVo : totems) {
			if (totemVo.getSkillId() == skillId) {
				return totemVo;
			}
		}
		return null;
	}
	
	public List<TotemVo> findCurrentTypeTotemVoList(int type){
		List<TotemVo> currentTypeTotemVoList = new ArrayList<TotemVo>();
		for (TotemVo totemVo : totems) {
			TotemProperty totemProperty = totemData.getTotemProperty(totemVo.getXmlId());
			if (totemProperty.getType() == type) {
				currentTypeTotemVoList.add(totemVo);
			}
		}
		return currentTypeTotemVoList;
	}
	
	public TotemVo findFinalTotemVo(int type){
		for (TotemVo totemVo : totems) {
			TotemProperty totemProperty = totemData.getTotemProperty(totemVo.getXmlId());
			if (totemProperty.getType() == type && totemProperty.getIndex() == TotemVo.final_totem_index) {
				return totemVo;
			}
		}
		return null;
	}
	

	public long getRoleId() {
		return roleId;
	}

	public List<TotemVo> getTotems() {
		return totems;
	}

	public void setTotems(List<TotemVo> totems) {
		this.totems = totems;
	}
	
	public int getActiveFinalTotemQuality()
	{
		int count = 0;
		for (TotemVo totemVo : totems) {
			TotemProperty totemProperty = totemData.getTotemProperty(totemVo.getXmlId());
			if (totemProperty.getIndex() == TotemVo.final_totem_index && totemVo.getId() != -1) {
				count += 1;
			}
		}
		return count;
	}
}
