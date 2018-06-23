package com.funcy.g01.login.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.user.AdminChannelInfo;
import com.funcy.g01.base.bo.user.AdminChannelInfoExample;
import com.funcy.g01.base.bo.user.UserInfo;
import com.funcy.g01.base.bo.user.UserInfoExample;
import com.funcy.g01.base.dao.sql.mapper.AdminChannelInfoMapper;
import com.funcy.g01.base.dao.sql.mapper.UserInfoMapper;


@Component
public class AdminChannelInfoDao {
	
	@Autowired
	private AdminChannelInfoMapper adminChannelInfoMapper;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	public List<AdminChannelInfo> getAdminChannelInfos() {
		return adminChannelInfoMapper.selectByExample(new AdminChannelInfoExample());
	}
	
	public UserInfo findUserInfoByUUID(String UUID){
		UserInfoExample userInfoExample = new UserInfoExample();
		userInfoExample.createCriteria().andUDIDEqualTo(UUID);
		List<UserInfo> list = userInfoMapper.selectByExample(userInfoExample);
		if(list.size() == 0) return null;
		return list.get(0);
	}
	
	public UserInfo findUserInfoByGameCenterPlayerId(String gameCenterPlayerId){
		UserInfoExample userInfoExample = new UserInfoExample();
		userInfoExample.createCriteria().andGameCenterIdEqualTo(gameCenterPlayerId);
		List<UserInfo> list = userInfoMapper.selectByExample(userInfoExample);
		if(list.size() == 0) return null;
		return list.get(0);
	}
	
	public int insertUserInfo(UserInfo userInfo){
		return userInfoMapper.insert(userInfo);
	}
	
	public int saveUserInfo(UserInfo userInfo){
		return userInfoMapper.updateByPrimaryKey(userInfo);
	}
	
	

}
