package com.funcy.g01.login.bo.frontversion;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiDevice.Info;

import com.funcy.g01.base.bo.user.AdminChannelInfo;
import com.funcy.g01.base.bo.user.FrontVersion;
import com.funcy.g01.base.bo.user.FrontVersionWithSourceVersionType;
import com.funcy.g01.base.global.PlatformConfig;
import com.funcy.g01.login.bo.admin.WhiteListData;


/**
 * 配置版本规则
 * 1.初始化一个环境，需要初始化一个根版本，一般为0.0或者1.0，并且设为强制更新标识
 * 2.如果当前大版本=最新的大版本，只是小版本更新，根据链式，从当前版本寻找一个最短的链，链接到能够连接的最新的版本（注意，不会搜索最新的是哪个版本，而是从当前版本开始搜索）
 * 3.如果当前大版本<最新的大版本，则为大版本更新，会提取最新的大版本x.0，注意，这里取的小版本号为0，然后查看是否有直接更新过去的包，有则直接更新过去
 * 4.如果没有，则搜索到最新的一个强制更新版本，然后强制更新
 * 
 * 注意：只有x.0这样的大版本，才能强制更新和填入source2，source3，这时候才有意义 2017.12.31修改：取消该限制
 * 升级到的同一个版本号，不能有两个不同的包
 * @author yizhuan
 *
 */
public class FrontVersionCal {
	
	private int sourceVersionNum1;
	
	private int sourceVersionNum2;
	
	private FrontVersionData frontVersionData;
	
	private WhiteListData whiteListData;
	
	private PlatformConfig platformConfig;

	private ResultVersionInfo resultVersionInfo = new ResultVersionInfo();
	
	public FrontVersionCal(int sourceVersionNum1, int sourceVersionNum2, PlatformConfig platformConfig) {
		this.sourceVersionNum1 = sourceVersionNum1;
		this.sourceVersionNum2 = sourceVersionNum2;
		this.platformConfig = platformConfig;
	}
	
	public String getResultStr(String ip, int channelId) {
		StringBuilder sb = new StringBuilder();
		ResultVersionInfo resultVersionInfo = cal(ip);
		if(resultVersionInfo == null) {
			return "";
		}
		String loginIp = platformConfig.getUpdateIp();
		int loginPort = platformConfig.getUpdatePort();
		String loginContext = platformConfig.getUpdateContext();
		if(resultVersionInfo.isForceUpdate() && channelId != -1000) {
			AdminChannelInfo info = frontVersionData.getAdminChannelInfo(channelId);
			if(info != null) {
				loginIp = info.getUpdateUrl();
				loginPort = info.getUpdatePort();
				loginContext = info.getUpdateContext();
			}
		}
		sb.append("http://").append(loginIp).append(":").append(loginPort).append(loginContext).append(" ").append(resultVersionInfo.isForceUpdate() ? 1 : 0).append(" ").append(resultVersionInfo.getResultBigVersion()).append(" ").append(resultVersionInfo.getResultSmallVersion());
		sb.append(" ").append(resultVersionInfo.getPacks().size());
		sb.append(" ").append(resultVersionInfo.getTotalSize());
		for(String pack : resultVersionInfo.getPacks()) {
			if(pack == null || pack.equals("")) {
				continue;
			}
			sb.append(" ").append(pack);
		}
		return sb.toString();
	}
	
	public ResultVersionInfo cal(String ip) {
//		List<FrontVersion> versions = getLinkVersions(this.sourceVersionNum1, this.sourceVersionNum2);
//		//当前已经是最新的版本
//		if(versions.size() == 0) {
//			return new ResultVersionInfo();
//		}
		boolean isInWhiteList = this.whiteListData.isContainTheIp(ip);
		
		int lastVersionNum1 = this.frontVersionData.getMaxBigVersion(isInWhiteList);
		int maxVersionNum2 = this.frontVersionData.getMaxSmallVersion(lastVersionNum1, isInWhiteList);
		//当前已是最新版本
		if(lastVersionNum1 == this.sourceVersionNum1 && maxVersionNum2 == this.sourceVersionNum2) {
			return new ResultVersionInfo();
		}
		//区分大版本更新还是小版本更新
		//小版本按链式搜索
		if(lastVersionNum1 == sourceVersionNum1) { //更新小版本
			List<FrontVersionWithSourceVersionType> smallVersions = getLinkVersions1(this.sourceVersionNum1, this.sourceVersionNum2, isInWhiteList);
			for(FrontVersionWithSourceVersionType smallVersion : smallVersions) {
				resultVersionInfo.addPacks(smallVersion.getSourcePackName(), smallVersion.frontVersion.getVersionNum1(), smallVersion.frontVersion.getVersionNum2(), smallVersion.getSourcePackSize());
			}
			return resultVersionInfo;
		} else {
			if(this.sourceVersionNum1 > lastVersionNum1) {
				return new ResultVersionInfo();
			}
			//如果是大版本
			//获取最高版本，查看是否是强制更新版本，如果是，直接返回，不需要资源包位置，跳转都某些路径重新下载
			//如果非强制更新，查看打出的三个包是否有与自己当前大版本对应的，如果没有，则强制更新，下载路径需要往前搜索大版本号，直到搜到一个强制更新的版本号
			//如果有与自己当前大版本对应，则返回对应的包，然后依次返回小版本的包
			FrontVersion frontVersion = this.frontVersionData.getFrontVersionByVersionNum(lastVersionNum1, 0, isInWhiteList);
			
			//强制更新
			if(frontVersion.getForceUpdate()) {
				this.resultVersionInfo.setForceUpdate(true);
				this.resultVersionInfo.addPacks("", frontVersion.getVersionNum1(), frontVersion.getVersionNum2(), 0);
				return resultVersionInfo;
			}
			//查看是否有可直接升级的包
			if(frontVersion.getSource1VersionNum1() != 0 && frontVersion.getSource1VersionNum2() != 0) {
				if(frontVersion.getSource1VersionNum1() == sourceVersionNum1) {
					addVersions(frontVersion, frontVersion.getSource1PackName(), frontVersion.getSource1PackSize(), isInWhiteList);
					return resultVersionInfo;
				}
			}
			if(frontVersion.getSource2VersionNum1() != 0 && frontVersion.getSource2VersionNum2() != 0) {
				if(frontVersion.getSource2VersionNum1() == sourceVersionNum1) {
					addVersions(frontVersion, frontVersion.getSource2PackName(), frontVersion.getSource2PackSize(), isInWhiteList);
					return resultVersionInfo;
				}
			}
			if(frontVersion.getSource3VersionNum1() != 0 && frontVersion.getSource3VersionNum2() != 0) {
				if(frontVersion.getSource3VersionNum1() == sourceVersionNum1) {
					addVersions(frontVersion, frontVersion.getSource3PackName(), frontVersion.getSource3PackSize(), isInWhiteList);
					return resultVersionInfo;
				}
			}
			
			//没有可直接升级的包，则强制更新,需要往上找到一个强制更新的
			for(int i = lastVersionNum1 - 1; i >=0;i--) {
				FrontVersion forceVersion = this.frontVersionData.getFrontVersionByVersionNum(i, 0, isInWhiteList);
				if(forceVersion.getForceUpdate()) {
					this.resultVersionInfo.setForceUpdate(true);
					this.resultVersionInfo.addPacks("", forceVersion.getVersionNum1(), forceVersion.getVersionNum2(), 0);
					return resultVersionInfo;
				}
			}
		}
		//不可能，按最新版本处理
		return null;
	}

	private void addVersions(FrontVersion frontVersion, String packName, int packSize, boolean isInWhiteList) {
		this.resultVersionInfo.addPacks(packName, frontVersion.getVersionNum1(), frontVersion.getVersionNum2(), packSize);
		List<FrontVersion> smallVersions = getLinkVersions(frontVersion.getVersionNum1(), 0, isInWhiteList);
		for(FrontVersion smallVersion : smallVersions) {
			resultVersionInfo.addPacks(smallVersion.getSource1PackName(), smallVersion.getVersionNum1(), smallVersion.getVersionNum2(), smallVersion.getSource1PackSize());
		}
	}
	
	public List<FrontVersionWithSourceVersionType> getLinkVersions1(int versionNum1, int versionNum2, boolean isInWhiteList) {
		List<FrontVersionWithSourceVersionType> versions = new ArrayList<FrontVersionWithSourceVersionType>();
		int vn1 = versionNum1;
		int vn2 = versionNum2;
		while(true) {
			FrontVersionWithSourceVersionType maxSingleVersion = frontVersionData.findMaxSingleVersionBySourceVersion1(vn1, vn2, isInWhiteList);
			if(maxSingleVersion == null) {
				break;
			}
			vn1 = maxSingleVersion.frontVersion.getVersionNum1();
			vn2 = maxSingleVersion.frontVersion.getVersionNum2();
			versions.add(maxSingleVersion);
		}
		return versions;
	}
	
	public List<FrontVersion> getLinkVersions(int versionNum1, int versionNum2, boolean isInWhiteList) {
		List<FrontVersion> versions = new ArrayList<FrontVersion>();
		int vn1 = versionNum1;
		int vn2 = versionNum2;
		while(true) {
			FrontVersion maxSingleVersion = frontVersionData.findMaxSingleVersionBySourceVersion(vn1, vn2, isInWhiteList);
			if(maxSingleVersion == null) {
				break;
			}
			vn1 = maxSingleVersion.getVersionNum1();
			vn2 = maxSingleVersion.getVersionNum2();
			versions.add(maxSingleVersion);
		}
		return versions;
	}
	
	public void initDaoAndData(FrontVersionData frontVersionData, WhiteListData whiteListData) {
		this.frontVersionData = frontVersionData;
		this.whiteListData = whiteListData;
	}
	
}
