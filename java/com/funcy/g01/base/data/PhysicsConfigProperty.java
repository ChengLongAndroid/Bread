package com.funcy.g01.base.data;

public class PhysicsConfigProperty {

	private final int id;
	
	private final float gravityY;
	
	private final float jumpTime;
	
	private final float moveSpeed;
	
	private final float initMoveSpeed;
	
	private final float moveSpeedA;
	
	private final float conjureObjDelay;
	
	private final float bombImpulse;
	
	public PhysicsConfigProperty(int id, float gravityY, float jumpTime,
			float moveSpeed, float initMoveSpeed, float moveSpeedA, float conjureObjDelay, float bombImpulse) {
		this.id = id;
		this.gravityY = gravityY;
		this.jumpTime = jumpTime;
		this.moveSpeed = moveSpeed;
		this.initMoveSpeed = initMoveSpeed;
		this.moveSpeedA = moveSpeedA;
		this.conjureObjDelay = conjureObjDelay;
		this.bombImpulse = bombImpulse;
	}

	public int getId() {
		return id;
	}

	public float getGravityY() {
		return gravityY;
	}

	public float getJumpTime() {
		return jumpTime;
	}

	public float getMoveSpeed() {
		return moveSpeed;
	}

	public float getInitMoveSpeed() {
		return initMoveSpeed;
	}

	public float getMoveSpeedA() {
		return moveSpeedA;
	}

	public float getConjureObjDelay() {
		return conjureObjDelay;
	}

	public float getBombImpulse() {
		return bombImpulse;
	}
	
}
