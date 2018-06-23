package com.funcy.g01.base.bo.friend;

import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.bo.role.RoleNetInfo;
import com.funcy.g01.base.bo.role.RoleState;
import com.funcy.g01.base.bo.role.RoleStateInfo;

public class FriendNpc {
	
	public static final long npc_friend_id = -9999999;
	
	public static final String login_talking_words = "开心兔一直在这里等着你哦!";
	
	public static final String platformId = "funny";
	
	public static final String name = "开心兔";
	
	public static final String declaration = "你好啊,我是开心兔!";
	
	private static Role role = new Role(npc_friend_id, platformId, "");
	
	public static Role newRole(){
		role.setRoleName(name);
		role.setDeclaration(declaration);
		role.setShammanLevel(10);
		role.setAchievePoint(999);
		role.setLoginIp("");
		role.setRoleLevel(80);
		return role;
	}
	
	/**
	 * 填充好友时需要
	 * @return
	 */
	public static Friend createNpcFriend(){
		RoleNetInfo roleNetInfo = new RoleNetInfo();
		roleNetInfo.setRoleId(FriendNpc.npc_friend_id);
		
		RoleStateInfo roleStateInfo = new RoleStateInfo();
		roleStateInfo.setState(RoleState.ONLINE);
		
		return new Friend(newRole(), roleStateInfo, roleNetInfo);
	}
	
	/***
	 * 通过过滤关键词自动回复
	 * @param key
	 * @return
	 */
	public static String filterKeyAndAutoReply(String key){
		return "你好,我是开心兔!";
	}
}
