package com.funcy.g01.base.sdk.talkingdata;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class TalkingDataInfo {
	
	private String msgID;
	
	private String status;
	
	@JsonProperty("OS") 
	private String OS;
	
	private String accountID;
	
	private String orderID;
	
	private double currencyAmount;
	
	private String currencyType;
	
	private double virtualCurrencyAmount;
	
	private String iapID;
	
	private String gameServer;
	
	private int level;

	public String getMsgID() {
		return msgID;
	}

	public void setMsgID(String msgID) {
		this.msgID = msgID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonIgnore
	public String getOS() {
		return OS;
	}
	
	@JsonIgnore
	public void setOS(String OS) {
		this.OS = OS;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public double getCurrencyAmount() {
		return currencyAmount;
	}

	public void setCurrencyAmount(double currencyAmount) {
		this.currencyAmount = currencyAmount;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public double getVirtualCurrencyAmount() {
		return virtualCurrencyAmount;
	}

	public void setVirtualCurrencyAmount(double virtualCurrencyAmount) {
		this.virtualCurrencyAmount = virtualCurrencyAmount;
	}

	public String getIapID() {
		return iapID;
	}

	public void setIapID(String iapID) {
		this.iapID = iapID;
	}

	public String getGameServer() {
		return gameServer;
	}

	public void setGameServer(String gameServer) {
		this.gameServer = gameServer;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public TalkingDataInfo(String msgID, String status, String oS, String accountID, String orderID,
			double currencyAmount, String currencyType, double virtualCurrencyAmount, String iapID, String gameServer,
			int level) {
		this.msgID = msgID;
		this.status = status;
		OS = oS;
		this.accountID = accountID;
		this.orderID = orderID;
		this.currencyAmount = currencyAmount;
		this.currencyType = currencyType;
		this.virtualCurrencyAmount = virtualCurrencyAmount;
		this.iapID = iapID;
		this.gameServer = gameServer;
		this.level = level;
	}
	
	public TalkingDataInfo() {
		
	}
}

