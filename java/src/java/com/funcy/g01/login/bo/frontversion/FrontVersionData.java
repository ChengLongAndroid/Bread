package com.funcy.g01.login.bo.frontversion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.user.AdminChannelInfo;
import com.funcy.g01.base.bo.user.FrontVersion;
import com.funcy.g01.base.bo.user.FrontVersionWithSourceVersionType;
import com.funcy.g01.login.dao.AdminChannelInfoDao;
import com.funcy.g01.login.dao.FrontVersionDao;

@Component
public class FrontVersionData {

	private volatile List<FrontVersion> frontVersions;
	
	private volatile Map<Integer, AdminChannelInfo> channelInfos;
	
	@Autowired
	private FrontVersionDao frontVersionDao;
	
	@Autowired
	private AdminChannelInfoDao adminChannelInfoDao;
	
	private FrontVersionData() {
	}

	public void reinit() {
		System.out.println("refresh frontVersionData success!");
		this.frontVersions = frontVersionDao.findAllFrontVersion();
		Map<Integer, AdminChannelInfo> channelInfos = new HashMap<Integer, AdminChannelInfo>();
		for(AdminChannelInfo info : adminChannelInfoDao.getAdminChannelInfos()) {
			channelInfos.put(info.getChannelId(), info);
		}
		this.channelInfos = channelInfos;		
	}
	
	@PostConstruct
	public void init() {
		this.reinit();
	}

	public FrontVersion getFrontVersionByVersionNum(int versionNum1, int versionNum2, boolean isInWhiteList) {
		for (FrontVersion frontVersion : frontVersions) {
			if(frontVersion.getIsInTest()) {
				if(!isInWhiteList) {
					continue;
				}
			}
			if(frontVersion.getVersionNum1() == versionNum1 && frontVersion.getVersionNum2() == versionNum2) {
				return frontVersion;
			}
		}
		return null;
	}
	
	public int getMaxSmallVersion(int versionNum1, boolean isInWhiteList) {
		int result = 0;
		for (FrontVersion frontVersion : frontVersions) {
			if(frontVersion.getIsInTest()) {
				if(!isInWhiteList) {
					continue;
				}
			}
			if(frontVersion.getVersionNum1() == versionNum1) {
				if(frontVersion.getVersionNum2() > result) {
					result = frontVersion.getVersionNum2();
				}
			}
		}
		return result;
	}
	
	public int getMaxBigVersion(boolean isInWhiteList) {
		int result = 0;
		for (FrontVersion frontVersion : frontVersions) {
			if(frontVersion.getIsInTest()) {
				if(!isInWhiteList) {
					continue;
				}
			}
			if(frontVersion.getVersionNum1() > result) {
				result = frontVersion.getVersionNum1();
			}
		}
		return result;
	}

	/**
	 * 搜索某一个sourceversion（1，2，3）为该num1和num2的最大的front version
	 * @param versionNum1
	 * @param versionNum2
	 * @param isInWhiteList
	 * @return
	 */
	public FrontVersionWithSourceVersionType findMaxSingleVersionBySourceVersion1(int versionNum1,
			int versionNum2, boolean isInWhiteList) {
		FrontVersionWithSourceVersionType result = null;
		for(int i = 1; i <= 3; i++) {
			for (FrontVersion frontVersion : frontVersions) {
				if(frontVersion.getIsInTest()) {
					if(!isInWhiteList) {
						continue;
					}
				}
				int sourceVersionNum1 = 0;
				int sourceVersionNum2 = 0;
				if(i == 1) {
					sourceVersionNum1 = frontVersion.getSource1VersionNum1();
					sourceVersionNum2 = frontVersion.getSource1VersionNum2();
				} else if(i == 2) {
					sourceVersionNum1 = frontVersion.getSource2VersionNum1();
					sourceVersionNum2 = frontVersion.getSource2VersionNum2();
				} else if(i == 3) {
					sourceVersionNum1 = frontVersion.getSource3VersionNum1();
					sourceVersionNum2 = frontVersion.getSource3VersionNum2();
				}
				if(versionNum1 == sourceVersionNum1 && versionNum2 == sourceVersionNum2) {
					if(result == null) {
						result = new FrontVersionWithSourceVersionType(frontVersion, i);
					} else {
						if(frontVersion.getVersionNum1() > result.frontVersion.getVersionNum1()) {
							result = new FrontVersionWithSourceVersionType(frontVersion, i);
						} else if(frontVersion.getVersionNum1() == result.frontVersion.getVersionNum1()) {
							if(frontVersion.getVersionNum2() > result.frontVersion.getVersionNum2()) {
								result = new FrontVersionWithSourceVersionType(frontVersion, i);
							}
						}
					}
				}
			}
		}
		return result;
	}
	
	public FrontVersion findMaxSingleVersionBySourceVersion(int versionNum1,
			int versionNum2, boolean isInWhiteList) {
		FrontVersion result = null;
		for (FrontVersion frontVersion : frontVersions) {
			if(frontVersion.getIsInTest()) {
				if(!isInWhiteList) {
					continue;
				}
			}
			if(versionNum1 == frontVersion.getSource1VersionNum1() && versionNum2 == frontVersion.getSource1VersionNum2()) {
				if(result == null) {
					result = frontVersion;
				} else {
					if(frontVersion.getVersionNum1() > result.getVersionNum1()) {
						result = frontVersion;
					} else if(frontVersion.getVersionNum1() == result.getVersionNum1()) {
						if(frontVersion.getVersionNum2() > result.getVersionNum2()) {
							result = frontVersion;
						}
					}
				}
			}
		}
		return result;
	}
	
	public AdminChannelInfo getAdminChannelInfo(int channelId) {
		return channelInfos.get(channelId);
	}
	
}
