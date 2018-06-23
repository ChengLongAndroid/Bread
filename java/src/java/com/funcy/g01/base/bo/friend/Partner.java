package com.funcy.g01.base.bo.friend;

import com.funcy.g01.base.proto.service.FriendProtoBuffer.PartnerVoProto;

public class Partner {
	
	private long roleId;
	
	private long recentPlayTime;

	public Partner(long roleId, long recentPlayTime) {
		this.roleId = roleId;
		this.recentPlayTime = recentPlayTime;
	}
	
	public Partner(PartnerVoProto proto){
		this.roleId = proto.getRoleId();
		this.recentPlayTime = proto.getRecentPlayTime();
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public long getRecentPlayTime() {
		return recentPlayTime;
	}

	public void setRecentPlayTime(long recentPlayTime) {
		this.recentPlayTime = recentPlayTime;
	}
	
	public PartnerVoProto build(){
		PartnerVoProto.Builder partnerProto = PartnerVoProto.newBuilder();
		partnerProto.setRoleId(this.getRoleId());
		partnerProto.setRecentPlayTime(this.getRecentPlayTime());
		return partnerProto.build();
	}
}
