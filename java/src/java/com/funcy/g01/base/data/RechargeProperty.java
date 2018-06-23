package com.funcy.g01.base.data;

import java.math.BigDecimal;

public class RechargeProperty {
	
	private final int type;
	private final BigDecimal money;
	private final int acerNum;
	private final int firstReward;
	private final int reward;
	private final int recommended;
	private final String name;
	
	public int getType() {
		return type;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public int getAcerNum() {
		return acerNum;
	}
	public int getFirstReward() {
		return firstReward;
	}
	public int getReward() {
		return reward;
	}
	public int getRecommended() {
		return recommended;
	}
	public String getName() {
		return name;
	}

	public RechargeProperty(int type, BigDecimal money, int acerNum, int firstReward,
			int reward, int recommended,String name) {
		this.type = type;
		this.money = money;
		this.acerNum = acerNum;
		this.firstReward = firstReward;
		this.reward = reward;
		this.recommended = recommended;
		this.name = name;
	}
	


}
