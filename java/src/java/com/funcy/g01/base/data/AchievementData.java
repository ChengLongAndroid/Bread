package com.funcy.g01.base.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.achieve.AchievementCategory;
import com.funcy.g01.base.bo.achieve.AchievementType;
import com.funcy.g01.base.util.DocumentBuilder;

@Component
public class AchievementData {
	
	private static final Logger logger  = Logger.getLogger(AchievementData.class);
	
	private Map<Integer, AchievementProperty> achievementsInfoMap = new HashMap<Integer, AchievementProperty>();
	
	private Map<Integer, AchievementStarRewardProperty> growupStarRewardMap = new HashMap<Integer, AchievementStarRewardProperty>();
	
	private Map<Integer, AchievementStarRewardProperty> promotionMap = new HashMap<Integer, AchievementStarRewardProperty>();
	
	private Map<AchievementType,Integer> achievementTypeInitXmlIdMap = new HashMap<AchievementType, Integer>();
	
	public void init(){
		this.initAchievements();
		this.initAchievementStarReward();
	}
	public void initAchievements() {
		String url = "com/funcy/g01/xml/achievements.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				String name = e.elementText("name");
				AchievementType type = AchievementType.getTypeByValue(Integer.parseInt(e.elementText("type")));
				boolean isInitXmlId = Boolean.parseBoolean(e.elementText("isInitXmlId"));
				if (isInitXmlId) {
					achievementTypeInitXmlIdMap.put(type, id);
				}
				AchievementCategory category = AchievementCategory.getTypeByValue(Integer.parseInt(e.elementText("category")));
				int gradeParam1 = Integer.parseInt(e.elementText("gradeParam1"));
				String gradeParam2 = e.elementText("gradeParam2");
				int grade = Integer.parseInt(e.elementText("grade"));
				int awardCheese = Integer.parseInt(e.elementText("awardCheese"));
				int achievePoint = Integer.parseInt(e.elementText("achievePoint"));
				int nextGradeXmlId = Integer.parseInt(e.elementText("nextGradeXmlId"));
				this.achievementsInfoMap.put(id, new AchievementProperty(id, name, type, category, gradeParam1, gradeParam2, grade, awardCheese, achievePoint, nextGradeXmlId));
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	public AchievementProperty getAchievementProperty(int xmlId) {
		return this.achievementsInfoMap.get(xmlId);
	}
	public void initAchievementStarReward() {
		String url = "com/funcy/g01/xml/achievementStarRewards.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int star = Integer.parseInt(e.elementText("star"));
				String reward = e.elementText("reward");
				AchievementCategory category = AchievementCategory.getTypeByValue(Integer.parseInt(e.elementText("category")));
				int grade = Integer.parseInt(e.elementText("grade"));
				AchievementStarRewardProperty achievementStarRewardProperty = new AchievementStarRewardProperty(star, category, grade, reward);
				if (category == AchievementCategory.grow_up) {
					this.growupStarRewardMap.put(grade, achievementStarRewardProperty);
				}else{
					this.promotionMap.put(grade, achievementStarRewardProperty);
				}
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	public AchievementStarRewardProperty getAchievementGrowupStarRewardProperty(int grade) {
		return this.growupStarRewardMap.get(grade);
	}
	
	public AchievementStarRewardProperty getAchievementPromotionStarRewardProperty(int grade) {
		return this.promotionMap.get(grade);
	}
	
	public int getAchievementTypeInitXmlId(AchievementType type){
		return this.achievementTypeInitXmlIdMap.get(type);
	}
	
	public Map<Integer, AchievementStarRewardProperty> getGrowupStarRewardMap() {
		return growupStarRewardMap;
	}
	public void setGrowupStarRewardMap(Map<Integer, AchievementStarRewardProperty> growupStarRewardMap) {
		this.growupStarRewardMap = growupStarRewardMap;
	}
	public Map<Integer, AchievementStarRewardProperty> getPromotionMap() {
		return promotionMap;
	}
	public void setPromotionMap(Map<Integer, AchievementStarRewardProperty> promotionMap) {
		this.promotionMap = promotionMap;
	}
	
}
