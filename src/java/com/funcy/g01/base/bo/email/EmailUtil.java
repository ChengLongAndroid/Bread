package com.funcy.g01.base.bo.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.dao.redis.EmailRepo;
import com.funcy.g01.base.net.CmdStatus;
import com.funcy.g01.base.net.GamePlayer;

public class EmailUtil {

	public static List<Email> findRoleEmails(RoleEmailInfo roleEmailInfo, RoleEmailInfo systemEmailInfo, Role role, EmailRepo emailRepo) {
		List<Email> roleEmails = roleEmailInfo.getEmails();
		List<Email> systemEmails = systemEmailInfo.findSystemEmails(role.getCreateTime());
		List<Email> emails = new ArrayList<Email>();
		emails.addAll(roleEmails);
		emails.addAll(systemEmails);
		Map<Integer, SysEmailStatus> sysEmailStatusMap = roleEmailInfo.getSysEmailStatusMap();
		
		boolean isUpdated = false;
		
		List<Email> emailResult = new ArrayList<Email>();
		for(Email email : emails){
			EmailState emailState = null;
			/*当私人邮件情况下*/
			if(email.getType() == EmailType.SINGLE){
				emailState = email.getState();
			}
			/*当公告邮件情况下*/
			else if(email.getType() == EmailType.ALL){
				/*先查询该用户下，如果存在该公告邮件的状态记录，且非删除状态，则有效*/
				if(sysEmailStatusMap.containsKey(email.getMailId()) && sysEmailStatusMap.get(email.getMailId()).getState() != EmailState.DELETED){
					SysEmailStatus sysEmailStatus = sysEmailStatusMap.get(email.getMailId());
					emailState = sysEmailStatus.getState();
					email.setState(emailState);
				}
				/*如果存在该公告邮件的状态记录，但为删除状态，则忽略*/
				else if(sysEmailStatusMap.containsKey(email.getMailId()) && sysEmailStatusMap.get(email.getMailId()).getState() == EmailState.DELETED){
					continue;
				}
				/*否则新建一个公告邮件的状态记录*/
				else {
					SysEmailStatus sysEmailStatus = new SysEmailStatus(email.getMailId(), EmailState.NEW);
					sysEmailStatusMap.put(email.getMailId(), sysEmailStatus);
					emailState = sysEmailStatus.getState();
					email.setState(emailState);
					isUpdated = true;
				}
			}
			
			if(emailState == null){
				continue;
			}
			
			/*如果邮件附带附件， 并且已经查看 并且 已经领取，则删除本邮件*/
			if(emailState == EmailState.READED_RECEIVED){
				roleEmailInfo.deleteEmail(email.getMailId(), systemEmails);
				isUpdated = true;
			}
			/*其余情况都需要给用户显示*/
			else {
				emailResult.add(email);
			}
		}
		if(isUpdated){
			emailRepo.saveRoleEmailInfo(roleEmailInfo);
		}
		return emailResult;
	}
		
	public static void sendEmail(Map<Long, GamePlayer> logonPlayers, Email email) {
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
