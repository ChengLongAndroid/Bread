package com.funcy.g01.base.bo.achieve;


public enum AchievementCategory {
	grow_up(1),promotion(2);
	private int value;
	
	private AchievementCategory(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public static AchievementCategory getTypeByValue(int value) {
		for (AchievementCategory achievementType : AchievementCategory.values()) {
			if (achievementType.value == value) {
				return achievementType;
			}
		}
		throw new RuntimeException();
	}
}