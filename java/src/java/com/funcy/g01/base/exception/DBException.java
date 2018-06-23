package com.funcy.g01.base.exception;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class DBException extends RuntimeException {

	private int code;
	
	private String msg;
	
	public DBException(int code) {
		this.code = code;
		this.msg = "no msg";
	}
	
	public DBException(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Map<String, Object> getReturnMap() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", code);
		return result;
	}
	
}
