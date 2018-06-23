package com.funcy.g01.base.dao.redis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ebo.storage.redis.JRedis;
import com.funcy.g01.base.bo.dress.DressType;
import com.funcy.g01.base.bo.dress.DressVo;
import com.funcy.g01.base.bo.dress.RoleDressInfo;
import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.dao.redis.base.RedisKeyUtil;
import com.funcy.g01.base.dao.redis.base.StorageKey;
import com.funcy.g01.base.data.DressData;

@Repository
public class DressRepo {

	@Autowired
	private JRedis jRedis;
	
	@Autowired
	private DressData dressData;
	
	private byte[] getRoleDressInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_dress_info_key);
	}
	
	public RoleDressInfo getRoleDressInfo(long roleId){
		byte[] key = getRoleDressInfoKey(roleId);
		byte[] bytes = jRedis.get(key);
		if (bytes == null) {
			RoleDressInfo roleDressInfo = new RoleDressInfo(roleId);
			saveRoleDressInfo(roleDressInfo);
			return roleDressInfo;
		} else {
			RoleDressInfo roleDressInfo = new RoleDressInfo(bytes);
			return roleDressInfo;
		}
	}
	
	public void saveRoleDressInfo(RoleDressInfo roleDressInfo) {
		byte[] key = getRoleDressInfoKey(roleDressInfo.getRoleId());
		byte[] bytes = roleDressInfo.toByteArray();
		jRedis.set(key, bytes);
	}
	
	public DressVo addDress(long roleId, Item item){
		RoleDressInfo roleDressInfo = getRoleDressInfo(roleId);
		int xmlId = item.getXmlId();
		DressVo  dressVo = null;
		if (!roleDressInfo.checkHadDress(xmlId)) {
			dressVo = new DressVo(item.getXmlId());
			roleDressInfo.addDress(dressVo);
			this.saveRoleDressInfo(roleDressInfo);
		}
		return dressVo;
	}
	
	public int getDressNumByType(long roleId, DressType type){
		RoleDressInfo roleDressInfo = getRoleDressInfo(roleId);
		int count = 0;
		List<DressVo> dresses = roleDressInfo.getDresses();
		for (DressVo dressVo : dresses) {
			if (dressData.getDressProperty(dressVo.getXmlId()).getType() == type.getCode()) {
				count += 1;
			}
		}
		return count;
	}
	
}
