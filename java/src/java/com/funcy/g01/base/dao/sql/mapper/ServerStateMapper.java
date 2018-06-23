package com.funcy.g01.base.dao.sql.mapper;

import com.funcy.g01.base.bo.serverconfig.ServerState;
import com.funcy.g01.base.bo.serverconfig.ServerStateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ServerStateMapper {
    int countByExample(ServerStateExample example);

    int deleteByExample(ServerStateExample example);

    int deleteByPrimaryKey(int id);

    int insert(ServerState record);

    int insertSelective(ServerState record);

    List<ServerState> selectByExample(ServerStateExample example);

    ServerState selectByPrimaryKey(int id);

    int updateByExampleSelective(@Param("record") ServerState record, @Param("example") ServerStateExample example);

    int updateByExample(@Param("record") ServerState record, @Param("example") ServerStateExample example);

    int updateByPrimaryKeySelective(ServerState record);

    int updateByPrimaryKey(ServerState record);
}