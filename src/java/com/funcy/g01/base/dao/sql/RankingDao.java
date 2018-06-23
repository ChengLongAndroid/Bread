package com.funcy.g01.base.dao.sql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.funcy.g01.base.bo.ranking.RoleRankingInfo;
import com.funcy.g01.base.bo.ranking.RoleRankingInfoExample;
import com.funcy.g01.base.dao.sql.mapper.ranking.RoleRankingInfoMapper;

@Repository
public class RankingDao {

	@Autowired
	private RoleRankingInfoMapper roleRankingInfoMapper;
	
	public List<RoleRankingInfo> getAllRoleRankings() {
		return roleRankingInfoMapper.selectByExample(new RoleRankingInfoExample());
	}
	
	public void updateRoleRankingInfo(RoleRankingInfo rankingInfo) {
		roleRankingInfoMapper.updateByPrimaryKey(rankingInfo);
	}
	
	public void insertRoleRankingInfo(RoleRankingInfo rankingInfo) {
		roleRankingInfoMapper.insert(rankingInfo);
	}
	
}
