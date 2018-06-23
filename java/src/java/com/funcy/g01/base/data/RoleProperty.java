package com.funcy.g01.base.data;

public class RoleProperty {
	private int level;
	
	private int needExp;
	
	private int maxWeekExp;
	
	private int maxWeekAddExp;
	
	private int maxWeekCheese;
	
	public RoleProperty(int level, int needExp, int maxWeekExp, int maxWeekAddExp, int maxWeekCheese) {
		super();
		this.level = level;
		this.needExp = needExp;
		this.maxWeekExp = maxWeekExp;
		this.maxWeekAddExp = maxWeekAddExp;
		this.maxWeekCheese = maxWeekCheese;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getNeedExp() {
		return needExp;
	}

	public void setNeedExp(int needExp) {
		this.needExp = needExp;
	}

	public int getMaxWeekExp() {
		return maxWeekExp;
	}

	public void setMxpWeekExp(int mxpWeekExp) {
		this.maxWeekExp = mxpWeekExp;
	}

	public int getMaxWeekAddExp() {
		return maxWeekAddExp;
	}

	public void setMaxWeekAddExp(int maxWeekAddExp) {
		this.maxWeekAddExp = maxWeekAddExp;
	}

	public int getMaxWeekCheese() {
		return maxWeekCheese;
	}

	public void setMaxWeekCheese(int maxWeekCheese) {
		this.maxWeekCheese = maxWeekCheese;
	}
}
