package com.funcy.g01.base.bo.friend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.service.FriendProtoBuffer.FriendReqVoProto;
import com.funcy.g01.base.proto.service.FriendProtoBuffer.RoleFriendReqInfoProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class RoleFriendReqInfo implements ProtobufSerializable {
	
	private final int friendsReqLimitCount = 50;
	
	private long roleId;
	
	private List<FriendReq> friendReqs = new LinkedList<FriendReq>();
	
	@SuppressWarnings("unused")
	private RoleFriendReqInfo() {
	}

	public boolean clearHaveReadState() {
		boolean result = false;
		for(FriendReq friendReq : friendReqs) {
			if(!friendReq.isHadRead()) {
				result = true;
				friendReq.setHadRead(true);
			}
		}
		return result;
	}
	
	public boolean acceptFriendReq(long targetRoleId){
		Iterator<FriendReq> it = this.friendReqs.iterator();
		while(it.hasNext()) {
			FriendReq friendReq = it.next();
			if(friendReq.getFromRoleId() == targetRoleId) {
				it.remove();
				return true;
			}
		}
		return false;
	}
	
	public boolean rejectFriendReq(long targetRoleId){
		Iterator<FriendReq> it = this.friendReqs.iterator();
		while(it.hasNext()) {
			FriendReq friendReq = it.next();
			if(friendReq.getFromRoleId() == targetRoleId) {
				it.remove();
				return true;
			}
		}
		return false;
	}
	
	public boolean checkIsExist(long fromRoleId){
		for(FriendReq obj : this.getFriendReqs()){
			if(obj.getFromRoleId() == fromRoleId){
				return true;
			}
		}
		return false;
	}
	
	public void add(FriendReq obj){
		if(this.getFriendReqs().size() >= friendsReqLimitCount){
			this.getFriendReqs().remove(0);
		}
		this.getFriendReqs().add(obj);
	}
	
	public void remove(FriendReq obj){
		Iterator<FriendReq> it = this.friendReqs.iterator();
		while(it.hasNext()) {
			FriendReq friendReq = it.next();
			if(friendReq.getFromRoleId() == obj.getFromRoleId()) {
				it.remove();
			}
		}
	}
	
	public FriendReq getFriendReqByFromRoleId(int fromRoleId){
		for(FriendReq obj : this.getFriendReqs()){
			if(obj.getFromRoleId() == fromRoleId){
				return obj;
			}
		}
		return null;
	}
	
	public RoleFriendReqInfo(long roleId){
		this.roleId = roleId;
		this.setFriendReqs(new ArrayList<FriendReq>());
	}
	
	public RoleFriendReqInfo(byte[] bytes){
		parseFrom(bytes);
	}
	
	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}
	
	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleFriendReqInfoProto proto = (RoleFriendReqInfoProto) message;
		this.roleId = proto.getRoleId();
		this.setFriendReqs(new LinkedList<FriendReq>()) ;
		for (FriendReqVoProto vo : proto.getFriendReqsList()) {
			this.add(new FriendReq(vo));
		}
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleFriendReqInfoProto.Builder buidler = RoleFriendReqInfoProto.newBuilder();
		buidler.setRoleId(roleId);
		for (FriendReq obj : this.getFriendReqs()) {
			buidler.addFriendReqs(obj.build());
		}
		return buidler.build();
	}
	
	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleFriendReqInfoProto infoProto = RoleFriendReqInfoProto.parseFrom(bytes);
			copyFrom(infoProto);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}
	
	public List<FriendReq> getFriendReqs() {
		return friendReqs;
	}

	public void setFriendReqs(List<FriendReq> friendReqs) {
		this.friendReqs = friendReqs;
	}

	public long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

}
