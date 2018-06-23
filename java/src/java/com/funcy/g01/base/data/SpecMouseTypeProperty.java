package com.funcy.g01.base.data;

import java.util.List;

public class SpecMouseTypeProperty {

	private final int id;
	
	private final int openSamanLevel;
	
	private final int limitSamanLevel;
	
	private final List<Integer> limitNails;
	
	private final int perHelpPoint;

	public SpecMouseTypeProperty(int id, int openSamanLevel,
			int limitSamanLevel, List<Integer> limitNails, int perHelpPoint) {
		this.id = id;
		this.openSamanLevel = openSamanLevel;
		this.limitSamanLevel = limitSamanLevel;
		this.limitNails = limitNails;
		this.perHelpPoint = perHelpPoint;
	}

	public int getId() {
		return id;
	}

	public int getOpenSamanLevel() {
		return openSamanLevel;
	}

	public int getLimitSamanLevel() {
		return limitSamanLevel;
	}

	public List<Integer> getLimitNails() {
		return limitNails;
	}

	public int getPerHelpPoint() {
		return perHelpPoint;
	}
	
}
