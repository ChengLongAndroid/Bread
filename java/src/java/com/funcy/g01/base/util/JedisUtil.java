package com.funcy.g01.base.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.funcy.g01.base.bo.serverconfig.ServerInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfoData;

public class JedisUtil {
	
	private static JedisPool jedisPool;
	
	private static Jedis jedis;
	
	public static void init(){
		ServerInfo self = ServerInfoData.getInstance().getSelfServerInfo();
		jedisPool = new JedisPool(self.getIp());
		jedis = jedisPool.getResource();
	}
	
	public static String valueOf(String key){
		return jedis.get(key);
	}
	
	public static void set(String key,String value){
		jedis.set(key, value);
	}
	
}
