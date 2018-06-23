package com.funcy.g01.login.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.bo.role.RoleNetInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.base.bo.user.UserInfo;
import com.funcy.g01.base.dao.redis.RoleRepo;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.net.DispatchServer;
import com.funcy.g01.base.proto.dispatcher.DispatcherRespCmdProtoBuffer.LoginGetHallRespProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.BoolReqProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.IntLongProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.StrProto;
import com.funcy.g01.base.sdk.ChannelIds;
import com.funcy.g01.login.dao.AdminChannelInfoDao;
import com.google.protobuf.Message.Builder;

@Service
public class LoginService {
	
	@Autowired
	private ServerContext serverContext;
	
	@Autowired
	private ServerInfoData serverInfoData;
	
	@Autowired
	private AdminChannelInfoDao adminChannelInfoDao;
	
	@Autowired
	private RoleRepo roleRepo;
	
	public Map<String, Object> signAndGetHall(String platformId, String ip, int channelID, String UUID) {
		Long loginTime = System.currentTimeMillis();
		
		if (channelID == ChannelIds.iosChannelId) {
			if (platformId.equals("")) {
				UserInfo userInfo = adminChannelInfoDao.findUserInfoByUUID(UUID);
				if (userInfo == null) {
					platformId = loginTime.toString();
					userInfo = new UserInfo(platformId, UUID);
					adminChannelInfoDao.insertUserInfo(userInfo);
				}else{
					platformId = userInfo.getPlatformId();
				}
			}
		}
		
		String token = DigestUtils.md5Hex(platformId + loginTime + ServerConfig.login_token_ext);
		token = loginTime + ";" + token;
		DispatchServer dispatchServer = serverContext.borrowLoginDispatchServer();
		try {
			int serverId;
			long roomId;
			RoleNetInfo roleNetInfo = roleRepo.getRoleNetInfoByPlatformId(platformId);
			if(roleNetInfo.getHallServerId() == -1) {
				Builder reqBuilder = StrProto.newBuilder().setParams1(platformId);
				LoginGetHallRespProto resp = (LoginGetHallRespProto)dispatchServer.sendAndWaitResp("roomDispatchService.loginGetHall", reqBuilder);
				serverId = resp.getServerId();
				roomId = resp.getRoomId();
			} else {
				IntLongProto.Builder isExistReqBuilder = IntLongProto.newBuilder();
				isExistReqBuilder.setParams1(roleNetInfo.getHallServerId());
				isExistReqBuilder.setParams2(roleNetInfo.getHallRoomId());
				BoolReqProto isExistResp = (BoolReqProto)dispatchServer.sendAndWaitResp("roomDispatchService.isTheRoomExist", isExistReqBuilder);
				boolean roomExist = isExistResp.getParams1();
				if(roomExist) {
					serverId = roleNetInfo.getHallServerId();
					roomId = roleNetInfo.getHallRoomId();
				} else {
					roleNetInfo.setHallRoomId(-1);
					roleNetInfo.setHallServerId(-1);
					roleRepo.saveRoleNetInfo(roleNetInfo);
					Builder reqBuilder = StrProto.newBuilder().setParams1(platformId);
					LoginGetHallRespProto resp = (LoginGetHallRespProto)dispatchServer.sendAndWaitResp("roomDispatchService.loginGetHall", reqBuilder);
					serverId = resp.getServerId();
					roomId = resp.getRoomId();
				}
			}
			int fightServerId = -1;
			long fightRoomId = -1;
			if(roleNetInfo.getFightServerId() != -1) {
				IntLongProto.Builder isExistReqBuilder = IntLongProto.newBuilder();
				isExistReqBuilder.setParams1(roleNetInfo.getHallServerId());
				isExistReqBuilder.setParams2(roleNetInfo.getHallRoomId());
				BoolReqProto isExistResp = (BoolReqProto)dispatchServer.sendAndWaitResp("roomDispatchService.isTheRoomExist", isExistReqBuilder);
				boolean roomExist = isExistResp.getParams1();
				if(roomExist) {
					fightServerId = roleNetInfo.getFightServerId();
					fightRoomId = roleNetInfo.getFightRoomId();
				} else {
					roleNetInfo.setFightRoomId(fightRoomId);
					roleNetInfo.setFightServerId(fightServerId);
					roleRepo.saveRoleNetInfo(roleNetInfo);
				}
			}
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("token", token);
			result.put("roomId", roomId);
			result.put("serverInfo", serverInfoData.getServerInfo(serverId).buildFrontServerInfo());
			result.put("fightRoomId", fightRoomId);
			result.put("platformId", platformId);
			if(fightServerId != -1) {
				result.put("fightServerInfo", serverInfoData.getServerInfo(fightServerId).buildFrontServerInfo());
			}
			return result;
		} finally {
			serverContext.addLoginDispatchServer4Send(dispatchServer);
		}
		
		
		
	}
	
	public Map<String, Object> bindGameCenter(String gameCenterPlayerId, String UUID) { 
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = adminChannelInfoDao.findUserInfoByGameCenterPlayerId(gameCenterPlayerId);
		if (userInfo == null) {
			userInfo = adminChannelInfoDao.findUserInfoByUUID(UUID);
			userInfo.setGameCenterId(gameCenterPlayerId);
			adminChannelInfoDao.saveUserInfo(userInfo);
		}else{
			Role role = roleRepo.getRole(userInfo.getPlatformId());
			result.put("roleName", role.getRoleName());
			result.put("level", role.getRoleLevel());
		}
		result.put("platformId", userInfo.getPlatformId());
		return result;
	}
	
}
