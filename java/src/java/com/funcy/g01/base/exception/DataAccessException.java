package com.funcy.g01.base.exception;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class DataAccessException extends RuntimeException{

	private int code;
	
	private String msg;
	
	public DataAccessException(int code) {
		this.code = code;
		this.msg = "no msg";
	}
	
	public DataAccessException(String msg) {
		this.msg = msg;
	}
	
	public DataAccessException(int code, String msg) {
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
