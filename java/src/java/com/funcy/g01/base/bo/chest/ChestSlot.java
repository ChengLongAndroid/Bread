package com.funcy.g01.base.bo.chest;

import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto;

public class ChestSlot {
	
	private ChestType chestType;
	
	private long canOpenTime;
	
	private int index;
	
	public ChestSlot(ChestType chestType, long canOpenTime, int index){
		this.chestType = chestType;
		this.canOpenTime = canOpenTime;
		this.index = index;
	}

	public ChestType getChestType() {
		return chestType;
	}

	public void setChestType(ChestType chestType) {
		this.chestType = chestType;
	}

	public long getCanOpenTime() {
		return canOpenTime;
	}

	public void setCanOpenTime(long canOpenTime) {
		this.canOpenTime = canOpenTime;
	}

	public ChestSlotProto copyTo() {
		ChestSlotProto.Builder builder = ChestSlotProto.newBuilder();
		builder.setCanOpenTime(canOpenTime);
		builder.setType(chestType.getCode());
		builder.setIndex(index);
		return builder.build();
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
