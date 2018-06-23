package com.funcy.g01.base.bo.fight;

public abstract class FixFrameEvent {

	private long roleId;
	
	private ServerEventType eventType;
	
	private int frameIndex;
	
	public FixFrameEvent(long roleId, ServerEventType serverEventType, int frameIndex) {
		this.roleId = roleId;
		this.eventType = serverEventType;
		this.frameIndex = frameIndex;
	}
	
	public abstract void executeEvent();

	public long getRoleId() {
		return roleId;
	}

	public ServerEventType getEventType() {
		return eventType;
	}

	public int getFrameIndex() {
		return frameIndex;
	}

	public void setFrameIndex(int frameIndex) {
		this.frameIndex = frameIndex;
	}

	@Override
	public String toString() {
		return "FixFrameEvent [roleId=" + roleId + ", eventType=" + eventType
				+ ", frameIndex=" + frameIndex + "]";
	}
	
}
