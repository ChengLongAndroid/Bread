package com.funcy.g01.base.sdk;

public enum ChannelInfo {
	
	mi(12, "xiaomiSdkClient"),
	qihoo360(24, "qihoo360SdkClient"),
	baidu(23, "baiduSdkClient"),
	uc(18, "ucSdkClient"),
	am(70, "amigoSdkClient"),
	lenovo(29, "lenovoSdkClient"),
	nearme(13, "nearmeSdkClient"),
	huawei(17, "huaweiSdkClient"),
	coolpad(44, "coolpadSdkClient"),
	mz(15, "mzSdkClient"),
	vivo(21, "vivoSdkClient"),
	wandoujia(19, "wandoujiaSdkClient"),
	chuangyou(41, "chuangyouSdkClient"),
	tecent(60, "tecentSdkClient"),
	yijie(99, "yijieSdkClient");
	
	public final int channelId;
	
	public final String clientName;
	
	private ChannelInfo(int channelId, String clientName) {
		this.channelId = channelId;
		this.clientName = clientName;
	}

}

