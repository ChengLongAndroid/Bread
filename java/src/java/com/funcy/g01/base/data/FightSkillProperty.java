package com.funcy.g01.base.data;

import java.util.Collections;
import java.util.List;

import com.funcy.g01.base.bo.fight.FightSkillEffectType;
import com.funcy.g01.base.bo.fight.FightSkillInfluenceObjType;
import com.funcy.g01.base.bo.fight.FightSkillTriggerType;

public class FightSkillProperty {

	private final int id;
	
	private final String name;
	
	private final String desc;
	
	private final List<Float> levelParams1;
	
	private final List<Float> levelParams2;
	
	private final FightSkillTriggerType fightSkillTriggerType;
	
	private final FightSkillEffectType fightSkillEffectType;
	
	private final FightSkillInfluenceObjType fightSkillInfluenceObjType;

	public FightSkillProperty(int id, String name, String desc,
			List<Float> levelParams1, List<Float> levelParams2, FightSkillTriggerType fightSkillTriggerType, FightSkillEffectType fightSkillEffectType, FightSkillInfluenceObjType fightSkillInfluenceObjType) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.levelParams1 = Collections.unmodifiableList(levelParams1);
		this.levelParams2 = Collections.unmodifiableList(levelParams2);
		this.fightSkillTriggerType = fightSkillTriggerType;
		this.fightSkillEffectType = fightSkillEffectType;
		this.fightSkillInfluenceObjType = fightSkillInfluenceObjType;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public FightSkillTriggerType getFightSkillTriggerType() {
		return fightSkillTriggerType;
	}

	public FightSkillEffectType getFightSkillEffectType() {
		return fightSkillEffectType;
	}

	public List<Float> getLevelParams1() {
		return levelParams1;
	}

	public List<Float> getLevelParams2() {
		return levelParams2;
	}

	public FightSkillInfluenceObjType getFightSkillInfluenceObjType() {
		return fightSkillInfluenceObjType;
	}
	
}
