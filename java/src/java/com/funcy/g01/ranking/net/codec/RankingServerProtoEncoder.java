package com.funcy.g01.ranking.net.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.ebo.synframework.nettybase.protoPool.ReusedProtoBuilder;
import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;

public class RankingServerProtoEncoder extends OneToOneEncoder {
    
    public RankingServerProtoEncoder() {
	}
    
    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		Object[] msgList = (Object[])msg;
		String serviceAndMethodStr = (String) msgList[0];
		int status = (Integer)msgList[1];
//		Builder builder = (Builder) msgList[2];
		return encode0(serviceAndMethodStr, status, msgList[2], ctx);
    }

	private ChannelBuffer encode0(String serviceAndMethodStr, int status, Object builder, ChannelHandlerContext ctx) {
		byte[] serviceAndMethodStrBytes = serviceAndMethodStr.getBytes();
		Message proto = null;
		if(builder instanceof Builder){
			proto = ((Builder) builder).build();
		}else if(builder instanceof ReusedProtoBuilder){
			proto = ((ReusedProtoBuilder) builder).getBuilder().build();
		}
		byte[] simpleClassNameBytes = proto.getClass().getSimpleName().getBytes();
		byte[] protoBytes = proto.toByteArray();
		int classNameBytesLength = simpleClassNameBytes.length;
		int msgLength = 1 + serviceAndMethodStrBytes.length + 1 + classNameBytesLength + protoBytes.length;
		ChannelBuffer buf = ChannelBuffers.buffer(4 + msgLength);
		buf.writeInt(msgLength);
		buf.writeByte(serviceAndMethodStrBytes.length);
		buf.writeBytes(serviceAndMethodStrBytes);
		buf.writeByte(classNameBytesLength);
		buf.writeBytes(simpleClassNameBytes);
		buf.writeBytes(protoBytes);
		if(builder instanceof ReusedProtoBuilder){
			((ReusedProtoBuilder) builder).release();
		}
		return buf;
	}

}
