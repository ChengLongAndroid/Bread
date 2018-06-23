package com.funcy.g01.base.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ebo.storage.redis.JRedis;
import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.bo.totem.RoleSkillInfo;
import com.funcy.g01.base.bo.totem.RoleTotemInfo;
import com.funcy.g01.base.bo.totem.SkillVo;
import com.funcy.g01.base.dao.redis.base.RedisKeyUtil;
import com.funcy.g01.base.dao.redis.base.StorageKey;
import com.funcy.g01.base.data.SkillData;
import com.funcy.g01.base.data.TotemData;

@Repository
public class TotemRepo {

	@Autowired
	private JRedis jRedis;
	
	@Autowired
	private SkillData skillData;
	
	@Autowired
	private TotemData totemData;
	
	private byte[] getRoleTotemInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_totem_info_key);
	}
	
	public RoleTotemInfo getRoleTotemInfo(long roleId){
		byte[] key = getRoleTotemInfoKey(roleId);
		byte[] bytes = jRedis.get(key);
		if (bytes == null) {
			RoleTotemInfo roleTotemInfo = new RoleTotemInfo(roleId, totemData);
			saveRoleTotemInfo(roleTotemInfo);
			return roleTotemInfo;
		} else {
			RoleTotemInfo roleTotemInfo = new RoleTotemInfo(bytes, totemData);
			return roleTotemInfo;
		}
	}
	
	public void saveRoleTotemInfo(RoleTotemInfo roleTotemInfo) {
		byte[] key = getRoleTotemInfoKey(roleTotemInfo.getRoleId());
		byte[] bytes = roleTotemInfo.toByteArray();
		jRedis.set(key, bytes);
	}
	
	
	private byte[] getRoleSkillInfoKey(long roleId) {
		return RedisKeyUtil.getByteArray(roleId, StorageKey.role_skill_info_key);
	}
	
	public RoleSkillInfo getRoleSkillInfo(long roleId){
		byte[] key = getRoleSkillInfoKey(roleId);
		byte[] bytes = jRedis.get(key);
		if (bytes == null) {
			RoleSkillInfo roleSkillInfo = new RoleSkillInfo(roleId);
			saveRoleSkillInfo(roleSkillInfo);
			return roleSkillInfo;
		} else {
			RoleSkillInfo roleSkillInfo = new RoleSkillInfo(bytes);
			return roleSkillInfo;
		}
	}
	
	public void saveRoleSkillInfo(RoleSkillInfo roleSkillInfo) {
		byte[] key = getRoleSkillInfoKey(roleSkillInfo.getRoleId());
		byte[] bytes = roleSkillInfo.toByteArray();
		jRedis.set(key, bytes);
	}
	
	public SkillVo addSkill(long roleId, Item item){
		RoleSkillInfo roleSkillInfo = getRoleSkillInfo(roleId);
		int xmlId = item.getXmlId();
		SkillVo skillVo = null;
		if (!roleSkillInfo.checkHadSkill(xmlId)) {
			skillVo = new SkillVo(skillData.getSkillProperty(xmlId).getInitstar(), xmlId);
			roleSkillInfo.addSkill(skillVo);
			this.saveRoleSkillInfo(roleSkillInfo);
		}
		return skillVo;
	}
	
}
