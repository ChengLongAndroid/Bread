package com.funcy.g01.base.bo.achieve;


public enum AchievementType {
	role_level_up(1,"玩家升级"),
	saman_level_up(2,"萨满升级"),
	fight_get_cheese(3,"比赛中获取的奶酪鼠"),
	modify_sex(4,"修改性别"),
	modify_photo(5,"修改头像"),
	record_sound(6,"录制语音"),
	leaving_message(7,"留言"),
	fight_use_speak(8,"使用语音"),
	fight_use_emote(9,"使用表情"),
	dress_tail_quality(10,"尾部装饰个数"),
	dress_skin_quality(11,"皮肤个数"),
	dress_hair_quality(12,"头部装饰个数"),
	dress_face_quality(13,"面部个数"),
	dress_tail_spec(14,"尾巴4"),
	dress_skin_spec(15,"皮肤68"),
	dress_hair_spec(16,"头部装饰1"),
	dress_face_spec(17,"面部装饰1"),
	totem_wind_quality(18,"风系图腾种类"),
	totem_fire_quality(19,"火系图腾种类"),
	totem_water_quality(20,"雷系图腾种类"),
	totem_total_quality(21,"图腾种类"),
	totem_wind_5star_quality(22,"风系5星图腾种类"),
	totem_fire_5star_quality(23,"火系5星图腾种类"),
	totem_water_5star_quality(24,"雷系5星图腾种类"),
	totem_total_5star_quality(25,"5星图腾种类"),
	totem_final_quality(26,"激活终极大招"),
	pet_quality(27,"宠物个数"),
	pet_padan(28,"获得熊猫"),
	gem_buy_cheese_quality(29,"砖石购买奶酪数量"),
	shopping_quality(30,"砖石购买奶酪数量"),
	charm(31,"魅力值"),
	follow(32,"关注数量"),
	fans(33,"粉丝数量"),
	photo_frame_quality(34,"相框数量"),
	continue_login(35,"连续登录"),
	cumulative_login(36,"累计登录"),
	watch_ads(37,"观看广告"),
	transfor_cheese_medium(38,"中级奶酪运到鼠洞"),
	transfor_cheese_senior(39,"高级奶酪运到鼠洞"),
	champion_medium(40,"中级场冠军"),
	champion_senior(41,"高级场冠军"),
	second_place_medium(42,"中级场亚军"),
	second_place_senior(43,"高级场亚军"),
	third_place_medium(44,"中级场季军"),
	third_place_senior(45,"高级场季军"),
	saman_dead_transfor_cheese_medium(46,"中级场萨满死亡之后运送奶酪到达鼠洞"),
	saman_dead_transfor_cheese_senior(47,"高级场萨满死亡之后运送奶酪到达鼠洞"),
	dead_quality_medium(48,"中级场死亡场次"),
	dead_quality_senior(49,"高级场死亡场次"),
	be_saman_quality_medium(50,"中级场担任萨满"),
	be_saman_quality_senior(51,"高级场担任萨满"),
	kill_mice_quality_medium(52,"中级场害死小鼠"),
	kill_mice_quality_senior(53,"高级场害死小鼠"),
	save_mice_quality_medium(54,"中级场救助小鼠"),
	save_mice_quality_senior(55,"高级场救助小鼠"),
	saman_dead_kill_self(56,"萨满死后自杀"),
	be_gold_saman(57,"担任黄金萨满"),
	be_holy_saman(58,"担任神圣萨满")
	;
	private int value;
	
	private String description;
	
	private AchievementType(int value, String description) {
		this.value = value;
		this.description = description;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public String getDescription(){
		return this.description;
	}
	public static AchievementType getTypeByValue(int value) {
		for (AchievementType achievementType : AchievementType.values()) {
			if (achievementType.value == value) {
				return achievementType;
			}
		}
		throw new RuntimeException();
	}
}