package com.funcy.g01.base.bo.fight;

import java.util.Iterator;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.jbox2d.dynamics.joints.RopeJoint;
import org.jbox2d.dynamics.joints.RopeJointDef;

import com.funcy.g01.base.data.PhysicsNailProperty;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.PhysicsNailProto;

public class PhysicsNail {
	
	private NailPosType nailPosType;
	
	private Joint joint;
	
	private PhysicsObj physicsObj;
	
	private PhysicsNailProperty physicsNailProperty;
	
	private PhysicsObj otherPhysicsObj;
	
	private FightUnit connectUnit;
	
	private FightRoom fightRoom;
	
	private Vec2 otherLocalPos;
	
	private boolean isActivate = false;
	
	private Vec2 pos;
	
	public PhysicsNail(FightRoom fightRoom, PhysicsNailProperty physicsNailProperty, NailPosType nailPosType, PhysicsObj physicsObj) {
		this.fightRoom = fightRoom;
		this.nailPosType = nailPosType;
		this.physicsObj = physicsObj;
		this.physicsNailProperty = physicsNailProperty;
	}
	
	public PhysicsNail(FightRoom fightRoom, PhysicsNailProperty physicsNailProperty, PhysicsObj physicsObj, Joint joint, Vec2 pos) {
		this.fightRoom = fightRoom;
		this.physicsNailProperty = physicsNailProperty;
		this.physicsObj = physicsObj;
		this.joint = joint;
		this.pos = pos;
	}
	
	public void initOtherObjInfo(PhysicsObj otherPhysicsObj, Vec2 otherLocalPos) {
		this.otherPhysicsObj = otherPhysicsObj;
		this.otherLocalPos = otherLocalPos;
	}

	public Joint getJoint() {
		return joint;
	}

	public void setJoint(Joint joint) {
		this.joint = joint;
	}

	public PhysicsNailProperty getPhysicsNailProperty() {
		return physicsNailProperty;
	}

	public NailPosType getNailPosType() {
		return nailPosType;
	}

	public PhysicsObj getPhysicsObj() {
		return physicsObj;
	}
	
	public boolean isActivate() {
		return isActivate;
	}
	
	public void setActivate() {
		this.isActivate = true;
	}

	public Vec2 getPosByPosType() {
		Body body = this.physicsObj.getBody();
		switch (this.nailPosType) {
		case left1:
			return body.getWorldPoint(new Vec2(-this.physicsObj.getWidth() / 2f + this.physicsNailProperty.getRadius() / 10f, 0f));
		case left2:
			return body.getWorldPoint(new Vec2(-this.physicsObj.getWidth() / 4f + this.physicsNailProperty.getRadius() / 2f / 10f, 0f));
		case middle:
			return new Vec2(body.getPosition());
		case right1:
			return body.getWorldPoint(new Vec2(this.physicsObj.getWidth() / 2f - this.physicsNailProperty.getRadius() / 10f, 0f));
		case right2:
			return body.getWorldPoint(new Vec2(this.physicsObj.getWidth() / 2f - this.physicsNailProperty.getRadius() / 2f / 10f, 0f));
		case down1:
			return new Vec2(body.getPosition()).add(new Vec2(0, -this.physicsObj.getWidth() / 2f - 5f));
		default:
			break;
		}
		return null;
	}

	public void activate() {
		int connectObjType = this.physicsNailProperty.getConnectObjType();
		switch (connectObjType) {
		case 1: //墙
		{
			RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
			Vec2 pos = getPosByPosType();
			revoluteJointDef.initialize(this.fightRoom.getBackgroundPhysicsObj().getBody(), this.physicsObj.getBody(), pos);
			World world = fightRoom.getWorld();
			RevoluteJoint joint = (RevoluteJoint) world.createJoint(revoluteJointDef);
			joint.enableLimit(false);
			initMotor(joint);
			this.joint = joint;
			this.joint.setUserData(this);
			this.isActivate = true;
			break;
		}
		case 2://物体
		{
			Vec2 pos = getPosByPosType();
			Iterator<PhysicsObj> it = this.fightRoom.getPhysicsObjs().values().iterator();
			while(it.hasNext()) {
				PhysicsObj otherObj = it.next();
				if(otherObj.getBody().getType() == BodyType.STATIC) {
					continue;
				}
				if(otherObj != this.physicsObj && otherObj.getBody().getFixtureList().testPoint(pos)) {
					RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
					revoluteJointDef.initialize(otherObj.getBody(), this.physicsObj.getBody(), pos);
					World world = fightRoom.getWorld();
					RevoluteJoint joint = (RevoluteJoint) world.createJoint(revoluteJointDef);
					joint.enableLimit(!this.physicsNailProperty.isCanRotate());
					if(!this.physicsNailProperty.isCanRotate()) {
						joint.setLimits((float)Math.toRadians(-3), (float)Math.toRadians(3));
					} 
					initMotor(joint);
					this.joint = joint;
					this.joint.setUserData(this);
					this.isActivate = true;
					this.otherPhysicsObj = otherObj;
					break;
				}
			}
			break;
		}
		case 3: //物体或老鼠
		{
			Vec2 pos = getPosByPosType();
			Iterator<FightUnit> unitsIt = this.fightRoom.getUnits().values().iterator();
			while(unitsIt.hasNext()) {
				FightUnit fightUnit = unitsIt.next();
				if(fightUnit.getBody().getFixtureList().testPoint(pos)) {
					RopeJointDef ropeJointDef = new RopeJointDef();
					ropeJointDef.maxLength = 8f;
					ropeJointDef.bodyA = fightUnit.getBody();
					ropeJointDef.bodyB = this.physicsObj.getBody();
					ropeJointDef.localAnchorB.set(0, -this.physicsObj.getHeight() / 2); //只考虑气球
					Vec2 fightUnitPos = fightUnit.getBody().getLocalPoint(pos);
					ropeJointDef.localAnchorA.set(fightUnitPos);
					World world = fightRoom.getWorld();
					RopeJoint ropeJoint = (RopeJoint)world.createJoint(ropeJointDef);
					this.joint = ropeJoint;
					this.joint.setUserData(this);
					this.isActivate = true;
					this.connectUnit = fightUnit;
					this.otherLocalPos = fightUnitPos;
					break;
				}
			}
			
			if(this.isActivate == false) {
				Iterator<PhysicsObj> it = this.fightRoom.getPhysicsObjs().values().iterator();
				while(it.hasNext()) {
					PhysicsObj otherObj = it.next();
					if(otherObj.getBody().getType() == BodyType.STATIC) {
						continue;
					}
					if(otherObj != this.physicsObj && otherObj.getBody().getFixtureList().testPoint(pos)) {
						RopeJointDef ropeJointDef = new RopeJointDef();
						ropeJointDef.maxLength = 8f;
						ropeJointDef.bodyA = otherObj.getBody();
						ropeJointDef.bodyB = this.physicsObj.getBody();
						ropeJointDef.localAnchorB.set(0, -this.physicsObj.getHeight() / 2); //只考虑气球
						Vec2 objPos = otherObj.getBody().getLocalPoint(pos);
						ropeJointDef.localAnchorA.set(objPos);
						World world = fightRoom.getWorld();
						RopeJoint ropeJoint = (RopeJoint)world.createJoint(ropeJointDef);
						this.joint = ropeJoint;
						this.joint.setUserData(this);
						this.isActivate = true;
						this.otherPhysicsObj = otherObj;
						this.otherLocalPos = objPos;
						break;
					}
				}
			}
			break;
			
		}
		default:
			break;
		}
	}
	
	public void initMotor(RevoluteJoint joint) {
		if(this.physicsNailProperty.getMotorDirectionType() == MotorDirectionType.positive) {
			joint.enableMotor(true);
			joint.setMotorSpeed((float) Math.toRadians(-15));
			joint.setMaxMotorTorque(3000);
		} else if(this.physicsNailProperty.getMotorDirectionType() == MotorDirectionType.negative) {
			joint.enableMotor(true);
			joint.setMotorSpeed((float) Math.toRadians(15));
			joint.setMaxMotorTorque(3000);
		} else if(this.physicsNailProperty.getMotorDirectionType() == MotorDirectionType.none) {
			joint.enableMotor(false);
		}
	}
	
	public PhysicsNailProto.Builder buildProto() {
		PhysicsNailProto.Builder builder = PhysicsNailProto.newBuilder();
		builder.setXmlId(this.physicsNailProperty.getId());
		if(this.nailPosType != null) {
			builder.setPosType(this.nailPosType.getCode());
		} else {
			builder.setPosX((int) (this.pos.x * 1000));
			builder.setPosY((int) (this.pos.y * 1000));
		}
		if(this.isActivate) {
			if(this.connectUnit != null) {
				builder.setConnectUnitRoleId(this.connectUnit.getRoleId());
			} else if(this.otherPhysicsObj != null) {
				builder.setOtherObjProto(this.otherPhysicsObj.buildProto(false));
			}
			if(this.otherLocalPos != null) {
				builder.setOtherObjPosX((int) (this.otherLocalPos.x * 1000));
				builder.setOtherObjPosY((int) (this.otherLocalPos.y * 1000));
			}
		}
		return builder;
	}

	public PhysicsObj getOtherPhysicsObj() {
		return otherPhysicsObj;
	}

	public FightUnit getConnectUnit() {
		return connectUnit;
	}
	
}
