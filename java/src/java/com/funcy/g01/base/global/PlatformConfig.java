package com.funcy.g01.base.global;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PlatformConfig {

	@Value("${platformType}")
	private String platformType;
	
	@Value("${update_context}")
	private String updateContext;
	
	@Value("${update_ip}")
	private String updateIp;
	
	@Value("${update_port}")
	private String updatePort;
	
	@Value("${talking_data_id}")
	private String talking_data_id;
	
	@Value("${aliyun_oss_access_key_id}")
	private String aliyunOssAccessKeyId;
	
	@Value("${aliyun_oss_secret_access_key}")
	private String aliyunOssSecretAccessKey;
	
	@Value("${aliyun_oss_bucket}")
	private String aliyunOssBucket;
	
	@Value("${aliyun_oss_headImg_normal_path}")
	private String aliyunOssNormalImgPath;
	
	@Value("${aliyun_oss_headImg_big_path}")
	private String aliyunOssBigImgPath;
	
	@Value("${aliyun_oss_url}")
	private String aliyunOssUrl;

	@Value("${yunpian_apikey}")
	private String yunpianApikey;
	
	@Value("${yunpian_url}")
	private String yunpianUrl;
	
	public String getPlatformType() {
		return platformType;
	}

	public String getUpdateIp() {
		return updateIp;
	}

	public String getUpdateContext() {
		return updateContext;
	}
	
	public String getTalkingDataId() {
		return talking_data_id;
	}

	public int getUpdatePort() {
		return Integer.parseInt(updatePort);
	}

	public String getAliyunOssAccessKeyId() {
		return aliyunOssAccessKeyId;
	}

	public String getAliyunOssSecretAccessKey() {
		return aliyunOssSecretAccessKey;
	}

	public String getAliyunOssBucket() {
		return aliyunOssBucket;
	}

	public String getAliyunOssNomalImgPath() {
		return aliyunOssNormalImgPath;
	}
	
	public String getAliyunOssBigImgPath(){
		return aliyunOssBigImgPath;
	}

	public String getAliyunOssUrl() {
		return aliyunOssUrl;
	} 
	
	public String getYunpianApikey() {
		return yunpianApikey;
	} 
	
	public String getYunpianUrl() {
		return yunpianUrl;
	}
}
