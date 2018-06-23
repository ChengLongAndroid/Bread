package com.funcy.g01.base.bo.mysteriousshop;

import java.util.ArrayList;
import java.util.List;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ExchangeRecordProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleMysteriousShopInfoProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class RoleMysteriousShopInfo implements ProtobufSerializable {
	
	
	private transient long roleId;
	
	private List<ExchangeRecord> records;
	
	
	public RoleMysteriousShopInfo(){
		records = new ArrayList<ExchangeRecord>();
		records.add(new ExchangeRecord(1, 1, 0));
	}
	
	public RoleMysteriousShopInfo(byte[] bytes){
		parseFrom(bytes);
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleMysteriousShopInfoProto proto = (RoleMysteriousShopInfoProto) message;
		this.records = new ArrayList<ExchangeRecord>() ;
		for (ExchangeRecordProto exchangeProto : proto.getRecordsList()) {
			this.records.add(new ExchangeRecord(exchangeProto.getActiveShopId(), exchangeProto.getShopPeriodId(), exchangeProto.getExchangeNum()));
		}
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleMysteriousShopInfoProto.Builder buidler = RoleMysteriousShopInfoProto.newBuilder();
		for (ExchangeRecord exchangeRecord : records) {
			buidler.addRecords(exchangeRecord.copyTo());
		}
		return buidler.build();
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleMysteriousShopInfoProto roleChestInfoProto = RoleMysteriousShopInfoProto.parseFrom(bytes);
			copyFrom(roleChestInfoProto);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}
	
	public ExchangeRecord findExchangeRecord(int activeId, int periodId){
		for (ExchangeRecord exchangeRecord : records) {
			if (exchangeRecord.getActiveShopId() == activeId && exchangeRecord.getShopPeriodId() == periodId) {
				return exchangeRecord;
			}
		}
		return null;
	}
	

	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public List<ExchangeRecord> getRecords() {
		return records;
	}

	public void setRecords(List<ExchangeRecord> records) {
		this.records = records;
	}
}
