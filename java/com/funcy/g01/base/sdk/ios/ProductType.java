package com.funcy.g01.base.sdk.ios;

public enum ProductType {
	yueka("com.ebo.xiudoujuntuan.yueka", 1),
	caishenka("com.ebo.xiudoujuntuan.vipcard", 2),
	gold1("com.ebo.xiudoujuntuan.6480gold",3),
	gold2("com.ebo.xiudoujuntuan.3280gold",4),
	gold3("com.ebo.xiudoujuntuan.1980gold",5),
	gold4("com.ebo.xiudoujuntuan.980gold",6),
	gold5("com.ebo.xiudoujuntuan.300gold",7),
	gold6("com.ebo.xiudoujuntuan.60gold",8),
	gold7("com.ebo.xiudoujuntuan.1gold",9);
	
	public String name;
	
	public int type;
	
	private ProductType(String name, int type) {
		this.name = name;
		this.type = type;
	}
	
	public static ProductType getProductTypeByName(String name) {
		for(ProductType productType : ProductType.values()) {
			if(productType.name.equals(name)) return productType;
		}
		return null;
	}
}
