package com.funcy.g01.base.bo.fight;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.jbox2d.common.Vec2;

import com.funcy.g01.base.bo.fight.FightSkillTrigger.RemoveUnitResult;
import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.dispatcher.bo.RoomType;

public class DeadChecker {
	
	public static final Logger logger = Logger.getLogger(DeadChecker.class);

	private FightRoom fightRoom;
	
	private float minX;
	
	private float minY;
	
	private float maxX;
	
	private float maxY;
	
	private float objMinX;
	
	private float objMaxX;
	
	private float objMinY;
	
	private float objMaxY;
	
	public DeadChecker(FightRoom fightRoom) {
		this.fightRoom = fightRoom;
		this.minX = 0 - FightConfig.unit_dead_x_range;
		this.maxX = fightRoom.getWidth() + FightConfig.unit_dead_x_range;
		this.minY = 0 - FightConfig.unit_dead_y_below_range;
		this.maxY = fightRoom.getHeight() + FightConfig.unit_dead_y_up_range;
		
		this.objMaxX = fightRoom.getWidth() + FightConfig.obj_dead_x_range;
		this.objMinX = 0 - FightConfig.obj_dead_x_range;
		this.objMaxY = fightRoom.getHeight() + FightConfig.obj_dead_y_up_range;
		this.objMinY = 0 - FightConfig.obj_dead_y_below_range;
	}
	
	public void checkUnitAndMakeDead() {
		Iterator<FightUnit> it = this.fightRoom.getUnits().values().iterator();
		boolean isNeedActionTimeout = (this.fightRoom.getRoomType() != RoomType.hall && this.fightRoom.getRoomType() != RoomType.tryPlay);
		while(it.hasNext()) {
			final FightUnit fightUnit = it.next();
			Vec2 pos = fightUnit.getBody().getPosition();
			final UnitDeadReasonType reasonType = isUnitOutOfRange(pos);
			if(reasonType != null) {
				//判断是否有新手复活次数
				Role role = fightUnit.getFightPlayer().getRole();
				if(role.getNewComerDayRebornLeftNum() > 0 && fightRoom.getFrameIndex() / 60 <= 20 && !fightUnit.isNewComerHadReborn()) {
					fightRoom.newComerReborn(reasonType, fightUnit);
					return;
				}
				
				RemoveUnitResult removeUnitResult = FightSkillTrigger.beforeUnitRemoveTriggerSkill(fightRoom, fightUnit, 0);
				if(removeUnitResult.isDelayRemove) {
					this.fightRoom.performAtFixFrameIndex(new FixFrameEvent(ServerConfig.system_role_id, ServerEventType.fight_skill_delay_dead, (int) (this.fightRoom.getFrameIndex() + removeUnitResult.delayRemoveTime * FightConfig.fps)) {
						@Override
						public void executeEvent() {
							fightUnit.dead(false, UnitDeadReasonType.soulFightTimeout);
						}
					});
					fightUnit.getFightPlayer().sendUnitDeadEvent(reasonType);
				} else if(removeUnitResult.isReborn) {
					fightUnit.getFightPlayer().sendUnitDeadEvent(reasonType, true, removeUnitResult.rebornSkillXmlId);
				} else {
					it.remove();
					fightUnit.dead(false, reasonType);
				}
			} else if(isNeedActionTimeout && fightUnit.isInFightActionPrepareTimeout(this.fightRoom.getFrameIndex())) {
				FightPlayer fightPlayer = fightUnit.getFightPlayer();
				if(!fightPlayer.isHadWarnActionPrepareTimeout()) {
					fightPlayer.setHadWarnActionPrepareTimeout(true);
					fightPlayer.warnActionPrepareTimeout();
				}
				if(fightUnit.isInFightActionTimeout(this.fightRoom.getFrameIndex())) {
					it.remove();
					fightUnit.dead(false, UnitDeadReasonType.actionTimeout);
					logger.info("action timeout remove unit(roleId:" + fightUnit.getRoleId() + ")");
				}
			}
		}
	}
	
	public void checkOfflinePlayerAndMakeDead() {
		Iterator<FightPlayer> it = this.fightRoom.getFightPlayers().iterator();
		while(it.hasNext()) {
			FightPlayer fightPlayer = it.next();
			if(fightPlayer.isOffLineTimeout(this.fightRoom.getFrameIndex())) {
				fightPlayer.sendUnitDeadEvent(UnitDeadReasonType.plyaerOfflineTimeout);
				fightPlayer.quitFight();
				it.remove();
			}
		}
	}
	
	public UnitDeadReasonType isUnitOutOfRange(Vec2 pos) {
		float x = pos.x;
		float y = pos.y;
		if(y < minY) {
			return UnitDeadReasonType.outOfMinY;
		} else if(x > maxX) {
			return UnitDeadReasonType.outOfMaxX;
		} else if(x < minX) {
			return UnitDeadReasonType.outOfMinX;
		} else if(y > maxY) {
			return UnitDeadReasonType.outOfMaxY;
		}
		return null;
	}
	
	public boolean isObjOutOfRange(Vec2 pos) {
		float x = pos.x;
		float y = pos.y;
		if(x >= objMinX && x <= objMaxX && y >= objMinY && y <= objMaxY) {
			return false;
		}
		return true;
	}
	
	public void checkObjAndMakeDead() {
		Iterator<PhysicsObj> it = this.fightRoom.getPhysicsObjs().values().iterator();
		while(it.hasNext()) {
			PhysicsObj physicsObj = it.next();
			Vec2 pos = physicsObj.getBody().getPosition();
			if(isObjOutOfRange(pos)) {
//				logger.info("out of range remove physics obj:" + physicsObj.getId());
				physicsObj.dead();
				it.remove();
			}
		}
	}
	
}
