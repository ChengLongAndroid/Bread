package com.funcy.g01.base.bo.achieve;

public enum AchievementState {
	running(1),recieve(2),completed(3);
	
	private int value;
	
	private AchievementState(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public static AchievementState getTypeByValue(int value) {
		for (AchievementState achievementState : AchievementState.values()) {
			if (achievementState.value == value) {
				return achievementState;
			}
		}
		throw new RuntimeException();
	}
}
