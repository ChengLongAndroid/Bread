package com.funcy.g01.base.data;

import java.util.List;

import com.funcy.g01.base.bo.chest.ChestType;


public class SigninPeriodProperty {
	
	private final int id;
	
	private final int periodId;
	
	private final String reward;
	
	private final int day;
	
	private final List<Integer> dropGroupIds;
	
	public SigninPeriodProperty(int id, int periodId, String reward, int day, List<Integer> dropGroupIds){
		this.id = id;
		this.periodId = periodId;
		this.reward = reward;
		this.day = day;
		this.dropGroupIds = dropGroupIds;
	}

	public int getId() {
		return id;
	}

	public String getReward() {
		return reward;
	}

	public int getDay() {
		return day;
	}

	public List<Integer> getDropGroupIds() {
		return dropGroupIds;
	}

	public int getPeriodId() {
		return periodId;
	}

}
