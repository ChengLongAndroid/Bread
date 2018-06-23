package com.funcy.g01.base.bo.email;

import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;


public enum EmailState {

	NEW(0),	//新增邮件 
	READED_UNRECEIVED(1), 	//已读，未领取
	READED_RECEIVED(2),	//已读，已领取
	DELETED(-1)	//删除状态，（目前只有系统公告邮件需要使用，个人邮件会直接删除）
	;
	
	private final int code;
		
	public int getCode() {
		return code;
	}

	private EmailState(int code) {
		this.code = code;
	}
	
	public static EmailState valueOf(int code) {
		for (EmailState type : values()) {
			if(type.getCode() == code) {
				return type;
			}
		}
		throw new BusinessException(ErrorCode.WRONG_CODE);
	}
	
}
