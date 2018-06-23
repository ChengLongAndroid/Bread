package com.funcy.g01.base.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.util.DocumentBuilder;

@Component
public class ItemData {
	
	private static final Logger logger  = Logger.getLogger(ItemData.class);
	
	private Map<Integer, ItemProperty> itemsInfoMap = new HashMap<Integer, ItemProperty>();
	
	public void init() {
		String url = "com/funcy/g01/xml/items.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				String name = e.elementText("name");
				int quality = Integer.parseInt(e.elementText("quality"));
				String priceStr = e.elementText("price");
				int price = 0;
				if (!priceStr.equals("")) {
					price = Integer.parseInt(priceStr);
				}
				String fightSkillIdStr = e.elementText("fightSkillId");
				int fightSkillId = 0;
				if (!fightSkillIdStr.equals("")) {
					fightSkillId = Integer.parseInt(fightSkillIdStr);
				}
				boolean isBtnLayerUse = Boolean.parseBoolean(e.elementText("isBtnLayerUse"));
				boolean isUse = Boolean.parseBoolean(e.elementText("isUse"));
				boolean isSell = Boolean.parseBoolean(e.elementText("isSell"));
				String awardStr = e.elementText("awardStr");
				int addLikes = Integer.parseInt(e.elementText("addLikes"));
				boolean isRealItem = Boolean.parseBoolean(e.elementText("isRealItem"));
				int useAddNum = Integer.parseInt(e.elementText("useAddNum"));
				int useAddParam = Integer.parseInt(e.elementText("useAddParam"));
				this.itemsInfoMap.put(id, new ItemProperty(id, name, quality, isSell, price, isUse, isBtnLayerUse, awardStr, fightSkillId
						, addLikes, isRealItem, useAddNum, useAddParam));
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	public ItemProperty getItemProperty(int xmlId) {
		return this.itemsInfoMap.get(xmlId);
	}

	public Map<Integer, ItemProperty> getItemsInfoMap() {
		return itemsInfoMap;
	}
	
	public List<ItemProperty> findAllItem(){
		List<ItemProperty> list = new ArrayList<ItemProperty>();
		for(Entry<Integer,ItemProperty> entry : this.itemsInfoMap.entrySet()){
			list.add(entry.getValue());
		}
		Collections.sort(list,new Comparator<ItemProperty>(){
			public int compare(ItemProperty a, ItemProperty b){
				return b.getId()-a.getId();
			}
		});
		return list;
	}
	
}
