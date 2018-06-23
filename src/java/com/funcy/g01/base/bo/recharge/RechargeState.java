package com.funcy.g01.base.bo.recharge;

public enum RechargeState {
	
	submit(1000),
	success(2000),
	sdk_fail(3000),
	business_fail(4000),
	connection_error(5000);
	
	public int code;
	
	private RechargeState(int code) {
		this.code = code;
	}
	
	public static RechargeState getRechargeStateByCode(int code) {
		for(RechargeState state : RechargeState.values()) {
			if(state.code == code) {
				return state;
			}
		}
		throw new RuntimeException();
	}
}
