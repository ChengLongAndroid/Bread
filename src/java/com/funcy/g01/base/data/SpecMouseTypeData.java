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

import com.funcy.g01.base.util.DocumentBuilder;

@Component
public class SpecMouseTypeData {
	
	private Map<Integer, SpecMouseTypeProperty> specMouseTypeMap = new HashMap<Integer, SpecMouseTypeProperty>();
	
	private Logger logger = Logger.getLogger(SpecMouseTypeData.class);
	
	public void init() {
		String url = "com/funcy/g01/xml/specMouseTypes.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				int openSamanLevel = Integer.parseInt(e.elementText("openSamanLevel"));
				int limitSamanLevel = Integer.parseInt(e.elementText("limitSamanLevel"));
				List<Integer> limitNails = new ArrayList<Integer>();
				String limitNailsStr = e.elementText("limitNails");
				for(String limitNailStr : limitNailsStr.split(",")) {
					if(limitNails == null || "".equals(limitNailStr)) {
						continue;
					}
					limitNails.add(Integer.parseInt(limitNailStr));
				}
				int perHelpPoint = Integer.parseInt(e.elementText("perHelpPoint"));
				SpecMouseTypeProperty specMouseTypeProperty = new SpecMouseTypeProperty(id, 
						openSamanLevel, limitSamanLevel, limitNails,perHelpPoint);
				this.specMouseTypeMap.put(id, specMouseTypeProperty);
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	public SpecMouseTypeProperty getSpecMouseTypeProperty(int id) {
		return this.specMouseTypeMap.get(id);
	}
	
}
