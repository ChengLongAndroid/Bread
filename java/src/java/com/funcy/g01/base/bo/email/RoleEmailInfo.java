package com.funcy.g01.base.bo.email;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.EmailProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleEmailInfoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.SysEmailStatusProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class RoleEmailInfo implements ProtobufSerializable {
	
	private transient long roleId;
	
	private List<Email> emails = null;
	
	/*维护系统公告邮件的状态*/
	private Map<Integer, SysEmailStatus> sysEmailStatusMap = new HashMap<Integer, SysEmailStatus>();
	
	private static final int maxEmails = 100;
	
	@SuppressWarnings("unused")
	private RoleEmailInfo() {
	}
	
	public RoleEmailInfo(long roleId){
		this.emails = new ArrayList<Email>();
	}
	
	public RoleEmailInfo(byte[] bytes){
		parseFrom(bytes);
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleEmailInfoProto proto = (RoleEmailInfoProto) message;
		this.emails = new ArrayList<Email>();
		for (EmailProto emailProto : proto.getEmailsList()) {
			Email email = new Email(emailProto);
			this.emails.add(email);
		}
		for (SysEmailStatusProto sysEmailStatusProto : proto.getSysEmailsList()) {
			SysEmailStatus sysEmailStatus = new SysEmailStatus(sysEmailStatusProto);
			this.sysEmailStatusMap.put(sysEmailStatusProto.getMailId(), sysEmailStatus);
		}
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleEmailInfoProto.Builder buidler = RoleEmailInfoProto.newBuilder();
		for (Email email : emails) {
			buidler.addEmails(email.copyTo());
		}
		for (SysEmailStatus sysEmailStatus : sysEmailStatusMap.values()) {
			buidler.addSysEmails(sysEmailStatus.copyTo());
		}
		return buidler.build();
	}
	
	public void addEmail(Email email){
		this.emails.add(email);
	}
		
	public List<Email> findSystemEmails(long roleRegTimeTime){
		DateTime thirtyDaysBefore = DateTime.now().minusDays(Email.SYSTEM_EMAIL_CONTINUE_DAYS);
		DateTime regDateTime = new DateTime(roleRegTimeTime);
		DateTime minCreateTime = null;
		if(thirtyDaysBefore.isAfter(regDateTime)) {
			minCreateTime = thirtyDaysBefore;
		} else {
			minCreateTime = regDateTime;
		} 
		List<Email> tempEmails = new ArrayList<Email>();
		for (Email email : emails) {
			if (new DateTime(email.getCreateTime()).isAfter(minCreateTime)) {
				tempEmails.add(email);
			}
		}
		return tempEmails;
	}
	
	public boolean deleteTimeoutEmail(){
		DateTime fifteenDaysBefore = DateTime.now().minusDays(Email.SYSTEM_EMAIL_CONTINUE_DAYS);
		boolean isDelete = false;
		Set<Integer> includedEmailIds = new HashSet<Integer>();
		Iterator<Email> iterator = emails.iterator();
		while(iterator.hasNext()){
			Email email = iterator.next();
			if (new DateTime(email.getCreateTime()).isBefore(fifteenDaysBefore)) {
				includedEmailIds.add(email.getMailId());
				iterator.remove();
				isDelete = true;
			}
		}
		return isDelete;
	}
	
	public boolean deleteMoreHundredsEmail(){
		boolean isDelete = false;
		if(this.emails.size() > maxEmails){
			Iterator<Email> iterator = emails.iterator();
			int index = 0;
			while(iterator.hasNext()){
				index ++;
				iterator.next();
				if(index > maxEmails){
					iterator.remove();
				}
			}
		}
		return isDelete;
	}
	
	public void updateEmailState(int emailId, EmailState state){
		/*先遍历个人邮件列表，如果存在，则更新*/
		for (Email email : emails) {
			if (email.getMailId() == emailId) {
				email.setState(state);
			}
		}
		/*然后遍历公告邮件状态，如果存在，也更新*/
		if(this.sysEmailStatusMap.containsKey(emailId) && this.sysEmailStatusMap.get(emailId).getState() != EmailState.DELETED){
			this.sysEmailStatusMap.get(emailId).setState(state);
		}
	}
	
	public Email findEmail(int emailId, List<Email> sysEmailList){
		/*先遍历个人邮件列表，如果存在，则返回*/
		for (Email email : emails) {
			if (email.getMailId() == emailId) {
				return email;
			}
		}
		/*然后遍历公告邮件状态，如果存在，返回*/
		if(this.sysEmailStatusMap.containsKey(emailId)){
			for(Email email : sysEmailList){
				if (email.getMailId() == emailId && this.sysEmailStatusMap.get(emailId).getState() != EmailState.DELETED) {
					email.setState(this.sysEmailStatusMap.get(emailId).getState());
					return email;
				}
			}
		}
		return null;
	}
	
	public Email findEmail(int emailId){
		/*先遍历个人邮件列表，如果存在，则返回*/
		for (Email email : emails) {
			if (email.getMailId() == emailId) {
				return email;
			}
		}
		return null;
	}
	
	public void deleteEmail(int emailId, List<Email> sysEmailList){
		/*先遍历个人邮件列表，如果存在，则删除*/
		for (Email email : emails) {
			if (email.getMailId() == emailId) {
				/*如果邮件有奖励并且不是已读已领取的状态，则忽略*/
				if(email.getState() != EmailState.READED_UNRECEIVED && StringUtils.isNotBlank(email.getRewards())){
					break;
				} else {
					emails.remove(email);
					break;
				}
			}
		}
		/*然后遍历公告邮件状态，如果存在，也删除*/
		if(this.sysEmailStatusMap.containsKey(emailId)){
			for(Email email : sysEmailList){
				if (email.getMailId() == emailId) {
					SysEmailStatus sysEmailStatus = this.sysEmailStatusMap.get(email.getMailId());
					if(sysEmailStatus == null){
						break;
					}
					/*如果邮件有奖励并且不是已读已领取的状态，则忽略*/
					if((sysEmailStatus.getState() != EmailState.NEW || sysEmailStatus.getState() != EmailState.READED_UNRECEIVED) && StringUtils.isNotBlank(email.getRewards())){
						break;
					} else if(sysEmailStatus.getState() == EmailState.DELETED){
						break;
					} else {
						sysEmailStatusMap.get(emailId).setState(EmailState.DELETED);
						break;
					}
				}
			}
		}
	}
	
	public void deleteEmailList(Collection<Integer> emailIds, List<Email> sysEmailList){
		if(emailIds.size() <= 0){
			return;
		}
		/*先遍历个人邮件列表，如果存在，则删除*/
		Iterator<Email> it = emails.iterator();
		while (it.hasNext()) {
			Email email = it.next();
			if(emailIds.contains(email.getMailId())){
				/*如果邮件有奖励并且不是已读已领取的状态，则忽略*/
				if((email.getState() != EmailState.NEW || email.getState() != EmailState.READED_UNRECEIVED) && StringUtils.isNotBlank(email.getRewards())){
					continue;
				} else {
					it.remove();
				}
			}
		}
		
		/*然后遍历公告邮件状态，如果存在，也删除*/
		for(Email email : sysEmailList){
			if(this.sysEmailStatusMap.containsKey(email.getMailId()) && emailIds.contains(email.getMailId())){
				SysEmailStatus sysEmailStatus = this.sysEmailStatusMap.get(email.getMailId());
				if(sysEmailStatus == null){
					continue;
				}
				/*如果邮件有奖励并且不是已读已领取的状态，则忽略*/
				if((sysEmailStatus.getState() != EmailState.NEW || sysEmailStatus.getState() != EmailState.READED_UNRECEIVED) && StringUtils.isNotBlank(email.getRewards())){
					continue;
				} else if(sysEmailStatus.getState() == EmailState.DELETED){
					continue;
				}  else {
					sysEmailStatusMap.get(email.getMailId()).setState(EmailState.DELETED);
				}
			}
		}
	}
	/*
	public void saveEmailStatus(EmailStatus emailStatus){
		this.emailStatusList.add(emailStatus);
	}*/
	
	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleEmailInfoProto roleItemInfoProto = RoleEmailInfoProto.parseFrom(bytes);
			copyFrom(roleItemInfoProto);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}

	public long getRoleId() {
		return roleId;
	}

	public List<Email> getEmails() {
		return emails;
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public Map<Integer, SysEmailStatus> getSysEmailStatusMap() {
		return sysEmailStatusMap;
	}

	public void setSysEmailStatusMap(Map<Integer, SysEmailStatus> sysEmailStatusMap) {
		this.sysEmailStatusMap = sysEmailStatusMap;
	}
	
}
