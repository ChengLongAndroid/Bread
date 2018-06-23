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
public class PhysicsConfigData {
	
	private static final Logger logger = Logger.getLogger(PhysicsConfigData.class);
	
	private Map<Integer, PhysicsConfigProperty> typeConfigMap = new HashMap<Integer, PhysicsConfigProperty>();

	public void init() {
		String url = "com/funcy/g01/xml/physicsConfigs.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				float gravityY = Float.parseFloat(e.elementText("gravityY"));
				float jumpTime = Float.parseFloat(e.elementText("jumpTime"));
				float moveSpeed = Float.parseFloat(e.elementText("moveSpeed"));
				float initMoveSpeed = Float.parseFloat(e.elementText("initMoveSpeed"));
				float moveSpeedA = Float.parseFloat(e.elementText("moveSpeedA"));
				float conjureObjDelay = Float.parseFloat(e.elementText("conjureObjDelay"));
				float bombImpulse = Float.parseFloat(e.elementText("bombImpulse"));
				PhysicsConfigProperty physicsConfigProperty = new PhysicsConfigProperty(id
						, gravityY, jumpTime, moveSpeed, initMoveSpeed, moveSpeedA
						, conjureObjDelay, bombImpulse);
				typeConfigMap.put(id, physicsConfigProperty);
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init "+ url +" fail");
			e.printStackTrace();
		}
	}
	
	public PhysicsConfigProperty getPhysicsConfigProperty(int id) {
		return this.typeConfigMap.get(id);
	}
	
}
