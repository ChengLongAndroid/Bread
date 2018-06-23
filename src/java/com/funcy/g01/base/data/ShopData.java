package com.funcy.g01.base.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.recharge.RechargeType;
import com.funcy.g01.base.util.DocumentBuilder;

@Component
public class ShopData {
	
	private static final Logger logger  = Logger.getLogger(ShopData.class);
	
	private Map<Integer, ShopProperty> shopsInfoMap = new HashMap<Integer, ShopProperty>();
	private Map<String, ShopProperty> rechargeInfoMap = new HashMap<String, ShopProperty>();
	
	private ShopProperty monthcardShopProperty = null;
	
	private ShopProperty yearcardShopProperty = null;
	
	public void init() {
		String url = "com/funcy/g01/xml/shops.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				String goods = e.elementText("goods");
				String reward = e.elementText("reward");
				int sellType = Integer.parseInt(e.elementText("sellType"));
				BigDecimal price = new BigDecimal(e.elementText("price"));
				int discount = Integer.parseInt(e.elementText("discount"));
				int limitLv = Integer.parseInt(e.elementText("limitLv"));
				String productId = e.elementText("productId");
				String rechargeTypeStr = e.elementText("rechargeType");
				int rechargeTypeInt = 0;
				if (!rechargeTypeStr.equals("")) {
					rechargeTypeInt = Integer.parseInt(rechargeTypeStr);
				}
				RechargeType rechargeType = RechargeType.valueOf(rechargeTypeInt);
				ShopProperty shopProperty = new ShopProperty(id, goods, sellType, price, discount, limitLv, productId,rechargeType, reward);
				this.shopsInfoMap.put(id, shopProperty);
				if (sellType == 3) {
					rechargeInfoMap.put(productId, shopProperty);
				}
				if (shopProperty.getRechargeType() == RechargeType.monthcard) {
					monthcardShopProperty = shopProperty;
				}else if(shopProperty.getRechargeType() == RechargeType.yearcard){
					yearcardShopProperty = shopProperty;
				}
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	public ShopProperty getShopProperty(int xmlId) {
		return this.shopsInfoMap.get(xmlId);
	}
	
	public ShopProperty getRechargeProperty(String productId) {
		return this.rechargeInfoMap.get(productId);
	}
	
	public int getRechargeTypeNum() {
		return this.rechargeInfoMap.size();
	}
	
	public ShopProperty getMonthcardShopProperty(){
		return monthcardShopProperty;
	}

	public ShopProperty getYearcardShopProperty() {
		return yearcardShopProperty;
	}

	public void setYearcardShopProperty(ShopProperty yearcardShopProperty) {
		this.yearcardShopProperty = yearcardShopProperty;
	}
	
}
