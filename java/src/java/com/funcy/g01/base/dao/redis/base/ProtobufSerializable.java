package com.funcy.g01.base.dao.redis.base;

import com.google.protobuf.GeneratedMessage;

public interface ProtobufSerializable {
	
	void copyFrom(GeneratedMessage message);
	
	GeneratedMessage copyTo();
	
	void parseFrom(byte[] bytes);

	byte[] toByteArray();
}
