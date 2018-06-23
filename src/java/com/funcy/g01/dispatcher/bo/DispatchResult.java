package com.funcy.g01.dispatcher.bo;

public class DispatchResult {

	private DispatcherRoom dispatcherRoom;
	
	private boolean isNew;
	
	public DispatchResult(DispatcherRoom dispatcherRoom, boolean isNew) {
		this.dispatcherRoom = dispatcherRoom;
		this.isNew = isNew;
	}

	public DispatcherRoom getDispatcherRoom() {
		return dispatcherRoom;
	}

	public boolean isNew() {
		return isNew;
	}
	
}
