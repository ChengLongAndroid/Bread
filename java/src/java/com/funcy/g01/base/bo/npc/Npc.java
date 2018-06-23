package com.funcy.g01.base.bo.npc;

import java.util.List;
import java.util.Random;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.data.NpcData;
import com.funcy.g01.base.data.NpcProperty;
import com.funcy.g01.base.data.NpcTaskProperty;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.NpcProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class Npc implements ProtobufSerializable {

	private int xmlId;
	
	private int likesLevel;
	
	private int likesExp;
	
	public static final int max_level = 4;
	
	public static final int max_exp = 100;
	
	public static final Random random = new Random();
	
	public Npc(NpcProto npcProto) {
		copyFrom(npcProto);
	}
	
	public Npc(NpcProperty npcProperty) {
		this.xmlId = npcProperty.getXmlId();
		this.likesLevel = npcProperty.getInitLikes();
		this.likesExp = 0;
	}
	
	public int getXmlId() {
		return xmlId;
	}

	public int getLikesLevel() {
		return likesLevel;
	}

	public int getLikesExp() {
		return likesExp;
	}
	
	public boolean isSpecLike(NpcData npcData, int xmlId) {
		NpcProperty npcProperty = npcData.getNpcProperty(this.xmlId);
		for(int favorItemXmlId : npcProperty.getFavoriteItems()) {
			if(favorItemXmlId == xmlId) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void copyFrom(GeneratedMessage message) {
		NpcProto npcProto = (NpcProto) message;
		this.xmlId = npcProto.getXmlId();
		this.likesLevel = npcProto.getLikesLevel();
		this.likesExp = npcProto.getLikesExp();
	}

	@Override
	public GeneratedMessage copyTo() {
		NpcProto.Builder builder = NpcProto.newBuilder();
		builder.setXmlId(this.xmlId);
		builder.setLikesExp(this.likesExp);
		builder.setLikesLevel(this.likesLevel);
		return builder.build();
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			NpcProto proto = NpcProto.parseFrom(bytes);
			copyFrom(proto);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] toByteArray() {
		return this.copyTo().toByteArray();
	}

	public NpcTask randomTask(NpcProperty npcProperty, NpcData npcData) {
		List<Integer> taskIds = npcProperty.getTaskIds();
		int index = random.nextInt(taskIds.size());
		int taskId = taskIds.get(index);
		if(taskId == 0) {
			taskId = npcProperty.getTalkTaskIds().get(random.nextInt(npcProperty.getTalkTaskIds().size()));
		}
		NpcTaskProperty npcTaskProperty = npcData.getNpcTaskProperty(taskId);
		NpcTask npcTask = new NpcTask(npcTaskProperty, this.xmlId);
		return npcTask;
	}

	public void addExp(int addLikes) {
		this.likesExp += addLikes;
		if(this.likesLevel < max_level) {
			if(likesExp >= max_exp) {
				this.likesExp -= max_exp;
				this.likesLevel++;
			}
		}
	}
	
}
