package com.funcy.g01.hall.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funcy.g01.base.bo.BoFactory;
import com.funcy.g01.base.bo.Currency;
import com.funcy.g01.base.bo.CurrencyType;
import com.funcy.g01.base.bo.achieve.AchievementType;
import com.funcy.g01.base.bo.achieve.UpdateAchievement;
import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.bo.item.RoleItemInfo;
import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.bo.totem.RoleSkillInfo;
import com.funcy.g01.base.bo.totem.RoleTotemInfo;
import com.funcy.g01.base.bo.totem.SkillVo;
import com.funcy.g01.base.bo.totem.TotemType;
import com.funcy.g01.base.bo.totem.TotemVo;
import com.funcy.g01.base.dao.redis.ItemRepo;
import com.funcy.g01.base.dao.redis.RoleRepo;
import com.funcy.g01.base.dao.redis.TotemRepo;
import com.funcy.g01.base.data.SkillData;
import com.funcy.g01.base.data.SkillProperty;
import com.funcy.g01.base.data.TotemData;
import com.funcy.g01.base.data.TotemProperty;
import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;
import com.funcy.g01.base.net.GamePlayer;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleTotemInfoProto.Builder;

@Service
public class TotemService {
	
	@Autowired
	private TotemRepo totemRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private ItemRepo itemRepo;
	
	@Autowired
	private TotemData totemData;
	
	@Autowired
	private SkillData skillData;
	
	@Autowired
	private AchievementService achievementService;
	
	@Autowired
	private ItemDomainService itemDomainService;
	
	public Builder getRoleTotemInfo(GamePlayer gamePlayer){
		RoleTotemInfo roleTotemInfo = totemRepo.getRoleTotemInfo(gamePlayer.getRoleId());
		return (Builder) roleTotemInfo.copyTo().toBuilder();
	}
	
	public void equip(int totemId, int skillId, GamePlayer gamePlayer){
		RoleTotemInfo roleTotemInfo = totemRepo.getRoleTotemInfo(gamePlayer.getRoleId());
		TotemVo totemVo = roleTotemInfo.findTotemVoById(totemId);
		if (totemVo == null) {
			throw new BusinessException(ErrorCode.NO_TOTEM);
		}
		
		TotemProperty totemProperty = totemData.getTotemProperty(totemId);
		if (!totemProperty.isEnabled()) {
			throw new BusinessException(ErrorCode.TOTEM_IS_NOT_ENABLED);
		}
		
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		if (role.getRoleLevel() < totemProperty.getOpenLv()) {
			throw new BusinessException(ErrorCode.EQUIP_SKILL_LEVEL_LIMIT);
		}
		
		SkillProperty skillProperty = skillData.getSkillProperty(skillId);
		if (skillProperty.getTotemId() != totemId) {
			throw new BusinessException(ErrorCode.SKILL_IS_NOT_MATCH_TOTEM);
		}
		RoleSkillInfo roleSkillInfo = totemRepo.getRoleSkillInfo(gamePlayer.getRoleId());
		if (roleSkillInfo.checkHadSkill(skillId)) {
			totemVo.setSkillId(skillId);
		}else{
			throw new BusinessException(ErrorCode.NO_SKILL);
		}
		this.checkFinalSkill(roleTotemInfo, roleSkillInfo, skillId);
		this.checkAchievement(gamePlayer.getRoleId(), null, roleSkillInfo, roleTotemInfo);
	}
	
	public void unEquip(int totemId, GamePlayer gamePlayer){
		RoleTotemInfo roleTotemInfo = totemRepo.getRoleTotemInfo(gamePlayer.getRoleId());
		TotemVo totemVo = roleTotemInfo.findTotemVoById(totemId);
		if (totemVo == null) {
			throw new BusinessException(ErrorCode.NO_TOTEM);
		}
		totemVo.setSkillId(-1);
		TotemProperty totemProperty = totemData.getTotemProperty(totemVo.getXmlId());
		TotemVo finalTotemVo = roleTotemInfo.findFinalTotemVo(totemProperty.getType());
		finalTotemVo.setSkillId(-1);
		totemRepo.saveRoleTotemInfo(roleTotemInfo);
	}
	
	public void checkFinalSkill(RoleTotemInfo roleTotemInfo, RoleSkillInfo roleSkillInfo, int skillId){
		TotemVo totemVo = roleTotemInfo.findTotemVoBySkillId(skillId);
		if (totemVo != null) {
			SkillVo skillVo = roleSkillInfo.findSkillVoByXmlId(skillId);
			if (skillVo != null) {
				boolean isOpen = true;
				TotemProperty totemProperty = totemData.getTotemProperty(totemVo.getXmlId());
				List<TotemVo> currentTypeTotemVoList = roleTotemInfo.findCurrentTypeTotemVoList(totemProperty.getType());
				for (TotemVo totemVo2 : currentTypeTotemVoList) {
					TotemProperty totemProperty2 = totemData.getTotemProperty(totemVo2.getXmlId());
					if (totemProperty2.isEnabled()) {
						if (totemVo2.getSkillId() <= 0) {
							isOpen = false;
							break;
						}else{
							SkillVo skillVo2 = roleSkillInfo.findSkillVoByXmlId(totemVo2.getSkillId());
							if (skillVo2.getStar() < SkillVo.max_star_lv) {
								isOpen = false;
								break;
							}
						}
					}
				}
				TotemVo finalTotemVo = roleTotemInfo.findFinalTotemVo(totemProperty.getType());
				if (isOpen) {
					finalTotemVo.setSkillId(skillData.getFinalSkillIdByType(totemProperty.getType()));
				} else {
					finalTotemVo.setSkillId(-1);
				}
				totemRepo.saveRoleTotemInfo(roleTotemInfo);
			}
		}
	}
	
	public void upStar(int skillId, GamePlayer gamePlayer){
		RoleSkillInfo roleSkillInfo = totemRepo.getRoleSkillInfo(gamePlayer.getRoleId());
		SkillVo skillVo = roleSkillInfo.findSkillVoByXmlId(skillId);
		if (skillVo == null) {
			throw new BusinessException(ErrorCode.NO_SKILL);
		}
		if (skillVo.getStar() >= SkillVo.max_star_lv) {
			throw new BusinessException(ErrorCode.SKILL_STAR_LEVEL_LIMIT);
		}
		
		SkillProperty skillProperty = skillData.getSkillProperty(skillId);
		
		int needCheese = skillProperty.getNeedCheese().get(skillVo.getStar());
		Currency currency = new Currency(CurrencyType.cheese, needCheese);
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		role.spend(currency);
		
		List<Item> upstarIitems = BoFactory.createMultiItems(skillProperty.getUpstarItems().get(skillVo.getStar()));
		itemDomainService.consumeItems(upstarIitems, gamePlayer.getRoleId());
		skillVo.setStar(skillVo.getStar() + 1);
		roleRepo.saveRole(role);
		totemRepo.saveRoleSkillInfo(roleSkillInfo);
		
		RoleTotemInfo roleTotemInfo = totemRepo.getRoleTotemInfo(gamePlayer.getRoleId());
		this.checkFinalSkill(roleTotemInfo, roleSkillInfo, skillId);
		if (skillVo.getStar() == 5) {
			this.checkAchievement(gamePlayer.getRoleId(),skillVo, roleSkillInfo,roleTotemInfo);
		}
	}
	
	public void checkAchievement(long roleId, SkillVo skillVo, RoleSkillInfo roleSkillInfo, RoleTotemInfo roleTotemInfo){
		List<UpdateAchievement> updateAchievements = new ArrayList<UpdateAchievement>();
		if (skillVo != null) {
			SkillProperty skillProperty = skillData.getSkillProperty(skillVo.getXmlId());
			switch (TotemType.valueOf(skillProperty.getType())) {
			case fire:
				updateAchievements.add(new UpdateAchievement(roleId, AchievementType.totem_fire_5star_quality, 1));
				break;
			case wind:
				updateAchievements.add(new UpdateAchievement(roleId, AchievementType.totem_wind_5star_quality, 1));
				break;
			case water:
				updateAchievements.add(new UpdateAchievement(roleId, AchievementType.totem_water_5star_quality, 1));
				break;
			default:
				break;
			}
			updateAchievements.add(new UpdateAchievement(roleId, AchievementType.totem_total_5star_quality, 1));
		}
		int finalTotemNum = roleTotemInfo.getActiveFinalTotemQuality();
		updateAchievements.add(new UpdateAchievement(roleId, AchievementType.totem_final_quality, finalTotemNum));
		achievementService.updateAchievements(roleId, updateAchievements);
	}
}
