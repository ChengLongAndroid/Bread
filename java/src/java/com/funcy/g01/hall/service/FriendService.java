package com.funcy.g01.hall.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebo.synframework.nettybase.protoPool.ProtoBuilderPoolManager;
import com.ebo.synframework.nettybase.protoPool.ReusedProtoBuilder;
import com.funcy.g01.base.bo.friend.Friend;
import com.funcy.g01.base.bo.friend.FriendMessage;
import com.funcy.g01.base.bo.friend.FriendNpc;
import com.funcy.g01.base.bo.friend.FriendReq;
import com.funcy.g01.base.bo.friend.FriendReqState;
import com.funcy.g01.base.bo.friend.Partner;
import com.funcy.g01.base.bo.friend.RecommendFriend;
import com.funcy.g01.base.bo.friend.RecommendFriendtype;
import com.funcy.g01.base.bo.friend.RoleFriendInfo;
import com.funcy.g01.base.bo.friend.RoleFriendMessageInfo;
import com.funcy.g01.base.bo.friend.RoleFriendReqInfo;
import com.funcy.g01.base.bo.friend.RoleRecentPartnerInfo;
import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.bo.role.RoleNetInfo;
import com.funcy.g01.base.bo.role.RoleState;
import com.funcy.g01.base.bo.role.RoleStateInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.base.dao.redis.AchievementRepo;
import com.funcy.g01.base.dao.redis.FriendRepo;
import com.funcy.g01.base.dao.redis.GlobalRepo;
import com.funcy.g01.base.dao.redis.RoleRepo;
import com.funcy.g01.base.global.BusinessPool;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.net.CmdStatus;
import com.funcy.g01.base.net.GamePlayer;
import com.funcy.g01.base.proto.service.FriendProtoBuffer.AcceptFriendReqResult;
import com.funcy.g01.base.proto.service.FriendProtoBuffer.FriendMessageVoProto;
import com.funcy.g01.base.proto.service.FriendProtoBuffer.GetRecommendFriendsRespProto;
import com.funcy.g01.base.proto.service.FriendProtoBuffer.RecommendFriendVoProto;
import com.funcy.g01.base.proto.service.FriendProtoBuffer.RoleFriendInfoProto4Front;
import com.funcy.g01.base.proto.service.ReqCmdProto.IntReqProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.LongListProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.LongReqProto;
import com.funcy.g01.base.util.DirtyUtil;
import com.funcy.g01.base.util.HttpUtils;
import com.google.protobuf.Message.Builder;

@Service
public class FriendService {

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private AchievementRepo achivementRepo;
			
//	@Autowired
//	private AchivementDomainService achivementDomainService;

	@Autowired
	private FriendRepo friendRepo;

	@Autowired
	private ServerContext serverContext;

	@Autowired
	private ServerInfoData serverInfoData;

	@Autowired
	private BusinessPool businessPool;
	
	@Autowired
	private GlobalRepo globalRepo;
	
	public Builder sendRoleMsg(final long targetRoleId, String msg, final GamePlayer gamePlayer){
		
		/*只允许60个中文字*/
		int toIndex = 60;
		if(msg.length() < toIndex) {
			toIndex = msg.length();
		}
		msg = msg.substring(0, toIndex);
		/*敏感词过滤*/
		final String filterdMsg = DirtyUtil.replace(msg);
		
		// 返回给发送方
		FriendMessageVoProto.Builder returnMsgBuilder = FriendMessageVoProto.newBuilder();
		returnMsgBuilder.setFromRoleId(gamePlayer.getRoleId());
		returnMsgBuilder.setToRoleId(targetRoleId);
		returnMsgBuilder.setMsg(filterdMsg);
		returnMsgBuilder.setCreateTime(System.currentTimeMillis());
		
		businessPool.schedule(new Runnable() {
			@Override
			public void run() {
				RoleNetInfo targetRoleNetInfo = null;
				if(targetRoleId==FriendNpc.npc_friend_id){
					targetRoleNetInfo = new RoleNetInfo();
					targetRoleNetInfo.setRoleId(FriendNpc.npc_friend_id);
					targetRoleNetInfo.setHallServerId(serverInfoData.getSelfServerId());
				}else{
					targetRoleNetInfo = roleRepo.getRoleNetInfo(targetRoleId);
				}
				if(targetRoleNetInfo != null){
					
					String sendUrlSuffix = ServerConfig.http_domain_name + "/service/receiveRoleMsg.jsp";			
					int hallServerId = targetRoleNetInfo.getHallServerId();
					if(hallServerId > 0){
						ServerInfo hallServerInfo = serverInfoData.getServerInfo(hallServerId);
						String hallUrl = ("http://" + hallServerInfo.getIp() + ":" + hallServerInfo.getHttpPort() + sendUrlSuffix);
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("targetRoleId", targetRoleId);
						params.put("msg", filterdMsg);
						params.put("fromRoleId", gamePlayer.getRoleId());
						HttpUtils.doGet(hallUrl, params, "utf-8");
					}
					/*保存离线记录*/
					else {
						RoleFriendMessageInfo roleFriendMessageInfo = friendRepo.getRoleFriendMessageInfo(targetRoleId, gamePlayer.getRoleId());
						roleFriendMessageInfo.addMsg(new FriendMessage(gamePlayer.getRoleId(), targetRoleId, filterdMsg, System.currentTimeMillis()));
						friendRepo.saveRoleFriendMessageInfo(targetRoleId, gamePlayer.getRoleId(), roleFriendMessageInfo);
					}
					
				}
			}
		}, 300, TimeUnit.MILLISECONDS);
		
		return returnMsgBuilder;
		
	}
	
	public void receiveRoleMsg(long targetRoleId, String msg, long fromRoleId){
		if(targetRoleId==FriendNpc.npc_friend_id){
			GamePlayer gamePlayer = new GamePlayer();
			gamePlayer.setRoleId(FriendNpc.npc_friend_id);
			sendRoleMsg(fromRoleId, FriendNpc.filterKeyAndAutoReply(msg), gamePlayer);
		}else{
			final GamePlayer targetGamePlayer = serverContext.findHallLogonPlayer(targetRoleId);
			if(targetGamePlayer != null){
				FriendMessageVoProto.Builder friendMessageVoProto = FriendMessageVoProto.newBuilder();
				friendMessageVoProto.setFromRoleId(fromRoleId);
				friendMessageVoProto.setToRoleId(targetRoleId);
				friendMessageVoProto.setMsg(msg);
				friendMessageVoProto.setCreateTime(System.currentTimeMillis());
				targetGamePlayer.respond("receiveRoleMsg", CmdStatus.notDecrypt, friendMessageVoProto);
			}
		}
	}
	
	public void notifyFriendReq(long fromRoleId, long toRoleId) {
		GamePlayer gamePlayer = serverContext.findHallLogonPlayer(toRoleId);
		if(gamePlayer != null) {
			Role friendRole = roleRepo.getRole(fromRoleId);
			FriendReq friendReq = new FriendReq(fromRoleId, FriendReqState.PENDING);
			friendReq.setFriendVo(new Friend(friendRole));
			gamePlayer.respond("notifyFriendReqReceived", CmdStatus.notDecrypt, friendReq.build4Front().toBuilder());
		}
	}
	
	public void removeFriend(final long targetRoleId, final GamePlayer gamePlayer){
		// 清空双方的聊天记录
		friendRepo.delRoleFriendMessageInfo(gamePlayer.getRoleId(), targetRoleId);
		friendRepo.delRoleFriendMessageInfo(targetRoleId, gamePlayer.getRoleId());
		
		// 清除双方的好友关系
		RoleFriendInfo roleFriendInfo = friendRepo.getRoleFriendInfo(gamePlayer.getRoleId());
		RoleFriendInfo targetRoleFriendInfo = friendRepo.getRoleFriendInfo(targetRoleId);
		roleFriendInfo.removeFriend(targetRoleId);
		targetRoleFriendInfo.removeFriend(gamePlayer.getRoleId());
		friendRepo.saveRoleFriendInfo(gamePlayer.getRoleId(), roleFriendInfo);
		friendRepo.saveRoleFriendInfo(targetRoleId, targetRoleFriendInfo);
		
		businessPool.schedule(new Runnable() {
			@Override
			public void run() {
				RoleNetInfo targetRoleNetInfo = roleRepo.getRoleNetInfo(targetRoleId);
				if(targetRoleNetInfo != null){
					
					String sendUrlSuffix = ServerConfig.http_domain_name + "/service/receiveFriendRemove.jsp";			
					int hallServerId = targetRoleNetInfo.getHallServerId();
					if(hallServerId > 0){
						ServerInfo hallServerInfo = serverInfoData.getServerInfo(hallServerId);
						String hallUrl = ("http://" + hallServerInfo.getIp() + ":" + hallServerInfo.getHttpPort() + sendUrlSuffix);
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("fromRoleId", gamePlayer.getRoleId());
						params.put("targetRoleId", targetRoleId);
						HttpUtils.doGet(hallUrl, params, "utf-8");
					}
				}
			}
		}, 300, TimeUnit.MILLISECONDS);
	}
	
	public void receiveFriendRemove(long fromRoleId, long targetRoleId){
		final GamePlayer targetGamePlayer = serverContext.findHallLogonPlayer(targetRoleId);
		if(targetGamePlayer != null){
			LongReqProto.Builder longReqProto = LongReqProto.newBuilder();
			longReqProto.setIndex(fromRoleId);
			targetGamePlayer.respond("receiveFriendRemove", CmdStatus.notDecrypt, longReqProto);
		}
	}
	
	public List<FriendMessage> getOfflineFriendMessageList(long roleId){
		List<FriendMessage> result = new ArrayList<FriendMessage>();
		RoleFriendInfo roleFriendInfo = friendRepo.getRoleFriendInfo(roleId);
		for(Friend friend : roleFriendInfo.getFriends()){
			RoleFriendMessageInfo roleFriendMessageInfo = friendRepo.getRoleFriendMessageInfo(roleId, friend.getRoleId());
			result.addAll(roleFriendMessageInfo.getMsgs());
			/*离线消息传过去后，立即删除服务端保存*/
			friendRepo.delRoleFriendMessageInfo(roleId, friend.getRoleId());
		}
		return result;
	}
	
	public Builder getFriendsUpdate(List<Long> roleIds, GamePlayer gamePlayer){
		RoleFriendInfoProto4Front.Builder builder = RoleFriendInfoProto4Front.newBuilder();
		List<Friend> friends = new ArrayList<Friend>();
		if(roleIds != null && roleIds.size() > 0){
			for(long roleId : roleIds){
				if(roleId==FriendNpc.npc_friend_id){
					builder.addFriends(FriendNpc.createNpcFriend().buildProto4Front());
				}else{
					Role role = roleRepo.getRole(roleId);
					RoleNetInfo roleNetInfo = roleRepo.getRoleNetInfo(roleId);
					RoleStateInfo roleStateInfo = roleRepo.getRoleStateInfo(roleId);
					Friend friend = new Friend(role, roleStateInfo, roleNetInfo);
//					builder.addFriends(friend.buildProto4Front());
					friends.add(friend);
				}
			}
		}
		friendsShowSort(friends);
		for(Friend friend : friends){
			builder.addFriends(friend.buildProto4Front());
		}
		return builder;
	}
	
	private void friendsShowSort(List<Friend> friends){
		friends.sort(new Comparator<Friend>() {  //比赛 -> 在线 ->  最近上线时间短
			public int compare(Friend o1, Friend o2) {
				if(o1.getState().getCode() > o2.getState().getCode()){
					return 1;
				}else if(o1.getState().getCode() < o2.getState().getCode()){
					return -1;
				}else{
					if(o1.getLastPlayOpenTime() > o2.getLastPlayOpenTime()){
						return 1;
					}else if(o1.getLastPlayOpenTime() < o2.getLastPlayOpenTime()){
						return -1;
					}
					return 0;
				}
			};
		});
	}
	
	public Builder getFriendPage(int index, GamePlayer gamePlayer){
		RoleFriendInfoProto4Front.Builder builder = RoleFriendInfoProto4Front.newBuilder();
		RoleFriendInfo roleFriendInfo = friendRepo.getRoleFriendInfo(gamePlayer.getRoleId());
		List<Friend> friendList = roleFriendInfo.getFriends();
		if(friendList != null && friendList.size() > 0){
			for(Friend friend : friendList){
				Role role = roleRepo.getRole(friend.getRoleId());
				RoleNetInfo roleNetInfo = roleRepo.getRoleNetInfo(friend.getRoleId());
				RoleStateInfo roleStateInfo = roleRepo.getRoleStateInfo(friend.getRoleId());
				friend.initFriend(role, roleStateInfo, roleNetInfo);
				builder.addFriends(friend.buildProto4Front());
			}
		}
		return builder;
	}
	
	public void clearFriendReqHaveReadState(GamePlayer gamePlayer) {
		RoleFriendReqInfo roleFriendReqInfo = friendRepo.getRoleFriendReqInfo(gamePlayer.getRoleId());
		if(roleFriendReqInfo != null){
			if(roleFriendReqInfo.clearHaveReadState()) {
				friendRepo.saveRoleFriendReqInfo(roleFriendReqInfo);
			}
		}
	}
	
	public List<FriendReq> getFriendReqList(long roleId){
		List<FriendReq> friendReqList = friendRepo.getRoleFriendReqInfo(roleId).getFriendReqs();
		if(friendReqList != null && friendReqList.size() > 0){
			for(FriendReq friendReq : friendReqList){
				Role fromRole = roleRepo.getRole(friendReq.getFromRoleId());
				friendReq.setFriendVo(new Friend(fromRole));
			}
		}
		return friendReqList;
	}
	
	/***
	 * 请求加对方为好友
	 * @param targetRoleId
	 * @param gamePlayer
	 * @return index: 1好友满  2对方好友满  3已是好友  0success
	 */
	public Object addFriendReq(long targetRoleId, GamePlayer gamePlayer){
		/*先判断对方以及自己的好友是否已经满额*/
		ReusedProtoBuilder reusedProtoBuilder = ProtoBuilderPoolManager.getBuilder(IntReqProto.class);
		IntReqProto.Builder builder = (IntReqProto.Builder) reusedProtoBuilder.getBuilder();
		RoleFriendInfo roleFriendInfo = friendRepo.getRoleFriendInfo(gamePlayer.getRoleId());
		if(roleFriendInfo.checkIsFull()){
			builder.setIndex(1);
			return reusedProtoBuilder;
		}
		RoleFriendInfo targetFriendInfo = friendRepo.getRoleFriendInfo(targetRoleId);
		if(targetFriendInfo.checkIsFull()){
			builder.setIndex(2);
			return reusedProtoBuilder;
		}
		
		/*要将加好友申请加到对方用户的名下*/
		RoleFriendReqInfo targetRoleFriendReqInfo = friendRepo.getRoleFriendReqInfo(targetRoleId);
		if(roleFriendInfo.checkIsExist(targetRoleId) || targetFriendInfo.checkIsExist(gamePlayer.getRoleId())) {
			builder.setIndex(3);
			return reusedProtoBuilder;
		}
		
		if(!targetRoleFriendReqInfo.checkIsExist(gamePlayer.getRoleId())){
			FriendReq friendReq = new FriendReq(gamePlayer.getRoleId(), FriendReqState.PENDING);
			targetRoleFriendReqInfo.add(friendReq);
			friendRepo.saveRoleFriendReqInfo(targetRoleFriendReqInfo);
			
			GamePlayer targetGamePlayer = serverContext.findHallLogonPlayer(targetRoleId);
			if(targetGamePlayer != null) {
				Role friendRole = roleRepo.getRole(gamePlayer.getRoleId());
				friendReq.setFriendVo(new Friend(friendRole));
				targetGamePlayer.respond("notifyFriendReqReceived", CmdStatus.notDecrypt, friendReq.build4Front().toBuilder());
			} else {
				RoleNetInfo roleNetInfo = roleRepo.getRoleNetInfo(targetRoleId);
				String sendUrlSuffix = ServerConfig.http_domain_name + "/service/reqFriend.jsp";			
				int hallServerId = roleNetInfo.getHallServerId();
				if(hallServerId > 0){
					ServerInfo hallServerInfo = serverInfoData.getServerInfo(hallServerId);
					String hallUrl = ("http://" + hallServerInfo.getIp() + ":" + hallServerInfo.getHttpPort() + sendUrlSuffix);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("toRoleId", targetRoleId);
					params.put("fromRoleId", gamePlayer.getRoleId());
					HttpUtils.doGet(hallUrl, params, "utf-8");
				}
			}
		}
		
		builder.setIndex(0);
		return reusedProtoBuilder;
	}
	
	/**
	 * 同意加好友请求
	 * @param targetRoleId
	 * @param gamePlayer
	 * @return result: 1 success 0 fail -1 对方好友数满  -2 好友数满
	 */
	public Builder acceptFriendReq(long targetRoleId, GamePlayer gamePlayer){
		AcceptFriendReqResult.Builder builder = AcceptFriendReqResult.newBuilder();
		
		/*先判断对方以及自己的好友是否已经满额*/
		RoleFriendInfo roleFriendInfo = friendRepo.getRoleFriendInfo(gamePlayer.getRoleId());
		if(roleFriendInfo.checkIsFull()){
			builder.setResult(-2);
			return builder;
//			throw new BusinessException(ErrorCode.ROLE_FRIENDS_COUNT_FULL);
		}
		RoleFriendInfo targetFriendInfo = friendRepo.getRoleFriendInfo(targetRoleId);
		if(targetFriendInfo.checkIsFull()){
			builder.setResult(-1);
			return builder;
//			throw new BusinessException(ErrorCode.ROLE_TARGET_FRIENDS_COUNT_FULL);
		}
		
		RoleFriendReqInfo roleFriendReqInfo = friendRepo.getRoleFriendReqInfo(gamePlayer.getRoleId());
		if(roleFriendReqInfo != null){
			boolean isUpdated = roleFriendReqInfo.acceptFriendReq(targetRoleId);
			if(isUpdated){
				friendRepo.saveRoleFriendReqInfo(roleFriendReqInfo);
				
				boolean addSuccess =false;	//
				boolean targetAddSuccess = false;
				
				/*同意后，要将好友加入列表*/
				if(roleFriendInfo != null){
					addSuccess = roleFriendInfo.addFriend(new Friend(targetRoleId));
				}
				/*添加成功*/
				if(addSuccess){
					builder.setResult(1);
					friendRepo.saveRoleFriendInfo(gamePlayer.getRoleId(), roleFriendInfo);
					/*也要在对方的好友列表加入数据*/
					if(targetFriendInfo != null){
						targetAddSuccess = targetFriendInfo.addFriend(new Friend(gamePlayer.getRoleId()));
					}
					if(targetAddSuccess){
						friendRepo.saveRoleFriendInfo(targetRoleId, targetFriendInfo);
						GamePlayer targetGamePlayer = serverContext.findHallLogonPlayer(targetRoleId);
						if(targetGamePlayer != null) {
							Role friendRole = roleRepo.getRole(gamePlayer.getRoleId());
							RoleNetInfo roleNetInfo = roleRepo.getRoleNetInfo(gamePlayer.getRoleId());
							RoleStateInfo roleStateInfo = roleRepo.getRoleStateInfo(gamePlayer.getRoleId());
							Friend friend = new Friend(friendRole, roleStateInfo, roleNetInfo);
							targetGamePlayer.respond("notifyAcceptReq", CmdStatus.notDecrypt, friend.buildProto4Front().toBuilder());
						} else {
							RoleNetInfo roleNetInfo = roleRepo.getRoleNetInfo(targetRoleId);
							String sendUrlSuffix = ServerConfig.http_domain_name + "/service/notifyAcceptFriend.jsp";			
							int hallServerId = roleNetInfo.getHallServerId();
							if(hallServerId > 0){
								ServerInfo hallServerInfo = serverInfoData.getServerInfo(hallServerId);
								String hallUrl = ("http://" + hallServerInfo.getIp() + ":" + hallServerInfo.getHttpPort() + sendUrlSuffix);
								Map<String, Object> params = new HashMap<String, Object>();
								params.put("reqRoleId", targetRoleId);
								params.put("acceptRoleId", gamePlayer.getRoleId());
								HttpUtils.doGet(hallUrl, params, "utf-8");
							}
						}
						/*如果对方玩家完成了好友成就检测，拥有100名好友*/
//						achivementDomainService.checkIsFinishFriendAchivement(targetRoleId);
					}
					/*如果玩家完成了好友成就检测，拥有100名好友*/
//					achivementDomainService.checkIsFinishFriendAchivement(gamePlayer.getRoleId());
				}else{
					builder.setResult(0);
				}
			}
		}
		return builder;
	}
	
	/*拒绝加好友请求*/
	public void rejectFriendReq(long targetRoleId, GamePlayer gamePlayer){
		RoleFriendReqInfo roleFriendReqInfo = friendRepo.getRoleFriendReqInfo(gamePlayer.getRoleId());
		if(roleFriendReqInfo != null){
			if(roleFriendReqInfo.rejectFriendReq(targetRoleId)) {
				friendRepo.saveRoleFriendReqInfo(roleFriendReqInfo);
			}
		}
	}
	
	/**
	 * 获取推荐好友
	 * 	1、优先获取最近一起游戏过的玩家
	 * 	2、1未满，继续获取附近玩家（同省或同市）
	 * 	3、2未满，获取异性随机玩家
	 * 	4、3未满，获取同性随机玩家
	 * @param gamePlayer
	 * @return
	 */
	public Builder getRecommendFriends(GamePlayer gamePlayer){
		GetRecommendFriendsRespProto.Builder resp = GetRecommendFriendsRespProto.newBuilder();
		
		RoleRecentPartnerInfo recentPartnerInfo = friendRepo.getRoleRecentPartnerInfo(gamePlayer.getRoleId());
		for(Partner partner : recentPartnerInfo.getPartners()){
			Role role = roleRepo.getRole(partner.getRoleId());
			RecommendFriend recommendFriend = new RecommendFriend(role, RecommendFriendtype.recentPartner.getCode());
			resp.addRecommendFriend((RecommendFriendVoProto)recommendFriend.build());
			if(resp.getRecommendFriendCount() == RoleRecentPartnerInfo.limitCount){
				break;
			}
		}
		
		if(resp.getRecommendFriendCount() < RoleRecentPartnerInfo.limitCount){
			Role selfRole = roleRepo.getRole(gamePlayer.getRoleId());
			long maxId = globalRepo.getRoleIdIndex();
			Random random = new Random();
			int incNum = random.nextInt(10)+1;	//增量
			int baseNum = (int) (maxId/incNum);	//基础偏移量
			int selectNum = 50; //随机次数最多50次
			RoleFriendInfo roleFriendInfo = friendRepo.getRoleFriendInfo(gamePlayer.getRoleId());
			
			if(!"尚未定位".equals(selfRole.getCity()) && !"".equals(selfRole.getCity())){  //获取附近玩家
				for(int id=baseNum; id<=maxId; id+=incNum,selectNum--){
					if(id==gamePlayer.getRoleId()){
						continue;
					}
					if(selectNum < 1){
						selectNum = 50;
						break;
					}
					Role role = roleRepo.getRole(id);
					if(role==null){
						continue;
					}
					if (role.getCity().startsWith(selfRole.getCity().substring(0, 3))) { // 同省或同市
						if (!isFriend(roleFriendInfo,id)) {
							RecommendFriend recommendFriend = new RecommendFriend(role,RecommendFriendtype.nearby.getCode());
							resp.addRecommendFriend((RecommendFriendVoProto) recommendFriend.build());
							if (resp.getRecommendFriendCount() == RoleRecentPartnerInfo.limitCount) {
								break;
							}
						}
					}
				}
			}
			
			if(resp.getRecommendFriendCount() < RoleRecentPartnerInfo.limitCount){	//获取异性玩家
				for(long id = baseNum; id<=maxId; id+=incNum,selectNum--){
					if(id==gamePlayer.getRoleId()){
						continue;
					}
					if(selectNum < 1){
						selectNum = 50;
						break;
					}
					Role role = roleRepo.getRole(id);
					if(role==null){
						continue;
					}
					if(role.getSex() != selfRole.getSex()){
						if (!isFriend(roleFriendInfo, id)) {
							RecommendFriend recommendFriend = new RecommendFriend(role,RecommendFriendtype.other.getCode());
							resp.addRecommendFriend((RecommendFriendVoProto) recommendFriend.build());
							if (resp.getRecommendFriendCount() == RoleRecentPartnerInfo.limitCount) {
								break;
							}
						}
					}
				}
			}
			
			if(resp.getRecommendFriendCount() < RoleRecentPartnerInfo.limitCount){ //获取同性玩家
				baseNum =1;
				incNum =1;
				for(long id = baseNum; id<=maxId; id+=incNum){
					if(id==gamePlayer.getRoleId()){
						continue;
					}
					Role role = roleRepo.getRole(id);
					if(role==null){
						continue;
					}
					if (!isFriend(roleFriendInfo, id)) {
						RecommendFriend recommendFriend = new RecommendFriend(role,RecommendFriendtype.other.getCode());
						resp.addRecommendFriend((RecommendFriendVoProto) recommendFriend.build());
						if (resp.getRecommendFriendCount() == RoleRecentPartnerInfo.limitCount) {
							break;
						}
					}
				}
			}
		}
		return resp;
	}
	
	private boolean isFriend(RoleFriendInfo roleFriendInfo , long id){
		boolean isFriend = false;
		for (Friend friend : roleFriendInfo.getFriends()) {
			if (friend.getRoleId() == id) {
				isFriend = true;
			}
		}
		return isFriend;
	}
	
	/**
	 * 换一换推荐好友
	 * @param gamePlayer
	 * @return
	 */
	public Builder changeRecommendFriends(GamePlayer gamePlayer){
		GetRecommendFriendsRespProto.Builder resp = GetRecommendFriendsRespProto.newBuilder();
		long maxId = globalRepo.getRoleIdIndex();
		Random random = new Random();
		int incNum = random.nextInt(10)+1;	//增量
		int baseNum = (int) (maxId/incNum);	//基础偏移量
		RoleFriendInfo roleFriendInfo = friendRepo.getRoleFriendInfo(gamePlayer.getRoleId());
		for(long id=baseNum; id<=maxId; id+=incNum){
			if(id==gamePlayer.getRoleId()){
				continue;
			}
			Role role = roleRepo.getRole(id);
			if(role==null){
				continue;
			}
			if(!isFriend(roleFriendInfo, id)){
				RecommendFriend recommendFriend = new RecommendFriend(role,RecommendFriendtype.none.getCode());
				resp.addRecommendFriend((RecommendFriendVoProto) recommendFriend.build());
				if (resp.getRecommendFriendCount() == RoleRecentPartnerInfo.limitCount) {
					break;
				}
			}
		}
		if(resp.getRecommendFriendCount() < RoleRecentPartnerInfo.limitCount){
			incNum = 1;
			baseNum = 1;
			for(long id=baseNum; id<=maxId; id+=incNum){
				if(id==gamePlayer.getRoleId()){
					continue;
				}
				Role role = roleRepo.getRole(id);
				if(role==null){
					continue;
				}
				if(!isFriend(roleFriendInfo, id)){
					RecommendFriend recommendFriend = new RecommendFriend(role,RecommendFriendtype.none.getCode());
					resp.addRecommendFriend((RecommendFriendVoProto) recommendFriend.build());
					if (resp.getRecommendFriendCount() == RoleRecentPartnerInfo.limitCount) {
						break;
					}
				}
			}
		}
		return resp;
	}
	
	/**
	 * 一键申请添加多个好友
	 * @param targetRoleIds
	 * @param gamePlayer
	 * @return -1自己好友已满  0正常
	 */
	public Object addSomeFriendsReq(List<Long> targetRoleIds, GamePlayer gamePlayer){
		//先判断自己的好友是否已经满额
		ReusedProtoBuilder reusedProtoBuilder = ProtoBuilderPoolManager.getBuilder(IntReqProto.class);
		IntReqProto.Builder builder = (IntReqProto.Builder) reusedProtoBuilder.getBuilder();
		RoleFriendInfo roleFriendInfo = friendRepo.getRoleFriendInfo(gamePlayer.getRoleId());
		if(roleFriendInfo.checkIsFull()){
			builder.setIndex(-1);
			return reusedProtoBuilder;
		}
		for(long targetRoleId : targetRoleIds){
			//判断对方是否已经满了好友,没满的发送请求
			RoleFriendInfo targetRoleFriendInfo = friendRepo.getRoleFriendInfo(targetRoleId);
			if(!targetRoleFriendInfo.checkIsFull()){
				RoleFriendReqInfo targetRoleFriendReqInfo = friendRepo.getRoleFriendReqInfo(targetRoleId);
				FriendReq friendReq = new FriendReq(gamePlayer.getRoleId(), FriendReqState.PENDING);
				targetRoleFriendReqInfo.add(friendReq);
				friendRepo.saveRoleFriendReqInfo(targetRoleFriendReqInfo);
				
				GamePlayer targetGamePlayer = serverContext.findHallLogonPlayer(targetRoleId);
				if(targetGamePlayer != null) {
					Role friendRole = roleRepo.getRole(gamePlayer.getRoleId());
					friendReq.setFriendVo(new Friend(friendRole));
					targetGamePlayer.respond("notifyFriendReqReceived", CmdStatus.notDecrypt, friendReq.build4Front().toBuilder());
				} else {
					RoleNetInfo roleNetInfo = roleRepo.getRoleNetInfo(targetRoleId);
					String sendUrlSuffix = ServerConfig.http_domain_name + "/service/reqFriend.jsp";			
					int hallServerId = roleNetInfo.getHallServerId();
					if(hallServerId > 0){
						ServerInfo hallServerInfo = serverInfoData.getServerInfo(hallServerId);
						String hallUrl = ("http://" + hallServerInfo.getIp() + ":" + hallServerInfo.getHttpPort() + sendUrlSuffix);
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("toRoleId", targetRoleId);
						params.put("fromRoleId", gamePlayer.getRoleId());
						HttpUtils.doGet(hallUrl, params, "utf-8");
					}
				}
			}
		}
		builder.setIndex(0);
		return reusedProtoBuilder;
	}
}
