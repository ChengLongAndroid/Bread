package com.funcy.g01.base.dao.redis.base;

public enum StorageKey {
	
	global_inc_mapIdKey(1),
	global_inc_emailIdKey(2),
	global_inc_roleIdKey(255),
	
	
	
	fight_map_key(1),
	role_map_info_key(2),
	role_key(3),
	roleId_key(4),
	role_totem_info_key(5),
	role_skill_info_key(6),
	role_item_info_key(7),
	role_dress_info_key(8),
	role_week_fight_info_key(9),
	role_net_info_key(10),
	role_social_info_key(11),
	role_achieve_info_key(12),
	role_photo_frame_info_key(13),
	role_sound_info_key(14),
	role_npcs_info_key(15),
	role_email_info_key(16),
	send_email_info_key(17),
	role_name_Key(18),
	role_hall_info_key(19),
	role_chest_info_key(20),
	role_signin_info_key(21),
	role_mysterious_shop_Info_key(22),
	role_friend_info_key(23),
	role_friend_message_info_key(24),
	role_friend_req_info_key(25),
	role_state_info_key(26),
	role_recentPartner_info_key(27)
	;
	
	public final int code;
	
	private StorageKey(int code) {
		this.code = code;
	}
}
