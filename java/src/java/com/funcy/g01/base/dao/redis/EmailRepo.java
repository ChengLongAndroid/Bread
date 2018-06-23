package com.funcy.g01.base.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ebo.storage.redis.JRedis;
import com.funcy.g01.base.bo.email.RoleEmailInfo;
import com.funcy.g01.base.bo.email.SendEmailMessage;
import com.funcy.g01.base.bo.email.SendEmailMessageInfo;
import com.funcy.g01.base.dao.redis.base.RedisKeyUtil;
import com.funcy.g01.base.dao.redis.base.StorageKey;
import com.funcy.g01.base.global.ServerConfig;

@Repository
public class EmailRepo {

	@Autowired
	private JRedis jRedis;
	
	private static final String EMAIL_ID_KEY_STR = "email id key str, do not change it";
	
	private byte[] emailIdKey = null;
	
	private byte[] getEmailIdKey() {
		if(emailIdKey == null) emailIdKey = RedisKeyUtil.getByteArray(EMAIL_ID_KEY_STR, StorageKey.global_inc_emailIdKey);
		return emailIdKey;
	}
	
	public int newEmailId() {
		return Integer.parseInt(jRedis.incr(getEmailIdKey())+"");
	}
	
	private byte[] getRoleEmailInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_email_info_key);
	}
	
	
	private byte[] getSendEmailInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.send_email_info_key);
	}
	
	
	public RoleEmailInfo getRoleEmailInfo(long roleId){
		byte[] key = getRoleEmailInfoKey(roleId);
		byte[] bytes = jRedis.get(key);
		if (bytes == null) {
			RoleEmailInfo roleEmailInfo = new RoleEmailInfo(roleId);
			roleEmailInfo.setRoleId(roleId);
			saveRoleEmailInfo(roleEmailInfo);
			return roleEmailInfo;
		} else {
			RoleEmailInfo roleEmailInfo = new RoleEmailInfo(bytes);
			roleEmailInfo.setRoleId(roleId);
			return roleEmailInfo;
		}
	}
	
	public void saveRoleEmailInfo(RoleEmailInfo roleEmailInfo) {
		byte[] key = getRoleEmailInfoKey(roleEmailInfo.getRoleId());
		byte[] bytes = roleEmailInfo.toByteArray();
		jRedis.set(key, bytes);
	}
	
	public SendEmailMessageInfo getSendEmailMessageInfo(){
		byte[] key = getSendEmailInfoKey(ServerConfig.system_role_id);
		byte[] bytes = jRedis.get(key);
		if (bytes == null) {
			SendEmailMessageInfo roleEmailInfo = new SendEmailMessageInfo();
			saveSendEmailMessageInfo(roleEmailInfo);
			return roleEmailInfo;
		} else {
			SendEmailMessageInfo roleEmailInfo = new SendEmailMessageInfo(bytes);
			return roleEmailInfo;
		}
	}
	
	public void saveSendEmailMessageInfo(SendEmailMessageInfo roleEmailInfo) {
		byte[] key = getRoleEmailInfoKey(ServerConfig.system_role_id);
		byte[] bytes = roleEmailInfo.toByteArray();
		jRedis.set(key, bytes);
	}
	
	public void addSendEmailMessageInfo(SendEmailMessage sendEmailMessage){
		getSendEmailMessageInfo().addSendEmailMessage(sendEmailMessage);
	}
}
