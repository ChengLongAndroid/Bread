package com.funcy.g01.base.dao.redis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ebo.storage.redis.JRedis;
import com.ebo.storage.redis.RedisStorageKey;
import com.funcy.g01.base.bo.map.RoleMapInfo;
import com.funcy.g01.base.bo.map.ServerMapInfo;
import com.funcy.g01.base.dao.redis.base.RedisKeyUtil;
import com.funcy.g01.base.dao.redis.base.StorageKey;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto;

@Repository
public class FightRepo {

	@Autowired
	private JRedis jRedis;
	
	private byte[] getMapIdKey(long mapId) {
		return RedisKeyUtil.getByteArray(mapId, StorageKey.fight_map_key);
	}
	
	private byte[] getRoleMapInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_map_info_key);
	}
	
	public long getUniMapId(long roleId, int mapIndex) {
		return roleId * 100000 + mapIndex;
	}
	
	public ServerMapInfo getServerMapInfo(long mapId) {
		byte[] key = getMapIdKey(mapId);
		byte[] bytes = jRedis.get(key);
		return new ServerMapInfo(bytes);
	}
	
	public void saveServerMapInfo(ServerMapInfo serverMapInfo,int mapIndex) {
		long mapId =  serverMapInfo.getMapId();
		if(mapId == 0) {
			mapId = getUniMapId(serverMapInfo.getCreateRoleId(), mapIndex);
			serverMapInfo.setMapId(mapId);
		}
		byte[] key = getMapIdKey(mapId);
		jRedis.set(key, serverMapInfo.toByteArray());
	}
	
	public void saveServerMapInfo(ServerMapInfoProto serverMapInfoProto){
		byte[] key = getMapIdKey(serverMapInfoProto.getMapId());
		jRedis.set(key, serverMapInfoProto.toByteArray());
	}
	
	public void deleteServerMapInfo(long mapId){
		byte[] key = getMapIdKey(mapId);
		byte[] bytes = jRedis.get(key);
		if (bytes != null) {
			ServerMapInfo serverMapInfo = new ServerMapInfo(bytes);
			RoleMapInfo roleMapInfo = this.getRoleMapInfo(serverMapInfo.getCreateRoleId());
			List<Long> mapIds = roleMapInfo.getMapIds();
			for (int i = 0; i < mapIds.size(); i++) {
				long tempMapId = mapIds.get(i);
				if (mapId == tempMapId) {
					mapIds.remove(i);
					this.saveRoleMapInfo(roleMapInfo);
					break;
				}
			}
			jRedis.del(key);
		}
	}
	
	public RoleMapInfo getRoleMapInfo(long roleId) {
		byte[] key = getRoleMapInfoKey(roleId);
		byte[] bytes = jRedis.get(key);
		if(bytes == null) {
			RoleMapInfo roleMapInfo = new RoleMapInfo(roleId);
			return roleMapInfo;
		} else {
			RoleMapInfo roleMapInfo = new RoleMapInfo(bytes);
			return roleMapInfo;
		}
	}
	
	public void saveRoleMapInfo(RoleMapInfo roleMapInfo) {
		byte[] key = getRoleMapInfoKey(roleMapInfo.getRoleId());
		byte[] bytes = roleMapInfo.toByteArray();
		jRedis.set(key, bytes);
	}
	
}
