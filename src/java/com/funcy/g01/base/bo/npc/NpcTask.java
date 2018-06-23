package com.funcy.g01.base.bo.npc;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.data.NpcTaskProperty;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.NpcProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.NpcTaskProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class NpcTask implements ProtobufSerializable {

	private int xmlId;
	
	private int npcXmlId;
	
	private int completeNum;
	
	private int state; //0 init 1 已接受, 2 已完成
	
	public static final int npc_task_state_init = 0;
	
	public static final int npc_task_state_accept = 1;
	
	public static final int npc_task_state_complete = 2;

	public NpcTask(NpcTaskProto npcTaskProto) {
		copyFrom(npcTaskProto);
	}
	
	public NpcTask(NpcTaskProperty npcTaskProperty, int npcXmlId) {
		this.xmlId = npcTaskProperty.getXmlId();
		this.npcXmlId = npcXmlId;
		this.completeNum = 0;
		this.state = 0;
	}

	public int getXmlId() {
		return xmlId;
	}

	public int getNpcXmlId() {
		return npcXmlId;
	}

	public int getCompleteNum() {
		return completeNum;
	}

	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public void setCompleteNum(int completeNum) {
		this.completeNum = completeNum;
	}
	
	public void addCompleteNum(NpcTaskProperty npcTaskProperty, int num) {
		this.completeNum += num;
		if(this.completeNum > npcTaskProperty.getNeedCompleteNum()) {
			this.completeNum = npcTaskProperty.getNeedCompleteNum();
		}
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		NpcTaskProto proto = (NpcTaskProto) message;
		this.xmlId = proto.getXmlId();
		this.npcXmlId = proto.getNpcXmlId();
		this.completeNum = proto.getCompleteNum();
		this.state = proto.getState();
	}

	@Override
	public GeneratedMessage copyTo() {
		NpcTaskProto.Builder builder = NpcTaskProto.newBuilder();
		builder.setXmlId(this.xmlId);
		builder.setNpcXmlId(this.npcXmlId);
		builder.setCompleteNum(this.completeNum);
		builder.setState(this.state);
		return builder.build();
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			NpcTaskProto proto = NpcTaskProto.parseFrom(bytes);
			copyFrom(proto);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] toByteArray() {
		return this.copyTo().toByteArray();
	}
	
}
