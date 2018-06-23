package com.funcy.g01.base.bo.achieve;

public class UpdateAchievement {

	private long roleId;
	
	private AchievementType type;
	
	private int param1;
	
	public UpdateAchievement(long roleId, AchievementType type, int param1){
		this.roleId = roleId;
		this.type = type;
		this.param1 = param1;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public AchievementType getType() {
		return type;
	}

	public void setType(AchievementType type) {
		this.type = type;
	}

	public int getParam1() {
		return param1;
	}

	public void setParam1(int param1) {
		this.param1 = param1;
	}
}
