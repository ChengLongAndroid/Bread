package com.funcy.g01.base.data;



public class MysteriousShopProperty {
	
	private final int id;
	
	private final long startTime;
	
	private final long endTime;
	
	private final int periodId;
	
	
	public MysteriousShopProperty(int id, long startTime, long endTime, int periodId){
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.periodId = periodId;
	}

	public int getId() {
		return id;
	}


	public int getPeriodId() {
		return periodId;
	}

	public long getEndTime() {
		return endTime;
	}

	public long getStartTime() {
		return startTime;
	}

}
