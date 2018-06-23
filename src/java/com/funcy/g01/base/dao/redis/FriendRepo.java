package com.funcy.g01.base.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ebo.storage.redis.JRedis;
import com.funcy.g01.base.bo.friend.RoleFriendInfo;
import com.funcy.g01.base.bo.friend.RoleFriendMessageInfo;
import com.funcy.g01.base.bo.friend.RoleFriendReqInfo;
import com.funcy.g01.base.bo.friend.RoleRecentPartnerInfo;
import com.funcy.g01.base.dao.redis.base.RedisKeyUtil;
import com.funcy.g01.base.dao.redis.base.StorageKey;

@Repository
public class FriendRepo {

	@Autowired
	private JRedis jRedis;
		
	private byte[] getRoleFriendInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_friend_info_key);
	}
	
	private byte[] getRoleFriendReqInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_friend_req_info_key);
	}
	
	private byte[] getRoleFriendMessageInfoKey(long roleId, long targetRoleId) {
		return RedisKeyUtil.getByteArray(roleId+"-"+targetRoleId, StorageKey.role_friend_message_info_key);
	}
	
	private byte[] getRoleRecentPartnerInfoKey(long roleId){
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_recentPartner_info_key);
	}
	
	public RoleFriendInfo getRoleFriendInfo(long roleId){
		byte[] key = getRoleFriendInfoKey(roleId);
		byte[] bytes = jRedis.get(key);
		if (bytes == null) {
			RoleFriendInfo RoleFriendInfo = new RoleFriendInfo();
			saveRoleFriendInfo(roleId, RoleFriendInfo);
			return RoleFriendInfo;
		} else {
			RoleFriendInfo RoleFriendInfo = new RoleFriendInfo(bytes);
			return RoleFriendInfo;
		}  
	}
	
	public void saveRoleFriendInfo(long roleId, RoleFriendInfo roleFriendInfo) {
		byte[] key = getRoleFriendInfoKey(roleId);
		byte[] bytes = roleFriendInfo.toByteArray();
		jRedis.set(key, bytes);
	}
	
	public RoleFriendReqInfo getRoleFriendReqInfo(long roleId){
		byte[] key = getRoleFriendReqInfoKey(roleId);
		byte[] bytes = jRedis.get(key);
		if (bytes == null) {
			RoleFriendReqInfo roleFriendReqInfo = new RoleFriendReqInfo(roleId);
			saveRoleFriendReqInfo(roleFriendReqInfo);
			return roleFriendReqInfo;
		} else {
			RoleFriendReqInfo roleFriendReqInfo = new RoleFriendReqInfo(bytes);
			return roleFriendReqInfo;
		}
	}
	
	public void saveRoleFriendReqInfo(RoleFriendReqInfo roleFriendReqInfo) {
		byte[] key = getRoleFriendReqInfoKey(roleFriendReqInfo.getRoleId());
		byte[] bytes = roleFriendReqInfo.toByteArray();
		jRedis.set(key, bytes);
	}
	
	public RoleFriendMessageInfo getRoleFriendMessageInfo(long roleId, long targetRoleId){
		byte[] key = getRoleFriendMessageInfoKey(roleId, targetRoleId);
		byte[] bytes = jRedis.get(key);
		if (bytes == null) {
			RoleFriendMessageInfo roleFriendMessageInfo = new RoleFriendMessageInfo(roleId, targetRoleId);
			saveRoleFriendMessageInfo(roleId, targetRoleId, roleFriendMessageInfo);
			return roleFriendMessageInfo;
		} else {
			RoleFriendMessageInfo roleFriendMessageInfo = new RoleFriendMessageInfo(bytes);
			return roleFriendMessageInfo;
		}  
	}
	
	public void saveRoleFriendMessageInfo(long roleId, long targetRoleId, RoleFriendMessageInfo roleFriendMessageInfo) {
		byte[] key = getRoleFriendMessageInfoKey(roleId, targetRoleId);
		byte[] bytes = roleFriendMessageInfo.toByteArray();
		jRedis.set(key, bytes);
	}

	public boolean delRoleFriendMessageInfo(long roleId, long targetRoleId){
		byte[] key = getRoleFriendMessageInfoKey(roleId, targetRoleId);
		return jRedis.del(key);
	}
	
	public RoleRecentPartnerInfo getRoleRecentPartnerInfo(long roleId){
		byte[] key = getRoleRecentPartnerInfoKey(roleId);
		byte[] bytes =  jRedis.get(key);
		if(bytes==null){
			RoleRecentPartnerInfo info = new RoleRecentPartnerInfo(roleId);
			saveRoleRecentPartnerInfo(info);
			return info;
		}else{
			RoleRecentPartnerInfo info = new RoleRecentPartnerInfo(roleId);
			info.parseFrom(bytes);
			return info;
		}
	}
	
	public void saveRoleRecentPartnerInfo(RoleRecentPartnerInfo info){
		byte[] key = getRoleRecentPartnerInfoKey(info.getRoleId());
		jRedis.set(key, info.toByteArray());
	}
}
