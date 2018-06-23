package com.funcy.g01.ranking.task;

import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.ranking.RoleRankingInfo;
import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.dao.redis.GlobalRepo;
import com.funcy.g01.base.dao.redis.RoleRepo;
import com.funcy.g01.base.dao.sql.RankingDao;
import com.funcy.g01.ranking.bo.RankingInfoManager;

@Component("refreshRoleRankingInfoTask")
public class RefreshRoleRankingInfoTask {
	
	private final Logger logger = Logger.getLogger(RefreshRoleRankingInfoTask.class);
	
	private Lock lock;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private RankingInfoManager rankingInfoManager;
	
	@Autowired
	private RankingDao rankingDao;
	
	@Autowired
	private GlobalRepo globalRepo;
	
	public void refresh() {
		try {
			lock.lock();
			logger.info("refresh role ranking info task start...");
			long maxRoleId = globalRepo.getRoleIdIndex() + 1000;
			
			for(long roleId = 1; roleId <= maxRoleId; roleId++) {
				Role role = roleRepo.getRole(roleId);
				if(role == null) {
					continue;
				}
				RoleRankingInfo rankingInfo = rankingInfoManager.getRoleRankingInfo(roleId);
				if(rankingInfo == null) {
					rankingInfo = new RoleRankingInfo(roleId);
					rankingInfo.update(role);
					rankingDao.insertRoleRankingInfo(rankingInfo);
					rankingInfoManager.addNewRoleRankingInfo(rankingInfo);
				} else {
					boolean isChange = rankingInfo.update(role);
					if(isChange) {
						rankingDao.updateRoleRankingInfo(rankingInfo);
					}
				}
			}
			logger.info("refresh role ranking info task end, end roleId(plus 1000) is " + maxRoleId);
		} finally {
			lock.unlock();
		}
	}

	public void setLock(Lock lock) {
		this.lock = lock;
	}

}
