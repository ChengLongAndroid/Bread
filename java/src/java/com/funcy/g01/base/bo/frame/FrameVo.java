package com.funcy.g01.base.bo.frame;

import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.FrameVoProto;

public class FrameVo {

	private int xmlId;
	
	@SuppressWarnings("unused")
	private FrameVo(){
		
	}
	
	public FrameVo(int xmlId){
		this.xmlId = xmlId;
	}
	
	public FrameVoProto copyTo(){
		FrameVoProto.Builder builder = FrameVoProto.newBuilder();
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
