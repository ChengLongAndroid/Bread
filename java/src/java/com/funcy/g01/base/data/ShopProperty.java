package com.funcy.g01.base.data;

import java.math.BigDecimal;

import com.funcy.g01.base.bo.recharge.RechargeType;


public class ShopProperty {
	
	private final int id;
	
	private final String goods;
	
	private final int sellType;
	
	private final BigDecimal price;
	
	private final int discount;
	
	private final int limitLv;
	
	private final String productId;
	
	private final RechargeType rechargeType;
	
	private final String reward;
	
	public ShopProperty(int id, String goods, int sellType, BigDecimal price, int discount, int limitLv, String productId, RechargeType rechargeType, String reward){
		this.id = id;
		this.goods = goods;
		this.sellType = sellType;
		this.price = price;
		this.discount = discount;
		this.limitLv = limitLv;
		this.productId = productId;
		this.rechargeType = rechargeType;
		this.reward = reward;
	}

	public int getId() {
		return id;
	}

	public String getGoods() {
		return goods;
	}

	public int getSellType() {
		return sellType;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public int getDiscount() {
		return discount;
	}

	public int getLimitLv() {
		return limitLv;
	}

	public String getProductId() {
		return productId;
	}

	public RechargeType getRechargeType() {
		return rechargeType;
	}

	public String getReward() {
		return reward;
	}

}
