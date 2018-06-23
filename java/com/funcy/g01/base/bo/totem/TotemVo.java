package com.funcy.g01.base.bo.totem;

import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.TotemVoProto;

public class TotemVo {
	
	public static final int final_totem_index = 7;
	
	private int id;
	
	private int skillId;
	
	private int xmlId;
	
	
	@SuppressWarnings("unused")
	private TotemVo() {
	}
	
	public TotemVo(TotemVo TotemVo) {
		this.id = TotemVo.id;
		this.xmlId = TotemVo.xmlId;
		this.skillId = TotemVo.skillId;
	}

	public TotemVo(int id, int xmlId, int skillId) {
		this.id = id;
		this.xmlId = xmlId;
		this.skillId = skillId;
	}
	
	public TotemVoProto copyTo(){
		TotemVoProto.Builder builder = TotemVoProto.newBuilder();
		builder.setId(id);
		builder.setSkillId(skillId);
		builder.setXmlId(xmlId);
		return builder.build();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public int getXmlId() {
		return xmlId;
	}

	public void setXmlId(int xmlId) {
		this.xmlId = xmlId;
	}
	
}
