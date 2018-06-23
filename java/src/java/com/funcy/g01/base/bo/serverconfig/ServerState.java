package com.funcy.g01.base.bo.serverconfig;

import java.util.Date;

public class ServerState {
    private Integer id;

    private int serverId;

    private Date lastUpdateTime;

    private int hallRoomNum;

    private int hallUserNum;

    private int fightRoomNum;

    private int fightUserNum;

    @SuppressWarnings("unused")
	private ServerState() {
    }
    
    public ServerState(int serverId) {
    	this.serverId = serverId;
    	this.lastUpdateTime = new Date();
    }

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getHallRoomNum() {
        return hallRoomNum;
    }

    public void setHallRoomNum(int hallRoomNum) {
        this.hallRoomNum = hallRoomNum;
    }

    public int getHallUserNum() {
        return hallUserNum;
    }

    public void setHallUserNum(int hallUserNum) {
        this.hallUserNum = hallUserNum;
    }

    public int getFightRoomNum() {
        return fightRoomNum;
    }

    public void setFightRoomNum(int fightRoomNum) {
        this.fightRoomNum = fightRoomNum;
    }

    public int getFightUserNum() {
        return fightUserNum;
    }

    public void setFightUserNum(int fightUserNum) {
        this.fightUserNum = fightUserNum;
    }
}