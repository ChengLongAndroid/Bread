package com.funcy.g01.base.data;

public class ShammanProperty {
	private int level;
	
	private int needExp;

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

	public ShammanProperty(int level, int needExp) {
		super();
		this.level = level;
		this.needExp = needExp;
	}
}
