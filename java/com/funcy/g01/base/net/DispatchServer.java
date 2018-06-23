package com.funcy.g01.base.net;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;

import com.ebo.synframework.synroom.executor.SynRoomPlayer;
import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;

public class DispatchServer extends SynRoomPlayer {

	private Channel channel;
	
	private ReentrantLock lock = new ReentrantLock();
	
	private Condition condition = lock.newCondition();
	
	private String waitServiceAndMethodStr;
	
	private volatile Message resp;
	
	private DispatchServerType dispatchServerType;
	
	private boolean isConnected = true;
	
	public DispatchServer(ChannelHandlerContext context, DispatchServerType dispatchServerType) {
		super(context.getChannel().getId(), context);
		this.channel = context.getChannel();
		this.dispatchServerType = dispatchServerType;
	}
	
	public Message sendAndWaitResp(String serviceAndMethodStr, Builder builder) {
		if(!this.isConnected) {
			throw new RuntimeException("not connect");
		}
		lock.lock();
		this.waitServiceAndMethodStr = serviceAndMethodStr;
		Object[] msg = {serviceAndMethodStr, 0, builder};
		send(msg);
		
		boolean isWaitEnd = false;
		try {
			isWaitEnd = condition.await(3, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		lock.unlock();
		if(!isWaitEnd) {
			throw new RuntimeException("get resp fail! service is " + serviceAndMethodStr + " params:" + builder.toString());
		}
		return resp;
	}
	
	public void setRespAndNotify(Message resp) {
		this.resp = resp;
		this.waitServiceAndMethodStr = null;
		lock.lock();
		try {
			condition.signalAll();
		} catch (Exception e) {
		}
		lock.unlock();
	}

	public void send(String serviceAndMethodStr, Builder builder) {
		if(!this.isConnected) {
			throw new RuntimeException("not connect");
		}
		Object[] msg = {serviceAndMethodStr, 0, builder};
		send(msg);
	}
	
	public void send(Object msg) {
		if(!this.isConnected) {
			throw new RuntimeException("not connect");
		}
		channel.write(msg);
    }

	public String getWaitServiceAndMethodStr() {
		return waitServiceAndMethodStr;
	}

	public DispatchServerType getDispatchServerType() {
		return dispatchServerType;
	}
	
	public void channelDisconnect() {
		this.isConnected = false;
	}
	
}
