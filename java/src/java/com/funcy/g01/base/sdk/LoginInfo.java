package com.funcy.g01.base.sdk;

public class LoginInfo {
	
	private int state;
	
	private String msg;
	
	private LoginData data;

    public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public LoginData getData() {
		return data;
	}

	public void setData(LoginData data) {
		this.data = data;
	}

	public LoginInfo() {
		state = 1;
		msg = "";
		data = null;
	}
	
	public LoginInfo(int state, String msg, LoginData data) {
		this.state = state;
		this.msg = msg;
		this.data = data;
	}
}
