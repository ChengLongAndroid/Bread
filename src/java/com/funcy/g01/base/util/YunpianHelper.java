package com.funcy.g01.base.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.global.PlatformConfig;
import com.funcy.g01.base.util.HttpUtils;

@Component
public class YunpianHelper {

	@Autowired
	private PlatformConfig platformConfig;
	
	public void sendSms(String mobile, String text) {
		System.out.println(text);
	    Map<String, Object> params = new HashMap<String, Object>();//请求参数集合
	    params.put("apikey", platformConfig.getYunpianApikey());
	    params.put("text", text);
	    params.put("mobile", mobile);
	    HttpUtils.doPost(platformConfig.getYunpianUrl(), params, "UTF-8");
	}
}
