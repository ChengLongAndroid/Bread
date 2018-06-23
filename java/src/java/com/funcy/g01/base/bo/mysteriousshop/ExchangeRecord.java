package com.funcy.g01.base.bo.mysteriousshop;

import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ExchangeRecordProto;

public class ExchangeRecord {

	private int activeShopId;
	
	private int shopPeriodId;
	
	private int exchangeNum;
	
	public ExchangeRecord(int activeShopId,int shopPeriodId, int exchangeNum){
		this.activeShopId = activeShopId;
		this.shopPeriodId = shopPeriodId;
		this.exchangeNum = exchangeNum;
	}

	public int getActiveShopId() {
		return activeShopId;
	}

	public void setActiveShopId(int activeShopId) {
		this.activeShopId = activeShopId;
	}

	public int getShopPeriodId() {
		return shopPeriodId;
	}

	public void setShopPeriodId(int shopPeriodId) {
		this.shopPeriodId = shopPeriodId;
	}

	public int getExchangeNum() {
		return exchangeNum;
	}

	public void setExchangeNum(int exchangeNum) {
		this.exchangeNum = exchangeNum;
	}
	
	public ExchangeRecordProto copyTo() {
		ExchangeRecordProto.Builder builder = ExchangeRecordProto.newBuilder();
		builder.setActiveShopId(activeShopId);
		builder.setShopPeriodId(shopPeriodId);
		builder.setExchangeNum(exchangeNum);
		return builder.build();
	} 
}
