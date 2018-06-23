package com.funcy.g01.base.bo.role;

import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;

public enum RoleState {
	
	NONE(-1),
	OFFLINE(0), 
	ONLINE(1), 
	MATCHING(2),
	PLAYING(3),
	TEAMING(4),
	;
	
	private final int code;
		
	public int getCode() {
		return code;
	}

	private RoleState(int code) {
		this.code = code;
	}
	
	public static RoleState valueOf(int code) {
		for (RoleState type : values()) {
			if(type.getCode() == code) {
				return type;
			}
		}
		throw new BusinessException(ErrorCode.WRONG_CODE);
	}
	
}
