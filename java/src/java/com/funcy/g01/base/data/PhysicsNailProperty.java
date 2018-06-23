package com.funcy.g01.base.data;

import com.funcy.g01.base.bo.fight.MotorDirectionType;

public class PhysicsNailProperty {

	private final int id;
	
	private final String name;
	
	private final boolean canRotate;
	
	private final int connectObjType;
	
	private final float radius;
	
	private final MotorDirectionType motorDirectionType;

	public PhysicsNailProperty(int id, String name, boolean canRotate,
			int connectObjType, float radius, MotorDirectionType motorDirectionType) {
		this.id = id;
		this.name = name;
		this.canRotate = canRotate;
		this.connectObjType = connectObjType;
		this.radius = radius;
		this.motorDirectionType = motorDirectionType;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isCanRotate() {
		return canRotate;
	}

	public int getConnectObjType() {
		return connectObjType;
	}

	public float getRadius() {
		return radius;
	}

	public MotorDirectionType getMotorDirectionType() {
		return motorDirectionType;
	}
	
}
