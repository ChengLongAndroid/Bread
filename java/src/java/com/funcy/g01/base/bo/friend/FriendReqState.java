package com.funcy.g01.base.bo.friend;

import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;

public enum FriendReqState {
	
	PENDING(0), 
	ACCEPT(1), 
	REJECT(2)
	;
	
	private final int code;
		
	public int getCode() {
		return code;
	}

	private FriendReqState(int code) {
		this.code = code;
	}
	
	public static FriendReqState valueOf(int code) {
		for (FriendReqState type : values()) {
			if(type.getCode() == code) {
				return type;
			}
		}
		throw new BusinessException(ErrorCode.WRONG_CODE);
	}
	
}
