package com.funcy.g01.login.bo.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.user.WhiteListInfo;
import com.funcy.g01.base.bo.user.WhiteListInfoExample;
import com.funcy.g01.base.dao.sql.mapper.WhiteListInfoMapper;

@Component("whiteListData")
public class WhiteListData {

	private static final Logger logger = Logger.getLogger(WhiteListData.class);
	
	private static WhiteListData instance;
	
	@Autowired
	private WhiteListInfoMapper whiteListInfoMapper;
	
	private Map<String, WhiteListInfo> ipMap = new HashMap<String, WhiteListInfo>();
	
	private Map<String, WhiteListInfo> platformIdMap = new HashMap<String, WhiteListInfo>();
	
	private WhiteListData() {
		instance = this;
	}
	
	public static WhiteListData getInstance() {
		return instance;
	}
	
	@PostConstruct
	public void init() {
		reInit();
	}
	
	public void reInit() {
		List<WhiteListInfo> list = whiteListInfoMapper.selectByExample(new WhiteListInfoExample());
		for (WhiteListInfo whiteListInfo : list) {
			if(whiteListInfo.getIp() != null && !"".equals(whiteListInfo.getIp())) {
				this.ipMap.put(whiteListInfo.getIp(), whiteListInfo);
			}
			if(whiteListInfo.getPlatformId() != null && !"".equals(whiteListInfo.getPlatformId())) {
				this.platformIdMap.put(whiteListInfo.getPlatformId(), whiteListInfo);
			}
		}
		logger.info("white list data reinit success!");
	}
	
	public boolean isContainTheIp(String ip) {
		return this.ipMap.containsKey(ip);
	}
	
	public boolean isInWhiteList(String platformId) {
		return platformIdMap.containsKey(platformId);
	}
}
