package com.funcy.g01.base.data;

import com.funcy.g01.base.bo.fight.CampType;
import com.funcy.g01.base.bo.fight.PhysicsObjSpecType;
import com.funcy.g01.base.bo.fight.PhysicsObjType;

public class PhysicsObjProperty {

	private final int id;
	
	private final String name;
	
	private final PhysicsObjSpecType specType;
	
	private final PhysicsObjType type;
	
	private final int shape;
	
	private final float[] size;
	
	private final float density;
	
	private final float friction;
	
	private final float restitution;
	
	private final float[] initV;
	
	private final int physicsCategory;
	
	private final int physicsContactMask;
	
	private final int battleResultId;
	
	private final CampType campType;
	
	private final boolean initObjTure;
	
	public PhysicsObjProperty(int id, String name, PhysicsObjSpecType specType, PhysicsObjType type,
			int shape, float[] size, float density, float friction,
			float restitution, float[] initV, int physicsCategory, int physicsContactMask, int battleResultId, CampType campType, boolean initObjTure) {
		this.id = id;
		this.name = name;
		this.specType = specType;
		this.type = type;
		this.shape = shape;
		this.size = size;
		this.density = density;
		this.friction = friction;
		this.restitution = restitution;
		this.initV = initV;
		this.physicsCategory = physicsCategory;
		this.physicsContactMask = physicsContactMask;
		this.battleResultId = battleResultId;
		this.campType = campType;
		this.initObjTure = initObjTure;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public PhysicsObjSpecType getSpecType() {
		return specType;
	}

	public PhysicsObjType getType() {
		return type;
	}

	public int getShape() {
		return shape;
	}

	public float[] getSize() {
		return size;
	}

	public float getDensity() {
		return density;
	}

	public float getFriction() {
		return friction;
	}

	public float getRestitution() {
		return restitution;
	}
	
	public float[] getInitV() {
		return initV;
	}

	public int getPhysicsCategory() {
		return physicsCategory;
	}

	public int getPhysicsContactMask() {
		return physicsContactMask;
	}

	public int getBattleResultId() {
		return battleResultId;
	}

	public CampType getCampType() {
		return campType;
	}

	public boolean isInitObjTure() {
		return initObjTure;
	}
	
}
