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

import com.funcy.g01.base.bo.BoFactory;
import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.util.DocumentBuilder;

@Component
public class DressData {
	
	private static final Logger logger  = Logger.getLogger(DressData.class);
	
	private Map<Integer, DressProperty> dressesInfoMap = new HashMap<Integer, DressProperty>();
	
	public void init() {
		String url = "com/funcy/g01/xml/dresss.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				String name = e.elementText("name");
				int quality = Integer.parseInt(e.elementText("quality"));
				int type = Integer.parseInt(e.elementText("type"));
				boolean isDefault = Boolean.parseBoolean(e.elementText("isDefault"));
				List<Item> convertItems = BoFactory.createMultiItems(e.elementText("convertItems"));
				this.dressesInfoMap.put(id, new DressProperty(id, name, quality, type, isDefault,convertItems));
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	public List<DressProperty> findAllItem(){
		List<DressProperty> list = new ArrayList<DressProperty>();
		for(Entry<Integer,DressProperty> entry : this.dressesInfoMap.entrySet()){
			list.add(entry.getValue());
		}
		Collections.sort(list,new Comparator<DressProperty>(){
			public int compare(DressProperty a, DressProperty b){
				return b.getId()-a.getId();
			}
		});
		return list;
	}
	
	public DressProperty getDressProperty(int xmlId) {
		return this.dressesInfoMap.get(xmlId);
	}

	public Map<Integer, DressProperty> getDressesInfoMap() {
		return dressesInfoMap;
	}
	
}
