package com.funcy.g01.base.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.BoFactory;
import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.util.DocumentBuilder;

@Component
public class CheckpointData {

	private Map<Integer, CheckpointProperty> checkpoints = new HashMap<Integer, CheckpointProperty>();
	
	private Logger logger = Logger.getLogger(CheckpointData.class);
	
	public void init() {
		String url = "com/funcy/g01/xml/checkpoints.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				String name = e.elementText("name");
				List<Item> awards;
				String awardsStr = e.elementText("awards");
				if(awardsStr != null && !"".equals(awardsStr)) {
					awards = BoFactory.createMultiItems(awardsStr);
				} else {
					awards = Collections.EMPTY_LIST;
				}
				CheckpointProperty checkpointProperty = new CheckpointProperty(id, name, awards);
				checkpoints.put(id, checkpointProperty);
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	public int getCheckpointNum() {
		return this.checkpoints.size();
	}
	
	public CheckpointProperty getCheckpointProperty(int xmlId) {
		return this.checkpoints.get(xmlId);
	}
	
}
