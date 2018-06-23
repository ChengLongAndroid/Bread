package com.funcy.g01.base.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StaticDataInitor {

	@Autowired
	private PhysicsConfigData physicsConfigData;
	
	@Autowired
	private UnitData unitData;
	
	@Autowired
	private PhysicsObjData physicsObjData;
	
	@Autowired
	private PhysicsNailData physicsNailData;
	
	@Autowired
	private DressData dressData;
	
	@Autowired
	private ItemData itemData;
	
	@Autowired
	private SkillData skillData;
	
	@Autowired
	private TotemData totemData;
	
	@Autowired
	private ShopData shopData;
	
	@Autowired
	private RoleData roleData;
	
	@Autowired
	private BattleResultData battleResultData;
	
	@Autowired
	private DropGroupData dropGroupData;
	
	@Autowired
	private FightSkillData fightSkillData;
	
	@Autowired
	private AchievementData achievementData;
	
	@Autowired
	private PhotoFrameData photoFrameData;
	
	@Autowired
	private NpcData npcData;
	
	@Autowired
	private ActiveData activeData;
	
	@Autowired
	private CheckpointData checkpointData;
	
	@Autowired
	private SpecMouseTypeData specMouseTypeData;
	
	@Autowired
	private SpecialCityData specialCityData;
	
	@Autowired
	private GiftExchangeData giftExchangeData;
	
	public void init() {
		physicsConfigData.init();
		unitData.init();
		physicsObjData.init();
		physicsNailData.init();
		dressData.init();
		itemData.init();
		fightSkillData.init();
		skillData.init(fightSkillData);
		totemData.init();
		shopData.init();
		roleData.init();
		battleResultData.init();
		dropGroupData.init();
		achievementData.init();
		photoFrameData.init();
		npcData.init();
		activeData.init();
		checkpointData.init();
		specMouseTypeData.init();
		specialCityData.init();
		giftExchangeData.init();
	}
	
}
