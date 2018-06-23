package com.funcy.g01.base.bo.fight;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.jbox2d.common.Vec2;

import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.data.PhysicsObjProperty;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ConjurePhysicsObjEventProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.PhysicsObjProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.UseSkillEventProto;

public class FightSkillTrigger {
	
	private static final Random random = new Random();

	public static class RemoveUnitResult {
		public boolean isDelayRemove = false;
		public float delayRemoveTime = 0f;
		public boolean isReborn = false;
		public int rebornSkillXmlId;
	}
	
	public static class ChangePhysicsObjResult {
		public float changeQigongRangeRate = 0f;
		public boolean changeQigongSkipSelf = false;
	}
	
	public static ChangePhysicsObjResult conjureBombTriggerSkill(final FightRoom fightRoom, final FightPlayer fightPlayer) {
		ChangePhysicsObjResult result = new ChangePhysicsObjResult();
		List<FightSkill> skills;
		if(fightPlayer.isUnitIsSpec()) {
			skills = fightPlayer.getSamanSkills();
		} else {
			skills = fightPlayer.getMouseSkills();
		}
		for(FightSkill fightSkill : skills) {
			if(fightSkill.getFightSkillProperty().getFightSkillTriggerType() != FightSkillTriggerType.conjureObj) {
				continue;
			} 
			switch(fightSkill.getFightSkillProperty().getFightSkillEffectType()) {
			case avoid_qigong_hurt:
				result.changeQigongSkipSelf = true;
				break;
			case super_qigong:
				result.changeQigongRangeRate += fightSkill.getParam1() / 100f;
				break;
			default:
				break;
			}
		}
		return result;
	}
	
	public static ChangePhysicsObjResult conjurePhysicsObjTriggerSkill(final FightRoom fightRoom, PhysicsObj physicsObj, final FightPlayer fightPlayer) {
		ChangePhysicsObjResult result = new ChangePhysicsObjResult();
		List<FightSkill> skills;
		if(fightPlayer.isUnitIsSpec()) {
			skills = fightPlayer.getSamanSkills();
		} else {
			skills = fightPlayer.getMouseSkills();
		}
		for(FightSkill fightSkill : skills) {
			if(fightSkill.getFightSkillProperty().getFightSkillTriggerType() != FightSkillTriggerType.conjureObj) {
				continue;
			} 
			
			switch(fightSkill.getFightSkillProperty().getFightSkillEffectType()) {
			case strengthen_iron:
				final int iron_xmlid = 100005;
				if(physicsObj.getPhysicsObjProperty().getId() == iron_xmlid) {
					physicsObj.changeDensityRate(fightSkill.getParam1() / 100f);
				}
				break;
			case rough_wood:
				final int long_wood_bar_xmlid = 100006;
				final int short_wood_bar_xmlid = 100007;
				if(physicsObj.getPhysicsObjProperty().getId() == long_wood_bar_xmlid || physicsObj.getPhysicsObjProperty().getId() == short_wood_bar_xmlid) {
					physicsObj.changeFrictionRate(fightSkill.getParam1() / 100f);
				}
				break;
			case strengthen_bullet:
				if(physicsObj.getPhysicsObjProperty().getSpecType() == PhysicsObjSpecType.bullet) {
					physicsObj.changeDensityRate(fightSkill.getParam1() / 100f);
				}
				break;
			case strengthen_balloon:
				if(physicsObj.getPhysicsObjProperty().getSpecType() == PhysicsObjSpecType.balloon) {
					physicsObj.changeBalloonVRate(fightSkill.getParam1() / 100f);
				}
				break;
			case super_fuwen:
				final int fuwen_xmlid = 100002;
				if(physicsObj.getPhysicsObjProperty().getId() == fuwen_xmlid && fightSkill.getUseCount() < Math.round(fightSkill.getParam1())) {
					physicsObj.changeToFixRotation();
					fightSkill.incUseCount();
				}
				break;
			case multi_ball:
				final int ball_xmlid = 100008;
				if(physicsObj.getPhysicsObjProperty().getId() == ball_xmlid) {
					if(random.nextInt(100) < Math.round(fightSkill.getParam1())) {
						PhysicsObjProto.Builder objBuilder = physicsObj.buildProto(true);
						objBuilder.clearNails();
						PhysicsObjProto physicsObjProto = objBuilder.build();
						int count = Math.round(fightSkill.getParam2());
						for(int i = 0; i < count; i++) {
							PhysicsObj.createNewPhysicsObj(fightRoom, fightPlayer.getRoleId(), physicsObj.getPhysicsObjProperty(), physicsObjProto, true);
							fightRoom.addPhysicsObj(physicsObj);
							ConjurePhysicsObjEventProto.Builder builder = ConjurePhysicsObjEventProto.newBuilder();
							builder.setEventType(EventType.server_conjure_physicsobj.getCode());
							builder.setFrameIndex(fightRoom.getFrameIndex());
							PhysicsObjProto.Builder physicsObjBuilder = physicsObj.buildProto(true);
							builder.setRoleId(ServerConfig.system_role_id);
							builder.setPhysicsObj(physicsObjBuilder);
							fightRoom.broadcastConjurePhysicsObjEvent(builder, fightPlayer);
						}
					}
				}
				break;
			case multi_balloon:
				final int balloon_xmlid = 100001;
				if(physicsObj.getPhysicsObjProperty().getId() == balloon_xmlid) {
					if(random.nextInt(100) < Math.round(fightSkill.getParam1())) {
						PhysicsObjProto physicsObjProto = physicsObj.buildProto(true).build();
						int count = Math.round(fightSkill.getParam2());
						for(int i = 0; i < count; i++) {
							PhysicsObj.createNewPhysicsObj(fightRoom, fightPlayer.getRoleId(), physicsObj.getPhysicsObjProperty(), physicsObjProto, true);
							fightRoom.addPhysicsObj(physicsObj);
							ConjurePhysicsObjEventProto.Builder builder = ConjurePhysicsObjEventProto.newBuilder();
							builder.setEventType(EventType.server_conjure_physicsobj.getCode());
							builder.setFrameIndex(fightRoom.getFrameIndex());
							PhysicsObjProto.Builder physicsObjBuilder = physicsObj.buildProto(true);
							builder.setRoleId(ServerConfig.system_role_id);
							builder.setPhysicsObj(physicsObjBuilder);
							fightRoom.broadcastConjurePhysicsObjEvent(builder, fightPlayer);
						}
					}
				}
				break;
			case conjure_inc_speed:
				final int changeFrameIndex = fightRoom.getFrameIndex();
				for(FightUnit fightUnit : fightRoom.getUnits().values()) {
					if(fightUnit.getFightPlayer().getCampType() == fightPlayer.getCampType()) {
						fightUnit.changeMovingSpeed2(fightSkill.getParam2(), changeFrameIndex, fightSkill.getParam1() / 100f);
					}
				}
				fightRoom.performAtFixFrameIndex(new FixFrameEvent(fightPlayer.getRoleId(), ServerEventType.fight_skill_delay_change_speed
						, (int) (fightRoom.getFrameIndex() + fightSkill.getParam2() * FightConfig.fps)) {
					@Override
					public void executeEvent() {
						for(FightUnit fightUnit : fightRoom.getUnits().values()) {
							if(fightUnit.getFightPlayer().getCampType() == fightPlayer.getCampType()) {
								fightUnit.changeMovingSpeed2Timeout(changeFrameIndex);
							}
						}
					}
				});
				break;
			case balloon_conjure_speed_up:
				if(physicsObj.getPhysicsObjProperty().getSpecType() == PhysicsObjSpecType.balloon) {
					if(random.nextInt(100) < fightSkill.getParam1()) {
						fightPlayer.getFightUnit().getSkillChangeConjureTimeMap().put(physicsObj.getPhysicsObjProperty().getSpecType(), fightSkill.getParam2() / 100f);
					}
				} 
				break;
			case bullet_conjure_speed_up:
				if(physicsObj.getPhysicsObjProperty().getSpecType() == PhysicsObjSpecType.bullet) {
					if(random.nextInt(100) < fightSkill.getParam1()) {
						fightPlayer.getFightUnit().getSkillChangeConjureTimeMap().put(physicsObj.getPhysicsObjProperty().getSpecType(), fightSkill.getParam2() / 100f);
					}
				} 
				break;
			case none:
			default:
				break;
			}
		}
		return result;
	}
	
	public static void startFightTriggerSkill(FightRoom fightRoom, FightUnit unit) {
		if(unit.getFightPlayer().getSamanSkills() != null) {
			for(FightSkill fightSkill : unit.getFightPlayer().getSamanSkills()) {
				if(fightSkill.getFightSkillProperty().getFightSkillTriggerType() != FightSkillTriggerType.gameStart) {
					continue;
				} 
				switch(fightSkill.getFightSkillProperty().getFightSkillEffectType()) {
				case fight_time_extend:
					fightRoom.addMaxFightTime((int) fightSkill.getParam1());
					break;
				case big_cheese:
					List<PhysicsObj> cheeses = fightRoom.findPhysicsObjsBySpecType(PhysicsObjSpecType.cheese);
					for(PhysicsObj cheese : cheeses) {
						cheese.expand(fightSkill.getParam1() / 100f);
					}
					break;
				case jump_height_change:
					unit.changeJumpHeight(fightSkill.getParam1() / 100f);
					break;
				case inc_friction:
					unit.changeFriction(fightSkill.getParam1() / 100f);
					break;
				case inc_speed:
					unit.changeMovingSpeed(fightSkill.getParam1() / 100f);
					break;
				case quick_conjure2_dec_conjure_range:
					unit.setSkillIncUseSkillSpeed(unit.getSkillIncUseSkillSpeed() + fightSkill.getParam1() / 100f);
					unit.setSkillIncUseSkillRange(unit.getSkillIncUseSkillRange() - fightSkill.getParam2() / 100f);
					break;
				case quick_conjure3:
					unit.setSkillIncUseSkillSpeed(unit.getSkillIncUseSkillSpeed() + fightSkill.getParam1() / 100f);
					break;
				case inc_conjure_range2_dec_conjure_speed:
					unit.setSkillIncUseSkillRange(unit.getSkillIncUseSkillRange() + fightSkill.getParam1() / 100f);
					unit.setSkillIncUseSkillSpeed(unit.getSkillIncUseSkillSpeed() - fightSkill.getParam2() / 100f);
					break;
				case inc_conjure_range3:
					unit.setSkillIncUseSkillRange(unit.getSkillIncUseSkillRange() + fightSkill.getParam1() / 100f);
					break;
				case become_bigger:
					unit.changeRadius(fightSkill.getParam1() / 100f);
					break;
				case become_smaller:
					unit.changeRadius(-fightSkill.getParam1() / 100f);
				case none:
					break;
				default:
					break;
				}
			}
		}
		
	}
	
	public static void dragReleaseTriggerSkill(FightRoom fightRoom, FightSkill fightSkill, final FightPlayer fightPlayer, UseSkillEventProto useSkillEventProto){
		int influnceObjId = useSkillEventProto.getInfluenObjId();
		switch (fightSkill.getFightSkillProperty().getFightSkillInfluenceObjType()) {
			case physicsObj:
				switch(fightSkill.getFightSkillProperty().getFightSkillEffectType()) {
					case mice_virtual_obj:
						if (fightSkill.getUseCount() < fightSkill.getParam1()) {
							PhysicsObj physicsObj = fightRoom.getPhysicsObjs().get(influnceObjId);
							physicsObj.switchPhysicsObjMiceVirtualObj();
							fightSkill.incUseCount();
						}
						break;
					case delete_saman_obj:
						if (fightSkill.getUseCount() < fightSkill.getParam1()) {
							PhysicsObj physicsObj = fightRoom.getPhysicsObjs().get(influnceObjId);
							if (physicsObj.isConjured()) {
								fightRoom.removePhysicsObj(physicsObj);
								physicsObj.dead();
								fightSkill.incUseCount();
							}
						}
						break;
					case delete_obj_nail:
						if (fightSkill.getUseCount() < fightSkill.getParam1()) {
							PhysicsObj physicsObj = fightRoom.getPhysicsObjs().get(influnceObjId);
							if (physicsObj.isConjured() && physicsObj.getNails().size() > 0) {
								fightRoom.removePhysicsObj(physicsObj);
								physicsObj.dead();
								fightSkill.incUseCount();
							}
						}
						break;
					case fix_obj_angle:
						if (fightSkill.getUseCount() < fightSkill.getParam1()) {
							PhysicsObj physicsObj = fightRoom.getPhysicsObjs().get(influnceObjId);
							physicsObj.switchPhysicsObjFixAngle();
							fightSkill.incUseCount();
						}
						break;
					case none:
						break;
					default:
						break;
			}
			break;
			
		case miceUnit:
			FightUnit specFightUnit = fightPlayer.getFightUnit();
			final FightUnit fightUnit = fightRoom.findFightPlayer(influnceObjId).getFightUnit();
			switch (fightSkill.getFightSkillProperty().getFightSkillEffectType()) {
				case send_unit_to_side:
					if (fightSkill.getUseCount() < fightSkill.getParam1()) {
						Vec2 vec2 = specFightUnit.getBody().getTransform().p;
						fightUnit.resetPosition(vec2);
						fightSkill.incUseCount();
					}
					break;
				case give_unit_cheese:
					if (specFightUnit.isHaveGotCheese() && fightSkill.getUseCount() < fightSkill.getParam1()) {
						if (!fightUnit.isDead() && !fightUnit.isHaveGotCheese()) {
							fightUnit.setHaveGotCheese(true);
							specFightUnit.setHaveGotCheese(false);
							fightSkill.incUseCount();
						}
					}
					break;
				case send_cheese_unit_to_door:
					if (!fightUnit.isDead() && fightUnit.isHaveGotCheese() && fightSkill.getUseCount() < fightSkill.getParam1()) {
						fightUnit.unitTransformCheeseSuccess(fightPlayer.getRoleId());
						fightSkill.incUseCount();
					}
					break;
				case frozen_unit:
					if (!fightUnit.isDead() && fightSkill.getUseCount() < fightSkill.getParam1()) {
						float delayRemoveTime = 5;
						fightRoom.performAtFixFrameIndex(new FixFrameEvent(ServerConfig.system_role_id, ServerEventType.fight_skill_delay_dead, (int) (fightRoom.getFrameIndex() + delayRemoveTime * FightConfig.fps)) {
							@Override
							public void executeEvent() {
								fightUnit.dead(false, UnitDeadReasonType.frozen);
							}
						});
					}
					break;
				case increase_bounce:
					if (!fightUnit.isDead() && fightSkill.getUseCount() < fightSkill.getParam1()) {
						fightUnit.setSkillChangeJumpHeightRate3( fightSkill.getParam2()/100);
						fightSkill.incUseCount();
						fightUnit.broadcastSkillChangeInfo();
					}
					break;
				case increase_speed:
					if (!fightUnit.isDead() && fightSkill.getUseCount() < fightSkill.getParam1()) {
						fightUnit.setSkillChangeSpeedRate3(fightSkill.getParam2()/100);
						fightSkill.incUseCount();
						fightUnit.broadcastSkillChangeInfo();
					}
					break;
				case second_jump:
					if (!fightUnit.isDead() && fightSkill.getUseCount() < fightSkill.getParam1()) {
						fightSkill.incUseCount();
					}
					break;
				case jump_three:
				case jump_three_by_item:
					if (!fightUnit.isDead() && System.currentTimeMillis() > fightSkill.getContinueEndTime()) {
						fightSkill.setContinueEndTime((long) (System.currentTimeMillis() + fightSkill.getParam2() * 1000));
						fightSkill.incUseCount();
					}
					break;
				case fly:
				case fly_by_item:
					if (!fightUnit.isDead() && System.currentTimeMillis() > fightSkill.getContinueEndTime()) {
						fightSkill.setContinueEndTime((long) (System.currentTimeMillis() + fightSkill.getParam2() * 1000));
						fightSkill.incUseCount();
					}
					break;
				default:
					break;
				}
			break;
		case none:
			switch(fightSkill.getFightSkillProperty().getFightSkillEffectType()) {
			case telesport:
					if (fightSkill.getUseCount() < fightSkill.getParam1()) {
						Vec2 newVec2 = new Vec2(useSkillEventProto.getPosX() / 1000f, useSkillEventProto.getPosY() / 1000f);
						fightPlayer.getFightUnit().resetPosition(newVec2);
						fightSkill.incUseCount();
					}
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
	}
		
	/**
	 * 
	 * @param fightRoom
	 * @param unit
	 * @param type 0:dead 1:win
	 */
	public static RemoveUnitResult beforeUnitRemoveTriggerSkill(FightRoom fightRoom, FightUnit unit, int type) {
		RemoveUnitResult result = new RemoveUnitResult();
		beforeUnitRemoveTriggerSourceUnitSkill(fightRoom, unit, type, result);
		beforeUnitRemoveTriggerSamanSkill(fightRoom, unit, type, result);
		return result;
	}
	
	private static void beforeUnitRemoveTriggerSamanSkill(FightRoom fightRoom, FightUnit unit,
			int type, RemoveUnitResult result) {
		List<FightUnit> samans = fightRoom.getSelfSideSaman(unit.getFightPlayer().getCampType());
		boolean haveTriggerBubble = false;
		boolean haveTriggerReborn = false;
		for(FightUnit saman : samans) {
			List<FightSkill> skills = saman.getFightPlayer().getSamanSkills();
			for(FightSkill fightSkill : skills) {
				if(fightSkill.getFightSkillProperty().getFightSkillTriggerType() == FightSkillTriggerType.unitDead || fightSkill.getFightSkillProperty().getFightSkillTriggerType() == FightSkillTriggerType.unitWin) {
					switch(fightSkill.getFightSkillProperty().getFightSkillEffectType()) {
					case dead_become_bubble:
						if(type == 0 && !haveTriggerBubble && fightSkill.getUseCount() < fightSkill.getParam1()) {
							haveTriggerBubble = true;
							fightSkill.incUseCount();
							PhysicsObjProperty physicsObjProperty = fightRoom.getPhysicsObjData().getPhysicsObjProperty(100175);
							PhysicsObjProto.Builder phyObjBuilder = PhysicsObj.createDefaultPbjProto(physicsObjProperty);
							phyObjBuilder.setPosX((int) (unit.getBody().getPosition().x * 1000f));
							phyObjBuilder.setPosY(10 * 1000);
							PhysicsObj physicsObj = PhysicsObj.createNewPhysicsObj(fightRoom, ServerConfig.system_role_id, physicsObjProperty, phyObjBuilder.build(), true);
							fightRoom.addPhysicsObj(physicsObj);
							ConjurePhysicsObjEventProto.Builder builder = ConjurePhysicsObjEventProto.newBuilder();
							builder.setEventType(EventType.server_conjure_physicsobj.getCode());
							builder.setFrameIndex(fightRoom.getFrameIndex());
							PhysicsObjProto.Builder physicsObjBuilder = physicsObj.buildProto(true);
							builder.setRoleId(ServerConfig.system_role_id);
							builder.setPhysicsObj(physicsObjBuilder);
							fightRoom.broadcastConjurePhysicsObjEvent(builder, unit.getFightPlayer());
						}
						break;
					case quick_conjure:
						if(fightSkill.getUseCount() < fightSkill.getParam2()) {
							fightSkill.incUseCount();
							saman.setSkillIncUseSkillSpeed(saman.getSkillIncUseSkillSpeed() + fightSkill.getParam1() / 100f);
							saman.broadcastSkillChangeInfo();
						}
						break;
					case inc_conjure_range:
						if(fightSkill.getUseCount() < fightSkill.getParam2()) {
							fightSkill.incUseCount();
							saman.setSkillIncUseSkillRange(saman.getSkillIncUseSkillRange() + fightSkill.getParam1() / 100f);
							saman.broadcastSkillChangeInfo();
						}
						break;
					case reborn_mouse:
						if(!unit.getFightPlayer().isUnitIsSpec() && type == 0 && !haveTriggerReborn && fightSkill.getUseCount() < fightSkill.getParam1()) {
							Boolean haveRebron = saman.getSkillHelpMouseRebornMap().get(unit.getRoleId());
							if(haveRebron == null) {
								haveTriggerReborn = true;
								fightSkill.incUseCount();
								saman.getSkillHelpMouseRebornMap().put(unit.getRoleId(), true);
								List<PhysicsObj> bornObjs = fightRoom.findPhysicsObjsBySpecType(PhysicsObjSpecType.mouseBorn);
								unit.getBody().setTransform(bornObjs.get(0).getBody().getPosition(), unit.getBody().getAngle());
								result.isReborn = true;
								result.rebornSkillXmlId = fightSkill.getFightSkillProperty().getId();
							}
						}
						break;
					case auto_win:
						if(!unit.getFightPlayer().isUnitIsSpec() && type == 1) {
							final FightPlayer samanFightPlayer = saman.getFightPlayer();
							List<FightUnit> selfUnits = fightRoom.getSelfSideFightPlayer(unit.getFightPlayer().getCampType());
							if (selfUnits.size() - 1 == 1) {
								PhysicsObj physicsObj = fightRoom.findCampDestination(samanFightPlayer.getCampType());
								UseSkillEventProto.Builder builder = UseSkillEventProto.newBuilder();
								final Vec2 vec2 = physicsObj.getBody().getTransform().p;
								builder.setPosX((int) (vec2.x * 1000));
								builder.setPosY((int) (vec2.y * 1000));
								builder.setRoleId(saman.getRoleId());
								builder.setEventType(EventType.use_skill.getCode());
								builder.setFightSkillId(fightSkill.getFightSkillProperty().getId());
								builder.setFrameIndex(fightRoom.getFrameIndex());
								fightRoom.broadcastUseSkillEvent(builder, saman.getFightPlayer());
								float delayRemoveTime = 0.8f;
								fightRoom.performAtFixFrameIndex(new FixFrameEvent(ServerConfig.system_role_id, ServerEventType.fight_skill_delay_dead, (int) (fightRoom.getFrameIndex() + delayRemoveTime * FightConfig.fps)) {
									@Override
									public void executeEvent() {
										if (samanFightPlayer.getFightUnit() != null) {
											samanFightPlayer.getFightUnit().setHaveGotCheese(true);
											samanFightPlayer.getFightUnit().resetPosition(vec2);
											samanFightPlayer.getFightUnit().unitTransformCheeseSuccess(samanFightPlayer.getRoleId());
										}
									}
								});
							}
						}
						break;
					case none:
					default:
						break;
					}
				}
			} 
		}
	}

	private static void beforeUnitRemoveTriggerSourceUnitSkill(FightRoom fightRoom, FightUnit unit,
			int type, RemoveUnitResult result) {
		List<FightSkill> skills;
		if(unit.getFightPlayer().isUnitIsSpec()) {
			skills = unit.getFightPlayer().getSamanSkills();
		} else {
			skills = unit.getFightPlayer().getMouseSkills();
		}
		for(FightSkill fightSkill : skills) {
			switch(fightSkill.getFightSkillProperty().getFightSkillEffectType()) {
			case soul_fight:
				if(type == 0 && fightSkill.getUseCount() == 0) {
					fightSkill.incUseCount();
					result.isDelayRemove = true;
					result.delayRemoveTime = fightSkill.getParam1();
					List<PhysicsObj> samanBornObj = fightRoom.findPhysicsObjsBySpecType(PhysicsObjSpecType.specMouseBorn);
					if(samanBornObj.size() == 0) {
						samanBornObj = fightRoom.findPhysicsObjsBySpecType(PhysicsObjSpecType.mouseBorn);
					}
					unit.getBody().setTransform(samanBornObj.get(0).getBody().getPosition(), unit.getBody().getAngle());
					unit.changeToSoul(result.delayRemoveTime, fightRoom.getFrameIndex());
				}
				break;
			case reborn:
				if(type == 0 && fightSkill.getUseCount() == 0) {
					float rate = fightSkill.getParam1();
					if(random.nextInt(100) < rate) {
						fightSkill.incUseCount();
						result.isReborn = true;
						result.rebornSkillXmlId = fightSkill.getFightSkillProperty().getId();
						List<PhysicsObj> samanBornObj = fightRoom.findPhysicsObjsBySpecType(PhysicsObjSpecType.specMouseBorn);
						if(samanBornObj.size() == 0) {
							samanBornObj = fightRoom.findPhysicsObjsBySpecType(PhysicsObjSpecType.mouseBorn);
						}
						unit.getBody().setTransform(samanBornObj.get(0).getBody().getPosition(), unit.getBody().getAngle());
					}
				}
				break;
			case none:
			default:
				break;
			}
		}
	}

}
