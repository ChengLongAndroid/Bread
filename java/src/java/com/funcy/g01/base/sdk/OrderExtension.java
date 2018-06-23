package com.funcy.g01.base.sdk;

public class OrderExtension {

	private int areaId;
	
	private long roleId;
	
	private int type;

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public OrderExtension() {
		
	}
	
	public OrderExtension(int areaId, long roleId, int type) {
		this.areaId = areaId;
		this.roleId = roleId;
		this.type = type;
	}
	
}
