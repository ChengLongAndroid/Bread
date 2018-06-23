package com.funcy.g01.base.dao.sql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.CommonConstant;
import com.funcy.g01.base.bo.recharge.FirstRecharge;
import com.funcy.g01.base.bo.recharge.FirstRechargeExample;
import com.funcy.g01.base.bo.recharge.RechargeOrder;
import com.funcy.g01.base.bo.recharge.RechargeOrderExample;
import com.funcy.g01.base.dao.sql.mapper.recharge.FirstRechargeMapper;
import com.funcy.g01.base.dao.sql.mapper.recharge.RechargeOrderMapper;
import com.funcy.g01.base.data.ShopData;


@Component("rechargeDao")
public class RechargeDao {
	
	@Autowired
	private RechargeOrderMapper rechargeOrderMapper;
	
	@Autowired
	private FirstRechargeMapper firstRechargeMapper;
	
	@Autowired
	private ShopData shopData;
	
	public RechargeOrder findRechargeOrder(String channelOrderId, int channelId) {
		RechargeOrderExample example = new RechargeOrderExample();
		example.createCriteria().andOrderIdEqualTo(channelOrderId).andChannelIdEqualTo(channelId);
		List<RechargeOrder> list = rechargeOrderMapper.selectByExample(example);
		if(list.isEmpty()) return null;
		return list.get(0);
	}
	
	public FirstRecharge findFirstRechargeByRoleId(long roleId){
		FirstRechargeExample example = new FirstRechargeExample();
		example.createCriteria().andRoleIdEqualTo(roleId);
		List<FirstRecharge> list = firstRechargeMapper.selectByExample(example);
		if(list.size()!=0){
			FirstRecharge firstRecharge = list.get(0);
			firstRecharge.init(shopData);
			return firstRecharge;
		}
		else{
			return null;
		}

	}
	
	public void saveFirstRecharge(FirstRecharge firstRecharge){
		firstRecharge.arrayToJson();
		if(firstRecharge.getId() == CommonConstant.NO_EXIST_PRIMARY_ID)
			firstRechargeMapper.insert(firstRecharge);
		else
			firstRechargeMapper.updateByPrimaryKey(firstRecharge);
	}
	
	public RechargeOrder getRechargeOrder(String id) {
		return rechargeOrderMapper.selectByPrimaryKey(id);
	}

	public int insertRechargeOrder(RechargeOrder order) {
		return rechargeOrderMapper.insert(order);
	}
	
	public int saveRechargeOrder(RechargeOrder order) {
		return rechargeOrderMapper.updateByPrimaryKey(order);
	}
	
	public void delRechargeOrder(String id) {
		rechargeOrderMapper.deleteByPrimaryKey(id);
	}

}
