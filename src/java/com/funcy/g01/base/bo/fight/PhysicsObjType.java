package com.funcy.g01.base.bo.fight;

public enum PhysicsObjType {
	unitObj(1), floor(2), decoration(3), others(4), background(5);
	
	private final int code;
	
	private PhysicsObjType(final int code) {
		this.code = code;
	}
	
	public static PhysicsObjType getTypeByCode(int code) {
		for (PhysicsObjType type : values()) {
			if(type.code == code) {
				return type;
			}
		}
		throw new RuntimeException("code is:" + code);
	}
}
