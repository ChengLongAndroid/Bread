package com.funcy.g01.hall.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebo.synframework.synroom.tools.ConcurrentHashMap;
import com.funcy.g01.base.bo.BoFactory;
import com.funcy.g01.base.bo.achieve.Achievement;
import com.funcy.g01.base.bo.achieve.AchievementCategory;
import com.funcy.g01.base.bo.achieve.AchievementState;
import com.funcy.g01.base.bo.achieve.AchievementType;
import com.funcy.g01.base.bo.achieve.RoleAchieveInfo;
import com.funcy.g01.base.bo.achieve.UpdateAchievement;
import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.dao.redis.AchievementRepo;
import com.funcy.g01.base.dao.redis.RoleRepo;
import com.funcy.g01.base.data.AchievementData;
import com.funcy.g01.base.data.AchievementStarRewardProperty;
import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;
import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.net.CmdStatus;
import com.funcy.g01.base.net.GamePlayer;
import com.funcy.g01.base.proto.bo.service.AchievementServiceRespProtoBuffer.ChangeAchievement;
import com.funcy.g01.base.proto.bo.service.AchievementServiceRespProtoBuffer.ClaimAchievement;
import com.google.protobuf.Message.Builder;

@Service
public class AchievementService {

	@Autowired
	private AchievementRepo achievementRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private AchievementData achievementData;
	
	@Autowired
	private ServerContext serverContext;
	
	@Autowired
	private ItemDomainService itemsService;
	
	public boolean recodAchievement(Achievement achievement, UpdateAchievement updateAchievement){
		boolean isChange = false;
		switch (updateAchievement.getType()) {
		case modify_sex:
		case modify_photo:
		case record_sound:
		case leaving_message:
		case dress_tail_spec:
		case dress_skin_spec:
		case dress_hair_spec:
		case dress_face_spec:
		case pet_padan:
			if (achievement.getState() == AchievementState.running) {
				achievement.setCompleteness(1);
				isChange = true;
			}
			break;
		case role_level_up:
		case saman_level_up:
		case charm:
		case cumulative_login:
			if (achievement.getState() != AchievementState.completed) {
				if (updateAchievement.getParam1() > achievement.getCompleteness()) {
					achievement.setCompleteness(updateAchievement.getParam1());
					isChange = true;
				}
			}
			break;
		case follow:
		case fans:
		case continue_login:
			if (achievement.getState() != AchievementState.completed) {
				achievement.setCompleteness(updateAchievement.getParam1());
				isChange = true;
			}
			break;
		case fight_get_cheese:
		case fight_use_speak:
		case fight_use_emote:
		case dress_tail_quality:
		case dress_skin_quality:
		case dress_hair_quality:
		case dress_face_quality:
		case totem_wind_quality:
		case totem_fire_quality:
		case totem_water_quality:
		case totem_total_quality:
		case totem_wind_5star_quality:
		case totem_fire_5star_quality:
		case totem_water_5star_quality:
		case totem_total_5star_quality:
		case totem_final_quality:
		case shopping_quality:
		case gem_buy_cheese_quality:
		case pet_quality:
		case photo_frame_quality:
		case transfor_cheese_medium:
		case transfor_cheese_senior:
		case champion_medium:
		case champion_senior:
		case second_place_medium:
		case second_place_senior:
		case third_place_medium:
		case third_place_senior:
		case saman_dead_transfor_cheese_medium:
		case saman_dead_transfor_cheese_senior:
		case dead_quality_medium:
		case dead_quality_senior:
		case be_saman_quality_medium:
		case be_saman_quality_senior:
		case kill_mice_quality_medium:
		case kill_mice_quality_senior:
		case save_mice_quality_medium:
		case save_mice_quality_senior:
		case saman_dead_kill_self:
		case be_gold_saman:
		case be_holy_saman:
			if (achievement.getState() != AchievementState.completed) {
				achievement.setCompleteness(achievement.getCompleteness() + updateAchievement.getParam1());
				isChange = true;
			}
			break;
		default:
			break;
		}
		achievement.checkCanRecieve();
		return isChange;
	}
	
	public void updateAchievements(long roleId, List<UpdateAchievement> updateAchievements){
		RoleAchieveInfo  roleAchieveInfo = achievementRepo.getRoleAchieveInfo(roleId);
		List<Achievement> changeAchievements = new ArrayList<Achievement>();
		boolean isChange = false;
		for (UpdateAchievement updateAchievement : updateAchievements) {
			Achievement achievement = roleAchieveInfo.findAchievementByType(updateAchievement.getType());
			if (this.recodAchievement(achievement, updateAchievement)) {
				isChange = true;
				changeAchievements.add(achievement);
			}
		}
		if (isChange) {
			achievementRepo.saveRoleAchieveInfo(roleAchieveInfo);
			notifyPlayer(changeAchievements, roleId);
		}
	}
	
	public void updateOneAchievement(UpdateAchievement updateAchievement){
		RoleAchieveInfo  roleAchieveInfo = achievementRepo.getRoleAchieveInfo(updateAchievement.getRoleId());
		List<Achievement> changeAchievements = new ArrayList<Achievement>();
		Achievement achievement = roleAchieveInfo.findAchievementByType(updateAchievement.getType());
		if (this.recodAchievement(achievement, updateAchievement)) {
			changeAchievements.add(achievement);
			achievementRepo.saveRoleAchieveInfo(roleAchieveInfo);
			notifyPlayer(changeAchievements, updateAchievement.getRoleId());
		}
	}
	
	
	public Builder claimAchieveReward(int xmlId, GamePlayer gamePlayer){
		RoleAchieveInfo  roleAchieveInfo = achievementRepo.getRoleAchieveInfo(gamePlayer.getRoleId());
		Achievement achievement = roleAchieveInfo.findAchievementByXmlId(xmlId);
		if (achievement == null) {
			throw new BusinessException(ErrorCode.no_achieve);
		}
		ClaimAchievement.Builder bulider = ClaimAchievement.newBuilder();
		bulider.setIsSuccess(false);
		if (achievement.getState() == AchievementState.recieve) {
			Role role = roleRepo.getRole(gamePlayer.getRoleId());
			role.addCheese(achievement.getAchievementProperty().getAwardCheese());
			roleAchieveInfo.claimAchievement(achievement);
			role.addAchievePoint(achievement.getAchievementProperty().getAchievePoint());
			roleRepo.saveRole(role);
			achievementRepo.saveRoleAchieveInfo(roleAchieveInfo);
			bulider.setAchievement(achievement.copyTo());
			bulider.setIsSuccess(true);
		}
		return bulider;
	}
	
	public void claimStarBox(int category, GamePlayer gamePlayer){
		RoleAchieveInfo  roleAchieveInfo = achievementRepo.getRoleAchieveInfo(gamePlayer.getRoleId());
		AchievementCategory achievementType = AchievementCategory.getTypeByValue(category);
		AchievementStarRewardProperty achievementStarRewardProperty;
		if (achievementType == AchievementCategory.grow_up) {
			achievementStarRewardProperty = achievementData.getAchievementGrowupStarRewardProperty(roleAchieveInfo.getGrowupGrade());
			if (achievementStarRewardProperty != null) {
				if (roleAchieveInfo.getGrowupStar() >= achievementStarRewardProperty.getStar()) {
					roleAchieveInfo.setGrowupGrade(roleAchieveInfo.getGrowupGrade() + 1);
					itemsService.addItems(BoFactory.createMultiItems(achievementStarRewardProperty.getReward()), gamePlayer.getRoleId());
				}
			}
		}else if(achievementType == AchievementCategory.promotion){
			achievementStarRewardProperty = achievementData.getAchievementPromotionStarRewardProperty(roleAchieveInfo.getPromotionGrade());
			if (achievementStarRewardProperty != null) {
				if (roleAchieveInfo.getPromotionStar() >= achievementStarRewardProperty.getStar()) {
					roleAchieveInfo.setPromotionGrade(roleAchieveInfo.getPromotionGrade() + 1);
					itemsService.addItems(BoFactory.createMultiItems(achievementStarRewardProperty.getReward()), gamePlayer.getRoleId());
				}
			}
		}
		achievementRepo.saveRoleAchieveInfo(roleAchieveInfo);
	}
	
	public void notifyPlayer(List<Achievement> achievements, long roleId){
		if (achievements.size() > 0) {
			ConcurrentHashMap<Long, GamePlayer> logonPlayers = serverContext.getHallLogonPlayers();
			GamePlayer gamePlayer = logonPlayers.get(roleId);
			if (gamePlayer != null) {
				ChangeAchievement.Builder builder = ChangeAchievement.newBuilder();
				for (Achievement achievement : achievements) {
					builder.addAchievements(achievement.copyTo());
				}
				gamePlayer.respond("achievementService.updateAchievement", CmdStatus.notDecrypt, builder);
			}
		}
	}
}
