package com.funcy.g01.base.bo.chest;

import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;

public enum ChestType {
	none(0),silver(1),gold(2),giant(3),xinshou1(4),xinshou2(5),xinshou3(6),xinshou4(7);
	
	private final int code;
	
	public int getCode() {
		return code;
	}

	private ChestType(int code) {
		this.code = code;
	}
	
	public static ChestType valueOf(int typeCode) {
		for (ChestType type : values()) {
			if(type.getCode() == typeCode) {
				return type;
			}
		}
		throw new BusinessException(ErrorCode.WRONG_CODE);
	}
}
