package com.funcy.g01.base.bo.recharge;

import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;

public enum RechargeType {
	common(0),monthcard(1),yearcard(2),libao(3);
	private final int code;
	public int getCode() {
		return code;
	}
	private RechargeType(int code) {
		this.code = code;
	}
	
	public static RechargeType valueOf(int typeCode) {
		for (RechargeType type : values()) {
			if(type.getCode() == typeCode) {
				return type;
			}
		}
		throw new BusinessException(ErrorCode.WRONG_CODE);
	}
}
