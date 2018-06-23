package com.funcy.g01.base.data;

import com.funcy.g01.base.bo.achieve.AchievementCategory;


public class AchievementStarRewardProperty {
	
	private final AchievementCategory category;
	
	private final int star;
	
	private final int grade;
	
	private final String reward;
	
	public AchievementStarRewardProperty(int star, AchievementCategory category,int grade, String reward){
		this.category = category;
		this.star = star;
		this.grade = grade;
		this.reward = reward;
	}

	public AchievementCategory getCategory() {
		return category;
	}

	public int getStar() {
		return star;
	}

	public int getGrade() {
		return grade;
	}

	public String getReward() {
		return reward;
	}

	
}
