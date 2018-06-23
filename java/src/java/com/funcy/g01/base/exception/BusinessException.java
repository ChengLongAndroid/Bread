package com.funcy.g01.base.exception;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {

	private int code;
	
	private String msg;
	
	public BusinessException(int code) {
		this.code = code;
		this.msg = "no msg";
	}
	
	public BusinessException(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
	public Map<String, Object> getReturnMap() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", code);
		return result;
	}
	
}
