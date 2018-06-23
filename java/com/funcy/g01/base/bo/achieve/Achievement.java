package com.funcy.g01.base.bo.achieve;

import com.funcy.g01.base.data.AchievementProperty;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.AchievementProto;

public class Achievement {

	private AchievementType type;

	private int xmlId;
	
	private int completeness;
	
	private AchievementState state;
	
	private AchievementProperty achievementProperty;
	
	@SuppressWarnings("unused")
	private Achievement(){
		
	}
	
	public Achievement(AchievementProperty achievementProperty){
		this.achievementProperty = achievementProperty;
		this.type = achievementProperty.getType();
		this.xmlId = achievementProperty.getId();
		this.completeness = 0;
		this.state = AchievementState.running;
	}
	public Achievement(AchievementProperty achievementProperty, int completeness, AchievementState state){
		this.achievementProperty = achievementProperty;
		this.xmlId = achievementProperty.getId();
		this.type = achievementProperty.getType();
		this.completeness = completeness;
		this.state = state;
	}
	
	public AchievementProto copyTo(){
		AchievementProto.Builder builder = AchievementProto.newBuilder();
		builder.setXmlId(xmlId);
		builder.setCompleteness(completeness);
		builder.setState(state.getValue());
		return builder.build();
	}
	
	public void checkCanRecieve(){
		if (this.state == AchievementState.running) {
			if (this.completeness >= this.achievementProperty.getGradeParam1()) {
				this.state = AchievementState.recieve;
			}
		}
	}

	public int getXmlId() {
		return xmlId;
	}

	public void setXmlId(int xmlId) {
		this.xmlId = xmlId;
	}

	public AchievementType getType() {
		return type;
	}

	public void setType(AchievementType type) {
		this.type = type;
	}

	public int getCompleteness() {
		return completeness;
	}

	public void setCompleteness(int completeness) {
		if (completeness > this.achievementProperty.getGradeParam1()) {
			completeness = this.achievementProperty.getGradeParam1();
		}
		this.completeness = completeness;
	}

	public AchievementProperty getAchievementProperty() {
		return achievementProperty;
	}

	public void setAchievementProperty(AchievementProperty achievementProperty) {
		this.achievementProperty = achievementProperty;
	}

	public AchievementState getState() {
		return state;
	}

	public void setState(AchievementState state) {
		this.state = state;
	}
}
