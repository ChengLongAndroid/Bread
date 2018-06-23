package com.funcy.g01.base.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ebo.storage.redis.JRedis;
import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.bo.item.RoleItemInfo;
import com.funcy.g01.base.dao.redis.base.RedisKeyUtil;
import com.funcy.g01.base.dao.redis.base.StorageKey;
import com.funcy.g01.base.data.ItemData;

@Repository
public class ItemRepo {

	@Autowired
	private JRedis jRedis;
	
	@Autowired
	private ItemData itemData;
	
	private byte[] getRoleItemInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_item_info_key);
	}
	
	public RoleItemInfo getRoleItemInfo(long roleId){
		byte[] key = getRoleItemInfoKey(roleId);
		byte[] bytes = jRedis.get(key);
		if (bytes == null) {
			RoleItemInfo roleItemInfo = new RoleItemInfo(roleId);
			saveRoleItemInfo(roleItemInfo);
			return roleItemInfo;
		} else {
			RoleItemInfo roleItemInfo = new RoleItemInfo(bytes);
			return roleItemInfo;
		}
	}
	
	public void saveRoleItemInfo(RoleItemInfo roleItemInfo) {
		byte[] key = getRoleItemInfoKey(roleItemInfo.getRoleId());
		byte[] bytes = roleItemInfo.toByteArray();
		jRedis.set(key, bytes);
	}
	
}
