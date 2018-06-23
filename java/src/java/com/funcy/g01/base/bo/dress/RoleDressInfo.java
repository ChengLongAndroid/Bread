package com.funcy.g01.base.bo.dress;

import java.util.ArrayList;
import java.util.List;

import com.funcy.g01.base.bo.totem.SkillVo;
import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.DressVoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleDressInfoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleItemInfoProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class RoleDressInfo implements ProtobufSerializable {
	
	
	private long roleId;
	
	private List<DressVo> dresses = null;
	
	
	@SuppressWarnings("unused")
	private RoleDressInfo() {
	}
	
	public RoleDressInfo(long roleId){
		this.roleId = roleId;
		dresses = new ArrayList<DressVo>();
		for (int i = 1; i <= 4; i++) {
			dresses.add(new DressVo(DressType.valueOf(i).getDefaultId()));
		}
	}
	
	public RoleDressInfo(byte[] bytes){
		parseFrom(bytes);
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleDressInfoProto proto = (RoleDressInfoProto) message;
		this.roleId = proto.getRoleId();
		this.dresses = new ArrayList<DressVo>() ;
		for (DressVoProto dressVoProto : proto.getDressesList()) {
			this.dresses.add(new DressVo(dressVoProto.getXmlId()));
		}
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleDressInfoProto.Builder buidler = RoleDressInfoProto.newBuilder();
		buidler.setRoleId(roleId);
		for (DressVo dressVo : dresses) {
			buidler.addDresses(dressVo.copyTo());
		}
		return buidler.build();
	}
	
	public void addDress(DressVo dressVo){
		this.dresses.add(dressVo);
	}
	
	public boolean checkHadDress(int xmlId){
		return findDressVoByXmlId(xmlId) != null;
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleDressInfoProto roleDressInfoProto = RoleDressInfoProto.parseFrom(bytes);
			copyFrom(roleDressInfoProto);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}
	
	public DressVo findDressVoByXmlId(int xmlId){
		for (DressVo dressVo : dresses) {
			if (dressVo.getXmlId() == xmlId) {
				return dressVo;
			}
		}
		return null;
	}
	

	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}

	public long getRoleId() {
		return roleId;
	}

	public List<DressVo> getDresses() {
		return dresses;
	}

	public void setDresses(List<DressVo> dresses) {
		this.dresses = dresses;
	}
}
