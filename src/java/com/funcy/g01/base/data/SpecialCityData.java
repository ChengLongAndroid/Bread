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
public class SpecialCityData {
	
	private static final Logger logger = Logger.getLogger(SpecialCityData.class);
	
	private Map<String, SpecialCityProperty> cities = new HashMap<String, SpecialCityProperty>();
	
	public void init(){
		String url = "com/funcy/g01/xml/specialCities.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				String province = e.elementText("province");
				String city = e.elementText("city");
				SpecialCityProperty specialCityProperty = new SpecialCityProperty(id, province, city);
				cities.put(city, specialCityProperty);
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	public boolean isSpecialCity(String province, String city){
		SpecialCityProperty specialCityProperty = cities.get(city);
		if(specialCityProperty!=null && specialCityProperty.getProvince().equals(province)){
			return true;
		}
		return false;
	}
}
