package com.funcy.g01.base.data;

public class UnitProperty {

	private final int id;
	
	private final float density;
	
	private final float radius;
	
	private final float friction;
	
	private final float restitution;
	
	private final int physicsCategory;
	
	private final int physicsContactMask;

	public UnitProperty(int id, float density, float radius, float friction,
			float restitution, int physicsCategory, int physicsContactMask) {
		this.id = id;
		this.density = density;
		this.radius = radius;
		this.friction = friction;
		this.restitution = restitution;
		this.physicsCategory = physicsCategory;
		this.physicsContactMask = physicsContactMask;
	}

	public int getId() {
		return id;
	}

	public float getDensity() {
		return density;
	}

	public float getRadius() {
		return radius;
	}

	public float getFriction() {
		return friction;
	}

	public float getRestitution() {
		return restitution;
	}

	public int getPhysicsCategory() {
		return physicsCategory;
	}

	public int getPhysicsContactMask() {
		return physicsContactMask;
	}
	
}
