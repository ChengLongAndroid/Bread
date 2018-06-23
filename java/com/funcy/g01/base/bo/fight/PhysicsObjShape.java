package com.funcy.g01.base.bo.fight;

public enum PhysicsObjShape {
	circle(1), rectangle(2), polygon(3);
	
	private final int code;
	
	private PhysicsObjShape(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	public static PhysicsObjShape getPhysicsObjShapeByCode(int code) {
		for (PhysicsObjShape shape : values()) {
			if(shape.getCode() == code) {
				return shape;
			}
		}
		throw new RuntimeException("code is:" + code);
	}
}
