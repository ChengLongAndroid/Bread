package com.funcy.g01.base.bo.serverconfig;

public enum ServerStateType {
	close(0), open(1),inmaintenance(2);
	
	private int value;
	
	public int getValue() {
		return this.value;
	}
	
	private ServerStateType(int value) {
		this.value = value;
	}
	
	public static ServerStateType getServerStateByValue(int value) {
		for (ServerStateType serverStateType : ServerStateType.values()) {
			if (serverStateType.getValue() == value) {
				return serverStateType;
			}
		}
		
		return ServerStateType.close;
	}
}
