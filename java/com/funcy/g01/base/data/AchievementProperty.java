package com.funcy.g01.base.data;

import java.util.List;

import com.funcy.g01.base.bo.BoFactory;
import com.funcy.g01.base.bo.achieve.AchievementCategory;
import com.funcy.g01.base.bo.achieve.AchievementType;
import com.funcy.g01.base.bo.item.Item;


public class AchievementProperty {
	
	private final int id;
	
	private final String name;
	
	private final AchievementType type;
	
	private final AchievementCategory category;
	
	private final int gradeParam1;
	
	private final String gradeParam2;
	
	private final int grade;
	
	private final int awardCheese;
	
	private final int achievePoint;
	
	private final int nextGradeXmlId;
	
	public AchievementProperty(int id, String name, AchievementType type, AchievementCategory category, int gradeParam1, String gradeParam2, int grade, int awardCheese, int achievePoint, int nextGradeXmlId){
		this.id = id;
		this.name = name;
		this.type = type;
		this.category = category;
		this.gradeParam1 = gradeParam1;
		this.gradeParam2 = gradeParam2;
		this.grade = grade;
		this.awardCheese = awardCheese;
		this.achievePoint = achievePoint;
		this.nextGradeXmlId = nextGradeXmlId;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public AchievementType getType() {
		return type;
	}

	public int getGradeParam1() {
		return gradeParam1;
	}

	public String getGradeParam2() {
		return gradeParam2;
	}

	public int getGrade() {
		return grade;
	}

	public int getAwardCheese() {
		return awardCheese;
	}

	public int getAchievePoint() {
		return achievePoint;
	}

	public int getNextGradeXmlId() {
		return nextGradeXmlId;
	}

	public AchievementCategory getCategory() {
		return category;
	}

}
