package com.funcy.g01.base.bo.totem;

import java.util.ArrayList;
import java.util.List;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleSkillInfoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.SkillVoProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class RoleSkillInfo implements ProtobufSerializable {
	
	
	private long roleId;
	
	private List<SkillVo> skills = null;
	
	
	@SuppressWarnings("unused")
	private RoleSkillInfo() {
	}
	
	public RoleSkillInfo(long roleId){
		this.roleId = roleId;
		setSkills(new ArrayList<SkillVo>());
	}
	
	public RoleSkillInfo(byte[] bytes){
		parseFrom(bytes);
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleSkillInfoProto proto = (RoleSkillInfoProto) message;
		this.roleId = proto.getRoleId();
		this.setSkills(new ArrayList<SkillVo>());
		for (SkillVoProto skillVoProto : proto.getSkillsList()) {
			this.getSkills().add(new SkillVo(skillVoProto.getStar(), skillVoProto.getXmlId()));
		}
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleSkillInfoProto.Builder buidler = RoleSkillInfoProto.newBuilder();
		buidler.setRoleId(roleId);
		for (SkillVo skillVo : getSkills()) {
			buidler.addSkills(skillVo.copyTo());
		}
		return buidler.build();
	}
	
	public void addSkill(SkillVo skillVo){
		this.getSkills().add(skillVo);
	}
	
	public boolean checkHadSkill(int xmlId){
		return findSkillVoByXmlId(xmlId) != null;
	}
	
	public SkillVo findSkillVoByXmlId(int xmlId){
		for (SkillVo skillVo : getSkills()) {
			if (skillVo.getXmlId() == xmlId) {
				return skillVo;
			}
		}
		return null;
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleSkillInfoProto roleSkillInfoProto = RoleSkillInfoProto.parseFrom(bytes);
			copyFrom(roleSkillInfoProto);
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

	public List<SkillVo> getSkills() {
		return skills;
	}

	public void setSkills(List<SkillVo> skills) {
		this.skills = skills;
	}
}
