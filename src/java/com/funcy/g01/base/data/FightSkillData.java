package com.funcy.g01.base.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.BoFactory;
import com.funcy.g01.base.bo.fight.FightSkillEffectType;
import com.funcy.g01.base.bo.fight.FightSkillInfluenceObjType;
import com.funcy.g01.base.bo.fight.FightSkillTriggerType;
import com.funcy.g01.base.util.DocumentBuilder;

@Component
public class FightSkillData {
	private final Logger logger = Logger.getLogger(FightSkillData.class);
	
	private Map<Integer, FightSkillProperty> fightSkillMap = new HashMap<Integer, FightSkillProperty>();
	
	@Autowired
	private BoFactory boFactory;
	
	private FightSkillData() {
		logger.info("FightSkillData init success");
	}

	public void init() {
		try {
			List<Element> elements = DocumentBuilder.build("com/funcy/g01/xml/fightSkills.xml");
			
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				String name = e.elementText("name");
				String desc = e.elementText("desc");
				List<Float> levelParams1 = new ArrayList<Float>();
				String temp1 = e.elementText("levelParams1");
				String[] tempStrs1 = temp1.split(",");
				for(String str : tempStrs1) {
					if("".equals(str)) {
						continue;
					}
					levelParams1.add(Float.parseFloat(str));
				}
				
				List<Float> levelParams2 = new ArrayList<Float>();
				String temp2 = e.elementText("levelParams2");
				String[] tempStrs2 = temp2.split(",");
				for(String str : tempStrs2) {
					if("".equals(str)) {
						continue;
					}
					levelParams2.add(Float.parseFloat(str));
				}
				String triggerTypeStr = e.elementText("triggerType");
				String influenceObjTypeStr = e.elementText("influenceObjType");
				FightSkillProperty fightSkillProperty = new FightSkillProperty(id, name, desc, levelParams1, levelParams2, FightSkillTriggerType.valueOf(triggerTypeStr), FightSkillEffectType.getFightSkillEffectTypeByXmlId(id), FightSkillInfluenceObjType.valueOf(influenceObjTypeStr));
				this.fightSkillMap.put(id, fightSkillProperty);
			}
			logger.info("init com/funcy/g01/xml/fightSkills.xml success!");

		} catch (DocumentException e) {
			logger.error("init com/funcy/g01/xml/fightSkills.xml fail!");
			e.printStackTrace();
		}
	}

	public FightSkillProperty findFightSkillProperty(int skillXmlId) {
		return this.fightSkillMap.get(skillXmlId);
	}
}
