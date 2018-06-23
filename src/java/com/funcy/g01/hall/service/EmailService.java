package com.funcy.g01.hall.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebo.synframework.synroom.tools.ConcurrentHashMap;
import com.funcy.g01.base.bo.BoFactory;
import com.funcy.g01.base.bo.email.Email;
import com.funcy.g01.base.bo.email.EmailState;
import com.funcy.g01.base.bo.email.EmailType;
import com.funcy.g01.base.bo.email.EmailUtil;
import com.funcy.g01.base.bo.email.RoleEmailInfo;
import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.bo.role.RoleNetInfo;
import com.funcy.g01.base.bo.sendemail.SimpleMailSender;
import com.funcy.g01.base.bo.serverconfig.ServerInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.base.bo.serverconfig.ServerType;
import com.funcy.g01.base.dao.redis.EmailRepo;
import com.funcy.g01.base.dao.redis.RoleRepo;
import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.net.CmdStatus;
import com.funcy.g01.base.net.GamePlayer;
import com.funcy.g01.base.util.HttpUtils;
import com.funcy.g01.base.util.JSONUtils;
import com.funcy.g01.base.util.LoggerType;

@Service
public class EmailService {
	private final Logger logger = Logger.getLogger(LoggerType.recharge.name());
	
	@Autowired
	private ItemDomainService itemDomainService;

	@Autowired
	private EmailRepo emailRepo;

	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private ServerInfoData serverInfoData;
	
	@Autowired
	private ServerContext serverContext;
	
	public void deleteEmail(int emailId, GamePlayer gamePlayer){
		RoleEmailInfo roleEmailInfo = emailRepo.getRoleEmailInfo(gamePlayer.getRoleId());
		RoleEmailInfo systemEmailInfo = emailRepo.getRoleEmailInfo(ServerConfig.system_role_id);
		roleEmailInfo.deleteEmail(emailId, systemEmailInfo.getEmails());
		emailRepo.saveRoleEmailInfo(roleEmailInfo);
	}
	
	
	public void deleteEmailQuick(GamePlayer gamePlayer){
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		RoleEmailInfo roleEmailInfo = emailRepo.getRoleEmailInfo(gamePlayer.getRoleId());
		RoleEmailInfo systemEmailInfo = emailRepo.getRoleEmailInfo(ServerConfig.system_role_id);
		List<Email> emails = EmailUtil.findRoleEmails(roleEmailInfo, systemEmailInfo, role, emailRepo);
		
		List<Integer> includedEmailIdList = new ArrayList<Integer>();		
		for(Email email : emails){
			/*如果是已经查看过+附带有奖励+奖励已经领取，则加入删除列表*/
			if(StringUtils.isNotBlank(email.getRewards()) && email.getState() == EmailState.READED_RECEIVED){
				includedEmailIdList.add(email.getMailId());
			}
			/*如果是已经查看过+无附带奖励邮件，则直接加入删除列表*/
			else if(StringUtils.isBlank(email.getRewards()) && email.getState() == EmailState.READED_UNRECEIVED){
				includedEmailIdList.add(email.getMailId());
			}
		}
		
		if(includedEmailIdList.size() > 0){
			roleEmailInfo.deleteEmailList(includedEmailIdList, systemEmailInfo.getEmails());
			emailRepo.saveRoleEmailInfo(roleEmailInfo);
		}
	}
	
	public List<Email> getEmailList(GamePlayer gamePlayer){
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		RoleEmailInfo roleEmailInfo = emailRepo.getRoleEmailInfo(gamePlayer.getRoleId());
		RoleEmailInfo systemEmailInfo = emailRepo.getRoleEmailInfo(ServerConfig.system_role_id);		
		return EmailUtil.findRoleEmails(roleEmailInfo, systemEmailInfo, role, emailRepo);
	}
	
	public void sawEmail(int emailId, GamePlayer gamePlayer){
		RoleEmailInfo roleEmailInfo = emailRepo.getRoleEmailInfo(gamePlayer.getRoleId());
		RoleEmailInfo systemEmailInfo = emailRepo.getRoleEmailInfo(ServerConfig.system_role_id);
		/*找出指定邮件记录*/
		Email email = roleEmailInfo.findEmail(emailId, systemEmailInfo.getEmails());
		if(email != null){
			if(email.getState() == EmailState.NEW){
				roleEmailInfo.updateEmailState(emailId, EmailState.READED_UNRECEIVED);
				emailRepo.saveRoleEmailInfo(roleEmailInfo);
			}
		}
	}
		
	public void getEmailRewards(int emailId, GamePlayer gamePlayer) {		
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		RoleEmailInfo roleEmailInfo = emailRepo.getRoleEmailInfo(gamePlayer.getRoleId());
		RoleEmailInfo systemEmailInfo = emailRepo.getRoleEmailInfo(ServerConfig.system_role_id);
		List<Email> emails = EmailUtil.findRoleEmails(roleEmailInfo, systemEmailInfo, role, emailRepo);
		
		roleEmailInfo.deleteTimeoutEmail();
		
		boolean needUpdate = false;
		
		for(Email email : emails){
			if(email.getMailId() == emailId){
				if (email.getType() == EmailType.SINGLE && !email.isSelfEmail(gamePlayer.getRoleId())) {
					throw new BusinessException(ErrorCode.EMAIL_IS_NOT_YOUR, "It's not your email");
				}
				if(email.getRewards() == null) {
					throw new BusinessException(ErrorCode.EMAIL_NO_REWARDS, "no rewards");
				}
				if(email.getState() == EmailState.READED_RECEIVED) {
					throw new BusinessException(ErrorCode.EMAIL_NO_REWARDS, "already rewarded");
				}
				String rewardStr = email.getRewards();
				itemDomainService.addItems(BoFactory.createMultiItems(rewardStr), gamePlayer.getRoleId());
				roleEmailInfo.updateEmailState(emailId, EmailState.READED_RECEIVED);
				roleEmailInfo.deleteEmail(emailId, systemEmailInfo.getEmails());
				needUpdate = true;
				break;
			}
		}
		
		if(needUpdate){
			emailRepo.saveRoleEmailInfo(roleEmailInfo);
		}
	}
	
	public void getEmailRewardsQuick(GamePlayer gamePlayer) {		
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		RoleEmailInfo roleEmailInfo = emailRepo.getRoleEmailInfo(gamePlayer.getRoleId());
		RoleEmailInfo systemEmailInfo = emailRepo.getRoleEmailInfo(ServerConfig.system_role_id);
		List<Email> emails = EmailUtil.findRoleEmails(roleEmailInfo, systemEmailInfo, role, emailRepo);
		
		roleEmailInfo.deleteTimeoutEmail();
		roleEmailInfo.deleteMoreHundredsEmail();
		
		List<Integer> includedEmailIds = new ArrayList<Integer>();
		for(Email email : emails){
			if (email.getType() == EmailType.SINGLE && !email.isSelfEmail(gamePlayer.getRoleId())) {
				continue;
			}
			if(StringUtils.isNotBlank(email.getRewards()) && email.getState() != EmailState.READED_RECEIVED){
				String rewardStr = email.getRewards();
				itemDomainService.addItems(BoFactory.createMultiItems(rewardStr), gamePlayer.getRoleId());
				roleEmailInfo.updateEmailState(email.getMailId(), EmailState.READED_RECEIVED);
				includedEmailIds.add(email.getMailId());
			}			
		}
		if(includedEmailIds.size() > 0){
			roleEmailInfo.deleteEmailList(includedEmailIds, systemEmailInfo.getEmails());
			emailRepo.saveRoleEmailInfo(roleEmailInfo);
		}
	}
	
	public void sendLogonPlayerEmail(int emailId, int roleId, int emailTypeCode) {
		if(emailId <= 0){
			return;
		}
		
		ConcurrentHashMap<Long, GamePlayer> logonPlayers = serverContext.getHallLogonPlayers();
		
		EmailType emailType = EmailType.valueOf(emailTypeCode);
		Email email = null;
		if(emailType == EmailType.ALL){
			RoleEmailInfo systemEmailInfo = emailRepo.getRoleEmailInfo(ServerConfig.system_role_id);
			email = systemEmailInfo.findEmail(emailId);
		} else if(emailType == EmailType.SINGLE){
			if(roleId > 0){
				RoleEmailInfo roleEmailInfo = emailRepo.getRoleEmailInfo(roleId);
				email = roleEmailInfo.findEmail(emailId);
			}
		}
		if(email != null){
			if (email.getType() == EmailType.SINGLE) {
				GamePlayer gamePlayer = logonPlayers.get(email.getAccepterId());
				if (gamePlayer != null) {
					gamePlayer.respond("emailService.recieveEmail", CmdStatus.notDecrypt, email.copyTo().toBuilder());
				}
			} else {
				for (GamePlayer logonPlayer : logonPlayers.values()) {
					if(logonPlayer == null) {
						continue;
					}
					logonPlayer.respond("emailService.recieveEmail", CmdStatus.notDecrypt, email.copyTo().toBuilder());
				}
			}
		}
	} 
	
	public void sendEmailByHttp(int emailId, long roleId, EmailType type) {
		String sendGmMailUrlSuffix = ServerConfig.http_domain_name + "/service/sendSysEmail.jsp";
		/*如果是公告邮件，则直接请求全服提醒*/
		if(type == EmailType.ALL){
			/*查出所有的大厅服务器*/
			for(ServerInfo serverInfo :serverInfoData.findAllServerInfo()){
				System.out.println(!serverInfo.isClose());
				System.out.println(serverInfo.getServerType().equals(ServerType.roomAndHallServer));
				System.out.println(serverInfo.getServerType().equals(ServerType.all));
				/*如果服务器是可用状态，并且提供大厅登陆服务 或 全部 */
				if(!serverInfo.isClose() && (serverInfo.getServerType().equals(ServerType.roomAndHallServer.name()) || serverInfo.getServerType().equals(ServerType.all.name()))){
					String hallUrl = ("http://" + serverInfo.getIp() + ":" + serverInfo.getHttpPort() + sendGmMailUrlSuffix);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("emailId", emailId);
					params.put("roleId", roleId);
					params.put("emailTypeCode", type.getCode());

					HttpUtils.doGet(hallUrl, params, "utf-8");
				}
			}
			
		} else if(type == EmailType.SINGLE){
			if(roleId > 0){
				/*查出该用户在哪台大厅服务器上登陆的*/
				RoleNetInfo roleNetInfo = roleRepo.getRoleNetInfo(roleId);
				ServerInfo hallServerInfo =serverInfoData.getServerInfo(roleNetInfo.getHallServerId());
				if(hallServerInfo != null){
					String hallUrl = ("http://" + hallServerInfo.getIp() + ":" + hallServerInfo.getHttpPort() + sendGmMailUrlSuffix);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("emailId", emailId);
					params.put("roleId", roleId);
					params.put("emailTypeCode", type.getCode());

					HttpUtils.doGet(hallUrl, params, "utf-8");
				}
				
			}
		}
		
	} 
	
	public void sendRoleEmail(Email email) {
		RoleEmailInfo roleEmailInfo  = null;
		if (email.getType() == EmailType.SINGLE) {
			roleEmailInfo = emailRepo.getRoleEmailInfo(email.getAccepterId());
		}else if(email.getType() == EmailType.ALL){
			roleEmailInfo = emailRepo.getRoleEmailInfo(ServerConfig.system_role_id);
		}
		
		roleEmailInfo.addEmail(email);
		emailRepo.saveRoleEmailInfo(roleEmailInfo);
		this.sendEmailByHttp(email.getMailId(), email.getAccepterId(), (email.getType()));
	}
	
	public void sendGMEmail(Map<Object,Object> map){
		Email email = JSONUtils.fromJSON(String.valueOf(map.get("email")), Email.class);
	}
	
	public void singleMailSend(String title,String content) {
		SimpleMailSender mailSender = new SimpleMailSender("userName", "password");
		try {
			mailSender.send("receive", title, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
}
