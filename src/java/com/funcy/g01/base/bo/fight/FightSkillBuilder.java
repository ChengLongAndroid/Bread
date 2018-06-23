package com.funcy.g01.base.bo.fight;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.funcy.g01.base.bo.totem.RoleSkillInfo;
import com.funcy.g01.base.bo.totem.SkillVo;
import com.funcy.g01.base.bo.totem.TotemVo;
import com.funcy.g01.base.dao.redis.TotemRepo;
import com.funcy.g01.base.data.FightSkillData;
import com.funcy.g01.base.data.SkillData;
import com.funcy.g01.base.data.SkillProperty;
import com.funcy.g01.base.data.TotemData;
import com.funcy.g01.base.data.TotemProperty;

@Component
public class FightSkillBuilder {
	
	private static final Logger logger = Logger.getLogger(FightSkillBuilder.class);
	
	public static class BuildFightSkillResult {
		public List<FightSkill> skills = new ArrayList<FightSkill>(); //只包含老鼠的技能
		public List<FightSkill> samanSkills = new ArrayList<FightSkill>();
	}
	
	@Autowired
	private FightSkillData fightSkillData;
	
	@Autowired
	private TotemRepo totemRepo;
	
	@Autowired
	private SkillData skillData;
	
	@Autowired
	private TotemData totemData;

	public BuildFightSkillResult buildFightSkill(long roleId) { //组装时区分老鼠还是萨满
		BuildFightSkillResult result = new BuildFightSkillResult();
		List<TotemVo> totems  = totemRepo.getRoleTotemInfo(roleId).getTotems();
		RoleSkillInfo roleSkillInfo = totemRepo.getRoleSkillInfo(roleId);
		for (TotemVo totemVo : totems) {
			if (totemVo.getSkillId() > 0) {
				SkillVo skillVo = roleSkillInfo.findSkillVoByXmlId(totemVo.getSkillId());
				if(skillVo == null) {
					TotemProperty totemProperty = totemData.getTotemProperty(totemVo.getXmlId());
					int finalSkillId = skillData.getFinalSkillIdByType(totemProperty.getType());
					if(finalSkillId != totemVo.getSkillId()) {
						logger.info("can't find skillId:" + totemVo.getSkillId());
					} else {
						SkillProperty skillProperty = skillData.getSkillProperty(finalSkillId);
						if (skillProperty.getUseObj().equals("mice")) {
							result.skills.add(new FightSkill(fightSkillData.findFightSkillProperty(skillProperty.getFightSkillId()),5));
						}else if(skillProperty.getUseObj().equals("saman")){
							result.samanSkills.add(new FightSkill(fightSkillData.findFightSkillProperty(skillProperty.getFightSkillId()),5));
						}
					}
				} else {
					SkillProperty skillProperty = skillData.getSkillProperty(skillVo.getXmlId());
					if (skillProperty.getUseObj().equals("mice")) {
						result.skills.add(new FightSkill(fightSkillData.findFightSkillProperty(skillProperty.getFightSkillId()),skillVo.getStar()));
					}else if(skillProperty.getUseObj().equals("saman")){
						result.samanSkills.add(new FightSkill(fightSkillData.findFightSkillProperty(skillProperty.getFightSkillId()),skillVo.getStar()));
					}
				}
			}
		}
//		result.samanSkills.add(new FightSkill(fightSkillData.findFightSkillProperty(1000013), 5));
//		result.samanSkills.add(new FightSkill(fightSkillData.findFightSkillProperty(1000014), 5));
//		result.samanSkills.add(new FightSkill(fightSkillData.findFightSkillProperty(1000015), 5));
//		result.samanSkills.add(new FightSkill(fightSkillData.findFightSkillProperty(1000026), 5));
//		result.samanSkills.add(new FightSkill(fightSkillData.findFightSkillProperty(1000027), 5));
//		result.samanSkills.add(new FightSkill(fightSkillData.findFightSkillProperty(1000028), 5));
//		result.samanSkills.add(new FightSkill(fightSkillData.findFightSkillProperty(1000031), 5));

//		result.samanSkills.add(new FightSkill(fightSkillData.findFightSkillProperty(1000034), 5));
//		result.samanSkills.add(new FightSkill(fightSkillData.findFightSkillProperty(1000033), 5));
		return result;
	}
	
}
