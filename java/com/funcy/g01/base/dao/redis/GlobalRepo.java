package com.funcy.g01.base.dao.redis;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Transaction;

import com.ebo.storage.redis.JRedis;
import com.funcy.g01.base.dao.redis.base.RedisKeyUtil;
import com.funcy.g01.base.dao.redis.base.StorageKey;
import com.funcy.g01.base.util.JedisUtil;

@Component
public class GlobalRepo {

	@Autowired
	private JRedis redisClient;
	
	private byte[] roleIdKey = null;
	
	private static final String ROLE_ID_KEY_STR = "role id key str, do not change it";
	
	private byte[] getRoleIdKey() {
		if(roleIdKey == null) roleIdKey = RedisKeyUtil.getByteArray(ROLE_ID_KEY_STR, StorageKey.global_inc_roleIdKey);
		return roleIdKey;
	}
	
	public long newRoleId() {
		return redisClient.incr(getRoleIdKey());
	}
	
	public long getRoleIdIndex() {
		byte[] byteResult = redisClient.get(getRoleIdKey());
		if(byteResult == null) {
			return 0l;
		}
		String strResult = new String(byteResult);
		return Long.parseLong(strResult);
	}
	
	public void delRecord(byte[] key) {
		redisClient.del(key);
	}
	
	public boolean tryGetPrepareLock(byte[] key, byte[] stamp, int seconds) {
		if(redisClient.setnx(key, stamp)) {
			redisClient.expire(key, seconds);
			return true;
		}
		return false;
	}
	
	public boolean tryGetFightLock(byte[] key, byte[] stamp, int seconds) {
		redisClient.watch(key);
		byte[] frontStamp = redisClient.get(key);
		if(Arrays.equals(stamp, frontStamp)) {
			Transaction transaction = redisClient.multi(key);
			transaction.expire(key, seconds);
			Object result = transaction.exec();
			if(result != null) return true; 
			else return false;
		}
		else {
			redisClient.unwatch(key);
			return false;
		}
	}
	
	public boolean tryReleaseLock(byte[] key, byte[] stamp) {
		redisClient.watch(key);
		byte[] frontStamp = redisClient.get(key);
		if(Arrays.equals(stamp, frontStamp)) {
			Transaction transaction = redisClient.multi(key);
			transaction.del(key);
			Object result = transaction.exec();
			if(result != null) return true; 
		}
		return false;
	}
}
