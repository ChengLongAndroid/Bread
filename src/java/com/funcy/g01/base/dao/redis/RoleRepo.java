package com.funcy.g01.base.dao.redis;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ebo.storage.redis.JRedis;
import com.ebo.storage.redis.JRedisFactory;
import com.funcy.g01.base.bo.fight.RoleWeekFightInfo;
import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.bo.role.RoleNetInfo;
import com.funcy.g01.base.bo.role.RoleSocialInfo;
import com.funcy.g01.base.bo.role.RoleSoundInfo;
import com.funcy.g01.base.bo.role.RoleStateInfo;
import com.funcy.g01.base.dao.redis.base.Bits;
import com.funcy.g01.base.dao.redis.base.RedisKeyUtil;
import com.funcy.g01.base.dao.redis.base.StorageKey;
import com.funcy.g01.base.data.RoleData;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleHallInfoProto;
import com.google.protobuf.InvalidProtocolBufferException;

@Repository
public class RoleRepo {

	private static final Logger logger  = Logger.getLogger(RoleData.class);
	
	@Autowired
	private JRedis jRedis;
	
	@Autowired
	private GlobalRepo globalRepo;
	
	
	public static class InitRoleIdResult {
		public long roleId;
		public boolean isNew = false;
	}
	
	private byte[] getRoleHallInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_hall_info_key);
	}
	
	private byte[] getRoleKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_key);
	}
	
	private byte[] getRoleIdKey(String platformId) {
		return RedisKeyUtil.getByteArray(platformId, StorageKey.roleId_key);
	}
	
	private byte[] getRoleNameKey(String roleName) {
		return RedisKeyUtil.getByteArray(roleName, StorageKey.role_name_Key);
	}
	
	private byte[] getRoleWeekFightInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_week_fight_info_key);
	}
	
	public byte[] getRoleNetInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_net_info_key);
	}
	
	public byte[] getRoleSocialInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_social_info_key);
	}
	
	public byte[] getRoleSoundInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_sound_info_key);
	}
	
	public byte[] getRoleStateInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_state_info_key);
	}
	
	public RoleHallInfoProto getRoleHallInfo(long roleId) {
		byte[] key = getRoleHallInfoKey(roleId);
		byte[] bytes = jRedis.get(key);
		if(bytes == null) {
			return null;
		}
		try {
			RoleHallInfoProto roleHallInfo = RoleHallInfoProto.parseFrom(bytes);
			return roleHallInfo;
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void saveRoleHallInfo(RoleHallInfoProto roleHallInfo, long roleId) {
		byte[] key = getRoleHallInfoKey(roleId);
		byte[] value = roleHallInfo.toByteArray();
		jRedis.set(key, value);
	}
	
	public RoleNetInfo getRoleNetInfoByPlatformId(String platformId) {
		long roleId = getRoleIdByPlatformId(platformId);
		return getRoleNetInfo(roleId);
	}
	
	public RoleNetInfo getRoleNetInfo(long roleId) {
		byte[] key = getRoleNetInfoKey(roleId);
		byte[] value = jRedis.get(key);
		if(value == null) {
			RoleNetInfo netInfo = new RoleNetInfo();
			netInfo.setRoleId(roleId);
			return netInfo;
		} else {
			RoleNetInfo roleNetInfo = new RoleNetInfo(value);
			roleNetInfo.setRoleId(roleId);
			return roleNetInfo;
		}
	}
	
	public void saveRoleNetInfo(RoleNetInfo roleNetInfo) {
		byte[] key = getRoleNetInfoKey(roleNetInfo.getRoleId());
		jRedis.set(key, roleNetInfo.toByteArray());
	}
	
	public Role getRole(String platformId) {
		long roleId = getRoleIdByPlatformId(platformId);
		return getRole(roleId);
	}
	
	public InitRoleIdResult tryInitRoleId(String platformId) {
		byte[] key = getRoleIdKey(platformId);
		byte[] value = jRedis.get(key);
		if(value == null) {
			long roleId = globalRepo.newRoleId();
			jRedis.set(key, Bits.getLongBytes(roleId));
			InitRoleIdResult initRoleIdResult = new InitRoleIdResult();
			initRoleIdResult.roleId = roleId;
			initRoleIdResult.isNew = true;
			return initRoleIdResult;
		} else {
			long roleId = Bits.getLong(value);
			InitRoleIdResult initRoleIdResult = new InitRoleIdResult();
			initRoleIdResult.roleId = roleId;
			initRoleIdResult.isNew = false;
			return initRoleIdResult;
		}
	}
	
	public boolean isExistPlatformId(String platformId) {
		byte[] key = getRoleIdKey(platformId);
		byte[] value = jRedis.get(key);
		if(value == null) {
			return false;
		}
		return true;
	}
	
	public long getRoleIdByPlatformId(String platformId) {
		byte[] key = getRoleIdKey(platformId);
		byte[] value = jRedis.get(key);
		if(value == null) {
			long roleId = globalRepo.newRoleId();
			jRedis.set(key, Bits.getLongBytes(roleId));
			return roleId;
		} else {
			return Bits.getLong(value);
		}
	}
	
	public Role getRole(long roleId) {
		byte[] key = getRoleKey(roleId);
		byte[] bytes = jRedis.get(key);
		if(bytes == null) {
			return null;
		}
		return new Role(bytes);
	}
	
	public void saveRole(Role role) {
		byte[] key = getRoleKey(role.getId());
		byte[] bytes = role.toByteArray();
		this.jRedis.set(key, bytes);
	}
	

	public RoleWeekFightInfo getRoleWeekFightInfo(long roleId){
		byte[] key = getRoleWeekFightInfoKey(roleId);
		byte[] bytes = jRedis.get(key);
		if (bytes == null) {
			RoleWeekFightInfo roleWeekFightInfo = new RoleWeekFightInfo(roleId);
			saveRoleWeekFightInfo(roleWeekFightInfo);
			return roleWeekFightInfo;
		} else {
			RoleWeekFightInfo roleWeekFightInfo = new RoleWeekFightInfo(bytes);
			return roleWeekFightInfo;
		}
	}
	
	public void saveRoleWeekFightInfo(RoleWeekFightInfo roleWeekFightInfo) {
		byte[] key = getRoleWeekFightInfoKey(roleWeekFightInfo.getRoleId());
		byte[] bytes = roleWeekFightInfo.toByteArray();
		jRedis.set(key, bytes);
	}
	
	
	public RoleSocialInfo getRoleSocialInfo(long roleId) {
		byte[] key = getRoleSocialInfoKey(roleId);
		byte[] value = jRedis.get(key);
		if(value == null) {
			RoleSocialInfo socialInfo = new RoleSocialInfo(roleId);
			socialInfo.setRoleId(roleId);
			saveRoleSocialInfo(socialInfo);
			return socialInfo;
		} else {
			RoleSocialInfo socialInfo = new RoleSocialInfo(value);
			socialInfo.setRoleId(roleId);
			return socialInfo;
		}
	}
	
	public void saveRoleSocialInfo(RoleSocialInfo socialInfo) {
		byte[] key = getRoleSocialInfoKey(socialInfo.getRoleId());
		jRedis.set(key, socialInfo.toByteArray());
	}
	
	public RoleSoundInfo getRoleSoundInfo(long roleId) {
		byte[] key = getRoleSoundInfoKey(roleId);
		byte[] value = jRedis.get(key);
		if(value == null) {
			RoleSoundInfo roleSoundInfo = new RoleSoundInfo(roleId);
			roleSoundInfo.setRoleId(roleId);
			return roleSoundInfo;
		} else {
			RoleSoundInfo roleSoundInfo = new RoleSoundInfo(value);
			roleSoundInfo.setRoleId(roleId);
			return roleSoundInfo;
		}
	}
	
	public void saveRoleSoundInfo(RoleSoundInfo roleSoundInfo) {
		byte[] key = getRoleSoundInfoKey(roleSoundInfo.getRoleId());
		jRedis.set(key, roleSoundInfo.toByteArray());
	}
	
	public boolean delRoleSoundInfo(long roleId){
		byte[] key = getRoleSoundInfoKey(roleId);
		return jRedis.del(key);
	}
	
	public void saveRoleName(String roleName){
		byte[] key = getRoleNameKey(roleName);
		byte[] value = new byte[1];
		jRedis.set(key, value);
	}
	
	public boolean checkIsHaveRoleName(String roleName){
		byte[] key = getRoleNameKey(roleName);
		byte[] value = jRedis.get(key);
		if (value == null) {
			return false;
		}
		return true;
	}
	
	public void saveRoleStateInfo(long roleId, RoleStateInfo roleStateInfo) {
		byte[] key = getRoleStateInfoKey(roleId);
		jRedis.set(key, roleStateInfo.toByteArray());
	}
	
	public RoleStateInfo getRoleStateInfo(long roleId) {
		byte[] key = getRoleStateInfoKey(roleId);
		byte[] value = jRedis.get(key);
		if(value == null) {
			RoleStateInfo info = new RoleStateInfo();
			this.saveRoleStateInfo(roleId, info);
			return info;
		} else {
			RoleStateInfo info = new RoleStateInfo(value);
			return info;
		}
	}
}
