package com.funcy.g01.base.bo.friend;

import com.funcy.g01.base.proto.service.FriendProtoBuffer.FriendMessageVoProto;

public class FriendMessage {
	
	private long fromRoleId;	// 发送人的ROLE ID
	
	private long toRoleId;	// 接收人的ROLE ID
	
	private String msg;
		
	private long createTime;
	
	private int isReaded;	//是否已经查看：1已看；0未看
	
	@SuppressWarnings("unused")
	private FriendMessage() {
	}
	
	public FriendMessage(FriendMessage item) {
		this.fromRoleId = item.fromRoleId;
		this.toRoleId = item.toRoleId;
		this.createTime = item.createTime;
		this.msg = item.msg;
		this.isReaded = item.getIsReaded();
	}

	public FriendMessage(long fromRoleId, long toRoleId, String msg, long createTime) {
		this.fromRoleId = fromRoleId;
		this.toRoleId = toRoleId;
		this.msg = msg;
		this.createTime = createTime;
		this.isReaded = 1;
	}
	
	public long getFromRoleId() {
		return fromRoleId;
	}

	public void setFromRoleId(long fromRoleId) {
		this.fromRoleId = fromRoleId;
	}

	public long getToRoleId() {
		return toRoleId;
	}

	public void setToRoleId(long toRoleId) {
		this.toRoleId = toRoleId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getIsReaded() {
		return isReaded;
	}

	public void setIsReaded(int isReaded) {
		this.isReaded = isReaded;
	}

	public FriendMessageVoProto copyTo() {
		FriendMessageVoProto.Builder builder = FriendMessageVoProto.newBuilder();
		builder.setFromRoleId(this.fromRoleId);
		builder.setToRoleId(this.toRoleId);
		builder.setMsg(this.msg);
		builder.setCreateTime(this.createTime);
		builder.setIsReaded(this.isReaded);
		return builder.build();
	}

	public FriendMessage(FriendMessageVoProto proto){
		this.fromRoleId = proto.getFromRoleId();
		this.toRoleId = proto.getToRoleId();
		this.msg = proto.getMsg();
		this.createTime = proto.getCreateTime();
		this.isReaded = proto.getIsReaded();
	}
}
