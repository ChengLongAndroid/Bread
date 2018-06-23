package com.funcy.g01.base.bo;

import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.CurrencyProto;

public class Currency {

	
	private CurrencyType currencyType;
	
	private int price;
	
	@SuppressWarnings("unused")
	private Currency(){
		
	}
	
	public Currency(CurrencyType currencyType, int price) {
		this.currencyType = currencyType;
		this.price = price;
	}

	public int getCurrencyType() {
		return currencyType.getValue();
	}
	
	public CurrencyType getCurrencyType0() {
	    return currencyType;
	}
	
	public void setCurrencyType(int value) {
		this.currencyType = CurrencyType.getTypeByValue(value);
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Currency discount(float discount) {
		this.price = (int) (this.price * discount);
		return this;
	}

	public CurrencyProto copyTo() {
		CurrencyProto.Builder builder = CurrencyProto.newBuilder();
		builder.setCurrencyType(this.getCurrencyType());
		builder.setPrice(this.price);
		return builder.build();
	}
	
}
