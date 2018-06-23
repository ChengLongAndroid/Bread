package com.funcy.g01.base.bo.fight;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Filter;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.contacts.ContactEdge;
import org.jbox2d.dynamics.joints.JointEdge;
import org.jbox2d.dynamics.joints.JointType;
import org.jbox2d.dynamics.joints.RopeJoint;
import org.jbox2d.dynamics.joints.RopeJointDef;

import com.funcy.g01.base.bo.fight.FightSkillTrigger.RemoveUnitResult;
import com.funcy.g01.base.data.PhysicsConfigProperty;
import com.funcy.g01.base.data.PhysicsObjProperty;
import com.funcy.g01.base.data.UnitProperty;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.net.CmdStatus;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.CommonEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.EmptyEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.RefreshUnitSkillChangeInfoEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.SpringBallTakeEffect;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.UnitInfoProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.UnitWinEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.UnitSkillChangeInfoProto;
import com.funcy.g01.dispatcher.bo.RoomType;

public class FightUnit {

	private long roleId;
	
	private UnitProperty unitProperty;
	
	private Body body;
	
	private PhysicsConfigProperty physicsConfigProperty;
	
	private FightRoom fightRoom;
	
	private boolean isDead = false;
	
	public static final Logger logger = Logger.getLogger(FightUnit.class);
	
	private FightPlayer fightPlayer;
	
	private boolean haveGotCheese = false;
	
	private boolean haveWon = false;
	
	private boolean haveGotDropItem = false;
	
	private boolean tranforingDropItem = false;
	
	private boolean isSpec = false;
	
	private UnitMovingState movingState = UnitMovingState.standing;
	
	private ServerFightUnitState serverFightUnitState = ServerFightUnitState.standFaceRight;
	
	private float movingForce;

	private PhysicsObjProperty dropItemPhysicsObjProperty;
	
	private int lastSynInfoFrameIndex;
	
	private boolean haveWarnCanNotEnterBeforeAllHaveEnter = false;
	
	private int lastContactTransferFrameIndex = 0;
	
	private long connectUnitRoleId = 0;
	
	private int becomeSoulFrameIndex = 0;
	
	private float maxSoulTime = 0f;
	
	private float skillIncUseSkillSpeed = 0f;
	
	private float skillIncUseSkillRange = 0f;
	
	private float maxMovingSpeed;
	
	private float skillChangeDensityRate = 0f;
	
	private float skillChangeFrictionRate = 0f;
	
	private float skillChangeSpeedRate = 0f;

	private float skillChangeJumpHeightRate = 0f;
	
	private float skillchangeRadiusRate = 0f;

	private float skillChangeSpeedRate2 = 0f;

	private float skillChangeSpeed2MaxTime = 0f;

	private int skillChangeSpeed2FrameIndex = 0;
	
	private float skillChangeSpeedRate3 = 0f;
	
	private float skillChangeJumpHeightRate2 = 0f;
	
	private float skillChangeJumpHeightRate3 = 0f;
	
	private EnumMap<PhysicsObjSpecType, Float> skillChangeConjureTimeMap = new EnumMap<PhysicsObjSpecType, Float>(PhysicsObjSpecType.class); 
	
	private Map<Long, Boolean> skillHelpMouseRebornMap = new HashMap<Long, Boolean>();
	
	private boolean isFlying = false;
	
	private boolean isSwimming = false;
	
	private boolean isDive = false;
	
	private boolean newComerHadReborn = false;
	
	private float jumpCheckVY;
	
	private boolean needCheckJump = false;
	
	private boolean isSkipRefreshPos = false;
	
	public enum UnitMovingState {
		standing, movingRight, movingLeft
	}
	
	
	public FightUnit(long roleId, UnitProperty unitProperty) {
		this.roleId = roleId;
		this.unitProperty = unitProperty;
	}
	
	public UnitInfoProto.Builder buildProto() {
		return buildProto(false);
	}
	
	public UnitInfoProto.Builder buildProto(boolean isInit) {
		UnitInfoProto.Builder builder = UnitInfoProto.newBuilder();
		builder.setPosX((int) (this.body.getPosition().x * 1000f));
		builder.setPosY((int) (this.body.getPosition().y * 1000f));
		builder.setVelocityX((int) (this.body.getLinearVelocity().x * 100f));
		builder.setVelocityY((int) (this.body.getLinearVelocity().y * 100f));
		builder.setState(this.serverFightUnitState.ordinal());
		if(isInit) {
			builder.setIsSpec(this.isSpec);
			builder.setHaveGotCheese(this.haveGotCheese);
			if(this.connectUnitRoleId != 0) {
				builder.setConnectOtherUnitRoleId(this.connectUnitRoleId);
			}
			if(!this.haveGotDropItem && this.tranforingDropItem) {
				builder.setTranforingDropItemXmlId(this.dropItemPhysicsObjProperty.getId());
			}
			if(this.isSpec) {
				if(this.fightPlayer.getSamanSkills() != null) {
					for(FightSkill fightSkill : this.fightPlayer.getSamanSkills()) {
						builder.addSkills(fightSkill.buildProto());
					}
				}
			} else {
				if(this.fightPlayer.getMouseSkills() != null) {
					for(FightSkill fightSkill : this.fightPlayer.getMouseSkills()) {
						builder.addSkills(fightSkill.buildProto());
					}
				}
			}
			builder.setSkillChangeInfo(buildSkillChangeInfoProto());
		}
		return builder;
	}
	
	public void broadcastSkillChangeInfo() {
		RefreshUnitSkillChangeInfoEventProto.Builder builder = RefreshUnitSkillChangeInfoEventProto.newBuilder();
		builder.setEventType(EventType.refresh_unit_skill_add_info.getCode());
		builder.setRoleId(this.getRoleId());
		builder.setFrameIndex(fightRoom.getFrameIndex());
		builder.setSkillChangeInfo(this.buildSkillChangeInfoProto());
		for(FightPlayer fightPlayer : fightRoom.getFightPlayers()) {
			fightPlayer.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder);
		}
	}

	public UnitSkillChangeInfoProto.Builder buildSkillChangeInfoProto() {
		UnitSkillChangeInfoProto.Builder changeInfoBuilder =UnitSkillChangeInfoProto.newBuilder();
		if(this.becomeSoulFrameIndex != 0) {
			float leftTime = this.becomeSoulFrameIndex * 1f / FightConfig.fps + this.maxSoulTime - this.fightRoom.getFrameIndex() * 1f / FightConfig.fps;
			if(leftTime > 0) {
				changeInfoBuilder.setSoulTimeLeftTime((int) (leftTime * 1000));
			}
		}
		changeInfoBuilder.setSkillIncUseSkillRange((int) (this.skillIncUseSkillRange * 100f));
		changeInfoBuilder.setSkillIncUseSkillSpeed((int) (this.skillIncUseSkillSpeed * 100f));
		changeInfoBuilder.setSkillChangeJumpHeightRate((int) (this.skillChangeJumpHeightRate * 100f));
		changeInfoBuilder.setSkillChangeFrictionRate((int) (this.skillChangeFrictionRate * 100f));
		changeInfoBuilder.setSkillChangeSpeedRate((int) (this.skillChangeSpeedRate * 100f));
		changeInfoBuilder.setSkillChangeRadiusRate((int) (this.skillchangeRadiusRate * 100f));
		if(this.skillChangeSpeedRate2 != 0) {
			changeInfoBuilder.setSkillChangeSpeedRate2((int) (this.skillChangeSpeedRate2 * 100f));
			changeInfoBuilder.setSkillChangeSpeed2LeftTime((int) ((this.skillChangeSpeed2MaxTime + this.skillChangeSpeed2FrameIndex / FightConfig.fps - this.fightRoom.getFrameIndex() / FightConfig.fps) * 100));
		}
		if (this.skillChangeJumpHeightRate2 != 0) {
			changeInfoBuilder.setSkillChangeJumpHeightRate2((int)(this.skillChangeJumpHeightRate2 * 100f));
		}
		if (this.skillChangeSpeedRate3 != 0) {
			changeInfoBuilder.setSkillChangeSpeedRate3((int)(this.skillChangeSpeedRate3 * 100f));
		}
		if (this.skillChangeJumpHeightRate3 != 0) {
			changeInfoBuilder.setSkillChangeJumpHeightRate3((int)(this.skillChangeJumpHeightRate3 * 100f));
		}
		for(Entry<PhysicsObjSpecType, Float> entry : this.skillChangeConjureTimeMap.entrySet()) {
			changeInfoBuilder.addSkillChangeConjureTimeMapKeys(entry.getKey().getCode());
			changeInfoBuilder.addSkillChangeConjureTimeMapVals((int)(entry.getValue() * 100));
		}
		return changeInfoBuilder;
	}
	
	
	public static FightUnit createNewFightUnit(FightRoom fightRoom, long roleId, World world, UnitProperty unitProperty, PhysicsConfigProperty physicsConfigProperty, Vec2 initPos, boolean isSpec) {
		FightUnit fightUnit = new FightUnit(roleId, unitProperty);
		fightUnit.fightRoom = fightRoom;
		fightUnit.physicsConfigProperty = physicsConfigProperty;
		BodyDef bodyDef = new BodyDef();
		bodyDef.setPosition(initPos);
		bodyDef.setType(BodyType.DYNAMIC);
		Body body = world.createBody(bodyDef);
		body.setSleepingAllowed(false);
		body.setFixedRotation(true);
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(unitProperty.getRadius() / 10);
		Fixture fixture = body.createFixture(circleShape, unitProperty.getDensity());
		fixture.setFriction(unitProperty.getFriction());
		fixture.setRestitution(unitProperty.getRestitution());
		Filter filter = new Filter();
		filter.categoryBits = unitProperty.getPhysicsCategory();
		filter.maskBits = unitProperty.getPhysicsContactMask();
		fixture.setFilterData(filter);
		fightUnit.body = body;
		fightUnit.isSpec = isSpec;
		fightUnit.movingForce = fightUnit.physicsConfigProperty.getMoveSpeedA() * fightUnit.body.getMass();
		body.setUserData(fightUnit);
		fightUnit.maxMovingSpeed = physicsConfigProperty.getMoveSpeed();
		
//		CircleShape sensorCircleShape = new CircleShape();
//		sensorCircleShape.setRadius(0.2f);
//		Fixture sensorFixture = body.createFixture(sensorCircleShape, unitProperty.getDensity());
//		sensorFixture.setFriction(unitProperty.getFriction());
//		sensorFixture.setRestitution(unitProperty.getRestitution());
//		Filter sensorfilter = new Filter();
//		sensorfilter.categoryBits = FightConfig.unit_sensor_category_mask;
//		sensorfilter.maskBits = FightConfig.unit_sensor_collision_mask;
//		sensorFixture.setFilterData(sensorfilter);
//		sensorFixture.setSensor(true);
//		System.out.println("unit movingForce:" + fightUnit.movingForce + " mass:" + fightUnit.body.getMass() + " gravity:" + world.getGravity().y);
		return fightUnit;
	}
	
	public int getLastSynInfoFrameIndex() {
		return lastSynInfoFrameIndex;
	}

	public void setLastSynInfoFrameIndex(int lastSynInfoFrameIndex) {
		this.lastSynInfoFrameIndex = lastSynInfoFrameIndex;
	}
	
	public float calPrepareConjureTime(PhysicsObjProperty physicsObjProperty, PhysicsConfigProperty physicsConfigProperty, boolean isReset) {
		float skillChangeConjureDelayRate = 0f;
		EnumMap<PhysicsObjSpecType, Float> changeConjureTimeMap = this.getSkillChangeConjureTimeMap();
		Float val = changeConjureTimeMap.get(physicsObjProperty.getSpecType());
		if(val != null) {
			skillChangeConjureDelayRate = val;
			if(isReset) {
				changeConjureTimeMap.put(physicsObjProperty.getSpecType(), 0f);
			}
		}
		return physicsConfigProperty.getConjureObjDelay() / (1f + fightPlayer.getFightUnit().getSkillIncUseSkillSpeed()) * (1f - skillChangeConjureDelayRate);
	}
	
	public void fly() {
		this.body.setGravityScale(0);
		this.isFlying = true;
	}
	
	public void flyJump() {
		this.body.setGravityScale(1);
		this.fightRoom.performAtFixFrameIndex(new FixFrameEvent(this.roleId, ServerEventType.flyjump_delay_change_gravity, (int) (this.fightRoom.getFrameIndex() + 0.4 * FightConfig.fps)) {
			@Override
			public void executeEvent() {
				FightUnit.this.body.setGravityScale(0);
				FightUnit.this.body.getLinearVelocity().y = -0.03f;
			}
		});
	}
	
	public void stopFly() {
		this.body.setGravityScale(1);
		this.isFlying = false;
	}
	
	private SynPeriodEvent synPeriodEvent;

	/**
	 * 
	 * @param otherObj
	 * @param contact 
	 * @param isA 
	 * @return 是否取消该次碰撞 true 取消
	 */
	public boolean contactObj(Object otherObj, Contact contact, boolean isA) {
		if(otherObj instanceof PhysicsObj) {
			PhysicsObj physicsObj = (PhysicsObj) otherObj;
			PhysicsObjSpecType specType = physicsObj.getPhysicsObjProperty().getSpecType();
			if(specType == PhysicsObjSpecType.cheese) {
				if(!this.haveGotCheese && !this.tranforingDropItem) {
					this.haveGotCheese = true;
					EmptyEventProto.Builder builder = EmptyEventProto.newBuilder();
					builder.setEventType(EventType.got_cheese.getCode());
					builder.setFrameIndex(this.fightRoom.getFrameIndex());
					builder.setRoleId(this.getRoleId());
					for (FightPlayer fightPlayer : this.fightRoom.getFightPlayers()) {
						fightPlayer.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder);
					}
				}
				return true;
			} else if(specType == PhysicsObjSpecType.cheeseDestination) {
				if(physicsObj.getDestinationSamanRoleId() != 0 && this.getFightPlayer().getCampType() != CampType.none && this.getFightPlayer().getCampType() != physicsObj.getPhysicsObjProperty().getCampType()) {
					return true;
				}
				if (this.tranforingDropItem) {
					if (!haveGotDropItem) {
						this.fightPlayer.unitTransforDropItem();
						this.tranforingDropItem = false;
						this.haveGotDropItem = true;
						this.dropItemPhysicsObjProperty = null;
					}
				}else if(this.haveGotCheese && !this.haveWon) {
					boolean canWin = true;
					if(this.isSpec) {
						for(FightPlayer fightPlayer : this.fightRoom.getFightPlayers())  {
							if(fightPlayer.getRoleId() != this.fightPlayer.getRoleId() && !fightPlayer.isUnitIsSpec() && fightPlayer.isUnitExist()) {
								canWin = false;
							}
						}
					}
					if(canWin) {
						this.unitTransformCheeseSuccess(physicsObj.getDestinationSamanRoleId());
					} else {
						if(this.isSpec && !this.haveWarnCanNotEnterBeforeAllHaveEnter) {
							this.haveWarnCanNotEnterBeforeAllHaveEnter = true;
							EmptyEventProto.Builder builder = EmptyEventProto.newBuilder();
							builder.setEventType(EventType.saman_can_not_enter_before_all_have_enter.getCode());
							builder.setRoleId(this.getRoleId());
							builder.setFrameIndex(this.fightRoom.getFrameIndex());
							this.fightPlayer.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder);
						}
					}
				}
				return true;
			}else if(specType == PhysicsObjSpecType.dropItem){
				if(!this.tranforingDropItem && !this.haveGotDropItem) {
					this.tranforingDropItem = true;
					this.dropItemPhysicsObjProperty = physicsObj.getPhysicsObjProperty();
					EmptyEventProto.Builder builder = EmptyEventProto.newBuilder();
					builder.setEventType(EventType.got_dropItem.getCode());
					builder.setFrameIndex(this.fightRoom.getFrameIndex());
					builder.setRoleId(this.getRoleId());
					for (FightPlayer fightPlayer : this.fightRoom.getFightPlayers()) {
						fightPlayer.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder);
					}
				}
				return true;
			} else if(specType == PhysicsObjSpecType.mouseBorn || specType == PhysicsObjSpecType.specMouseBorn) {
				return true;
			} else if(specType == PhysicsObjSpecType.springBall) {
				if(physicsObj.getSpringBallContactUsersNum().get(this.getRoleId()) == null) {
					physicsObj.getSpringBallContactUsersNum().put(this.getRoleId(), 1);
					float impluse = 22f;
					if(isA && contact.getManifold().localNormal.y > 1.732f / 2) {
						impluse *= 1.5f;
					} else if(!isA && contact.getManifold().localNormal.y < -1.732f / 2) {
						impluse *= 1.5f;
					}
					this.getBody().getLinearVelocity().y = 0f;
					this.getBody().applyLinearImpulse(new Vec2(0, impluse), new Vec2(0, 0), true);
					SpringBallTakeEffect.Builder builder2 = SpringBallTakeEffect.newBuilder();
					builder2.setRoleId(this.getRoleId());
					builder2.setEventType(EventType.springball_take_effect.getCode());
					builder2.setFrameIndex(this.fightRoom.getFrameIndex());
					builder2.setUnitInfo(this.buildProto());
					builder2.setSpringBallId(physicsObj.getId());
					for (FightPlayer player : this.fightRoom.getFightPlayers()) {
						player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder2);
					}
				}
				return true;
			} else if(specType == PhysicsObjSpecType.transferDoorA || specType == PhysicsObjSpecType.transferDoorB) {
				if(this.fightRoom.getFrameIndex() - this.lastContactTransferFrameIndex > 10) {
					if(this.fightRoom.getRoomType() == RoomType.hall) {
						if(physicsObj.getHallTransferDoorTarget() != null) {
							final PhysicsObj obj = physicsObj.getHallTransferDoorTarget();
							this.lastContactTransferFrameIndex = this.fightRoom.getFrameIndex();
							this.fightRoom.executeRightNow(new SynEvent(ServerConfig.system_role_id, "transferdoor") {
								@Override
								public void executeEvent() {
									if(FightUnit.this.getBody() == null || obj.getBody() == null) {
										return;
									}
									FightUnit.this.getBody().setTransform(obj.getBody().getTransform().p, FightUnit.this.body.getAngle());
									CommonEventProto.Builder builder2 = CommonEventProto.newBuilder();
									builder2.setRoleId(FightUnit.this.getRoleId());
									builder2.setEventType(EventType.unit_reset_pos.getCode());
									builder2.setFrameIndex(FightUnit.this.fightRoom.getFrameIndex());
									builder2.setUnitInfo(FightUnit.this.buildProto());
									for (FightPlayer player : FightUnit.this.fightRoom.getFightPlayers()) {
										player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder2);
									}
								}
							});
						}
					} else {
						PhysicsObjSpecType transferToType = (specType == PhysicsObjSpecType.transferDoorA ? PhysicsObjSpecType.transferDoorB : PhysicsObjSpecType.transferDoorA);
						for(final PhysicsObj obj : this.fightRoom.getPhysicsObjs().values()) {
							if(obj.getPhysicsObjProperty().getSpecType() == transferToType) {
								this.lastContactTransferFrameIndex = this.fightRoom.getFrameIndex();
								this.fightRoom.executeRightNow(new SynEvent(ServerConfig.system_role_id, "transferdoor") {
									@Override
									public void executeEvent() {
										if(FightUnit.this.getBody() == null || obj.getBody() == null) {
											return;
										}
										FightUnit.this.getBody().setTransform(obj.getBody().getTransform().p, FightUnit.this.body.getAngle());
										CommonEventProto.Builder builder2 = CommonEventProto.newBuilder();
										builder2.setRoleId(FightUnit.this.getRoleId());
										builder2.setEventType(EventType.unit_reset_pos.getCode());
										builder2.setFrameIndex(FightUnit.this.fightRoom.getFrameIndex());
										builder2.setUnitInfo(FightUnit.this.buildProto());
										for (FightPlayer player : FightUnit.this.fightRoom.getFightPlayers()) {
											player.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder2);
										}
									}
								});
								break;
							}
						}
					}
				}
				return true;
			}
		}
		return false;
	}
	
	public void unitTransformCheeseSuccess(long samanRoleId){
		RemoveUnitResult removeUnitResult = FightSkillTrigger.beforeUnitRemoveTriggerSkill(this.fightRoom, this, 1);
		this.fightPlayer.unitWin(samanRoleId);
		this.fightRoom.removeUnit(this);
		this.body = null;
		this.haveWon = true;
		this.haveGotCheese = false;
		
		if (this.fightRoom.getUnitWinNum() == 1) {
			this.fightPlayer.addRankingNo1Num();
		}
		
		UnitWinEventProto.Builder builder = UnitWinEventProto.newBuilder();
		builder.setEventType(EventType.unit_win.getCode());
		builder.setRoleId(this.getRoleId());
		builder.setFrameIndex(this.fightRoom.getFrameIndex());
		builder.setSamanRoleId(samanRoleId);
		for (FightPlayer fightPlayer : this.fightRoom.getFightPlayers()) {
			fightPlayer.respond("fightRoom.broadcast", CmdStatus.notDecrypt, builder);
		}
	}
	
	public long getRoleId() {
		return roleId;
	}

	public Body getBody() {
		return body;
	}

	public void stopMoving() {
		if(this.body.m_force.x != 0) {
			this.body.m_force.x = 0;
		}
		if(this.movingState == UnitMovingState.standing) {
			return;
		}
		this.movingState = UnitMovingState.standing;
//		System.out.println("stopMoving");
//		System.out.println(this.fightRoom.getFrameIndex() + "," + this.body.getLinearVelocity() + ","+  this.body.getPosition());
//		if(this.synPeriodEvent == null) {
//			this.synPeriodEvent = new SynPeriodEvent(ServerEventType.debug_event, 1000/60) {
//				@Override
//				public void executeEvent() {
//					System.out.println(FightUnit.this.fightRoom.getFrameIndex() + "," + FightUnit.this.body.getLinearVelocity() + FightUnit.this.body.getPosition());
//				}
//			};
//			this.fightRoom.addPeriodTimeExecuteEvent(this.synPeriodEvent);
//			this.fightRoom.performAtFixFrameIndex(new FixFrameEvent(-10000, ServerEventType.debug_event, this.fightRoom.getFrameIndex() + 60) {
//				@Override
//				public void executeEvent() {
//					FightUnit.this.fightRoom.removePeriodTimeExecuteEvent(FightUnit.this.synPeriodEvent);
//					FightUnit.this.synPeriodEvent = null;
//				}
//			});
//		}
	}
	
	public void connectWithOtherUnit(FightUnit otherUnit) {
		RopeJointDef ropeJointDef = new RopeJointDef();
		ropeJointDef.maxLength = 13f;
		ropeJointDef.bodyA = this.getBody();
		ropeJointDef.bodyB = otherUnit.getBody();
		ropeJointDef.localAnchorB.set(0, 0);
		ropeJointDef.localAnchorA.set(0, 0);
		World world = fightRoom.getWorld();
		world.createJoint(ropeJointDef);
		this.connectUnitRoleId = otherUnit.getRoleId();
		otherUnit.connectUnitRoleId = this.getRoleId();
	}
	
	public int countContactNum() {
		ContactEdge contactEdge = this.body.getContactList();
		int count = 0;
		while(contactEdge != null) {
			count++;
			contactEdge = contactEdge.next;
		}
		return count;
	}
	
	public void startMoving(boolean isRight, UnitInfoProto unitInfoProto) {
		this.stopMoving();
		this.movingState = isRight ? UnitMovingState.movingRight : UnitMovingState.movingLeft;
		this.tryApplyOrRemoveForce();
		this.destroyRopeJoint();
		this.serverFightUnitState = ServerFightUnitState.values()[unitInfoProto.getState()];
	
//		System.out.println(this.fightRoom.getFrameIndex() + "," + this.body.getLinearVelocity() + ","+  this.body.getPosition());
//		if(this.synPeriodEvent == null) {
//			this.synPeriodEvent = new SynPeriodEvent(ServerEventType.debug_event, 1000/60) {
//				@Override
//				public void executeEvent() {
//					System.out.println(FightUnit.this.fightRoom.getFrameIndex() + "," + FightUnit.this.body.getLinearVelocity() + FightUnit.this.body.getPosition());
//				}
//			};
//			this.fightRoom.addPeriodTimeExecuteEvent(this.synPeriodEvent);
//			this.fightRoom.performAtFixFrameIndex(new FixFrameEvent(-10000, ServerEventType.debug_event, this.fightRoom.getFrameIndex() + 60) {
//				@Override
//				public void executeEvent() {
//					FightUnit.this.fightRoom.removePeriodTimeExecuteEvent(FightUnit.this.synPeriodEvent);
//					FightUnit.this.synPeriodEvent = null;
//				}
//			});
//		}
	}
	
	public void startDive() {
		this.isDive = true;
	}
	
	public void stopDive() {
		this.isDive = false;
	}
	
	public void jump(UnitInfoProto unitInfoProto) {
		refreshByUnitInfo(unitInfoProto);
		destroyRopeJoint();
		this.body.setGravityScale(2);
		this.jumpCheckVY = this.body.getLinearVelocity().y / 7f;
		this.needCheckJump = true;
	}
	
	public void destroyRopeJoint() {
		JointEdge jointEdge = this.body.getJointList();
		while(jointEdge != null) {
			if(jointEdge.joint.getType() == JointType.ROPE) {
				PhysicsNail physicsNail = (PhysicsNail)jointEdge.joint.m_userData;
				if(physicsNail == null) { //伴侣模式的弹簧
					
				} else {
					if(physicsNail.getConnectUnit() == this && physicsNail.getPhysicsNailProperty().getConnectObjType() == 3) {
						this.fightRoom.getWorld().destroyJoint(jointEdge.joint);
					}
				}
			}
			jointEdge = jointEdge.next;
		}
	}
	
	public void refreshByUnitInfo(UnitInfoProto unitInfoProto) {
		if(this.isSkipRefreshPos) {
			return;
		}
//		System.out.println("before posX:" + this.body.getPosition().x + "," + this.body.getPosition().y + " cur body posX:" + unitInfoProto.getPosX() / 1000f + ", posY:" + unitInfoProto.getPosY() / 1000f);
		this.body.setTransform(new Vec2(unitInfoProto.getPosX() / 1000f, unitInfoProto.getPosY() / 1000f), this.body.getAngle());
		this.body.setLinearVelocity(new Vec2(unitInfoProto.getVelocityX() / 100f, unitInfoProto.getVelocityY() / 100f));
		this.serverFightUnitState = ServerFightUnitState.values()[unitInfoProto.getState()];
	}
	
	public void resetPosition(Vec2 vec2){
		this.body.setTransform(vec2,this.body.getAngle());
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public void quitFight() {
		this.fightRoom.removeUnit(this);
	}
	
	public FightPlayer getFightPlayer() {
		return fightPlayer;
	}

	public void setFightPlayer(FightPlayer fightPlayer) {
		this.fightPlayer = fightPlayer;
	}

	public void dead(boolean isTotalEnd, UnitDeadReasonType unitDeadReasonType) {
		if(this.isDead) {
			return;
		}
		this.fightPlayer.unitDead(unitDeadReasonType);
		this.isDead = true;
		if(!isTotalEnd) {
			this.fightRoom.removeUnit(this);
		}
		this.body = null;
		this.movingState = UnitMovingState.standing;
		this.haveWarnCanNotEnterBeforeAllHaveEnter = false;
		this.lastSynInfoFrameIndex = 0;
	}
	
	public void tryApplyOrRemoveForce() {
		Vec2 v = this.body.getLinearVelocity();
		this.refreshSwimmingState();
		if(this.movingState == UnitMovingState.movingLeft) {
			if(v.x < -this.maxMovingSpeed) {
				this.body.m_force.x = 0;
			} else {
				if(this.body.m_force.x != -this.movingForce) {
					if(!this.body.isAwake()) {
						this.body.setAwake(true);
					}
					this.body.m_force.x = -this.movingForce;
				}
			}
		} else if(this.movingState == UnitMovingState.movingRight) {
			if(v.x > this.maxMovingSpeed) {
				this.body.m_force.x = 0f;
			} else {
				if(this.body.m_force.x != this.movingForce) {
					if(!this.body.isAwake()) {
						this.body.setAwake(true);
					}
					this.body.m_force.x = this.movingForce;
				}
			}
		} else {
			this.body.m_force.x = 0;
		}
		
		if(this.isSwimming) {
			if(this.isDive) {
				if(this.haveGotCheese || this.haveGotDropItem) {
					this.body.m_force.y = this.body.getMass() * this.fightRoom.getPhysicsConfigProperty().getGravityY() * -0.45f;
				} else {
					this.body.m_force.y = this.body.getMass() * this.fightRoom.getPhysicsConfigProperty().getGravityY() * -0.5f;
				}
			} else {
				if(this.haveGotCheese || this.haveGotDropItem) {
					this.body.m_force.y = this.body.getMass() * this.fightRoom.getPhysicsConfigProperty().getGravityY() * -0.9f;
				} else {
					this.body.m_force.y = this.body.getMass() * this.fightRoom.getPhysicsConfigProperty().getGravityY() * -1.5f;
				}
			}
		} else {
			this.body.m_force.y = 0f;
		}
	}
	
	public void refreshSwimmingState() {
		if(this.fightRoom.getWaterObjs().size() == 0) {
			return;
		}
		for(PhysicsObj physicsObj : this.fightRoom.getWaterObjs()) {
			Body body = physicsObj.getBody();
			if(body!=null){
				Fixture fixture = body.getFixtureList();
				if(fixture!=null && this.getBody()!=null){
					if(fixture.testPoint(this.getBody().getWorldCenter())){
						if(!this.isSwimming) {
							this.body.setLinearDamping(0.2f);
						}
						this.isSwimming = true;
						return;
					}
				}
			}
		}
		if(this.isSwimming) {
			this.body.setLinearDamping(0f);
		}
		this.isSwimming = false;
	}
	
	public void update() {
		if(this.isDead) {
			return;
		}
		if(this.fightRoom.getFrameIndex() - this.lastSynInfoFrameIndex > FightConfig.unit_syn_frame_num && this.movingState != UnitMovingState.standing) {
			this.movingState = UnitMovingState.standing;
			this.lastSynInfoFrameIndex = this.fightRoom.getFrameIndex();
		}
		tryApplyOrRemoveForce();
		checkAndModifyJump();
//		logger.info(String.format("unit state:%d, force:%f,%f, v:%f,%f", this.movingState.ordinal(), this.body.m_force.x, this.body.m_force.y, this.body.getLinearVelocity().x, this.body.getLinearVelocity().y));
	}

	private void checkAndModifyJump() {
		if(!needCheckJump) {
			return;
		}
		if(this.serverFightUnitState == ServerFightUnitState.jumpLeft || this.serverFightUnitState == ServerFightUnitState.jumpRight || this.serverFightUnitState == ServerFightUnitState.jumpStand) {
			if(this.body.getLinearVelocity().y < 0f) {
				this.body.setGravityScale(1);
				this.needCheckJump = false;
			} else if(this.body.getLinearVelocity().y < this.jumpCheckVY) {
				this.body.setGravityScale(0.5f);
			}
		} else {
			this.needCheckJump = false;
			this.body.setGravityScale(1);
		}
	}

	public boolean isInFightActionTimeout(int frameIndex) {
		boolean isTimeout = frameIndex - this.fightPlayer.getLastActionFrame() >= FightConfig.unit_action_timeout_frames;
		this.fightPlayer.setLastFightIsNotAction(true);
		return isTimeout;
	}
	
	public boolean isInFightActionPrepareTimeout(int frameIndex) {
		return frameIndex - this.fightPlayer.getLastActionFrame() >= FightConfig.unit_action_prepare_timeout_frames;
	}

	public boolean isHaveGotCheese() {
		return haveGotCheese;
	}

	public void setHaveGotCheese(boolean haveGotCheese) {
		this.haveGotCheese = haveGotCheese;
	}
	public float getSkillIncUseSkillSpeed() {
		return skillIncUseSkillSpeed;
	}

	public void setSkillIncUseSkillSpeed(float skillIncUseSkillSpeed) {
		this.skillIncUseSkillSpeed = skillIncUseSkillSpeed;
	}

	public float getSkillIncUseSkillRange() {
		return skillIncUseSkillRange;
	}

	public void setSkillIncUseSkillRange(float skillIncUseSkillRange) {
		this.skillIncUseSkillRange = skillIncUseSkillRange;
	}
	
	public void changeJumpHeight(float rate) {
		this.skillChangeJumpHeightRate += rate;
	}

	public void changeDensity(float rate) {
		this.skillChangeDensityRate += rate;
		this.body.getFixtureList().setDensity(unitProperty.getDensity() * (1 + this.skillChangeDensityRate));
		this.body.resetMassData();
	}

	public void changeFriction(float rate) {
		this.skillChangeFrictionRate += rate;
		this.body.getFixtureList().setFriction(unitProperty.getFriction() * (1 + this.skillChangeFrictionRate));
	}
	
	public void changeMovingSpeed(float rate) {
		this.skillChangeSpeedRate += rate;
		this.recalMovingSpeed();
	}
	
	public void recalMovingSpeed() {
		this.maxMovingSpeed = physicsConfigProperty.getMoveSpeed() * (1 + this.skillChangeSpeedRate) * (1 + this.skillChangeSpeedRate2);
		if(this.isFlying) {
			this.maxMovingSpeed = this.maxMovingSpeed * 0.4f;
		}
	}

	public void changeRadius(float rate) {
		this.skillchangeRadiusRate += rate;
		this.body.getFixtureList().getShape().setRadius(this.unitProperty.getRadius() / 10 * (1 + this.skillchangeRadiusRate));
		this.body.resetMassData();
	}

	public float getSkillChangeSpeedRate() {
		return skillChangeSpeedRate;
	}

	public void setSkillChangeSpeedRate(float skillChangeSpeedRate) {
		this.skillChangeSpeedRate = skillChangeSpeedRate;
	}

	public float getSkillChangeJumpHeightRate() {
		return skillChangeJumpHeightRate;
	}

	public void setSkillChangeJumpHeightRate(float skillChangeJumpHeightRate) {
		this.skillChangeJumpHeightRate = skillChangeJumpHeightRate;
	}

	public void changeToSoul(float maxSoulTime, int frameIndex) {
		this.maxSoulTime = maxSoulTime;
		this.becomeSoulFrameIndex = frameIndex;
	}
	
	public void changeMovingSpeed2(float maxChangeTime, int frameIndex, float changeRate) {
		this.skillChangeSpeedRate2 = changeRate;
		this.skillChangeSpeed2MaxTime = maxChangeTime;
		this.skillChangeSpeed2FrameIndex = frameIndex;
		this.recalMovingSpeed();
	}
	
	public void changeMovingSpeed2Timeout(int changeFrame) {
		if(this.skillChangeSpeed2FrameIndex == changeFrame) {
			this.skillChangeSpeedRate2 = 0;
			this.skillChangeSpeed2MaxTime = 0;
			this.skillChangeSpeed2FrameIndex = 0;
			this.recalMovingSpeed();
		}
	}

	public EnumMap<PhysicsObjSpecType, Float> getSkillChangeConjureTimeMap() {
		return skillChangeConjureTimeMap;
	}

	public Map<Long, Boolean> getSkillHelpMouseRebornMap() {
		return skillHelpMouseRebornMap;
	}

	public float getSkillChangeSpeedRate3() {
		return skillChangeSpeedRate3;
	}

	public void setSkillChangeSpeedRate3(float skillChangeSpeedRate3) {
		this.skillChangeSpeedRate3 = skillChangeSpeedRate3;
	}

	public float getSkillChangeJumpHeightRate2() {
		return skillChangeJumpHeightRate2;
	}

	public void setSkillChangeJumpHeightRate2(float skillChangeJumpHeightRate2) {
		this.skillChangeJumpHeightRate2 = skillChangeJumpHeightRate2;
	}

	public float getSkillChangeJumpHeightRate3() {
		return skillChangeJumpHeightRate3;
	}

	public void setSkillChangeJumpHeightRate3(float skillChangeJumpHeightRate3) {
		this.skillChangeJumpHeightRate3 = skillChangeJumpHeightRate3;
	}

	public boolean isNewComerHadReborn() {
		return newComerHadReborn;
	}

	public void setNewComerHadReborn(boolean newComerHadReborn) {
		this.newComerHadReborn = newComerHadReborn;
	}

	public boolean isSkipRefreshPos() {
		return isSkipRefreshPos;
	}

	public void setSkipRefreshPos(boolean isSkipRefreshPos) {
		this.isSkipRefreshPos = isSkipRefreshPos;
	}
	
}
