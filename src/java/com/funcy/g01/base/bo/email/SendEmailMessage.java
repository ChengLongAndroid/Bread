package com.funcy.g01.base.bo.email;

import java.util.Date;

import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.SendEmailMessageProto;
import com.funcy.g01.base.util.JSONUtils;

public class SendEmailMessage {
	
	private Date sendTime;

	private String ip;
	
	private Email email;

	private String roleName;
	
	private String emailMsg;
	

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getEmailMsg() {
		return emailMsg;
	}

	public void setEmailMsg(String emailMsg) {
		this.emailMsg = emailMsg;
	}

	public SendEmailMessage(SendEmailMessageProto proto){
		this.sendTime = new Date(proto.getSendTime());
		this.ip = proto.getIp();
		this.roleName = proto.getRoleName();
		this.emailMsg = proto.getEmailMsg();
	}
	
	public SendEmailMessageProto copyTo(){
		SendEmailMessageProto.Builder builder = SendEmailMessageProto.newBuilder();
		builder.setEmailMsg(emailMsg);
		builder.setSendTime(sendTime.getTime());
		builder.setIp(ip);
		builder.setRoleName(roleName);
		return builder.build();
	}
	
	public SendEmailMessage(long sendTime,String ip,Email email,String roleName){
		this.sendTime = new Date(sendTime);
		this.ip = ip;
		this.email = email;
		this.roleName = roleName;
		this.emailMsg = JSONUtils.toJSON(email);
	}
}
