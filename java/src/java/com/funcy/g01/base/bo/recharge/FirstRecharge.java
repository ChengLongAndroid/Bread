package com.funcy.g01.base.bo.recharge;


import java.util.ArrayList;
import java.util.List;

import com.funcy.g01.base.bo.CommonConstant;
import com.funcy.g01.base.data.ShopData;
import com.funcy.g01.base.util.JSONUtils;

public class FirstRecharge {
    private Integer id;

    private Long roleId;

    private String type;
    
    private List<Integer> types;

    public Integer getId() {
        return id;
    }

    public List<Integer> getTypes() {
		return types;
	}

	public void setTypes(List<Integer> types) {
		this.types = types;
	}

	public void setId(Integer id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public FirstRecharge(){
    	this.id = CommonConstant.NO_EXIST_PRIMARY_ID;
    }

	public FirstRecharge(Long roleId, ShopData shopData) {
		this();
		this.roleId = roleId;
		int num = shopData.getRechargeTypeNum();
		this.types = new ArrayList<Integer>(num);
		for(int i=0;i<num;i++) types.add(1);
	}
    
	public void init(ShopData shopData){
		types = JSONUtils.fromJSONToList(type, Integer.class);
		int size = types.size();
		int num = shopData.getRechargeTypeNum();
		for(int i=size; i<num; i++) types.add(1);
	}
	
	public boolean recordFirstRecharge(int type){
		if(types.get(type-1)  == 1){
			types.set(type-1, 0);
		    return true;
		}
		else 
			return false;
	}
	
    public void arrayToJson(){
    	this.type=JSONUtils.toJSON(types);
    }
    
//    public FirstRechargeVo copyTo(){
//    	FirstRechargeVo.Builder builder = FirstRechargeVo.newBuilder();
//    	int size = types.size();
//		for(int i=0; i<size; i++) {
//			builder.addIsFirstRecharge(types.get(i)==1);
//		}
//    	return builder.build();
//    }
}