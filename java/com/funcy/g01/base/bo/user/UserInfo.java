package com.funcy.g01.base.bo.user;



public class UserInfo {
    private int id;

    private String UDID;

    private String gameCenterId;

    private String platformId;

    private int reward;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUDID() {
        return UDID;
    }

    public void setUDID(String UDID) {
        this.UDID = UDID;
    }

    public String getGameCenterId() {
        return gameCenterId;
    }

    public void setGameCenterId(String gameCenterId) {
        this.gameCenterId = gameCenterId;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }
    
    public UserInfo() {
    }
    
	public UserInfo(String platformId,String UUID) {
		this.platformId = platformId;
		this.UDID = UUID;
	}
}