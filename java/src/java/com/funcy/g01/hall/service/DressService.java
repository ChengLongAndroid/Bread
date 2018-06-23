package com.funcy.g01.hall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funcy.g01.base.bo.dress.DressType;
import com.funcy.g01.base.bo.dress.RoleDressInfo;
import com.funcy.g01.base.bo.fight.EventType;
import com.funcy.g01.base.bo.fight.FightRoom;
import com.funcy.g01.base.bo.fight.FightUnit;
import com.funcy.g01.base.bo.fight.SynEvent;
import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.dao.redis.DressRepo;
import com.funcy.g01.base.dao.redis.RoleRepo;
import com.funcy.g01.base.data.DressData;
import com.funcy.g01.base.data.DressProperty;
import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;
import com.funcy.g01.base.net.GamePlayer;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.DressVoProto.Builder;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.PlayerChangeDressProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.PlayerEnterProto;

@Service
public class DressService {

	@Autowired
	private DressRepo dressRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private DressData dressData;
	
	public Builder getRoleDressInfo(GamePlayer gamePlayer){
		RoleDressInfo roleDressInfo = dressRepo.getRoleDressInfo(gamePlayer.getRoleId());
		return (Builder) roleDressInfo.copyTo().toBuilder();
	}
	
	public void changeDress(int xmlId, final GamePlayer gamePlayer){
		RoleDressInfo roleDressInfo = dressRepo.getRoleDressInfo(gamePlayer.getRoleId());
		if (roleDressInfo.checkHadDress(xmlId)) {
			DressProperty dressProperty = dressData.getDressProperty(xmlId);
			int dressTypeCode = dressProperty.getType();
			final Role role = roleRepo.getRole(gamePlayer.getRoleId());
			role.getDresses().set(dressTypeCode - 1, xmlId);
			roleRepo.saveRole(role);
			final FightRoom fightRoom = gamePlayer.getSynRoom();
			if(fightRoom == null) {
				return;
			}
			FightUnit fightUnit = gamePlayer.getFightPlayer().getFightUnit();
			if(fightUnit == null || fightUnit.isDead()) {
				return;
			}
			fightRoom.executeRightNow(new SynEvent(gamePlayer.getRoleId(), "DressService.changeDress") {
				@Override
				public void executeEvent() {
					PlayerChangeDressProto.Builder builder = PlayerChangeDressProto.newBuilder();
					builder.setFrameIndex(fightRoom.getFrameIndex());
					builder.setRoleId(role.getId());
					builder.setRoleName(role.getRoleName());
					builder.addAllDresses(role.getDresses());
					builder.setEventType(EventType.change_dress.getCode());
					fightRoom.broadChangeDressEvent(builder,gamePlayer.getFightPlayer());
					
					if(gamePlayer.getFightPlayer() != null) {
						gamePlayer.getFightPlayer().setRole(role);
						PlayerEnterProto.Builder playerBuilder = gamePlayer.getFightPlayer().getPlayerEnterProto().toBuilder();
						playerBuilder.setPlayerInfo(role.buildPlayerInfoProto4Front());
						gamePlayer.getFightPlayer().setPlayerEnterProto(playerBuilder.build());
					}
				}
			});
		}else{
			throw new BusinessException(ErrorCode.NO_DRESS);
		}
	}
}
