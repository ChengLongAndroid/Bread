package com.funcy.g01.base.sdk.ios;

public class IOSLoginInfo {
	
	private String playerID;
	
	private String bundleID;
	
	private long timestamp;
	
	private String salt;
	
	private String signature;
	
	private String publicKeyURL;

	public String getPlayerID() {
		return playerID;
	}

	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}

	public String getBundleID() {
		return bundleID;
	}

	public void setBundleID(String bundleID) {
		this.bundleID = bundleID;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getPublicKeyURL() {
		return publicKeyURL;
	}

	public void setPublicKeyURL(String publicKeyURL) {
		this.publicKeyURL = publicKeyURL;
	}
	
}
