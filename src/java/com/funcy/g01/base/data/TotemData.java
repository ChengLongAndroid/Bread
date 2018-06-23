package com.funcy.g01.base.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.util.DocumentBuilder;

@Component
public class TotemData {
	
	private static final Logger logger  = Logger.getLogger(TotemData.class);
	
	private Map<Integer, TotemProperty> totemsInfoMap = new HashMap<Integer, TotemProperty>();
	
	public void init() {
		String url = "com/funcy/g01/xml/totems.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				String name = e.elementText("name");
				int type = Integer.parseInt(e.elementText("type"));
				int index = Integer.parseInt(e.elementText("index"));
				int openLv = Integer.parseInt(e.elementText("openLv"));
				boolean enabled = Boolean.parseBoolean(e.elementText("enabled"));
				this.totemsInfoMap.put(id, new TotemProperty(id, name, type, index, enabled, openLv));
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	public TotemProperty getTotemProperty(int xmlId) {
		return this.totemsInfoMap.get(xmlId);
	}
	
	public Map<Integer, TotemProperty> getTotemsInfoMap(){
		return this.totemsInfoMap;
	}
	
}
