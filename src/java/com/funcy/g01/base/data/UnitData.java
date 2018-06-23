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
public class UnitData {
	
	private static final Logger logger  = Logger.getLogger(UnitData.class);
	
	private Map<Integer, UnitProperty> unitsInfoMap = new HashMap<Integer, UnitProperty>();
	
	public void init() {
		String url = "com/funcy/g01/xml/units.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				String name = e.elementText("name");
				float density = Float.parseFloat(e.elementText("density"));
				float radius = Float.parseFloat(e.elementText("radius"));
				float friction = Float.parseFloat(e.elementText("friction"));
				float restitution = Float.parseFloat(e.elementText("restitution"));
				int physicsCategory = Integer.parseInt(e.elementText("physicsCategory"));
				int physicsContactMask = Integer.parseInt(e.elementText("physicsContactMask"));
				this.unitsInfoMap.put(id, new UnitProperty(id, density, radius, friction, restitution, physicsCategory, physicsContactMask));
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	public UnitProperty getUnitProperty(int xmlId) {
		return this.unitsInfoMap.get(xmlId);
	}
	
}
