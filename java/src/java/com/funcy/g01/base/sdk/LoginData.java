package com.funcy.g01.base.sdk;

public class LoginData {
	
	private int userID;
	
	private String sdkUserID;
	
	private String token;
	
	private String extension;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getSdkUserID() {
		return sdkUserID;
	}

	public void setSdkUserID(String sdkUserID) {
		this.sdkUserID = sdkUserID;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	public LoginData() {
		
	}

	public LoginData(String sdkUserID, String token) {
		this.userID = 0;
		this.sdkUserID = sdkUserID;
		this.token = token;
		this.extension = "";
	}
}
