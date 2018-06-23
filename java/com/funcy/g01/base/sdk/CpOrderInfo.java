package com.funcy.g01.base.sdk;

public class CpOrderInfo {

	private String accountId;
	
	private String cpOrderId;
	
	private String orderId;
	
	private int channelId;
	
	private int money;
	
	private String resp;
	
	private boolean orderState;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getCpOrderId() {
		return cpOrderId;
	}

	public void setCpOrderId(String cpOrderId) {
		this.cpOrderId = cpOrderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getResp() {
		return resp;
	}

	public void setResp(String resp) {
		this.resp = resp;
	}

	public boolean isOrderState() {
		return orderState;
	}

	public void setOrderState(boolean orderState) {
		this.orderState = orderState;
	}

	public CpOrderInfo() {
		
	}
	
	public CpOrderInfo(String accountId, String cpOrderId, String orderId, int channelId, int money, String resp,
			boolean orderState) {
		this.accountId = accountId;
		this.cpOrderId = cpOrderId;
		this.orderId = orderId;
		this.channelId = channelId;
		this.money = money;
		this.resp = resp;
		this.orderState = orderState;
	}	
}
