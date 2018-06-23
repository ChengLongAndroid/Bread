package com.funcy.g01.base.bo.friend;

import com.funcy.g01.base.proto.service.FriendProtoBuffer.FriendReqVoProto;
import com.funcy.g01.base.proto.service.FriendProtoBuffer.FriendVoProto;

public class FriendReq {
	
	private long fromRoleId;	// 好友发起人的ROLE ID
	
	private FriendReqState state;	// 好友发起人的同意状态
	
	private boolean hadRead = false;

	private transient Friend friendVo;
	
	@SuppressWarnings("unused")
	private FriendReq() {
	}
	
	public FriendReq(FriendReq item) {
		this.fromRoleId = item.fromRoleId;
		this.state = item.state;
		this.friendVo = item.friendVo;
		this.hadRead = item.hadRead;
	}

	public FriendReq(long fromRoleId, FriendReqState state) {
		this.fromRoleId = fromRoleId;
		this.state = state;
		this.hadRead = false;
	}
	
	public long getFromRoleId() {
		return fromRoleId;
	}

	public void setFromRoleId(long fromRoleId) {
		this.fromRoleId = fromRoleId;
	}

	public FriendReqState getState() {
		return state;
	}

	public void setState(FriendReqState state) {
		this.state = state;
	}

	public Friend getFriendVo() {
		return friendVo;
	}

	public void setFriendVo(Friend friendVo) {
		this.friendVo = friendVo;
	}
	
	public boolean isHadRead() {
		return hadRead;
	}

	public void setHadRead(boolean hadRead) {
		this.hadRead = hadRead;
	}

	public FriendReqVoProto build4Front() {
		FriendReqVoProto.Builder builder = FriendReqVoProto.newBuilder();
		builder.setFromRoleId(this.fromRoleId);
		builder.setState(this.state.getCode());
		builder.setHadRead(this.hadRead);
		if(this.getFriendVo() != null){
			FriendVoProto friendVoProto = (FriendVoProto) this.friendVo.buildProto4Front();
			builder.setFriendVo(friendVoProto);
		}
		return builder.build();
	}
	
	public FriendReqVoProto build() {
		FriendReqVoProto.Builder builder = FriendReqVoProto.newBuilder();
		builder.setFromRoleId(this.fromRoleId);
		builder.setState(this.state.getCode());
		builder.setHadRead(this.hadRead);
		return builder.build();
	}

	public FriendReq(FriendReqVoProto proto){
		this.fromRoleId = proto.getFromRoleId();
		this.state = FriendReqState.valueOf(proto.getState());
		this.hadRead = proto.getHadRead();
//		this.role = new Role(proto.getRole());
	}
}
