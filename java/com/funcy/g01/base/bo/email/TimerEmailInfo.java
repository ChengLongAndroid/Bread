package com.funcy.g01.base.bo.email;

import java.util.concurrent.atomic.AtomicInteger;

public class TimerEmailInfo {
	
	private static AtomicInteger id_creator = new AtomicInteger();
	
	private int id;

	private String sendIp;
	
	private Email email;
	
	private int areaId;
	
	private String roleName;

	private Long sendTime;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSendIp() {
		return sendIp;
	}

	public void setSendIp(String sendIp) {
		this.sendIp = sendIp;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getSendTime() {
		return sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}

	public TimerEmailInfo(String sendIp, Email email, int areaId, String roleName) {
		super();
		this.id = id_creator.incrementAndGet();
		this.sendIp = sendIp;
		this.email = email;
		this.areaId = areaId;
		this.roleName = roleName;
		this.sendTime = email.getCreateTime();
	}

}
