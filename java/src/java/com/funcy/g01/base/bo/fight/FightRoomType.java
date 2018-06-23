package com.funcy.g01.base.bo.fight;

import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;

public enum FightRoomType {
	hall(0),room(1);

	private final int code;
	
	public int getCode() {
		return code;
	}
	
	private FightRoomType(int code){
		this.code = code;
	}
	
	public static FightRoomType valueOf(int typeCode){
		for (FightRoomType type : values()) {
			if(type.getCode() == typeCode) {
				return type;
			}
		}
		throw new BusinessException(ErrorCode.WRONG_CODE);
	}
}
