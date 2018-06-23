package com.funcy.g01.base.bo.dress;

import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;

public enum DressType {
	
	none(0,0), hair(1,500001), face(2,500002),skin(3,23101),tail(4,500004);
	
	private final int code;
	
	private final int defaultId;
	
	public int getCode() {
		return code;
	}
	
	public int getDefaultId() {
		return defaultId;
	}

	private DressType(int code, int defaultId) {
		this.code = code;
		this.defaultId = defaultId;
	}
	
	public static DressType valueOf(int typeCode) {
		for (DressType type : values()) {
			if(type.getCode() == typeCode) {
				return type;
			}
		}
		throw new BusinessException(ErrorCode.WRONG_CODE);
	}
}
