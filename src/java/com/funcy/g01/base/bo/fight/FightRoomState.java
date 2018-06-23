package com.funcy.g01.base.bo.fight;

import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;

public enum FightRoomState {
	preparing(0), fighting(1), ending(2), ended(3), prepare_countdown(4), prepareEnded(5), prepareMerge(6);
	
	private final int code;
	
	public int getCode() {
		return code;
	}
	
	private FightRoomState(int code){
		this.code = code;
	}
	
	public static FightRoomState valueOf(int typeCode){
		for (FightRoomState type : values()) {
			if(type.getCode() == typeCode) {
				return type;
			}
		}
		throw new BusinessException(ErrorCode.WRONG_CODE);
	}
}
