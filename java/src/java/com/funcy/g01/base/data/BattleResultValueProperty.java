package com.funcy.g01.base.data;

import java.util.List;

public class BattleResultValueProperty {
	
	private final int id;
	
	private final int baseWinExp;
	
	private final int shammanBaseWinExp;
	
	private final int deadExp;
	
	private final int baseWinCheese;
	
	private final int normalTypeExp;
	
	private final int advanceTypeExp;
	
	private final List<Integer> rankingAddExpList;
	
	private final int helpMiceExp;
	
	public BattleResultValueProperty(int id, int baseWinExp, int shammanBaseWinExp,  int deadExp,  int baseWinCheese,  int normalTypeExp, int advanceTypeExp
			, List<Integer> rankingAddExpList, int helpMiceExp){
		this.id = id;
		this.baseWinExp = baseWinExp;
		this.shammanBaseWinExp = shammanBaseWinExp;
		this.deadExp = deadExp;
		this.baseWinCheese = baseWinCheese;
		this.normalTypeExp = normalTypeExp;
		this.advanceTypeExp = advanceTypeExp;
		this.rankingAddExpList = rankingAddExpList;
		this.helpMiceExp = helpMiceExp;
	}

	public int getId() {
		return id;
	}

	public int getBaseWinExp() {
		return baseWinExp;
	}

	public int getShammanBaseWinExp() {
		return shammanBaseWinExp;
	}

	public int getDeadExp() {
		return deadExp;
	}

	public int getBaseWinCheese() {
		return baseWinCheese;
	}

	public int getNormalTypeExp() {
		return normalTypeExp;
	}

	public int getAdvanceTypeExp() {
		return advanceTypeExp;
	}

	public List<Integer> getRankingAddExpList() {
		return rankingAddExpList;
	}

	public int getHelpMiceExp() {
		return helpMiceExp;
	}
	
}
