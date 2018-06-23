package com.funcy.g01.base.bo.fight;

import java.util.HashMap;
import java.util.Map;

public enum FightSkillEffectType {
	none(0), fight_time_extend(1000001), big_cheese(1000002), soul_fight(1000003), reborn(1000004), all_jump_two(1000005), dead_become_bubble(1000006)
	, quick_conjure(1000008), inc_conjure_range(1000009), jump_height_change(1000010), inc_friction(1000011)
	, inc_speed(1000012), quick_conjure2_dec_conjure_range(1000013), quick_conjure3(1000014), inc_conjure_range2_dec_conjure_speed(1000015)
	, inc_conjure_range3(1000016), become_bigger(1000017), become_smaller(1000018), strengthen_iron(1000019)
	, rough_wood(1000020), super_qigong(1000021), strengthen_bullet(1000022), strengthen_balloon(1000023)
	, spring_ball(1000024), super_fuwen(1000025), chocolate_bar(1000026), multi_ball(1000027), multi_balloon(1000028)
	, conjure_inc_speed(1000029), avoid_qigong_hurt(1000030)
	, bullet_conjure_speed_up(1000032), balloon_conjure_speed_up(1000033), reborn_mouse(1000034),fly(1000035),jump_three(1000036), telesport(1000037)
	, conjure_cloud(1000038)
	,mice_virtual_obj(1001001),delete_saman_obj(1001002),delete_obj_nail(1001003),fix_obj_angle(1001004),rocket_jump(1001005),
	send_unit_to_side(1001006),give_unit_cheese(1001007),send_cheese_unit_to_door(1001008),frozen_unit(1001009),increase_bounce(1001010),
	increase_speed(1001011),second_jump(1001012),frozen_over(1001013),auto_win(1001014),
	fly_by_item(1002001), jump_three_by_item(1002002);
	
	
	private static Map<Integer, FightSkillEffectType> map = new HashMap<Integer, FightSkillEffectType>();
	
	static {
		for(FightSkillEffectType fightSkillEffectType : values()) {
			map.put(fightSkillEffectType.xmlId, fightSkillEffectType);
		}
	}
	
	private final int xmlId;

	private FightSkillEffectType(int xmlId) {
		this.xmlId = xmlId;
	}

	public int getXmlId() {
		return xmlId;
	}
	
	public static FightSkillEffectType getFightSkillEffectTypeByXmlId(int xmlId) {
		FightSkillEffectType type = map.get(xmlId);
		if(type == null) {
			return FightSkillEffectType.none;
		} 
		return type;
	}
}
