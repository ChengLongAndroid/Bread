package com.funcy.g01.base.bo.fight;

import com.funcy.g01.base.data.FightSkillProperty;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.UnitSkillProto;

public class FightSkill {

	private final FightSkillProperty fightSkillProperty;
	
	private final int level;
	
	private int useCount = 0;
	
	private long continueEndTime = 0;
	
	public FightSkill(FightSkillProperty fightSkillProperty, int level) {
		this.fightSkillProperty = fightSkillProperty;
		this.level = level;
	}

	public FightSkillProperty getFightSkillProperty() {
		return fightSkillProperty;
	}

	public int getLevel() {
		return level;
	}

	public UnitSkillProto.Builder buildProto() {
		UnitSkillProto.Builder builder = UnitSkillProto.newBuilder();
		builder.setSkillLevel(this.level);
		builder.setSkillXmlId(this.fightSkillProperty.getId());
		if(this.useCount != 0) {
			builder.setUseCount(this.useCount);
		}
		if (this.continueEndTime != 0) {
			builder.setContinueEndTime(this.continueEndTime);
		}
		return builder;
	}
	
	public float getParam1() {
		return this.fightSkillProperty.getLevelParams1().get(this.level - 1);
	}
	
	public float getParam2() {
		return this.fightSkillProperty.getLevelParams2().get(this.level - 1);
	}

	public int getUseCount() {
		return useCount;
	}

	public void setUseCount(int useCount) {
		this.useCount = useCount;
	}
	
	public void incUseCount() {
		this.useCount++;
	}

	public void resetUseCount() {
		this.useCount = 0;
		this.continueEndTime = 0;
	}

	public long getContinueEndTime() {
		return continueEndTime;
	}

	public void setContinueEndTime(long continueEndTime) {
		this.continueEndTime = continueEndTime;
	}
}
