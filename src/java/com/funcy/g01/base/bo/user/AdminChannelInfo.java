package com.funcy.g01.base.bo.user;

public class AdminChannelInfo {
    private Integer id;

    private Integer channelId;

    private String updateUrl;

    private Integer updatePort;

    private String updateContext;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public Integer getUpdatePort() {
        return updatePort;
    }

    public void setUpdatePort(Integer updatePort) {
        this.updatePort = updatePort;
    }

    public String getUpdateContext() {
        return updateContext;
    }

    public void setUpdateContext(String updateContext) {
        this.updateContext = updateContext;
    }
}