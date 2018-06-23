package com.funcy.g01.base.dao.sql.mapper;

import com.funcy.g01.base.bo.user.WhiteListInfo;
import com.funcy.g01.base.bo.user.WhiteListInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WhiteListInfoMapper {
    int countByExample(WhiteListInfoExample example);

    int deleteByExample(WhiteListInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WhiteListInfo record);

    int insertSelective(WhiteListInfo record);

    List<WhiteListInfo> selectByExample(WhiteListInfoExample example);

    WhiteListInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WhiteListInfo record, @Param("example") WhiteListInfoExample example);

    int updateByExample(@Param("record") WhiteListInfo record, @Param("example") WhiteListInfoExample example);

    int updateByPrimaryKeySelective(WhiteListInfo record);

    int updateByPrimaryKey(WhiteListInfo record);
}