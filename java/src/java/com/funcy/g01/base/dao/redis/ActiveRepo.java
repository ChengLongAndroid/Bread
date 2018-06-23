package com.funcy.g01.base.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ebo.storage.redis.JRedis;
import com.funcy.g01.base.bo.chest.RoleChestInfo;
import com.funcy.g01.base.bo.mysteriousshop.RoleMysteriousShopInfo;
import com.funcy.g01.base.bo.signin.RoleSigninInfo;
import com.funcy.g01.base.dao.redis.base.RedisKeyUtil;
import com.funcy.g01.base.dao.redis.base.StorageKey;

@Repository
public class ActiveRepo {

	@Autowired
	private JRedis jRedis;
	
	private byte[] getRoleChestInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_chest_info_key);
	}
	
	private byte[] getRoleSigninInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_signin_info_key);
	}
	
	
	private byte[] getRoleMysteriousShopInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_mysterious_shop_Info_key);
	}
	
	public RoleChestInfo getRoleChestInfo(long roleId) {
		byte[] key = getRoleChestInfoKey(roleId);
		byte[] bytes = jRedis.get(key);
		if(bytes == null) {
			RoleChestInfo roleChestInfo = new RoleChestInfo();
			roleChestInfo.setRoleId(roleId);
			this.saveRoleChestInfo(roleChestInfo);
			return roleChestInfo;
		} else {
			RoleChestInfo roleChestInfo = new RoleChestInfo(bytes);
			roleChestInfo.setRoleId(roleId);
			return roleChestInfo;
		}
	}
	
	public void saveRoleChestInfo(RoleChestInfo roleChestInfo) {
		byte[] key = getRoleChestInfoKey(roleChestInfo.getRoleId());
		byte[] bytes = roleChestInfo.toByteArray();
		jRedis.set(key, bytes);
	}
	
	public void saveRoleSigninfo(RoleSigninInfo roleSigninInfo) {
		byte[] key = getRoleSigninInfoKey(roleSigninInfo.getRoleId());
		byte[] bytes = roleSigninInfo.toByteArray();
		jRedis.set(key, bytes);
	}
	
	public RoleSigninInfo getRoleSigninfo(long roleId) {
		byte[] key = getRoleSigninInfoKey(roleId);
		byte[] bytes = jRedis.get(key);
		if(bytes == null) {
			RoleSigninInfo roleSigninfo = new RoleSigninInfo();
			roleSigninfo.setRoleId(roleId);
			this.saveRoleSigninfo(roleSigninfo);
			return roleSigninfo;
		} else {
			RoleSigninInfo roleSigninfo = new RoleSigninInfo(bytes);
			roleSigninfo.setRoleId(roleId);
			return roleSigninfo;
		}
	}
	
	public void saveRoleMysteriousShopInfo(RoleMysteriousShopInfo roleMysteriousShopfo) {
		byte[] key = getRoleMysteriousShopInfoKey(roleMysteriousShopfo.getRoleId());
		byte[] bytes = roleMysteriousShopfo.toByteArray();
		jRedis.set(key, bytes);
	}
	
	public RoleMysteriousShopInfo getRoleMysteriousShopInfo(long roleId) {
		byte[] key = getRoleMysteriousShopInfoKey(roleId);
		byte[] bytes = jRedis.get(key);
		if(bytes == null) {
			RoleMysteriousShopInfo roleSigninfo = new RoleMysteriousShopInfo();
			roleSigninfo.setRoleId(roleId);
			this.saveRoleMysteriousShopInfo(roleSigninfo);
			return roleSigninfo;
		} else {
			RoleMysteriousShopInfo roleSigninfo = new RoleMysteriousShopInfo(bytes);
			roleSigninfo.setRoleId(roleId);
			return roleSigninfo;
		}
	}
	
}
