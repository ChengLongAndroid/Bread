package com.funcy.g01.base.data;

import java.math.BigDecimal;

import com.funcy.g01.base.bo.recharge.RechargeType;


public class NpcShopProperty {
	
	private final int id;
	
	private final String goods;
	
	private final int sellType;
	
	private final BigDecimal price;
	
	private final int discount;
	
	private final int likeLv;
	
	
	public NpcShopProperty(int id, String goods, int sellType, BigDecimal price, int discount, int likeLv){
		this.id = id;
		this.goods = goods;
		this.sellType = sellType;
		this.price = price;
		this.discount = discount;
		this.likeLv = likeLv;
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

	public int getLikeLv() {
		return likeLv;
	}

}
