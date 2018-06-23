package com.funcy.g01.base.sdk;

import javax.servlet.http.HttpServletRequest;

public interface SdkClientInterface {
	
	public LoginInfo login(HttpServletRequest request);
	
	public String recharge(HttpServletRequest request);
	
	public void initSdkData();
}
