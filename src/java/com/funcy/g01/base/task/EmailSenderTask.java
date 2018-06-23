package com.funcy.g01.base.task;

import com.funcy.g01.base.bo.email.Email;
import com.funcy.g01.base.bo.email.TimerEmailInfo;
import com.funcy.g01.hall.service.EmailService;


public class EmailSenderTask implements Runnable {

	private TimerEmailInfo timerEmailInfo; 
	
	
	private static EmailService emailService;
	
//	@Override
//	public void run() {		
//		List<TimerEmailInfo> tmpList = EmailSenderConstant.timerEmailInfos;
//		Iterator<TimerEmailInfo> it = tmpList.iterator();
//		while(it.hasNext()){
//			final TimerEmailInfo timerEmailInfo = it.next();
//			if(timerEmailInfo.getSendTime() <= System.currentTimeMillis()){
//				Email email = timerEmailInfo.getEmail();
//				RoleEmailInfo roleEmailInfo  = null;
//				if (email.getType() == EmailType.SINGLE) {
//					roleEmailInfo = emailRepo.getRoleEmailInfo(email.getAccepterId());
//				}else if(email.getType() == EmailType.ALL){
//					roleEmailInfo = emailRepo.getRoleEmailInfo(ServerConfig.system_role_id);
//				}
//				roleEmailInfo.addEmail(email);
//				emailRepo.saveRoleEmailInfo(roleEmailInfo);
//				emailService.sendEmailByHttp(email.getMailId(), email.getAccepterId(), (email.getType()));
//				it.remove();
//			}
//		}
//	}
	
	
	@Override
	public void run() {
		try {
			Email email = this.timerEmailInfo.getEmail();
			emailService.sendRoleEmail(email);
		} catch (Exception e) {
			throw new RuntimeException("发送邮件异常");
		}
	}

	public TimerEmailInfo getTimerEmailInfo() {
		return timerEmailInfo;
	}

	public void setTimerEmailInfo(TimerEmailInfo timerEmailInfo) {
		this.timerEmailInfo = timerEmailInfo;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public static void setEmailService(EmailService emailService) {
		EmailSenderTask.emailService = emailService;
	}
	
}
