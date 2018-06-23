package com.funcy.g01.base.dao.sql.mapper;

import com.funcy.g01.base.bo.user.AdminChannelInfo;
import com.funcy.g01.base.bo.user.AdminChannelInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminChannelInfoMapper {
    int countByExample(AdminChannelInfoExample example);

    int deleteByExample(AdminChannelInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AdminChannelInfo record);

    int insertSelective(AdminChannelInfo record);

    List<AdminChannelInfo> selectByExample(AdminChannelInfoExample example);

    AdminChannelInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AdminChannelInfo record, @Param("example") AdminChannelInfoExample example);

    int updateByExample(@Param("record") AdminChannelInfo record, @Param("example") AdminChannelInfoExample example);

    int updateByPrimaryKeySelective(AdminChannelInfo record);

    int updateByPrimaryKey(AdminChannelInfo record);
}