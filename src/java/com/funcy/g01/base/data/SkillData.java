package com.funcy.g01.base.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.BoFactory;
import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.bo.totem.TotemType;
import com.funcy.g01.base.util.DocumentBuilder;

@Component
public class SkillData {
	
	private static final Logger logger  = Logger.getLogger(SkillData.class);
	
	
	public static int fire_final_skill_id = 11701;
	
	public static int wing_final_skill_id = 12701;
	
	public static int water_final_skill_id = 13701;
	
	private Map<Integer, SkillProperty> skillsInfoMap = new HashMap<Integer, SkillProperty>();
	
	public void init(FightSkillData fightSkillData) {
		String url = "com/funcy/g01/xml/skills.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				String name = e.elementText("name");
				String useObj = e.elementText("useObj");
				String fightSkillIdStr = e.elementText("fightSkillId");
				int fightSkillId = 0;
				if (!fightSkillIdStr.equals("")) {
					fightSkillId = Integer.parseInt(fightSkillIdStr);
				}
				if(fightSkillData.findFightSkillProperty(fightSkillId) == null) {
					System.out.println(id);
				}
				int type = Integer.parseInt(e.elementText("type"));
				int totemId = Integer.parseInt(e.elementText("totemId"));
				int initstar = Integer.parseInt(e.elementText("initstar"));
				boolean isFinal = Boolean.parseBoolean(e.elementText("isFinal"));
				if (isFinal) {
					if (type == TotemType.fire.getCode()) {
						fire_final_skill_id = id;
					}else if (type == TotemType.wind.getCode()){
						wing_final_skill_id = id;
					}else if (type == TotemType.water.getCode()){
						water_final_skill_id = id;
					}
				}
				List<Integer> needCheese = new ArrayList<Integer>();
				String needCheeseStr = e.elementText("needCheese");
				if (!needCheeseStr.equals("")) {
					String[] needCheeseString = needCheeseStr.split(",");
					for (int i = 0; i < needCheeseString.length; i++) {
						String string = needCheeseString[i];
						needCheese.add(Integer.parseInt(string));
					}
				}
				List<String> upstarItems = new ArrayList<String>();
				for (int i = 0; i < 5; i++) {
					String upstarItem = e.elementText("upstarItem"+i);
					upstarItems.add(upstarItem);
				}
				List<Item> convertItems = BoFactory.createMultiItems(e.elementText("convertItems"));
				this.skillsInfoMap.put(id, new SkillProperty(id, type, name, totemId, needCheese, upstarItems, initstar, isFinal, useObj, fightSkillId, convertItems));
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	public SkillProperty getSkillProperty(int xmlId) {
		return this.skillsInfoMap.get(xmlId);
	}

	public Map<Integer, SkillProperty> getSkillsInfoMap() {
		return skillsInfoMap;
	}
	
	public List<SkillProperty> findAllItem(){
		List<SkillProperty> list = new ArrayList<SkillProperty>();
		for(Entry<Integer,SkillProperty> entry : this.skillsInfoMap.entrySet()){
			list.add(entry.getValue());
		}
		Collections.sort(list,new Comparator<SkillProperty>(){
			public int compare(SkillProperty a, SkillProperty b){
				return b.getId()-a.getId();
			}
		});
		return list;
	}
	
	public int getFinalSkillIdByType(int type){
		if (type == TotemType.fire.getCode()) {
			return fire_final_skill_id;
		}else if (type == TotemType.wind.getCode()){
			return wing_final_skill_id;
		}else if (type == TotemType.water.getCode()){
			return water_final_skill_id;
		}
		return 0;
	}
	
}
