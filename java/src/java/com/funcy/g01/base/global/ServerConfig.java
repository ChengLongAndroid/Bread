package com.funcy.g01.base.global;

import java.util.concurrent.TimeUnit;

import com.ebo.synframework.nettybase.codec.LengthOptimizeConfig;

public class ServerConfig {
	
	public static final long system_role_id = -10000;
	
	public static final long edit_main_city_map_rold_id = -30000;
	
	public static final long system_map_role_id = 6;
	
	public static final long test_system_map_role_id = 6;
	
	public static final String login_token_ext = "yizhuan123";
	
	public static final String key_ext = ";!@#@$ebote323";
	
	public static final int hall_room_base_player_num = 50; 
//	public static final int hall_room_base_player_num = 5;
	
	public static final int hall_room_with_friend_player_num = 70; 
//	public static final int hall_room_with_friend_player_num = 7;
	
	public static final int hall_room_max_player_num = 80; 
//	public static final int hall_room_max_player_num = 8;
	
	public static final int hall_room_merge_threshold = 15;
//	public static final int hall_room_merge_threshold = 2;
	
	public static final int fight_room_base_player_num = 25;
//	public static final int fight_room_base_player_num = 5;
	
	public static final int fight_room_merge_threshold = 8;
//	public static final int fight_room_merge_threshold = 2;
	public static final int fight_room_max_player_num = 50;
//	public static final int fight_room_max_player_num = 8;
	
	public static final int fight_room_with_friend_max_num = 40;
//	public static final int fight_room_with_friend_max_num = 6;
	
	public static final float increase_border_rate = 0.9f;
	
	public static final float decrease_border_rate = 0.7f;
	
	public static final long dispatch_room_fresh_time = TimeUnit.MINUTES.toMillis(1);
	
	public static boolean isDev = false;
	
	public static final String devIp = "127.0.0.1";
	
//	public static final int new_commer_step1_mapId = 50083;
//	
//	public static final int new_commer_step2_mapId = 50084;
	
	public static final int new_commer_step1_mapId = 100001;
	
	public static final int new_commer_step2_mapId = 100002;
	
	public static final int new_commer_step3_mapId = 100013;
	
	public static final boolean isStat = false;

	public static final long max_not_receive_msg_time = TimeUnit.MINUTES.toMillis(1);
	
	public static final long max_cut_out_time = TimeUnit.MINUTES.toMillis(3);
	
	public static final boolean isOpenH5 = true;
	
	public static final String http_domain_name = "/g06-web";
	
	public static final String channelNamePattern = "童话镇%d线";
	
	public static final LengthOptimizeConfig lengthOptimizeConfig = new LengthOptimizeConfig();
	static {
		lengthOptimizeConfig.addProto("EmptyReqProto", "1");
        lengthOptimizeConfig.addProto("IntReqProto", "2");
        lengthOptimizeConfig.addProto("CommonEventReqProto", "3");
        lengthOptimizeConfig.addProto("ConjurePhysicsObjReqProto", "4");
        lengthOptimizeConfig.addProto("UseSkillEventReqProto", "5");
        lengthOptimizeConfig.addProto("FightSkillObjEventReqProto", "6");
        lengthOptimizeConfig.addProto("PeriodUpdateObjInfoProto", "7");
        lengthOptimizeConfig.addProto("RefreshRoomInfoProto", "8");
        lengthOptimizeConfig.addProto("SpeakEventReqProto", "9");
        lengthOptimizeConfig.addProto("ChatEventReqProto", "a");
        lengthOptimizeConfig.addProto("SynEmptyEventReqProto", "b");
        
        lengthOptimizeConfig.addService("accountService.ping", "1");
        lengthOptimizeConfig.addService("synFightingService.playerSendCommonEvent", "2");
        lengthOptimizeConfig.addService("synFightingService.playerSendChatEvent", "3");
        lengthOptimizeConfig.addService("synFightingService.playerSendSpeakEvent", "4");
        lengthOptimizeConfig.addService("synFightingService.playerUseSkillEvent", "5");
        lengthOptimizeConfig.addService("synFightingService.playerSendFightSkillObjEvent", "6");
        lengthOptimizeConfig.addService("fightRoom.broadcast", "7");
        lengthOptimizeConfig.addService("fightRoom.periodSynRoomInfo", "8");
	}
	
	public static LengthOptimizeConfig getLengthOptimizeConfig() {
	        return lengthOptimizeConfig;
	}
}
