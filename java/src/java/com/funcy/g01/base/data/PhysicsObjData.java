package com.funcy.g01.base.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.fight.CampType;
import com.funcy.g01.base.bo.fight.PhysicsObjSpecType;
import com.funcy.g01.base.bo.fight.PhysicsObjType;
import com.funcy.g01.base.util.DocumentBuilder;

@Component
public class PhysicsObjData {
	
	private static final Logger logger = Logger.getLogger(PhysicsObjData.class);
	
	private Map<Integer, PhysicsObjProperty> objsMap = new HashMap<Integer, PhysicsObjProperty>();

	public void init() {
		String url = "com/funcy/g01/xml/physicsObjs.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				String name = e.elementText("name");
				int typeInt = Integer.parseInt(e.elementText("type"));
				String battleResultStr = e.elementText("battleResultId");
				int battleResultId = -1;
				if (battleResultStr != "" && battleResultStr != null) {
					battleResultId = Integer.parseInt(battleResultStr);
				}
				
				PhysicsObjSpecType physicsObjSpecType = PhysicsObjSpecType.getByCode(Integer.parseInt(e.elementText("specType")));
				PhysicsObjType type = PhysicsObjType.getTypeByCode(typeInt);
				int shape = Integer.parseInt(e.elementText("shape"));
				String sizeStr = e.elementText("size");
				float[] size = null;
				if(shape == 1) { //圆形
					size = new float[1];
					size[0] = Float.parseFloat(sizeStr);
				} else if(shape == 2) {
					size = new float[2];
					String[] temp = sizeStr.split(",");
					float width = Float.parseFloat(temp[0]);
					float height = Float.parseFloat(temp[1]);
					size[0] = width;
					size[1] = height;
				}
				float density = Float.parseFloat(e.elementText("density"));
				float friction = Float.parseFloat(e.elementText("friction"));
				float restitution = Float.parseFloat(e.elementText("restitution"));
				String initVStr = e.elementText("initV");
				float[] initV = new float[2];
				if(initVStr != null && !"".equals(initVStr)) {
					String[] temp = initVStr.split(",");
					initV[0] = Float.parseFloat(temp[0]);
					initV[1] = Float.parseFloat(temp[1]);
				}
				int physicsCategory = Integer.parseInt(e.elementText("physicsCategory"));
				int physicsMask = Integer.parseInt(e.elementText("physicsContactMask"));
				int campTypeCode = Integer.parseInt(e.elementText("campType"));
				boolean initObjTure = Boolean.parseBoolean(e.elementText("initObjTure"));
				PhysicsObjProperty physicsObjProperty = new PhysicsObjProperty(id, name, physicsObjSpecType, type
						, shape, size, density, friction, restitution, initV
						, physicsCategory, physicsMask, battleResultId, CampType.getCampTypeByCode(campTypeCode), initObjTure);
				this.objsMap.put(id, physicsObjProperty);
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init "+ url +" fail");
			e.printStackTrace();
		}
	}
	
	public PhysicsObjProperty getPhysicsObjProperty(int xmlId) {
		return this.objsMap.get(xmlId);
	}
	
}
