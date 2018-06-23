package com.funcy.g01.base.dao.sql.mapper.ranking;

import com.funcy.g01.base.bo.ranking.RoleRankingInfo;
import com.funcy.g01.base.bo.ranking.RoleRankingInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleRankingInfoMapper {
    int countByExample(RoleRankingInfoExample example);

    int deleteByExample(RoleRankingInfoExample example);

    int deleteByPrimaryKey(Long roleId);

    int insert(RoleRankingInfo record);

    int insertSelective(RoleRankingInfo record);

    List<RoleRankingInfo> selectByExample(RoleRankingInfoExample example);

    RoleRankingInfo selectByPrimaryKey(Long roleId);

    int updateByExampleSelective(@Param("record") RoleRankingInfo record, @Param("example") RoleRankingInfoExample example);

    int updateByExample(@Param("record") RoleRankingInfo record, @Param("example") RoleRankingInfoExample example);

    int updateByPrimaryKeySelective(RoleRankingInfo record);

    int updateByPrimaryKey(RoleRankingInfo record);
}