package com.funcy.g01.base.bo.friend;

import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;

public enum RecommendFriendtype {
	
	recentPartner(1), nearby(2), other(3), none(4)
	;
	
	private final int code;
		
	public int getCode() {
		return code;
	}

	private RecommendFriendtype(int code) {
		this.code = code;
	}
	
	public static RecommendFriendtype valueOf(int code) {
		for (RecommendFriendtype type : values()) {
			if(type.getCode() == code) {
				return type;
			}
		}
		throw new BusinessException(ErrorCode.WRONG_CODE);
	}
	
}
