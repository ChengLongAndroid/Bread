package com.funcy.g01.base.bo.serverconfig;

public enum ServerType {
	roomAndHallServer(1), login(2), dispatch(3), all(4);
	
	private int value;
	
	public int getValue() {
		return this.value;
	}
	
	private ServerType(int value) {
		this.value = value;
	}
	
	public static ServerType getServerType(int value) {
		for (ServerType serverType : ServerType.values()) {
			if (serverType.getValue() == value) {
				return serverType;
			}
		}
		
		return null;
	}
}
