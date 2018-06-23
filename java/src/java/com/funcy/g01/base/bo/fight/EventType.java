package com.funcy.g01.base.bo.fight;

public enum EventType {
	
	start_moving_left_event_type(1),
	end_moving_left_event_type(2),
	start_moving_right_event_type(3),
	end_moving_right_event_type(4),
	jump_event_type(5),
	enter_fight_event_type(6),
	period_syn_unit_info_event_type(7),
	contact_syn_unit_info_event_type(8),
	quit_event_type(9), 
	conjure_physicsobj_update_event_type(10),
	conjure_physicsobj_confirm_event_type(11),
	conjure_physicsobj_cancel_event_type(12),
	conjure_physicsobj_conjuring_update_event_type(13),
	conjure_physicsobj_conjuring_init_event_type(14),
	conjure_physicsobj_init_event_type(17),
	unit_dead(18),
	conjure_physicsobj_dead_event_type(19),
	fight_end(20),
	unit_init(21),
	got_cheese(22),
	unit_win(23),
	cutout_game_event_type(24),
	back_to_game_event_type(25),
	got_dropItem(26),
	transfor_dropItem(27),
	fight_act_start(28),
	chat(29),
	saman_can_not_enter_before_all_have_enter(30),
	unit_reset_pos(31),
	unit_reset_velocity(32),
	speak(33),
	use_skill(34),
	refresh_unit_skill_add_info(35),
	server_conjure_physicsobj(36),
	change_dress(37),
	fight_skill_obj_init_event_type(38),
	fight_skill_obj_update_event_type(39),
	fight_skill_obj_remove_event_type(40),
	fly_event_type(41),
	fly_jump_event_type(42),
	stop_fly_event_type(43),
	start_dive(44),
	stop_dive(45),
	springball_take_effect(46),
	action_prepare_timeout_warn(47),
	balloon_explode(48),
	;
	
	private final int code;

	private EventType(int code) {
		this.code = code;
	}
	
	public static EventType getEventTypeFromCode(int code) {
		for (EventType eventType : values()) {
			if(eventType.code == code) {
				return eventType;
			}
		}
		return null;
	}

	public int getCode() {
		return code;
	}
	
}
