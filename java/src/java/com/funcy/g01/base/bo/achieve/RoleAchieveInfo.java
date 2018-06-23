package com.funcy.g01.base.bo.achieve;

import java.util.ArrayList;
import java.util.List;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.data.AchievementData;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.AchievementProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleAchievementInfoProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class RoleAchieveInfo implements ProtobufSerializable {
	
	
	private long roleId;
	
	private List<Achievement> achievements = null;
	
	private AchievementData achievementData;
	
	private int growupStar;
	
	private int promotionStar;
	
	private int growupGrade;
	
	private int promotionGrade;
	
	@SuppressWarnings("unused")
	private RoleAchieveInfo() {
	}
	
	public RoleAchieveInfo(long roleId, AchievementData achievementData){
		this.roleId = roleId;
		this.achievementData = achievementData;
		this.growupStar = 0;
		this.promotionStar = 0;
		this.growupGrade = 1;
		this.promotionGrade = 1;
		achievements = new ArrayList<Achievement>();
		for (AchievementType achievementType : AchievementType.values()) {
			achievements.add(new Achievement(achievementData.getAchievementProperty(achievementData.getAchievementTypeInitXmlId(achievementType))));
		}
	}
	
	public RoleAchieveInfo(byte[] bytes, AchievementData achievementData){
		this.achievementData = achievementData;
		parseFrom(bytes);
	}
	
	public boolean checkNewAchievements(){
		boolean isNewAdd = false;
		if (AchievementType.values().length > achievements.size()) {
			for (AchievementType achievementType : AchievementType.values()) {
				if (this.findAchievementByType(achievementType) == null) {
					achievements.add(new Achievement(achievementData.getAchievementProperty(achievementData.getAchievementTypeInitXmlId(achievementType))));
					isNewAdd = true;
				}
			}
		}
		return isNewAdd;
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleAchievementInfoProto proto = (RoleAchievementInfoProto) message;
		this.roleId = proto.getRoleId();
		this.growupStar = proto.getGrowupStar();
		this.promotionStar = proto.getPromotionStar();
		this.growupGrade = proto.getGrowupGrade();
		this.promotionGrade = proto.getPromotionGrade();
		this.achievements = new ArrayList<Achievement>() ;
		for (AchievementProto achievementProto : proto.getAchievementsList()) {
			this.achievements.add(new Achievement(this.achievementData.getAchievementProperty(achievementProto.getXmlId()), achievementProto.getCompleteness(),AchievementState.getTypeByValue(achievementProto.getState())));
		}
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleAchievementInfoProto.Builder buidler = RoleAchievementInfoProto.newBuilder();
		buidler.setRoleId(roleId);
		for (Achievement achievement : achievements) {
			buidler.addAchievements(achievement.copyTo());
		}
		buidler.setGrowupStar(growupStar);
		buidler.setPromotionStar(promotionStar);
		buidler.setGrowupGrade(growupGrade);
		buidler.setPromotionGrade(promotionGrade);
		return buidler.build();
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleAchievementInfoProto roleAchievementInfoProto = RoleAchievementInfoProto.parseFrom(bytes);
			copyFrom(roleAchievementInfoProto);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}
	
	public Achievement findAchievementByType(AchievementType type){
		for (Achievement achievement : achievements) {
			if (achievement.getType() == type) {
				return achievement;
			}
		}
		return null;
	}
	
	public Achievement findAchievementByXmlId(int xmlId){
		for (Achievement achievement : achievements) {
			if (achievement.getXmlId() == xmlId) {
				return achievement;
			}
		}
		return null;
	}
	
	public void claimAchievement(Achievement achievement){
		if (achievement.getAchievementProperty().getCategory() == AchievementCategory.grow_up) {
			this.growupStar += achievement.getAchievementProperty().getGrade();
		}else if (achievement.getAchievementProperty().getCategory() == AchievementCategory.promotion){
			this.promotionStar += achievement.getAchievementProperty().getGrade();
		}
		if (achievement.getAchievementProperty().getNextGradeXmlId() == -1) {
			achievement.setState(AchievementState.completed);
		}else{
			achievement.setState(AchievementState.running);
			achievement.setXmlId(achievement.getAchievementProperty().getNextGradeXmlId());
			achievement.setAchievementProperty(achievementData.getAchievementProperty(achievement.getAchievementProperty().getNextGradeXmlId()));
			achievement.checkCanRecieve();
		}
	}

	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}

	public long getRoleId() {
		return roleId;
	}

	public int getGrowupStar() {
		return growupStar;
	}

	public void setGrowupStar(int growupStar) {
		this.growupStar = growupStar;
	}

	public int getPromotionStar() {
		return promotionStar;
	}

	public void setPromotionStar(int promotionStar) {
		this.promotionStar = promotionStar;
	}

	public int getGrowupGrade() {
		return growupGrade;
	}

	public void setGrowupGrade(int growupGrade) {
		this.growupGrade = growupGrade;
	}

	public int getPromotionGrade() {
		return promotionGrade;
	}

	public void setPromotionGrade(int promotionGrade) {
		this.promotionGrade = promotionGrade;
	}
}
