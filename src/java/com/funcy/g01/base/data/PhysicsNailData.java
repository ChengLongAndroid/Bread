package com.funcy.g01.base.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.fight.MotorDirectionType;
import com.funcy.g01.base.util.DocumentBuilder;

@Component
public class PhysicsNailData {

	private static final Logger logger = Logger.getLogger(PhysicsNailData.class);
	
	private Map<Integer, PhysicsNailProperty> nailsMap = new HashMap<Integer, PhysicsNailProperty>();

	public void init() {
		String url = "com/funcy/g01/xml/nails.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				String name = e.elementText("name");
				boolean canRotate = Boolean.parseBoolean(e.elementText("canRotate"));
				int connectObjType = Integer.parseInt(e.elementText("connectObjType"));
				float radius = Float.parseFloat(e.elementText("radius"));
				String motorDirectionStr = e.elementText("motorDirection");
				MotorDirectionType motorDirectionType = MotorDirectionType.valueOf(motorDirectionStr);
				PhysicsNailProperty physicsNailProperty = new PhysicsNailProperty(id, name, canRotate, connectObjType, radius, motorDirectionType);
				nailsMap.put(id, physicsNailProperty);
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init "+ url +" fail");
			e.printStackTrace();
		}
	}
	
	public PhysicsNailProperty getPhysicsNailProperty(int xmlId) {
		return this.nailsMap.get(xmlId);
	}
	
}
