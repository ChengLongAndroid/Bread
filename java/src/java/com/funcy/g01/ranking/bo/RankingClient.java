package com.funcy.g01.ranking.bo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;

import com.ebo.synframework.synroom.executor.SynRoomPlayer;
import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;

public class RankingClient extends SynRoomPlayer {

	private Channel channel;
	
	private boolean isConnected = true;
	
	private ReentrantLock lock = new ReentrantLock();
	
	private Condition condition = lock.newCondition();
	
	private String waitServiceAndMethodStr;
	
	private volatile Message resp;
	
	private int serverId;
	
	public RankingClient(ChannelHandlerContext ctx) {
		super(ctx.getChannel().getId(), ctx);
		this.channel = ctx.getChannel();
	}
	
	public Message sendAndWaitResp(String serviceAndMethodStr, Builder builder) {
		if(!this.isConnected) {
			throw new RuntimeException("not connect");
		}
		this.waitServiceAndMethodStr = serviceAndMethodStr;
		Object[] msg = {serviceAndMethodStr, 0, builder};
		send(msg);
		
		boolean isWaitEnd = false;
		lock.lock();
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
	
	public void respond(String serviceAndMethodStr, Builder builder) {
		Object[] msg = {serviceAndMethodStr, 0, builder};
		respond(msg);
	}
	
	public void send(Object msg) {
		if(!this.isConnected) {
			throw new RuntimeException("not connect");
		}
		channel.write(msg);
    }
	
	public void respond(Object msg) {
		channel.write(msg);
    }

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public int getServerId() {
		return serverId;
	}

	public String getWaitServiceAndMethodStr() {
		return waitServiceAndMethodStr;
	}
	
}
