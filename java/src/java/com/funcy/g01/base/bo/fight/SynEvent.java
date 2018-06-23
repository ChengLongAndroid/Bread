package com.funcy.g01.base.bo.fight;

public abstract class SynEvent {

	private long roleId;
	
	private String serviceAndMethod;
	
	public SynEvent(long roleId, String serviceAndMethod) {
		this.roleId = roleId;
		this.serviceAndMethod = serviceAndMethod;
	}
	
	public abstract void executeEvent();

	public long getRoleId() {
		return roleId;
	}
	
	public String getServiceAndMethod() {
		return serviceAndMethod;
	}

	@Override
	public String toString() {
		return "SynEvent [roleId=" + roleId + ", serviceAndMethod="
				+ serviceAndMethod + "]";
	}
	
}
