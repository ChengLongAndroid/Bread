package com.funcy.g01.base.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ebo.storage.redis.JRedis;
import com.funcy.g01.base.bo.npc.RoleNpcsInfo;
import com.funcy.g01.base.dao.redis.base.RedisKeyUtil;
import com.funcy.g01.base.dao.redis.base.StorageKey;

@Repository
public class NpcRepo {

	@Autowired
	private JRedis jRedis;
	
	private byte[] getRoleNpcsInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_npcs_info_key);
	}
	
	public RoleNpcsInfo getRoleNpcsInfo(long roleId) {
		byte[] key = getRoleNpcsInfoKey(roleId);
		byte[] bytes = jRedis.get(key);
		if(bytes == null) {
			return null;
		} else {
			RoleNpcsInfo roleNpcsInfo = new RoleNpcsInfo(bytes);
			roleNpcsInfo.setRoleId(roleId);
			return roleNpcsInfo;
		}
	}
	
	public void saveRoleNpcsInfo(RoleNpcsInfo roleNpcsInfo) {
		byte[] key = getRoleNpcsInfoKey(roleNpcsInfo.getRoleId());
		byte[] bytes = roleNpcsInfo.toByteArray();
		jRedis.set(key, bytes);
	}
	
}
