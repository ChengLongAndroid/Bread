package com.funcy.g01.base.bo.fight;

public abstract class SynPeriodEvent {

	private ServerEventType eventType;
	
	private long peroidTime;
	
	private long startTime;
	
	private int count;

	/**
	 * 
	 * @param eventType
	 * @param peroidTime 毫秒
	 */
	public SynPeriodEvent(ServerEventType eventType,
			long peroidTime) {
		this.eventType = eventType;
		this.peroidTime = peroidTime * 1000; 
	}
	
	public abstract void executeEvent();
	
	public void tryExecute(long curTime) {
		long delta = curTime - this.startTime;
		int curCount = (int) (delta / this.peroidTime);
		if(count < curCount) {
			executeEvent();
			count++;
			return;
		}
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
}
