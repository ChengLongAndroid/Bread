package com.funcy.g01.base.bo.email;

import java.util.ArrayList;
import java.util.List;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.SendEmailMessageInfoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.SendEmailMessageProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class SendEmailMessageInfo implements ProtobufSerializable {
	
	private List<SendEmailMessage> records = null;
	
	public SendEmailMessageInfo(){
		this.records = new ArrayList<SendEmailMessage>();
	}
	
	public SendEmailMessageInfo(byte[] bytes){
		parseFrom(bytes);
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		SendEmailMessageInfoProto proto = (SendEmailMessageInfoProto) message;
		this.records = new ArrayList<SendEmailMessage>();
		for (SendEmailMessageProto sendEmailMessageProto : proto.getRecordsList()) {
			SendEmailMessage email = new SendEmailMessage(sendEmailMessageProto);
			this.records.add(email);
		}
	}

	@Override
	public GeneratedMessage copyTo() {
		SendEmailMessageInfoProto.Builder buidler = SendEmailMessageInfoProto.newBuilder();
		for (SendEmailMessage sendEmailMessage : records) {
			buidler.addRecords(sendEmailMessage.copyTo());
		}
		return buidler.build();
	}
	
	public void addSendEmailMessage(SendEmailMessage email){
		this.records.add(email);
	}
	

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			SendEmailMessageInfoProto sendEmailMessageInfoProto = SendEmailMessageInfoProto.parseFrom(bytes);
			copyFrom(sendEmailMessageInfoProto);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}
}
