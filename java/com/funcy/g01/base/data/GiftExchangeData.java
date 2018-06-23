package com.funcy.g01.base.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.util.DocumentBuilder;

@Component
public class GiftExchangeData {
	
	private Map<Integer, GiftExchangeProperty> giftExchangeProperties = new HashMap<Integer, GiftExchangeProperty>();
	
	private static final Logger logger = Logger.getLogger(GiftExchangeData.class);
	
	public void init(){
		String url = "com/funcy/g01/xml/giftExchange.xml";
		try{
			List<Element> elements = DocumentBuilder.build(url);
			for(Element e : elements){
				int id = Integer.parseInt(e.elementText("id"));
				String giftItem = e.elementText("giftItem");
				int sellType = Integer.parseInt(e.elementText("sellType"));
				int oldPrice = Integer.parseInt(e.elementText("oldPrice"));
				int curPrice = Integer.parseInt(e.elementText("newPrice"));
				giftExchangeProperties.put(id, new GiftExchangeProperty(id, giftItem, sellType, oldPrice, curPrice));
			}
			logger.info("init "+url+" success");
		}catch (Exception e) {
			logger.error("init "+url+" error");
		}
	}
	
	public Map<Integer, GiftExchangeProperty> getAllGiftExchangeProperties(){
		return giftExchangeProperties;
	}
}
