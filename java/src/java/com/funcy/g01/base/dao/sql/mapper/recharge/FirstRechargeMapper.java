package com.funcy.g01.base.dao.sql.mapper.recharge;

import com.funcy.g01.base.bo.recharge.FirstRecharge;
import com.funcy.g01.base.bo.recharge.FirstRechargeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FirstRechargeMapper {
    int countByExample(FirstRechargeExample example);

    int deleteByExample(FirstRechargeExample example);

    int deleteByPrimaryKey(int id);

    int insert(FirstRecharge record);

    int insertSelective(FirstRecharge record);

    List<FirstRecharge> selectByExample(FirstRechargeExample example);

    FirstRecharge selectByPrimaryKey(int id);

    int updateByExampleSelective(@Param("record") FirstRecharge record, @Param("example") FirstRechargeExample example);

    int updateByExample(@Param("record") FirstRecharge record, @Param("example") FirstRechargeExample example);

    int updateByPrimaryKeySelective(FirstRecharge record);

    int updateByPrimaryKey(FirstRecharge record);
}