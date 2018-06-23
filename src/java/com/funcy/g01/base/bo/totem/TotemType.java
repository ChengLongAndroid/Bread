package com.funcy.g01.base.bo.totem;

import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;

public enum TotemType {
	
	none(0), wind(1), water(2), fire(3);
	
	private final int code;
	
	public int getCode() {
		return code;
	}

	private TotemType(int code) {
		this.code = code;
	}
	
	public static TotemType valueOf(int typeCode) {
		for (TotemType type : values()) {
			if(type.getCode() == typeCode) {
				return type;
			}
		}
		throw new BusinessException(ErrorCode.WRONG_CODE);
	}
}
