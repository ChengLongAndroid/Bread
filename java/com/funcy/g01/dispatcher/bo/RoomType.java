package com.funcy.g01.dispatcher.bo;

import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;

public enum RoomType {
	hall(0), advance(1), normal(2), easy(3), tryPlay(4);

	private final int code;
	
	public int getCode() {
		return code;
	}
	
	private RoomType(int code){
		this.code = code;
	}
	
	public static RoomType valueOf(int typeCode){
		for (RoomType type : values()) {
			if(type.getCode() == typeCode) {
				return type;
			}
		}
		throw new BusinessException(ErrorCode.WRONG_CODE);
	}
}
