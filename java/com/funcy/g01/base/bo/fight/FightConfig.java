package com.funcy.g01.base.bo.fight;

public class FightConfig {

	public static final int fps = 60;
	
	public static final int syn_physics_obj_period_frame_num = 30;//每秒2次
	
	public static final int check_unit_dead_period_frame_num = 20;//每秒3次
	
	public static final int check_obj_dead_period_frame_num = 60;//每秒1次
	
	public static final int check_players_offline_period_frame_num = 60;//每秒1次
	
	public static final int check_force_period_frame_num = 2;
	
	public static final int check_balloon_period_frame_num = 20;
	
	public static final float unit_dead_x_range = 20;
	
	public static final float unit_dead_y_up_range = 400;
	
	public static final float unit_dead_y_below_range = 5;
	
	public static final float obj_dead_x_range = 20;
	
	public static final float obj_dead_y_up_range = 20;
	
	public static final float obj_dead_y_below_range = 5;
	
	public static final int common_virtual_physicsobj_collision_mask = 20;
	
	public static final int land_mice_virtual_collision_mask = 20;
	
	public static final int land_obj_virtual_collision_mask = 8;
	
	public static final int common_physicsobj_collision_mask = 60;
	
	public static final int all_virtual_collision_mask = 0;
	
	public static final int unit_category_mask = 8;
	
	public static final int fight_prepare_frames = 8 * fps;
	
	public static final int fight_ending_seconds = 8;
	
	public static final int fight_ending_frames = fight_ending_seconds * fps;
	
	public static final int fight_prepare_countdown_frames = 4 * fps;
	
	public static final int max_fight_frames = 3 * 10 * fps;
	
	public static final int unit_action_timeout_frames = 30 * fps;
	
	public static final int unit_action_prepare_timeout_frames = 20 * fps;
	
	public static final int fightplayer_offline_timeout_frames = 60 * fps;
	
	public static final float scene_width = 133.4f;
	
	public static final float scene_height = 75;
	
	public static final int unit_syn_frame_num = 60;
	
	public static final int syn_fight_time_frames = 30;
	

	//鼠洞，奶酪一些只有传感的，而不碰撞的物体
	public static final int spec_physicsobj_sensor_category_mask = 32;
	
	public static final int spec_physicsobj_sensor_collision_mask = 8;
	
	public static final int ave_net_delay_frame = 10;
	
	public static final int ave_net_delay_frame_half = ave_net_delay_frame / 2;
	
}
