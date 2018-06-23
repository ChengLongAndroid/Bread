package com.funcy.g01.base.bo.recharge;

import java.math.BigDecimal;
import java.util.Date;

import com.funcy.g01.base.sdk.RechargeEnv;


public class RechargeOrder {
    private String id;

    private int areaId;

    private String platformId;

    private int roleLevel;

    private int vipLevel;

    private String accountId;

    private String orderId;

    private int channelId;

    private int type;

    private String amount;

    private int diamond;

    private int state;

    private Date createTime;

    private String resp;

    private int errorCode;

    private String roleName;

    private String env;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public int getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(int roleLevel) {
        this.roleLevel = roleLevel;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }
    
    
	public RechargeOrder() {
    	
    }
    
    public RechargeOrder(String id) {
		this.id = id;
		this.areaId = -1;
		this.platformId = null;
		this.roleLevel = -1;
		this.vipLevel = -1;
		this.type = -1;
		this.state = -1;
		this.createTime = new Date(System.currentTimeMillis());
		this.accountId = null;
		this.orderId = null;
		this.channelId = -1;
		this.amount = "-1";
		this.diamond = -1;
		this.resp = null;
		this.errorCode = -1;
		this.env = "none";
    }
    
	public RechargeOrder(String id, String platformId,
			Integer roleLevel, Integer vipLevel, Integer type, String roleName, RechargeEnv rechargeEnv) {
		this.id = id;
		this.areaId = -1;
		this.platformId = platformId;
		this.roleLevel = roleLevel;
		this.vipLevel = vipLevel;
		this.type = type;
		this.state = RechargeState.submit.code;
		this.createTime = new Date(System.currentTimeMillis());
		this.accountId = null;
		this.orderId = null;
		this.channelId = -1;
		this.amount = "-1";
		this.diamond = -1;
		this.resp = null;
		this.errorCode = -1;
		this.roleName = roleName;
		this.env = rechargeEnv.name();
	}
	
	public void complete(String accountId, String orderId, Integer channelId, BigDecimal amount,
			Integer diamond, Integer state, String resp, int errorCode) {
		this.accountId = accountId;
		this.orderId = orderId;
		this.channelId = channelId;
		this.amount = amount.toPlainString();
		this.diamond = diamond;
		this.state = state;
		this.resp = resp;
		this.errorCode = errorCode;
	}

}