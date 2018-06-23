package com.funcy.g01.base.bo.email;

import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;


public enum EmailType {
	
	SINGLE(1,"单人"),	//"单人",
	ALL(0,"全服"),	//"所有人",
	TOGM(2,"GM");	//"给GM的",
	
	private int Code;
	
	public String description;

	public int getCode() {
		return Code;
	}

	private EmailType(int code,String description) {
		Code = code;
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static EmailType valueOf(int code){
		for(EmailType type : values()){
			if(type.getCode()==code){
				return type;
			}
		}
		throw new BusinessException(ErrorCode.WRONG_CODE);
	}
}
