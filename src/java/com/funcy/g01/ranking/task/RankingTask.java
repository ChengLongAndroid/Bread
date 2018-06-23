package com.funcy.g01.ranking.task;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.ranking.RoleRankingInfo;
import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.dao.redis.RoleRepo;
import com.funcy.g01.base.dao.sql.RankingDao;
import com.funcy.g01.ranking.bo.RankingInfoManager;

@Component("rankingTask")
public class RankingTask {
	
	private static final Logger logger = Logger.getLogger(RankingTask.class);
	
	private Lock lock;
	
	@Autowired
	private RankingInfoManager rankingInfoManager;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private RankingDao rankingDao;
	
	public void periodRanking() {
		try {
			lock.lock();
			rankingInfoManager.refresh(null);
			logger.info("period refresh");
		} finally {
			lock.unlock();
		}
	}

	public void monthRanking() {
		try {
			lock.lock();
			ConcurrentHashMap<Long, RoleRankingInfo> map = rankingInfoManager.getRoleRankingMap();
			for(RoleRankingInfo rankingInfo : map.values()) {
				Role role = roleRepo.getRole(rankingInfo.getRoleId());
				if(role == null) {
					continue;
				}
				rankingInfo.monthRefresh(role);
				rankingDao.updateRoleRankingInfo(rankingInfo);
			}
			
			logger.info("month refresh");
		} finally {
			lock.unlock();
		}
	}
	
	public void weekRanking() {
		try {
			lock.lock();
			ConcurrentHashMap<Long, RoleRankingInfo> map = rankingInfoManager.getRoleRankingMap();
			for(RoleRankingInfo rankingInfo : map.values()) {
				Role role = roleRepo.getRole(rankingInfo.getRoleId());
				if(role == null) {
					continue;
				}
				rankingInfo.weekRefresh(role);
				rankingDao.updateRoleRankingInfo(rankingInfo);
			}
			logger.info("week refresh");
		} finally {
			lock.unlock();
		}
	}

	public void setLock(Lock lock) {
		this.lock = lock;
	}
	
}
