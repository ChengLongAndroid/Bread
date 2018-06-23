package com.funcy.g01.base.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ebo.storage.redis.JRedis;
import com.funcy.g01.base.bo.achieve.RoleAchieveInfo;
import com.funcy.g01.base.dao.redis.base.RedisKeyUtil;
import com.funcy.g01.base.dao.redis.base.StorageKey;
import com.funcy.g01.base.data.AchievementData;

@Repository
public class AchievementRepo {

	@Autowired
	private JRedis jRedis;
	
	@Autowired
	private AchievementData achievementData;
	
	private byte[] getRoleAchieveInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_achieve_info_key);
	}
	
	public RoleAchieveInfo getRoleAchieveInfo(long roleId){
		byte[] key = getRoleAchieveInfoKey(roleId);
		byte[] bytes = jRedis.get(key);
		if (bytes == null) {
			RoleAchieveInfo roleAchieveInfo = new RoleAchieveInfo(roleId,achievementData);
			saveRoleAchieveInfo(roleAchieveInfo);
			return roleAchieveInfo;
		} else {
			RoleAchieveInfo roleAchieveInfo = new RoleAchieveInfo(bytes,achievementData);
			return roleAchieveInfo;
		}
	}
	
	public void saveRoleAchieveInfo(RoleAchieveInfo RoleAchieveInfo) {
		byte[] key = getRoleAchieveInfoKey(RoleAchieveInfo.getRoleId());
		byte[] bytes = RoleAchieveInfo.toByteArray();
		jRedis.set(key, bytes);
	}
}
