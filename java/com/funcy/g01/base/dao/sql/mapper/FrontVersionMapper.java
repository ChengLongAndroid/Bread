package com.funcy.g01.base.dao.sql.mapper;

import com.funcy.g01.base.bo.user.FrontVersion;
import com.funcy.g01.base.bo.user.FrontVersionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FrontVersionMapper {
    int countByExample(FrontVersionExample example);

    int deleteByExample(FrontVersionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FrontVersion record);

    int insertSelective(FrontVersion record);

    List<FrontVersion> selectByExample(FrontVersionExample example);

    FrontVersion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FrontVersion record, @Param("example") FrontVersionExample example);

    int updateByExample(@Param("record") FrontVersion record, @Param("example") FrontVersionExample example);

    int updateByPrimaryKeySelective(FrontVersion record);

    int updateByPrimaryKey(FrontVersion record);
}