package com.funcy.g01.base.bo.recharge;

public enum RechargeError {
	none_error(-1),
	error_in_add_diamond(9000),
	error_in_send_email(9001),
	error_in_diamond_record(9002),
	error_in_open_mission(9003),
	inexistent_order(9004),
	wrong_amount(9005),
	repeat_Cp_Order_Id(9006);
	
	public int code;
	
	private RechargeError(int code) {
		this.code = code;
	}
	
	public static RechargeError getRechargeErrorByCode(int code) {
		for(RechargeError state : RechargeError.values()) {
			if(state.code == code) {
				return state;
			}
		}
		throw new RuntimeException();
	}
}
