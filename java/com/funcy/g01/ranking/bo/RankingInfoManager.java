package com.funcy.g01.ranking.bo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.ranking.RoleRankingInfo;
import com.funcy.g01.base.dao.sql.RankingDao;

@Component
public class RankingInfoManager {

	private ConcurrentHashMap<String, List<RoleRankingInfo>> typeRoleRankingMap = new ConcurrentHashMap<String, List<RoleRankingInfo>>();
	
	private ConcurrentHashMap<String, Lock> typeUpdateLockMap = new ConcurrentHashMap<String, Lock>();
	
	private ConcurrentHashMap<Long, RoleRankingInfo> roleRankingMap = new ConcurrentHashMap<Long, RoleRankingInfo>();
	
	@Autowired
	private RankingDao rankingDao;
	
	//只有ranking server需要做初始化
	public void init() {
		List<RoleRankingInfo> rankingInfos = rankingDao.getAllRoleRankings();
		for(RoleRankingInfo roleRankingInfo : rankingInfos) {
			roleRankingInfo.initRefresh();
			roleRankingMap.put(roleRankingInfo.getRoleId(), roleRankingInfo);
		} 
		refresh(rankingInfos);
//		for(RankingBigType rankingBigType : RankingBigType.values()) {
//			if(rankingBigType.getRankingType() == 0) { //需要地区类别，初始化只处理全部
//				RankingSmallType rankingSmallType = RankingSmallType.global;
//				List<RoleRankingInfo> newList = new ArrayList<RoleRankingInfo>(rankingInfos);
//				updateTypeRoleRankingMap(newList, rankingBigType,
//						rankingSmallType, null);
//			} else { //按照时间分组类别
//				RankingSmallType[] smallTypes = {RankingSmallType.month, RankingSmallType.week, RankingSmallType.all};
//				for(RankingSmallType rankingSmallType : smallTypes) {
//					List<RoleRankingInfo> newList = new ArrayList<RoleRankingInfo>(rankingInfos);
//					updateTypeRoleRankingMap(newList, rankingBigType,
//							rankingSmallType, null);
//				}
//			}
//		}
	}
	
	/**
	 * 
	 * @param type 0:period, 1:day, 2:week
	 */
	public void refresh(List<RoleRankingInfo> rankingInfos) {
		if(rankingInfos == null) {
			rankingInfos = new ArrayList<RoleRankingInfo>();
			for(RoleRankingInfo rankingInfo : roleRankingMap.values()) {
				rankingInfos.add(rankingInfo);
			}
		}
		
		
//		if(type == 0) {
			for(RankingBigType rankingBigType : RankingBigType.values()) {
				if(rankingBigType.getRankingType() == 0) {
					List<RoleRankingInfo> newList = new ArrayList<RoleRankingInfo>(rankingInfos);
					updateTypeRoleRankingMap(newList, rankingBigType,
							RankingSmallType.global, null);
				}
			}
			
			for(RankingBigType rankingBigType : RankingBigType.values()) {
				if(rankingBigType.getRankingType() == 1) {
					List<RoleRankingInfo> newList = new ArrayList<RoleRankingInfo>(rankingInfos);
					updateTypeRoleRankingMap(newList, rankingBigType,
							RankingSmallType.all, null);
				}
			}
//		} else if(type == 1) {
			for(RankingBigType rankingBigType : RankingBigType.values()) {
				if(rankingBigType.getRankingType() == 1) {
					List<RoleRankingInfo> newList = new ArrayList<RoleRankingInfo>(rankingInfos);
					updateTypeRoleRankingMap(newList, rankingBigType,
							RankingSmallType.month, null);
				}
			}
//		} else if(type == 2) {
			for(RankingBigType rankingBigType : RankingBigType.values()) {
				if(rankingBigType.getRankingType() == 1) {
					List<RoleRankingInfo> newList = new ArrayList<RoleRankingInfo>(rankingInfos);
					updateTypeRoleRankingMap(newList, rankingBigType,
							RankingSmallType.week, null);
				}
			}
//		} else {
//			throw new RuntimeException();
//		}
		
	}

	public void updateTypeRoleRankingMap(List<RoleRankingInfo> newList,
			RankingBigType rankingBigType, RankingSmallType rankingSmallType, String cityType) {
		String key = getKey(rankingBigType, rankingSmallType, cityType);
		Lock lock = tryGetOrAddLock(key);
		try {
			lock.lock();
			Collections.sort(newList, rankingBigType.getComparator(rankingSmallType));
			typeRoleRankingMap.put(key, newList);
		} finally {
			lock.unlock();
		}
		long ranking = 1;
		for(RoleRankingInfo rankingInfo : newList) {
			rankingInfo.getRankingMap().put(key, ranking);
			ranking++;
		}
	}
	
	public List<RoleRankingInfo> getRankingList(RankingBigType rankingBigType, RankingSmallType rankingSmallType, String cityType, int fromRanking, int num) {
		String key = getKey(rankingBigType, rankingSmallType, cityType);
		List<RoleRankingInfo> rankingInfos = typeRoleRankingMap.get(key);
		if(rankingInfos == null) {
			Lock lock = tryGetOrAddLock(key);
			try {
				lock.lock();
				rankingInfos = new ArrayList<RoleRankingInfo>();
				if(rankingSmallType == RankingSmallType.friend) {
					//TODO 有好友再做
				} else if(rankingSmallType == RankingSmallType.city) {
					for(RoleRankingInfo rankingInfo : this.roleRankingMap.values()) {
						if(rankingInfo!= null) {
							if(cityType.equals(rankingInfo.getLocation())) {
								rankingInfos.add(rankingInfo);
							}
						}
					}
				} else {
					throw new RuntimeException();
				}
				updateTypeRoleRankingMap(rankingInfos, rankingBigType, rankingSmallType, cityType);
			} finally {
				lock.unlock();
			}
		}
		int size = rankingInfos.size();
		fromRanking = fromRanking > size ? size : fromRanking;
		int toIndex = fromRanking + num;
		toIndex = toIndex > size ? size : toIndex;
		return rankingInfos.subList(fromRanking, toIndex);
	}

	public Lock tryGetOrAddLock(String key) {
		Lock lock = typeUpdateLockMap.get(key);
		if(lock == null) {
			lock = new ReentrantLock();
			Lock beforeLock = typeUpdateLockMap.putIfAbsent(key, lock);
			if(beforeLock != null) {
				lock = beforeLock;
			}
		}
		return lock;
	}
	
	public String getKey(RankingBigType rankingBigType, RankingSmallType rankingSmallType) {
		return getKey(rankingBigType, rankingSmallType, null);
	}
	
	public String getKey(RankingBigType rankingBigType, RankingSmallType rankingSmallType, String cityType) {
		if(rankingSmallType == RankingSmallType.city) {
			return rankingBigType.name() + "_" + rankingSmallType.name() + "_" + cityType;
		} else {
			return rankingBigType.name() + "_" + rankingSmallType.name();
		}
	}
	
	public RoleRankingInfo getRoleRankingInfo(long roleId) {
		return this.roleRankingMap.get(roleId);
	}

	public void addNewRoleRankingInfo(RoleRankingInfo rankingInfo) {
		this.roleRankingMap.putIfAbsent(rankingInfo.getRoleId(), rankingInfo);
	}

	public ConcurrentHashMap<Long, RoleRankingInfo> getRoleRankingMap() {
		return roleRankingMap;
	}
	
}
