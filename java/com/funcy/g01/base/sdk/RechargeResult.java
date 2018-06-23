package com.funcy.g01.base.sdk;

import com.funcy.g01.base.proto.service.ReqCmdProto.StrProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.StrProto.Builder;


public class RechargeResult {
	
	private long roleId;
	
	private long time;
	
	private StrProto.Builder builder;
	
	@SuppressWarnings("unused")
	private RechargeResult(){
		
	}

	public RechargeResult(Long roleId, Builder builder) {
		this.roleId = roleId;
		this.time = System.currentTimeMillis()+3600000;
		this.builder = builder;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public StrProto.Builder getBuilder() {
		return builder;
	}

	public void setBuilder(StrProto.Builder builder) {
		this.builder = builder;
	}
	
	
}
