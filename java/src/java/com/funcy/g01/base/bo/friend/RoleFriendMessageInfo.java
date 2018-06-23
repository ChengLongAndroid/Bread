package com.funcy.g01.base.bo.friend;

import java.util.ArrayList;
import java.util.List;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.service.FriendProtoBuffer.FriendMessageVoProto;
import com.funcy.g01.base.proto.service.FriendProtoBuffer.RoleFriendMessageInfoProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class RoleFriendMessageInfo implements ProtobufSerializable {
	
	private transient final int limitCount = 50;
	
	private List<FriendMessage> msgs = new ArrayList<FriendMessage>();
	
	@SuppressWarnings("unused")
	private RoleFriendMessageInfo() {
	}

	public RoleFriendMessageInfo(long roleId, long targetRoleId){
		this.setMsgs(new ArrayList<FriendMessage>());
	}
	
	public RoleFriendMessageInfo(byte[] bytes){
		parseFrom(bytes);
	}
	
	public void addMsg(long fromRoleId, long toRoleId, String msg){
		if(this.msgs.size() >= limitCount){
			this.msgs.remove(0);
		}
		this.msgs.add(new FriendMessage(fromRoleId, toRoleId, msg, System.currentTimeMillis()));
	}
	
	public void addMsg(FriendMessage msg){
		if(this.msgs.size() >= limitCount){
			this.msgs.remove(0);
		}
		this.msgs.add(msg);
	}
	
	/*###########*/
	
	public boolean checkIsFull(){
		if(msgs.size() >= limitCount){
			return true;
		} else {
			return false;
		}
	}
	
	public void cleanFriendMsg(){
		this.setMsgs(new ArrayList<FriendMessage>());
	}
	
	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}
	
	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleFriendMessageInfoProto proto = (RoleFriendMessageInfoProto) message;
		this.setMsgs(new ArrayList<FriendMessage>()) ;
		for (FriendMessageVoProto vo : proto.getMsgsList()) {
			this.addMsg(new FriendMessage(vo));
		}
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleFriendMessageInfoProto.Builder buidler = RoleFriendMessageInfoProto.newBuilder();
		for (FriendMessage obj : this.getMsgs()) {
			buidler.addMsgs(obj.copyTo());
		}
		return buidler.build();
	}
	
	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleFriendMessageInfoProto infoProto = RoleFriendMessageInfoProto.parseFrom(bytes);
			copyFrom(infoProto);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

	public List<FriendMessage> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<FriendMessage> msgs) {
		this.msgs = msgs;
	}

}
