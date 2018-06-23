package com.funcy.g01.base.bo;

public enum CurrencyType {

	cheese(1, "奶酪"), gem(2, "钻石");
	
	public int value;
	
	public String description;
	
	private CurrencyType(int value, String description) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public static CurrencyType getTypeByValue(int value) {
		for (CurrencyType currencyType : CurrencyType.values()) {
			if (currencyType.value == value) {
				return currencyType;
			}
		}
		throw new RuntimeException();
	}
}

