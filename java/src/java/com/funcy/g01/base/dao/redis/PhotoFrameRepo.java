package com.funcy.g01.base.dao.redis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ebo.storage.redis.JRedis;
import com.funcy.g01.base.bo.dress.DressType;
import com.funcy.g01.base.bo.dress.DressVo;
import com.funcy.g01.base.bo.dress.RoleDressInfo;
import com.funcy.g01.base.bo.frame.FrameVo;
import com.funcy.g01.base.bo.frame.RoleFrameInfo;
import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.dao.redis.base.RedisKeyUtil;
import com.funcy.g01.base.dao.redis.base.StorageKey;
import com.funcy.g01.base.data.DressData;
import com.funcy.g01.base.data.PhotoFrameData;

@Repository
public class PhotoFrameRepo {

	@Autowired
	private JRedis jRedis;
	
	@Autowired
	private PhotoFrameData photoFrameData;
	
	private byte[] getRoleFrameInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_photo_frame_info_key);
	}
	
	public RoleFrameInfo getRoleFrameInfo(long roleId){
		byte[] key = getRoleFrameInfoKey(roleId);
		byte[] bytes = jRedis.get(key);
		if (bytes == null) {
			RoleFrameInfo roleFrameInfo = new RoleFrameInfo(roleId);
			saveRoleFrameInfo(roleFrameInfo);
			return roleFrameInfo;
		} else {
			RoleFrameInfo roleFrameInfo = new RoleFrameInfo(bytes);
			return roleFrameInfo;
		}
	}
	
	public void saveRoleFrameInfo(RoleFrameInfo roleFrameInfo) {
		byte[] key = getRoleFrameInfoKey(roleFrameInfo.getRoleId());
		byte[] bytes = roleFrameInfo.toByteArray();
		jRedis.set(key, bytes);
	}
	
	public FrameVo addFrame(long roleId, Item item){
		RoleFrameInfo roleFrameInfo = getRoleFrameInfo(roleId);
		int xmlId = item.getXmlId();
		FrameVo  frameVo = null;
		if (!roleFrameInfo.checkHadFrame(xmlId)) {
			frameVo = new FrameVo(item.getXmlId());
			roleFrameInfo.addPhotoFrame(frameVo);
			this.saveRoleFrameInfo(roleFrameInfo);
		}
		return frameVo;
	}
	
}
