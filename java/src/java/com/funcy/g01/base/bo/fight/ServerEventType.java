package com.funcy.g01.base.bo.fight;

public enum ServerEventType {
	
	init_player_unit(10000),
	init_physicsobj(10001),
	fight_end(10002),
	fight_skill_delay_dead(10003),
	fight_skill_delay_change_speed(10004),
	flyjump_delay_change_gravity(10005),
	delay_fight_end(10006),
	delay_balloon_explode(10007),
	common_action(10008),
	debug_event(10999);
	
	private final int code;
	
	private ServerEventType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
}
