package com.funcy.g01.base.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.BoFactory;
import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.util.DocumentBuilder;

@Component
public class DropGroupData {
	private final Logger logger = Logger.getLogger(DropGroupData.class);
	
	private Map<Integer, DropGroupProperty> dropGroupPropertyMap = new HashMap<Integer, DropGroupProperty>();
	
	@Autowired
	private BoFactory boFactory;
	
	private DropGroupData() {
		logger.info("DropGroupData init success");
	}

	public void init() {
		try {
			List<Element> elements = DocumentBuilder.build("com/funcy/g01/xml/dropGroup.xml");
			
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				
				List<Item> items = new ArrayList<Item>();
				List<Integer> weightArray = new ArrayList<Integer>();
				for (int i = 0; i <= 100; i++) {
					String itemStr = e.elementText("item" + i);
					if(itemStr == null || "".equals(itemStr)) {
						continue;
					}
					Item item = BoFactory.createSingleItem(itemStr);
					items.add(item);
					
					int weight = Integer.parseInt(e.elementText("weight" + i));
					weightArray.add(weight);
				}
				
				int[] weights = new int[weightArray.size()];
				int index = 0;
				for(int weight : weightArray) {
					weights[index] = weight;
					index++;
				}
				DropGroupProperty property = new DropGroupProperty(id, items, weights);
				dropGroupPropertyMap.put(id, property);
			}
			logger.info("init com/funcy/g01/xml/dropGroup.xml success!");

		} catch (DocumentException e) {
			logger.error("init com/funcy/g01/xml/dropGroup.xml fail!");
			e.printStackTrace();
		}
	}

	public DropGroupProperty findDropGroupProperty(int dropGroupXmlId) {
		return this.dropGroupPropertyMap.get(dropGroupXmlId);
	}

}
