package com.funcy.g01.base.dao.sql.mapper;

import com.funcy.g01.base.bo.serverconfig.ServerInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ServerInfoMapper {
    int countByExample(ServerInfoExample example);

    int deleteByExample(ServerInfoExample example);

    int deleteByPrimaryKey(int id);

    int insert(ServerInfo record);

    int insertSelective(ServerInfo record);

    List<ServerInfo> selectByExample(ServerInfoExample example);

    ServerInfo selectByPrimaryKey(int id);

    int updateByExampleSelective(@Param("record") ServerInfo record, @Param("example") ServerInfoExample example);

    int updateByExample(@Param("record") ServerInfo record, @Param("example") ServerInfoExample example);

    int updateByPrimaryKeySelective(ServerInfo record);

    int updateByPrimaryKey(ServerInfo record);
}