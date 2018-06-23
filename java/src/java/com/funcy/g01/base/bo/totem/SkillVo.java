package com.funcy.g01.base.bo.totem;

import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.SkillVoProto;

public class SkillVo {
	
	public static final int max_star_lv = 5;
	
	private int star;
	
	private int xmlId;
	
	@SuppressWarnings("unused")
	private SkillVo() {
	}
	
	public SkillVo(SkillVo skillVo) {
		this.xmlId = skillVo.xmlId;
		this.star = skillVo.star;
	}

	public SkillVo(int star, int xmlId) {
		this.star = star;
		this.xmlId = xmlId;
	}
	
	public SkillVoProto copyTo(){
		SkillVoProto.Builder builder = SkillVoProto.newBuilder();
		builder.setXmlId(xmlId);
		builder.setStar(star);
		return builder.build();
	}


	public int getXmlId() {
		return xmlId;
	}

	public void setXmlId(int xmlId) {
		this.xmlId = xmlId;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}
	
}
