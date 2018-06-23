package com.funcy.g01.base.data;

import java.util.List;

import com.funcy.g01.base.bo.item.Item;


public class MysteriousShopPeriodProperty {
	
	private final int id;
	
	private final int periodId;
	
	private final int index;
	
	private final Item exchangeItem;
	
	private final List<Item> needs;
	
	private final int exchangeNum;
	
	private final int type;
	
	
	public MysteriousShopPeriodProperty(int id, int periodId, int index, Item exchangeItem, List<Item> needs, int exchangeNum, int type){
		this.id = id;
		this.index = index;
		this.exchangeItem = exchangeItem;
		this.periodId = periodId;
		this.needs = needs;
		this.exchangeNum = exchangeNum;
		this.type = type;
	}


	public int getId() {
		return id;
	}


	public int getPeriodId() {
		return periodId;
	}


	public int getIndex() {
		return index;
	}


	public Item getExchangeItem() {
		return exchangeItem;
	}


	public List<Item> getNeeds() {
		return needs;
	}


	public int getExchangeNum() {
		return exchangeNum;
	}


	public int getType() {
		return type;
	}

}
