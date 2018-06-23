package com.funcy.g01.base.bo.fight;

public enum CampType {
	none(0), red(1), blue(2);
	
	private final int code;

	private CampType(int code) {
		this.code = code;
	}
	
	public static CampType getCampTypeByCode(int code) {
		for(CampType campType : values()) {
			if(campType.getCode() == code) {
				return campType;
			}
		}
		return null;
	}
	
	public int getCode() {
		return this.code;
	}
}
