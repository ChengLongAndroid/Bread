package com.funcy.g01.base.bo.fight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Filter;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.contacts.ContactEdge;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.PrismaticJointDef;

import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.data.PhysicsNailData;
import com.funcy.g01.base.data.PhysicsNailProperty;
import com.funcy.g01.base.data.PhysicsObjProperty;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.net.CmdStatus;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ConjurePhysicsObjEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.PhysicsNailProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.PhysicsObjProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.PhysicsObjSkillChangeInfoProto;
import com.funcy.g01.base.util.CalUtil;

public class PhysicsObj {
	
	public static class PhysicsObjChangeInfo {
		public float scaleRate = 1.0f;
	}
	
	private static final Logger logger = Logger.getLogger(PhysicsObj.class);

	private int id;
	
	private long creatorRoleId;
	
	private PhysicsObjType physicsObjType;
	
	private Body body;
	
	private FightRoom fightRoom;
	
	private PhysicsObjProperty physicsObjProperty;
	
	private List<PhysicsNail> nails = new ArrayList<PhysicsNail>();
	
	private float width;
	
	private float height;
	
	private float scaleX;
	
	private float scaleY;
	
	private boolean miceVirtual;
	
	private boolean objVirtual;
	
	private boolean objTure;
	
	private int rotationForcePercent;
	
	private int moveForcePercent;
	
	private int restitutionPercent;
	
	private int frictionPercent;
	
	private int densityPercent;
	
	private int motionSpeedPercent;
	
	private int launchFrequencyPercent;
	
	private int emitterXmlId;
	
	private int layerNum;
	
	private boolean isFixAngle;
	
	private boolean visible;
	
	private int battleResultId;
	
	private float movingForce;
	
	private float movingSpeed;
	
	private PhysicsObjProto.Builder commonBuilder;
	
	private PhysicsObjProto.Builder initBuilder;
	
	private Map<Long, Integer> springBallContactUsersNum;
	
	private long destinationSamanRoleId = 0;
	
	private float skillExpandRate = 0f;
	
	private boolean isConjured = false;
	private float skillChangeDensityRate = 0f;
	
	private float skillChangeFrictionRate = 0f;
	
	private float skillChangeBalloonVRate = 0f;

	private boolean skillChangeFixRotation = false;
	
	private boolean isSwimming = false;
	
	public static final float default_balloonV = 2.5f;
	
	public static final float default_qigong_explode_range = 15f;
	
	private PhysicsObj hallTransferDoorTarget;
	
	private Joint cloudJoint;
	
	private boolean isExploding = false;
	
	private int createFrameIndex;
	
	public static final int max_live_frame_num = 20 * FightConfig.fps;
	
	public PhysicsObj(int id, long creatorRoleId) {
		this.id = id;
		this.creatorRoleId = creatorRoleId;
	}

	public PhysicsObjProto.Builder buildProto(boolean isInit) {
		PhysicsObjProto.Builder builder;
		if(isInit) {
			if(this.initBuilder == null) {
				this.initBuilder = PhysicsObjProto.newBuilder();
			}
			builder = this.initBuilder;
		} else {
			if(this.commonBuilder == null) {
				this.commonBuilder = PhysicsObjProto.newBuilder();
			}
			builder = this.commonBuilder;
		}
		builder.setId(this.id);
		if(isInit) {
			builder.setXmlId(this.physicsObjProperty.getId());
			builder.clearNails();
			for (PhysicsNail physicsNail : nails) {
				builder.addNails(physicsNail.buildProto());
			}
			builder.setScaleX((int) (this.scaleX * 100));
			builder.setScaleY((int) (this.scaleY * 100));
			builder.setMiceVirtual(this.miceVirtual);
			builder.setObjVirtual(this.objVirtual);
			builder.setObjTure(this.objTure);
			builder.setRotationForcePercent(this.rotationForcePercent);
			builder.setMoveForcePercent(this.moveForcePercent);
			builder.setRestitutionPercent(this.restitutionPercent);
			builder.setFrictionPercent(this.frictionPercent);
			builder.setDensityPercent(this.densityPercent);
			builder.setMotionSpeedPercent(this.motionSpeedPercent);
			builder.setLaunchFrequencyPercent(this.launchFrequencyPercent);
			builder.setEmitterXmlId(this.emitterXmlId);
			builder.setLayerNum(this.layerNum);
			builder.setIsFixAngle(this.isFixAngle);
			builder.setVisible(this.visible);
			builder.setBattleResultId(this.battleResultId);
			if(this.getDestinationSamanRoleId() != 0) {
				builder.setSamanRoleId(this.getDestinationSamanRoleId());
			}
			if (isConjured) {
				builder.setIsConjured(isConjured);
			}
			builder.setSkillChangeInfo(this.buildSkillChangeInfoProto());
			if(this.body.getFixtureList().m_filter.groupIndex != 0) {
				builder.setFilterGroupId(this.body.getFixtureList().m_filter.groupIndex);
			}
		}
		if(this.body.getType() == BodyType.DYNAMIC) {
			builder.setAngularV((int) (body.getAngularVelocity() * 100));
			builder.setVX((int) (body.getLinearVelocity().x * 100));
			builder.setVY((int) (body.getLinearVelocity().y * 100));
		}
		
		builder.setRotation((int) (-Math.toDegrees(body.getAngle())  * 100));
		builder.setPosX((int) (this.body.getPosition().x * 1000));
		builder.setPosY((int) (this.body.getPosition().y * 1000));
		
		return builder;
	}
	
	public PhysicsObjSkillChangeInfoProto.Builder buildSkillChangeInfoProto() {
		PhysicsObjSkillChangeInfoProto.Builder builder = PhysicsObjSkillChangeInfoProto.newBuilder();
		builder.setSkillExpandRate((int) (this.skillExpandRate * 100));
		builder.setSkillChangeBalloonVRate((int) (this.skillChangeBalloonVRate * 100f));
		builder.setSkillChangeFrictionRate( (int) (this.skillChangeFrictionRate * 100f));
		builder.setSkillChangeDensityRate((int) (this.skillChangeDensityRate * 100f));
		builder.setSkillChangeFixRotation(this.skillChangeFixRotation);
		return builder;
	}
	
	public static PhysicsObj createBackground(FightRoom fightRoom) {
		PhysicsObj physicsObj = new PhysicsObj(-1, ServerConfig.system_role_id);
		physicsObj.fightRoom = fightRoom;
		physicsObj.physicsObjType = PhysicsObjType.background;
		BodyDef bodyDef = new BodyDef();
		bodyDef.setPosition(new Vec2(fightRoom.getWidth() / 2f, fightRoom.getHeight() / 2f));
		bodyDef.setType(BodyType.STATIC);
		bodyDef.setAngle(0);
		bodyDef.setAllowSleep(true);
		Body body = fightRoom.getWorld().createBody(bodyDef);
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(fightRoom.getWidth() / 2f, fightRoom.getHeight() / 2f);
		Fixture fixture = body.createFixture(polygonShape, 0);
		fixture.setFriction(0);
		fixture.setRestitution(0);
		Filter filter = new Filter();
		filter.categoryBits = 0;
		filter.maskBits = 0;
		fixture.setFilterData(filter);
		physicsObj.body = body;
		return physicsObj;
	}
	
	public static PhysicsObjProto.Builder createDefaultPbjProto(PhysicsObjProperty physicsObjProperty) {
		PhysicsObjProto.Builder builder = PhysicsObjProto.newBuilder();
		builder.setScaleX(100);
		builder.setScaleY(100);
		builder.setXmlId(physicsObjProperty.getId());
		builder.setMiceVirtual(false);
		builder.setObjVirtual(false);
		builder.setObjTure(physicsObjProperty.isInitObjTure());
		builder.setRotationForcePercent(100);
		builder.setMoveForcePercent(100);
		builder.setRestitutionPercent(100);
		builder.setFrictionPercent(100);
		builder.setDensityPercent(100);
		builder.setMotionSpeedPercent(100);
		builder.setLaunchFrequencyPercent(100);
		builder.setEmitterXmlId(100001);
		builder.setIsFixAngle(false);
		builder.setLayerNum(6900);
		builder.setIsFixAngle(false);
		builder.setVisible(true);
		return builder;
	}

	public static PhysicsObj createNewPhysicsObj(FightRoom fightRoom, long roleId, PhysicsObjProperty physicsObjProperty, PhysicsObjProto physicsObjProto, boolean isInit) {
		int physicsId;
		if(physicsObjProto.getId() != 0) {
			physicsId = physicsObjProto.getId();
			if(physicsId > fightRoom.getCurPhysicsObjIdProducerId()) {
				fightRoom.setPhysicsObjId(physicsId);
			}
		} else {
			physicsId = fightRoom.produceNewPhysicsObjId();
		}
		PhysicsObj physicsObj = new PhysicsObj(physicsId, roleId);
		physicsObj.fightRoom = fightRoom;
		physicsObj.createFrameIndex = fightRoom.getFrameIndex();
		physicsObj.physicsObjProperty = physicsObjProperty;
		physicsObj.physicsObjType = physicsObjProperty.getType();
		physicsObj.scaleX = physicsObjProto.getScaleX() / 100f;
		physicsObj.scaleY = physicsObjProto.getScaleY() / 100f;
		physicsObj.miceVirtual = physicsObjProto.getMiceVirtual();
		physicsObj.objVirtual = physicsObjProto.getObjVirtual();
		physicsObj.objTure = physicsObjProto.getObjTure();
		physicsObj.rotationForcePercent = physicsObjProto.getRotationForcePercent();
		physicsObj.moveForcePercent = physicsObjProto.getMoveForcePercent();
		physicsObj.restitutionPercent = physicsObjProto.getRestitutionPercent();
		physicsObj.frictionPercent = physicsObjProto.getFrictionPercent();
		physicsObj.densityPercent = physicsObjProto.getDensityPercent();
		physicsObj.motionSpeedPercent = physicsObjProto.getMotionSpeedPercent();
		physicsObj.launchFrequencyPercent = physicsObjProto.getLaunchFrequencyPercent();
		physicsObj.emitterXmlId = physicsObjProto.getEmitterXmlId();
		physicsObj.layerNum = physicsObjProto.getLayerNum();
		physicsObj.isFixAngle = physicsObjProto.getIsFixAngle();
		physicsObj.visible = physicsObjProto.getVisible();
		physicsObj.battleResultId = physicsObjProto.getBattleResultId();
		physicsObj.isConjured = physicsObjProto.getIsConjured();
		
		createPhysicsBody0(fightRoom, physicsObjProperty, physicsObjProto,
				isInit, physicsObj);
		
		//一些特殊处理
		physicsObj.initSpecTypeAttribute(fightRoom.getWorld());
		
		//钉子生效
		List<PhysicsNailProto> nails = physicsObjProto.getNailsList();
		PhysicsNailData physicsNailData = fightRoom.getPhysicsNailData();
		for (PhysicsNailProto physicsNailProto : nails) {
			PhysicsNailProperty physicsNailProperty = physicsNailData.getPhysicsNailProperty(physicsNailProto.getXmlId());
			PhysicsNail physicsNail = new PhysicsNail(fightRoom, physicsNailProperty, NailPosType.getNailPosTypeByCode(physicsNailProto.getPosType()), physicsObj);
			if(!isInit) {
				physicsNail.activate();
			}
			physicsObj.nails.add(physicsNail);
		}
		physicsObj.body.setUserData(physicsObj);
		return physicsObj;
	}
	
	public void addNail(PhysicsNail physicsNail) {
		this.nails.add(physicsNail);
	}

	private static void createPhysicsBody0(FightRoom fightRoom,
			PhysicsObjProperty physicsObjProperty,
			PhysicsObjProto physicsObjProto, boolean isInit,
			PhysicsObj physicsObj) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.setPosition(new Vec2(physicsObjProto.getPosX() / 1000f, physicsObjProto.getPosY() / 1000f));
		bodyDef.setAngle((float) Math.toRadians(-physicsObjProto.getRotation() / 100f));
		Body body = fightRoom.getWorld().createBody(bodyDef);
		body.setLinearDamping(0.1f);
		body.setAngularDamping(1f);
		createFixture(physicsObjProperty, physicsObj, body);
		physicsObj.body = body;
	}

	public static void createFixture(PhysicsObjProperty physicsObjProperty,
			PhysicsObj physicsObj, Body body) {
		PhysicsObjShape physicsObjShape = PhysicsObjShape.getPhysicsObjShapeByCode(physicsObjProperty.getShape());
		Shape shape = null;
		if(physicsObjShape == PhysicsObjShape.circle) {
			CircleShape circleShape = new CircleShape();
			circleShape.setRadius(physicsObjProperty.getSize()[0] / 10f * Math.abs(physicsObj.scaleX) * (1f + physicsObj.skillExpandRate));
			shape = circleShape;
			physicsObj.width = circleShape.getRadius() * Math.abs(physicsObj.scaleX) * 2;
			physicsObj.height = circleShape.getRadius() * Math.abs(physicsObj.scaleY) * 2;
		} else if(physicsObjShape == PhysicsObjShape.rectangle) {
			PolygonShape polygonShape = new PolygonShape();
			physicsObj.width = physicsObjProperty.getSize()[0]  / 10f * Math.abs(physicsObj.scaleX) * (1f + physicsObj.skillExpandRate);
			physicsObj.height = physicsObjProperty.getSize()[1] / 10f * Math.abs(physicsObj.scaleY) * (1f + physicsObj.skillExpandRate);
			polygonShape.setAsBox(physicsObj.width / 2, physicsObj.height / 2);
			shape = polygonShape;
		}
		Fixture fixture = body.createFixture(shape, physicsObjProperty.getDensity() * physicsObj.densityPercent / 100);
		fixture.setFriction(physicsObjProperty.getFriction() * physicsObj.frictionPercent / 100);
		fixture.setRestitution(physicsObjProperty.getRestitution() * physicsObj.restitutionPercent / 100);
		Filter filter = new Filter();
		filter.categoryBits = physicsObjProperty.getPhysicsCategory();
		filter.maskBits = physicsObjProperty.getPhysicsContactMask();
		switch (physicsObj.physicsObjType) {
			case unitObj:
				body.setType(BodyType.DYNAMIC);
				if (physicsObj.miceVirtual) {
					filter.maskBits = FightConfig.common_virtual_physicsobj_collision_mask;
				}
				break;
			case floor:
				body.setFixedRotation(physicsObj.isFixAngle);
				body.setAngularDamping(body.getAngularDamping() * physicsObj.rotationForcePercent / 100);
				body.setLinearDamping(body.getLinearDamping() * physicsObj.moveForcePercent / 100);
				if(physicsObj.miceVirtual && physicsObj.objVirtual) {
					filter.maskBits = FightConfig.all_virtual_collision_mask;
				} else {
					if(physicsObj.miceVirtual) {
						filter.maskBits = FightConfig.land_mice_virtual_collision_mask;;
					} else if(physicsObj.objVirtual) {
						filter.maskBits = FightConfig.land_obj_virtual_collision_mask;;
					} else {
//						filter.maskBits = FightConfig.common_physicsobj_collision_mask;;
						//TODO need check
						filter.maskBits = physicsObjProperty.getPhysicsContactMask();
					}
				}
				if(physicsObj.objTure){
					body.setType(BodyType.DYNAMIC);
				} else {
					body.setType(BodyType.STATIC);
				}
				break;
			case decoration:
				body.setType(BodyType.STATIC);
				if (physicsObj.objTure) {
					filter.maskBits = FightConfig.common_physicsobj_collision_mask;;
				}
				break;
			case others:
				body.setType(BodyType.STATIC);
				break;
			default:
				break;
		}
		if(physicsObjProperty.getSpecType() == PhysicsObjSpecType.cheese) {
			setFixtureToSensor(body, filter);
		} else if(physicsObjProperty.getSpecType() == PhysicsObjSpecType.cheeseDestination) {
			setFixtureToSensor(body, filter);
		} else if(physicsObjProperty.getSpecType() == PhysicsObjSpecType.dropItem) {
			setFixtureToSensor(body, filter);
		} else if(physicsObjProperty.getSpecType() == PhysicsObjSpecType.springBall) {
			body.setType(BodyType.STATIC);
			setFixtureToSensor(body, filter);
			physicsObj.springBallContactUsersNum = new HashMap<Long, Integer>();
		} else if(physicsObjProperty.getSpecType() == PhysicsObjSpecType.transferDoorA || physicsObjProperty.getSpecType() == PhysicsObjSpecType.transferDoorB) {
			body.setType(BodyType.STATIC);
			setFixtureToSensor(body, filter);
		} else if(physicsObjProperty.getSpecType() == PhysicsObjSpecType.star) {
			body.setType(BodyType.STATIC);
			setFixtureToSensor(body, filter);
		}
		fixture.setFilterData(filter);
	}

	public static void setFixtureToSensor(Body body, Filter filter) {
		filter.maskBits  = FightConfig.spec_physicsobj_sensor_collision_mask;
		filter.categoryBits = FightConfig.spec_physicsobj_sensor_category_mask;
		body.getFixtureList().setSensor(true);
	}
	
	//只对萨满物体和地块有影响
	public void switchPhysicsObjMiceVirtualObj(){
		Filter filter = body.getFixtureList().getFilterData();
		miceVirtual = !miceVirtual;
		filter.categoryBits = physicsObjProperty.getPhysicsCategory();
		filter.maskBits = physicsObjProperty.getPhysicsContactMask();
		switch (physicsObjType) {
			case unitObj:
				if (miceVirtual) {
					filter.maskBits = FightConfig.common_virtual_physicsobj_collision_mask;
				}
				break;
			case floor:
				if(miceVirtual && objVirtual) {
					filter.maskBits = FightConfig.all_virtual_collision_mask;
				} else {
					if(miceVirtual) {
						filter.maskBits = FightConfig.land_mice_virtual_collision_mask;;
					} else if(objVirtual) {
						filter.maskBits = FightConfig.land_obj_virtual_collision_mask;;
					} else {
						filter.maskBits = FightConfig.common_physicsobj_collision_mask;;
					}
				}
				break;
			default:
				break;
		}
		body.getFixtureList().setFilterData(filter);
	}
	
	public void switchPhysicsObjFixAngle(){
		isFixAngle = !isFixAngle;
		body.setFixedRotation(isFixAngle);
	}
	
	public long getDestinationSamanRoleId() {
		return destinationSamanRoleId;
	}

	public void setDestinationSamanRoleId(long roleId) {
		this.destinationSamanRoleId = roleId;
	}

	public Map<Long, Integer> getSpringBallContactUsersNum() {
		return springBallContactUsersNum;
	}

	public void initSpecTypeAttribute(World world) {
		switch (this.physicsObjProperty.getSpecType()) {
		case balloon:
		{
			specInitBalloon();
			break;
		}
		case bullet:
		{
			float[] initV = this.physicsObjProperty.getInitV();
			Vec2 v = CalUtil.pRotateByDegree(new Vec2(initV[0],initV[1]), this.body.getAngle());
			this.body.setLinearVelocity(v);
			break;
		}
		case fuwen:
		{
			float[] initV = this.physicsObjProperty.getInitV();
			this.movingSpeed = initV[0];
			Vec2 v = CalUtil.pRotate(new Vec2(initV[0],initV[1]), this.body.getAngle());
			this.body.setLinearVelocity(v);
			float movingSpeedA = 40f;
			this.movingForce = this.body.getMass() * movingSpeedA;
			this.body.setGravityScale(0);
			this.body.setSleepingAllowed(false);
			tryApplyOrRemoveForce();
			break;
		}
		case cloud:
		{
			PrismaticJointDef jointDef = new PrismaticJointDef();
			Vec2 axis = new Vec2(1, 0);
			axis = CalUtil.pRotate(axis, this.body.getAngle());
			jointDef.initialize(this.getFightRoom().getBackgroundPhysicsObj().getBody(), this.body, this.getBody().getPosition(), axis);
			Joint joint = this.getFightRoom().getWorld().createJoint(jointDef);
			this.cloudJoint = joint;
		}
		default:
			break;
		}
	}

	public void specInitBalloon() {
//		local linearDamping = GameGlobalConfig.default_fps * -gravityY / (GameGlobalConfig.default_fps * v * (1 + self.skillChangeBalloonVRate) - gravityY);
//		local b2Body = self.box2dBody:getBody()
//		b2Body:SetLinearDamping(linearDamping)
//		self.box2dBody:setForceToCenter(cc.p(0, -self.fightContext.fightScene.gravityY * 2 * self.box2dBody:getBody():GetMass()))
		float linearDamping = FightConfig.fps * this.fightRoom.getWorld().getGravity().y * -1 / (FightConfig.fps * default_balloonV * (1 + this.skillChangeBalloonVRate) - this.fightRoom.getWorld().getGravity().y);
		this.body.setLinearDamping(linearDamping);
		this.body.applyForceToCenter(new Vec2(0, this.fightRoom.getWorld().getGravity().y * this.body.getMass() * -2f));
		this.body.setSleepingAllowed(false);
	}
	
	public void tryApplyOrRemoveForce() {
		switch (this.physicsObjProperty.getSpecType()) {
		case fuwen:
			float v = this.body.getLinearVelocity().length();
			if(v > this.movingSpeed) {
				this.body.m_force.x = 0f;
				this.body.m_force.y = 0f;
			} else if(v < this.movingSpeed) {
				this.body.m_force.x = 0f;
				this.body.m_force.y = 0f;
				Vec2 force = new Vec2(this.movingForce, 0);
				force =  CalUtil.pRotate(force, this.body.getAngle());
				this.body.applyForceToCenter(force);
			}
			break;
		case balloon:
			break;
		default:
			this.refreshSwimmingState();
			if(this.isSwimming) {
				this.body.m_force.y = this.body.getMass() * this.fightRoom.getPhysicsConfigProperty().getGravityY() * - 1.5f;
			} else {
				this.body.m_force.y = 0f;
			}
			break;
		}
	}
	
	public void refreshSwimmingState() {
		if(this.body.getType() != BodyType.DYNAMIC || this.fightRoom.getWaterObjs().size() == 0) {
			return;
		}
		if(this.body.getFixtureList().getDensity() > 0.06) {
			return;
		}
		for(PhysicsObj waterObj : this.fightRoom.getWaterObjs()) {
			if(waterObj.getBody().getFixtureList().testPoint(this.getBody().getWorldCenter())) {
				this.isSwimming = true;
				return;
			}
		}
		this.isSwimming = false;
	}
	
	public void dead() {
		this.sendPhysicsObjDeadEvent();
		this.fightRoom.getWorld().destroyBody(this.body);
		this.body = null;
	}
	
	public void sendPhysicsObjDeadEvent() {
		ConjurePhysicsObjEventProto.Builder builder = ConjurePhysicsObjEventProto.newBuilder();
		builder.setEventType(EventType.conjure_physicsobj_dead_event_type.getCode());
		builder.setFrameIndex(this.fightRoom.getFrameIndex());
		builder.setPhysicsObj(this.buildProto(false));
		for (FightPlayer player : this.fightRoom.getFightPlayers()) {
			player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder);
		}
	}
	
	/**
	 * 摧毁再重置，注意，钉子会全部不生效
	 * @param rate
	 */
	public void expand(float rate) {
		this.body.destroyFixture(this.body.getFixtureList());
		this.skillExpandRate += rate;
		createFixture(physicsObjProperty, this, body);
	}
	
	public void update() {
		tryApplyOrRemoveForce();
	}
	
	public Body getBody() {
		return body;
	}

	public int getId() {
		return id;
	}

	public long getCreatorRoleId() {
		return creatorRoleId;
	}

	public FightRoom getFightRoom() {
		return fightRoom;
	}

	public PhysicsObjProperty getPhysicsObjProperty() {
		return physicsObjProperty;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public List<PhysicsNail> getNails() {
		return nails;
	}
	public boolean isConjured() {
		return isConjured;
	}
	
	public void setConjured(boolean isConjured) {
		this.isConjured = isConjured;
	}
	public void changeDensityRate(float rate) {
		this.skillChangeDensityRate += rate;
		this.body.getFixtureList().setDensity(physicsObjProperty.getDensity() * this.densityPercent / 100 * (1 + this.skillChangeDensityRate));
		this.body.resetMassData();
	}

	public void changeFrictionRate(float rate) {
		this.skillChangeFrictionRate += rate;
		this.body.getFixtureList().setFriction(physicsObjProperty.getFriction() * this.frictionPercent / 100 * (1 + this.skillChangeFrictionRate));
	}

	public void changeBalloonVRate(float rate) {
		this.skillChangeBalloonVRate += rate;
		this.specInitBalloon();
	}

	public void changeToFixRotation() {
		getBody().setFixedRotation(true);
		this.skillChangeFixRotation = true;
	}

	public int getLayerNum() {
		return layerNum;
	}

	public PhysicsObj getHallTransferDoorTarget() {
		return hallTransferDoorTarget;
	}

	public void setHallTransferDoorTarget(PhysicsObj hallTransferDoorTarget) {
		this.hallTransferDoorTarget = hallTransferDoorTarget;
	}

	public void balloonCheck() {
		if(this.isExploding) {
			return;
		}
		if(this.getPhysicsObjProperty().getSpecType() == PhysicsObjSpecType.balloon) {
			//超时检查
			boolean explode = false;
			if(this.fightRoom.getFrameIndex() - createFrameIndex >= max_live_frame_num) {
				explode = true;
			}
			if(!explode) {
				ContactEdge contactEdge = this.body.getContactList();
				int count = 0;
				while(contactEdge != null) {
					Contact contact = contactEdge.contact;
					if(contact.m_nodeA == contactEdge) {
						Object userData = contact.getFixtureB().getBody().getUserData();
						if(userData instanceof FightUnit) {
							WorldManifold worldManifold = new WorldManifold();
							contact.getWorldManifold(worldManifold);
							if(worldManifold.normal.y > 0.707) {
								count++;
							}
						}
					} else if(contact.m_nodeB == contactEdge) {
						Object userData = contact.getFixtureA().getBody().getUserData();
						if(userData instanceof FightUnit) {
							WorldManifold worldManifold = new WorldManifold();
							contact.getWorldManifold(worldManifold);
							if(worldManifold.normal.y < 0.707) {
								count++;
							} 
						}
					}
					contactEdge = contactEdge.next;
				}
				if(count >= 3) { 
					explode = true;
				}
			}
			
			if(explode) { 
				this.isExploding = true;
				fightRoom.performAtFixFrameIndex(new FixFrameEvent(ServerConfig.system_role_id, ServerEventType.delay_balloon_explode, fightRoom.getFrameIndex() + 30) {
					@Override
					public void executeEvent() {
						Vec2 pos = body.getPosition();
						ConjurePhysicsObjEventProto.Builder builder = ConjurePhysicsObjEventProto.newBuilder();
						builder.setEventType(EventType.balloon_explode.getCode());
						builder.setPhysicsObj(buildProto(false));
						builder.setFrameIndex(fightRoom.getFrameIndex());
						fightRoom.broadcastConjurePhysicsObjEvent(builder, null);
						fightRoom.removePhysicsObj(PhysicsObj.this);
						
						Vec2 baseImpulse = new Vec2(-fightRoom.getPhysicsConfigProperty().getBombImpulse() * 2f / 3, 0f);
						fightRoom.bombExplode(null, pos.x, pos.y, false, default_qigong_explode_range, baseImpulse);
						//remove
						fightRoom.removePhysicsObj(PhysicsObj.this);
						fightRoom.getWorld().destroyBody(body);
						body = null;
					}
				});
			}
		}
	}
	
}
