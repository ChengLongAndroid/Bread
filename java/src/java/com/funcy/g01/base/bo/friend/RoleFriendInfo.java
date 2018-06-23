package com.funcy.g01.base.bo.friend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.service.FriendProtoBuffer.RoleFriendInfoProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class RoleFriendInfo implements ProtobufSerializable {
	
	public static final int limitCount = 100;
	
	private List<Friend> friends = new LinkedList<Friend>();
	
	public boolean checkIsFinishFriendAchivement(int achivementLimit){
		if(friends.size() >= achivementLimit){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkIsFull(){
		if(friends.size() >= limitCount){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkIsExist(long friendRoleId){
		for(Friend obj : this.getFriends()){
			if(obj.getRoleId() == friendRoleId){
				return true;
			}
		}
		return false;
	}
	
	public boolean addFriend(Friend obj){
		boolean isRepeated = false;
		for(Friend friend:friends){
			if(friend.getRoleId()==obj.getRoleId()){
				isRepeated =  true;
			}
		}
		if(!isRepeated){
			this.friends.add(obj);
			return true;
		}
		return false;
	}
	
	public void removeFriend(long friendRoleId){
		Iterator<Friend> it = this.friends.iterator();
		while(it.hasNext()) {
			Friend friend = it.next();
			if(friend.getRoleId() == friendRoleId) {
				it.remove();
			}
		}
	}
	
	public Friend getFriendByFriendRoleId(int friendRoleId){
		for(Friend friend : this.friends){
			if(friend.getRoleId() == friendRoleId){
				return friend;
			}
		}
		return null;
	}
	
	public RoleFriendInfo(){
		this.setFriends(new ArrayList<Friend>());
	}
	
	public RoleFriendInfo(byte[] bytes){
		parseFrom(bytes);
	}
	
	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}
	
	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleFriendInfoProto proto = (RoleFriendInfoProto) message;
		this.setFriends(new LinkedList<Friend>()) ;
		for (long friendRoleId : proto.getFriendsList()) {
			this.addFriend(new Friend(friendRoleId));
		}
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleFriendInfoProto.Builder buidler = RoleFriendInfoProto.newBuilder();
		for (Friend obj : this.getFriends()) {
			buidler.addFriends(obj.getRoleId());
		}
		return buidler.build();
	}
	
	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleFriendInfoProto infoProto = RoleFriendInfoProto.parseFrom(bytes);
			copyFrom(infoProto);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

	public List<Friend> getFriendPage(int index){
		int fromIndex = index;
		int toIndex = fromIndex + 19;
		if(friends.size() < toIndex) {
			toIndex = friends.size();
		}
		if(toIndex < 0) {
			return java.util.Collections.EMPTY_LIST;
		} else {
			return friends.subList(fromIndex, toIndex);
		}
	}
	
	public List<Friend> getFriends() {
		return friends;
	}

	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}

}
