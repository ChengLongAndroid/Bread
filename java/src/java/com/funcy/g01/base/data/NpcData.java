
package com.funcy.g01.base.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.BoFactory;
import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.bo.recharge.RechargeType;
import com.funcy.g01.base.util.DocumentBuilder;

@Component
public class NpcData {

	private static final Logger logger  = Logger.getLogger(NpcData.class);
	
	private final Map<Integer, NpcProperty> npcPropertyMap = new HashMap<Integer, NpcProperty>();
	
	private final Map<Integer, NpcTaskProperty> npcTaskPropertyMap = new HashMap<Integer, NpcTaskProperty>();
	
	private Map<Integer, NpcShopProperty> shopsInfoMap = new HashMap<Integer, NpcShopProperty>();
	
	public void init() {
		initNpcs();
		initNpcTasks();
		initNpcShop();
	}

	public void initNpcs() {
		String url = "com/funcy/g01/xml/npcs.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int xmlId = Integer.parseInt(e.elementText("id"));
				int initLikes = Integer.parseInt(e.elementText("initLikes"));
				List<Integer> taskIds = new ArrayList<Integer>();
				String[] temp = e.elementText("taskIds").split(",");
				for(String str : temp) {
					int taskId = Integer.parseInt(str);
					taskIds.add(taskId);
				}
				
				List<Integer> favoriteItems = new ArrayList<Integer>();
				String[] temp1 = e.elementText("favoriteItems").split(",");
				for(String str : temp1) {
					int itemXmlId = Integer.parseInt(str);
					favoriteItems.add(itemXmlId);
				}
				
				List<Integer> talkTaskIds = new ArrayList<Integer>();
				if(e.elementText("talkTaskIds") != null) {
					String[] temp2 = e.elementText("talkTaskIds").split(",");
					for(String str : temp2) {
						if("".equals(str)) {
							continue;
						}
						int taskId = Integer.parseInt(str);
						talkTaskIds.add(taskId);
					}
				}
				
				NpcProperty npcProperty = new NpcProperty(xmlId, initLikes, taskIds, favoriteItems, talkTaskIds);
				this.npcPropertyMap.put(npcProperty.getXmlId(), npcProperty);
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	public void initNpcTasks() {
		String url = "com/funcy/g01/xml/npcTasks.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int xmlId = Integer.parseInt(e.elementText("id"));
				int type = Integer.parseInt(e.elementText("type"));
				int needCompleteNum = Integer.parseInt(e.elementText("needCompleteNum"));
				List<List<Item>> awards = new ArrayList<List<Item>>();
				String awardsStr = e.elementText("awards");
				String[] awardsStrList = awardsStr.split("\\|");
				for(String temp : awardsStrList) {
					if("".equals(temp)) {
						continue;
					}
					List<Item> items = BoFactory.createMultiItems(temp);
					awards.add(items);
				}
				int needItemXmlId = 0;
				if(e.elementText("needItemXmlId") != null && !"".equals(e.elementText("needItemXmlId"))) {
					needItemXmlId = Integer.parseInt(e.elementText("needItemXmlId"));
				}
				NpcTaskProperty npcTaskProperty = new NpcTaskProperty(xmlId, type, awards, needCompleteNum, needItemXmlId);
				this.npcTaskPropertyMap.put(npcTaskProperty.getXmlId(), npcTaskProperty);
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	public void initNpcShop() {
		String url = "com/funcy/g01/xml/npcShops.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				String goods = e.elementText("goods");
				int sellType = Integer.parseInt(e.elementText("sellType"));
				BigDecimal price = new BigDecimal(e.elementText("price"));
				int discount = Integer.parseInt(e.elementText("discount"));
				int likeLv = Integer.parseInt(e.elementText("likeLv"));
				NpcShopProperty shopProperty = new NpcShopProperty(id, goods, sellType, price, discount, likeLv);
				this.shopsInfoMap.put(id, shopProperty);
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	public NpcProperty getNpcProperty(int xmlId) {
		return this.npcPropertyMap.get(xmlId);
	}
	
	public NpcTaskProperty getNpcTaskProperty(int xmlId) {
		return this.npcTaskPropertyMap.get(xmlId);
	}
	
	public NpcShopProperty getNpcShopProperty(int xmlId) {
		return this.shopsInfoMap.get(xmlId);
	}

	public Map<Integer, NpcProperty> getNpcPropertyMap() {
		return npcPropertyMap;
	}

	public Map<Integer, NpcTaskProperty> getNpcTaskPropertyMap() {
		return npcTaskPropertyMap;
	}
	
}
