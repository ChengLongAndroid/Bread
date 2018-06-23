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
public class RoleData {
	
	private static final Logger logger  = Logger.getLogger(RoleData.class);

	public static final int max_level = 120;
	
	public static final int max_shamman_level = 10;
	
	private Map<Integer, RoleProperty> rolePropertyMap = new HashMap<Integer, RoleProperty>();
	
	private Map<Integer, ShammanProperty> shammanPropertyMap = new HashMap<Integer, ShammanProperty>();
	
	public void init() {
		this.initRoleLevelUp();
		this.initShammanLevelUp();
	}
	public void initRoleLevelUp() {
		try {
			List<Element> elements = DocumentBuilder.build("com/funcy/g01/xml/roleLevelUp.xml");
			for (Element e : elements) {
				int level = Integer.parseInt(e.elementText("level"));
				int maxWeekExp = Integer.parseInt(e.elementText("maxWeekExp"));
				int maxWeekAddExp = Integer.parseInt(e.elementText("maxWeekAddExp"));
				int maxWeekCheese = Integer.parseInt(e.elementText("maxWeekCheese"));
				int needExp = -1;
				if(e.elementText("needExp")!=null&&!"".equals(e.elementText("needExp"))){
					needExp = Integer.parseInt(e.elementText("needExp"));
				}
				RoleProperty property = new RoleProperty(level, needExp, maxWeekExp, maxWeekAddExp, maxWeekCheese);
				rolePropertyMap.put(level, property);
			}
			logger.info("init com/funcy/g01/xml/roleLevelUp.xml success");
		} catch (DocumentException e) {
			logger.error("init com/funcy/g01/xml/roleLevelUp.xml fail");	
			e.printStackTrace();
		}
	}
	public RoleProperty findRoleLevelUpProperty(int level) {
		return this.rolePropertyMap.get(level);
	}
	public void initShammanLevelUp() {
		try {
			List<Element> elements = DocumentBuilder.build("com/funcy/g01/xml/shammanLevelUp.xml");
			for (Element e : elements) {
				int level = Integer.parseInt(e.elementText("id"));
				int needExp = -1;
				if(e.elementText("needExp")!=null&&!"".equals(e.elementText("needExp"))){
					needExp = Integer.parseInt(e.elementText("needExp"));
				}
				
				ShammanProperty property = new ShammanProperty(level, needExp);
				shammanPropertyMap.put(level, property);
			}
			logger.info("init com/funcy/g01/xml/shammanLevelUp.xml success");
		} catch (DocumentException e) {
			logger.error("init com/funcy/g01/xml/shammanLevelUp.xml fail");	
			e.printStackTrace();
		}
	}
	public ShammanProperty findShammanLevelUpProperty(int level) {
		return this.shammanPropertyMap.get(level);
	}
}
