package com.funcy.g01.base.bo.fight;

public enum SpeakState {

	speak_start(1),speak_running(2),speak_colddown(3),speak_stop(4);
	
	private final int code;

	private SpeakState(int code) {
		this.code = code;
	}
	
	public static SpeakState getSpeakStateFromCode(int code) {
		for (SpeakState eventType : values()) {
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
