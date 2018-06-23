package com.funcy.g01.base.bo.dress;

import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.DressVoProto;

public class DressVo {

	private int xmlId;
	
	@SuppressWarnings("unused")
	private DressVo(){
		
	}
	
	public DressVo(int xmlId){
		this.xmlId = xmlId;
	}
	
	public DressVoProto copyTo(){
		DressVoProto.Builder builder = DressVoProto.newBuilder();
		builder.setXmlId(xmlId);
		return builder.build();
	}
	

	public int getXmlId() {
		return xmlId;
	}

	public void setXmlId(int xmlId) {
		this.xmlId = xmlId;
	}
}
