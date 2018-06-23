package com.funcy.g01.base.net;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import com.ebo.synframework.nettybase.codec.ProtoDecoder;
import com.ebo.synframework.nettybase.codec.ProtoEncoder;
import com.ebo.synframework.nettybase.dispatcher.ReqCmd;
import com.ebo.synframework.synroom.executor.SynRoomPlayer;
import com.funcy.g01.base.bo.fight.FightPlayer;
import com.funcy.g01.base.bo.fight.FightRoom;
import com.funcy.g01.base.global.ServerConfig;
import com.google.protobuf.Message.Builder;

public class GamePlayer extends SynRoomPlayer {
	
	private static final Logger logger = Logger.getLogger(GamePlayer.class);

	private volatile long roleId;
	
    private final AtomicInteger state = new AtomicInteger(PlayerState.connected.ordinal());
	
    private final Channel channel;
    
    private AtomicInteger lastCmdIndex = new AtomicInteger(-1);
    
    public static final int max_ping_time = 1000;
    
    private int pingTime = 80;
    
    private int pingCount = 0;
    
    private int exceedMaxPingTimeNum = 0;
    
    private long connectedTime = -1;
    
    private volatile String platformId;
    
    private String token;
    
    private String key;
    
    private boolean isCutOut = false;
    
    private volatile FightPlayer fightPlayer;
    
    private volatile FightRoom synRoom;
    
    private List<TimePackageInfoCounter> receiveCounters;
    
    private List<TimePackageInfoCounter> sendCounters;
    
    private long lastReceiveMsgTime = System.currentTimeMillis();
    
    private boolean isWebSocket = false;
    
    private ProtoEncoder protoEncoder = null;
    
    private long cutoutStartTime;
    
    public GamePlayer() {
    	super(-10000, null);
    	this.channel = null;
    	this.playerId = -1;
    	if(ServerConfig.isStat) {
    		receiveCounters = new ArrayList<TimePackageInfoCounter>();
    		receiveCounters.add(new TimePackageInfoCounter(1));
    		receiveCounters.add(new TimePackageInfoCounter(3));
    		receiveCounters.add(new TimePackageInfoCounter(8));
    		
    		sendCounters = new ArrayList<TimePackageInfoCounter>();
    		sendCounters.add(new TimePackageInfoCounter(1));
    		sendCounters.add(new TimePackageInfoCounter(3));
    		sendCounters.add(new TimePackageInfoCounter(8));
    	}
    }
    
    public GamePlayer(ChannelHandlerContext channelHandlerContext) {
    	super(channelHandlerContext.getChannel().getId(), channelHandlerContext);
        this.channel = channelHandlerContext.getChannel();
        if(ServerConfig.isStat) {
    		receiveCounters = new ArrayList<TimePackageInfoCounter>();
    		receiveCounters.add(new TimePackageInfoCounter(1));
    		receiveCounters.add(new TimePackageInfoCounter(3));
    		receiveCounters.add(new TimePackageInfoCounter(8));
    		
    		sendCounters = new ArrayList<TimePackageInfoCounter>();
    		sendCounters.add(new TimePackageInfoCounter(1));
    		sendCounters.add(new TimePackageInfoCounter(3));
    		sendCounters.add(new TimePackageInfoCounter(8));
    	}
	}
    
    public FightRoom getSynRoom() {
		return synRoom;
	}
    
    public int addPingTime(int pingTime) {
    	if(this.fightPlayer != null) {
    		this.fightPlayer.refreshDelayFrame(pingTime);
    	}
    	
    	if(pingTime > max_ping_time) {
    		this.exceedMaxPingTimeNum++;
    	}
    	this.pingTime = (this.pingTime * 5 + pingTime) / 6;
    	this.pingCount++;
    	return this.pingTime;
    }

	public void setSynRoom(FightRoom synRoom) {
		this.synRoom = synRoom;
	}
	
	public void respond(String serviceAndMethod, CmdStatus cmdStatus, Object builder) {
		respond(serviceAndMethod, cmdStatus, builder, false);
	}

	public void respond(String serviceAndMethod, CmdStatus cmdStatus, Object builder, boolean isForceResp) {
		if(isCutOut && !isForceResp) {
			return;
		}
		if(isWebSocket) {
			if(this.protoEncoder == null) {
				this.protoEncoder = new ProtoEncoder(ServerConfig.getLengthOptimizeConfig());
			}
			ChannelBuffer channelBuffer = protoEncoder.encode0(serviceAndMethod, cmdStatus.ordinal(), builder, this.key, null, true);
			if (getState() != PlayerState.disconnected) {
	            if (channel.isOpen()) {
	                channel.write(new BinaryWebSocketFrame(channelBuffer));
	            }
	        }
		} else {
			Object[] sendMsg = new Object[] {serviceAndMethod, cmdStatus.ordinal(), builder, this.key};
			if (getState() != PlayerState.disconnected) {
	            if (channel.isOpen()) {
	                channel.write(sendMsg);
	            }
	        }
		}
	}
	
	public void forceRespond(String serviceAndMethod, CmdStatus cmdStatus, Builder builder) {
		respond(serviceAndMethod, cmdStatus, builder, true);
	}
    
    public boolean isCutOut() {
		return isCutOut;
	}

	public void setCutOut(boolean isCutOut) {
		this.isCutOut = isCutOut;
		if(isCutOut) {
			this.cutoutStartTime = System.currentTimeMillis();
		} else {
			this.cutoutStartTime = 0;
		}
	}
	
	public long getCutoutStartTime() {
		return cutoutStartTime;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public FightPlayer getFightPlayer() {
		return fightPlayer;
	}

	public void setFightPlayer(FightPlayer fightPlayer) {
		this.fightPlayer = fightPlayer;
	}

	public int getPingTime() {
		return pingTime;
	}

	public Channel getChannel() {
		return channel;
	}

    public void setCmdIndex(int cmdIndex) {
    	this.lastCmdIndex.set(cmdIndex);
    }
    
	public boolean trySetLastCmdIndex(int cmdIndex) {
        int lastCmdIndex = this.lastCmdIndex.get();
        if(lastCmdIndex >= cmdIndex) {
            return false;
        } else {
        	this.lastCmdIndex.set(cmdIndex);
            return true;
        }
    }
	
	public int getCmdIndex() {
		return this.lastCmdIndex.get();
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	
	public long getPlayerId() {
		return playerId;
	}

	public PlayerState getState() {
		return PlayerState.convertToPlayerState(this.state.get());
	}
	
	public void setState(PlayerState state) {
        this.state.set(state.ordinal());
    }
  
    public boolean casState(PlayerState preState, PlayerState nextState) {
        return this.state.compareAndSet(preState.ordinal(), nextState.ordinal());
    }

	public void destroy() {
	    this.channel.close();
	}

    public String getIp() {
    	InetSocketAddress address = (InetSocketAddress) this.channel.getRemoteAddress();
    	return address.getAddress().getHostAddress();
    }

	public boolean isExistRole() {
		long roleId = this.getRoleId();
		return roleId != 0;
	}

	public long getConnectedTime() {
		return connectedTime;
	}

	public void setConnectedTime(long connectedTime) {
		this.connectedTime = connectedTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<TimePackageInfoCounter> getReceiveCounters() {
		return receiveCounters;
	}

	public List<TimePackageInfoCounter> getSendCounters() {
		return sendCounters;
	}

	public void countReceivePackage(ReqCmd reqCmd) {
		if(ServerConfig.isStat) {
			int curTimeInSecond = (int) (System.currentTimeMillis() / 1000);
			int length = reqCmd.getLength() + 4;
			for (TimePackageInfoCounter timePackageInfoCounter : receiveCounters) {
				timePackageInfoCounter.count(length, curTimeInSecond);
			}
		}
	}
	
	public void countSendPackage(int length) {
		if(ServerConfig.isStat) {
			int curTimeInSecond = (int) (System.currentTimeMillis() / 1000);
			for (TimePackageInfoCounter timePackageInfoCounter : sendCounters) {
				timePackageInfoCounter.count(length, curTimeInSecond);
			}
		}
	}
	
	public long getLastReceiveMsgTime() {
		return lastReceiveMsgTime;
	}

	public void setLastReceiveMsgTime(long lastReceiveMsgTime) {
		this.lastReceiveMsgTime = lastReceiveMsgTime;
	}

	public boolean isReceiveMsgTimeout(long curTime) {
		if(curTime - this.lastReceiveMsgTime >= ServerConfig.max_not_receive_msg_time) {
			return true;
		}
		return false;
	}
	
	public boolean isCutoutTimeout(long curTime) {
		if(!this.isCutOut) {
			return false;
		}
		if(curTime - this.cutoutStartTime >= ServerConfig.max_cut_out_time) {
			return true;
		}
		return false;
	}

	public boolean isWebSocket() {
		return isWebSocket;
	}

	public void setWebSocket(boolean isWebSocket) {
		this.isWebSocket = isWebSocket;
	}
	
}
