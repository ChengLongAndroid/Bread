package com.funcy.g01.base.bo.email;

public class RewardCodeData {

	private int result;
	
	private String reward;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public RewardCodeData(){
		
	}
	
	public RewardCodeData(int result) {
		super();
		this.result = result;
		this.reward = "";
	}
	
}
