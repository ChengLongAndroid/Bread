package com.funcy.g01.base.data;


public class GiftExchangeProperty {
	
	private final int id;
	
	private final String giftItem;
	
	private final int sellType;
	
	private final int oldPrice;
	
	private final int curPrice;

	public GiftExchangeProperty(int id, String giftItem, int sellType, int oldPrice,
			int curPrice) {
		this.id = id;
		this.giftItem = giftItem;
		this.sellType = sellType;
		this.oldPrice = oldPrice;
		this.curPrice = curPrice;
	}

	public int getId() {
		return id;
	}

	public String getGiftItem() {
		return giftItem;
	}

	public int getSellType() {
		return sellType;
	}

	public int getOldPrice() {
		return oldPrice;
	}

	public int getCurPrice() {
		return curPrice;
	}
	
}
