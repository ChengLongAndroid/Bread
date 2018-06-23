package com.funcy.g01.hall.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebo.synframework.nettybase.protoPool.ProtoBuilderPoolManager;
import com.ebo.synframework.nettybase.protoPool.ReusedProtoBuilder;
import com.funcy.g01.base.bo.achieve.AchievementType;
import com.funcy.g01.base.bo.achieve.UpdateAchievement;
import com.funcy.g01.base.bo.fight.EventType;
import com.funcy.g01.base.bo.fight.FightPlayer;
import com.funcy.g01.base.bo.fight.FightRoom;
import com.funcy.g01.base.bo.fight.FightUnit;
import com.funcy.g01.base.bo.fight.SynEvent;
import com.funcy.g01.base.bo.friend.FriendNpc;
import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.bo.role.RoleSocialInfo;
import com.funcy.g01.base.bo.role.RoleSoundInfo;
import com.funcy.g01.base.dao.redis.RoleRepo;
import com.funcy.g01.base.data.SpecMouseTypeData;
import com.funcy.g01.base.data.SpecMouseTypeProperty;
import com.funcy.g01.base.net.GamePlayer;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleSocialBaseInfoProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.PlayerChangeDressProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.PlayerEnterProto;
import com.funcy.g01.base.proto.bo.service.RoleServiceRespProtoBuffer.GetRoleSocialBaseInfo;
import com.funcy.g01.base.proto.bo.service.RoleServiceRespProtoBuffer.GetRoleSounds;
import com.funcy.g01.base.proto.service.ReqCmdProto.BoolReqProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.IntReqProto;
import com.funcy.g01.base.util.DirtyUtil;
import com.google.protobuf.ByteString;
import com.google.protobuf.Message.Builder;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private AchievementService achievementService;
	
	@Autowired
	private SpecMouseTypeData specMouseTypeData;
	
	public void enableSpeech(GamePlayer gamePlayer){
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		boolean isEnableSpeech = !role.isEnableSpeech();
		role.setEnableSpeech(isEnableSpeech);
		roleRepo.saveRole(role);
		FightPlayer fightPlayer = gamePlayer.getFightPlayer();
		if(fightPlayer != null) {
			fightPlayer.getRole().setEnableSpeech(isEnableSpeech);
		}
	}
	
	public void saveRoleSound(List<ByteString> sounds, int soundTime, GamePlayer gamePlayer){
		RoleSoundInfo roleSoundInfo = roleRepo.getRoleSoundInfo(gamePlayer.getRoleId());
		roleSoundInfo.setSoundTime(soundTime);
		roleSoundInfo.setSounds(sounds);
		roleRepo.saveRoleSoundInfo(roleSoundInfo);
		achievementService.updateOneAchievement(new UpdateAchievement(gamePlayer.getRoleId(), AchievementType.record_sound, 1));
	}
	
	public Builder delRoleSound(GamePlayer gamePlayer){
		boolean delSuc = roleRepo.delRoleSoundInfo(gamePlayer.getRoleId());
		return BoolReqProto.newBuilder().setParams1(delSuc);
	}
	
	public void saveDeclaration(String declaration, GamePlayer gamePlayer){
		String filterDeclaration = DirtyUtil.replace(declaration);
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		role.setDeclaration(filterDeclaration);
		roleRepo.saveRole(role);
	}
	
	
	public void saveSex(int sex, GamePlayer gamePlayer){
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		role.setSex(sex);
		roleRepo.saveRole(role);
		achievementService.updateOneAchievement(new UpdateAchievement(gamePlayer.getRoleId(), AchievementType.modify_sex, 1));
	}
	
	public void modifyPhoto(String photo, GamePlayer gamePlayer){
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		role.setPhoto(photo);
		roleRepo.saveRole(role);
		achievementService.updateOneAchievement(new UpdateAchievement(gamePlayer.getRoleId(), AchievementType.modify_photo, 1));
	}
	
	public Object modifyName(String name, GamePlayer gamePlayer){
		String filterName = DirtyUtil.replace(name);
		ReusedProtoBuilder reusedProtoBuilder = ProtoBuilderPoolManager.getBuilder(IntReqProto.class);
		IntReqProto.Builder builder = (IntReqProto.Builder) reusedProtoBuilder.getBuilder();
		if (roleRepo.checkIsHaveRoleName(filterName)) {
			builder.setIndex(1);
		}else{
			builder.setIndex(0);
			Role role = roleRepo.getRole(gamePlayer.getRoleId());
			role.setRoleName(filterName);
			roleRepo.saveRole(role);
			roleRepo.saveRoleName(filterName);
			
			final FightRoom fightRoom = gamePlayer.getSynRoom();
			if(fightRoom == null) {
				return reusedProtoBuilder;
			}
			FightUnit fightUnit = gamePlayer.getFightPlayer().getFightUnit();
			if(fightUnit == null || fightUnit.isDead()) {
				return reusedProtoBuilder;
			}
			fightRoom.executeRightNow(new SynEvent(gamePlayer.getRoleId(), "DressService.changeDress") {
				@Override
				public void executeEvent() {
					PlayerChangeDressProto.Builder builder = PlayerChangeDressProto.newBuilder();
					builder.setFrameIndex(fightRoom.getFrameIndex());
					builder.setRoleId(role.getId());
					builder.addAllDresses(role.getDresses());
					builder.setRoleName(role.getRoleName());
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
		}
		return reusedProtoBuilder;
	}
	
	public void modifyPhotoFrame(int photoFrame, GamePlayer gamePlayer){
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		role.setPhotoFrame(photoFrame);
		roleRepo.saveRole(role);
	}
	
	
	public void saveCity(String city, GamePlayer gamePlayer){
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		role.setCity(city);
		roleRepo.saveRole(role);
	}
	
	public void follow(long roleId, GamePlayer gamePlayer){
		RoleSocialInfo selfRoleSocialInfo = roleRepo.getRoleSocialInfo(gamePlayer.getRoleId());
		RoleSocialInfo anotherRoleSocialInfo = roleRepo.getRoleSocialInfo(roleId);
		Role selfRole = roleRepo.getRole(gamePlayer.getRoleId());
		Role anotherRole = roleRepo.getRole(roleId);
		if (selfRoleSocialInfo.getFollows().contains(anotherRoleSocialInfo.getRoleId())) {
			selfRoleSocialInfo.getFollows().remove(anotherRoleSocialInfo.getRoleId());
			anotherRoleSocialInfo.getFans().remove(gamePlayer.getRoleId());
		}else{
			selfRoleSocialInfo.getFollows().add(anotherRoleSocialInfo.getRoleId());
			anotherRoleSocialInfo.getFans().add(gamePlayer.getRoleId());
		}
		anotherRole.setFansNum(anotherRoleSocialInfo.getFans().size());
		selfRole.setFollowsNum(selfRoleSocialInfo.getFollows().size());
		roleRepo.saveRoleSocialInfo(selfRoleSocialInfo);
		roleRepo.saveRoleSocialInfo(anotherRoleSocialInfo);
		roleRepo.saveRole(selfRole);
		roleRepo.saveRole(anotherRole);
		achievementService.updateOneAchievement(new UpdateAchievement(selfRoleSocialInfo.getRoleId(), AchievementType.follow, selfRoleSocialInfo.getFollows().size()));
		achievementService.updateOneAchievement(new UpdateAchievement(anotherRoleSocialInfo.getRoleId(), AchievementType.fans, anotherRoleSocialInfo.getFans().size()));
	} 
	
	public Builder getRoleBaseSocialInfo(long roleId, GamePlayer gamePlayer){
		GetRoleSocialBaseInfo.Builder getRoleSocialBaseInfo = GetRoleSocialBaseInfo.newBuilder();
		getRoleSocialBaseInfo.setRoleIsExist(true);
		if(roleId == FriendNpc.npc_friend_id){
			Role role = FriendNpc.newRole();
			getRoleSocialBaseInfo.setRole(role.buildFrontProto());
			return getRoleSocialBaseInfo;
		}
		Role role = roleRepo.getRole(roleId);
		if(role==null){
			getRoleSocialBaseInfo.setRoleIsExist(false);
			return getRoleSocialBaseInfo;
		}
		RoleSocialInfo rolesocialInfo = roleRepo.getRoleSocialInfo(roleId);
		getRoleSocialBaseInfo.setRole(role.buildFrontProto());
		RoleSocialBaseInfoProto.Builder roleSocialBaseBuilder = (com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleSocialBaseInfoProto.Builder) rolesocialInfo.copyBaseInfo();
		boolean isFollowe = rolesocialInfo.getFans().contains(gamePlayer.getRoleId());
		roleSocialBaseBuilder.setIsFollow(isFollowe);
		RoleSoundInfo roleSoundInfo = roleRepo.getRoleSoundInfo(roleId);
		roleSocialBaseBuilder.setSoundTime(roleSoundInfo.getSoundTime());
		getRoleSocialBaseInfo.setSocialInfo(roleSocialBaseBuilder.build());
		return getRoleSocialBaseInfo;
	}
	
	public Builder getRoleSounds(long roleId, GamePlayer gamePlayer){
		RoleSoundInfo roleSoundInfo = roleRepo.getRoleSoundInfo(roleId);
		GetRoleSounds.Builder getRoleSounds = GetRoleSounds.newBuilder();
		getRoleSounds.addAllSounds(roleSoundInfo.getSounds());
		return getRoleSounds;
	}
	
	public void changeSpecMouse(int typeId, GamePlayer gamePlayer) {
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		if(typeId == role.getSpecMouseTypeId()) {
			return;
		}
		SpecMouseTypeProperty specMouseTypeProperty = specMouseTypeData.getSpecMouseTypeProperty(typeId);
		if(role.getShammanLevel() >= specMouseTypeProperty.getOpenSamanLevel()) {
			role.setSpecMouseTypeId(typeId);
			roleRepo.saveRole(role);
		}
	}
	public void saveMaxSpecMouseType(int typeId, GamePlayer gamePlayer) {
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		if(typeId == role.getMaxSpecMouseTypeId()) {
			return;
		}
		SpecMouseTypeProperty specMouseTypeProperty = specMouseTypeData.getSpecMouseTypeProperty(typeId);
		if(role.getShammanLevel() >= specMouseTypeProperty.getOpenSamanLevel()) {
			role.setMaxSpecMouseTypeId(typeId);
			roleRepo.saveRole(role);
		}
	}
	public void changeRecordShammanLevel(int shammanLevel, GamePlayer gamePlayer) {
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		if (role.getRecordShammanLevel() != role.getShammanLevel()) {
			role.setRecordShammanLevel(role.getShammanLevel());
			roleRepo.saveRole(role);
		}
	}
	
}
