package com.funcy.g01.base.dao.sql.mapper.recharge;

import com.funcy.g01.base.bo.recharge.RechargeOrder;
import com.funcy.g01.base.bo.recharge.RechargeOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RechargeOrderMapper {
    int countByExample(RechargeOrderExample example);

    int deleteByExample(RechargeOrderExample example);

    int deleteByPrimaryKey(String id);

    int insert(RechargeOrder record);

    int insertSelective(RechargeOrder record);

    List<RechargeOrder> selectByExample(RechargeOrderExample example);

    RechargeOrder selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RechargeOrder record, @Param("example") RechargeOrderExample example);

    int updateByExample(@Param("record") RechargeOrder record, @Param("example") RechargeOrderExample example);

    int updateByPrimaryKeySelective(RechargeOrder record);

    int updateByPrimaryKey(RechargeOrder record);
}