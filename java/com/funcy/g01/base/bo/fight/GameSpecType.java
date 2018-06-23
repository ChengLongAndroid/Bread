package com.funcy.g01.base.bo.fight;

public enum GameSpecType {
	common(1), no_shaman(2), double_saman_pk(3), partner(4), double_saman_cooperation(5), team_compete(6);
	
	private final int code;
	
	private GameSpecType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	public static GameSpecType getGameSpecTypeByCode(int code) {
		for(GameSpecType gameSpecType : values()) {
			if(gameSpecType.getCode() == code) {
				return gameSpecType;
			}
		}
		return null;
	}
	
}
