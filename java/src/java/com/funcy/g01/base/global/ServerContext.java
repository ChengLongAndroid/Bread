package com.funcy.g01.base.global;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.ebo.synframework.synroom.executor.pool.SynRoomThreadPool;
import com.ebo.synframework.synroom.tools.ConcurrentHashMap;
import com.funcy.g01.base.bo.fight.FightRoom;
import com.funcy.g01.base.bo.fight.FightRoomType;
import com.funcy.g01.base.bo.map.ServerMapInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.base.bo.serverconfig.ServerState;
import com.funcy.g01.base.bo.serverconfig.ServerType;
import com.funcy.g01.base.bo.user.FrontVersion;
import com.funcy.g01.base.net.DispatchServer;
import com.funcy.g01.base.net.DispatchServerType;
import com.funcy.g01.base.net.DispatcherClientInitor;
import com.funcy.g01.base.net.GamePlayer;
import com.funcy.g01.base.net.RankingServerClient;
import com.funcy.g01.base.net.RankingServerClientInitor;
import com.funcy.g01.dispatcher.bo.DispatchClient;
import com.funcy.g01.dispatcher.bo.RoomType;

@Component
public class ServerContext implements ApplicationContextAware {

    /**
     * key channelId
     */
    private ConcurrentHashMap<Long, GamePlayer> connectedPlayers = new ConcurrentHashMap<Long, GamePlayer>();

    /**
     * key roleId
     */
    private ConcurrentHashMap<Long, GamePlayer> hallLogonPlayers = new ConcurrentHashMap<Long, GamePlayer>();
    
    private ConcurrentHashMap<Long, GamePlayer> fightLogonPlayers = new ConcurrentHashMap<Long, GamePlayer>();
    
    private ConcurrentHashMap<Long, FightRoom> fightRooms = new ConcurrentHashMap<Long, FightRoom>();
    
    private ConcurrentHashMap<Long, FightRoom> hallFightRooms = new ConcurrentHashMap<Long, FightRoom>();
    
    private volatile ConcurrentHashMap<Integer, ServerState> serverStates = new ConcurrentHashMap<Integer, ServerState>();
    
    private DispatchServer loginDispatchServer4ReceiveMsg;
    
    private ConcurrentLinkedQueue<DispatchServer> loginDispatchServer4SendMsg = new ConcurrentLinkedQueue<DispatchServer>();
    
    private DispatchServer roomDispatchServer4ReceiveMsg;
    
    private ConcurrentLinkedQueue<DispatchServer> roomDispatchServer4SendMsg = new ConcurrentLinkedQueue<DispatchServer>();
    
    private DispatchServer hallDispatchServer4ReceiveMsg;
    
    private ConcurrentLinkedQueue<DispatchServer> hallDispatchServer4SendMsg = new ConcurrentLinkedQueue<DispatchServer>();
    
    private RankingServerClient rankingServerClient4ReceiveMsg;
    
    private ConcurrentLinkedQueue<RankingServerClient> rankingServerClient4SendMsg = new ConcurrentLinkedQueue<RankingServerClient>();
    
    /**
     * 5个档
     */
    private ConcurrentHashMap<Integer, List<ServerMapInfo>> serverMapInfos = new ConcurrentHashMap<Integer, List<ServerMapInfo>>();
    
    private long serverStartTime;
    
    @Autowired
    private ServerInfoData serverInfoData;
    
    private ApplicationContext ac;
    
    private volatile FrontVersion curVersion = null;
    
    private ServerContext() {
    	for(int star = 1; star <= 5; star++) {
    		serverMapInfos.put(star, new ArrayList<ServerMapInfo>());
    	}
    }
    
	public int getConnectedPlayersNum(){
    	return connectedPlayers.size();
    }
    
    public ConcurrentHashMap<Long, GamePlayer> getConnectedPlayers(){
    	return connectedPlayers;
    }
    
    public GamePlayer findHallLogonPlayer(long roleId) {
    	return this.hallLogonPlayers.get(roleId);
    }
    
    public boolean addConnectedPlayer(GamePlayer gamePlayer) {
        return connectedPlayers.putIfAbsent(gamePlayer.getPlayerId(), gamePlayer) == null;
    }
    
    public boolean addHallLogonPlayer(GamePlayer gamePlayer) {
        return hallLogonPlayers.put(gamePlayer.getRoleId(), gamePlayer) == null;
    }
    
    public void removeHallLogonPlayer(GamePlayer gamePlayer){
    	this.hallLogonPlayers.remove(gamePlayer.getRoleId());
    }

	public void removeConnectedPlayer(GamePlayer gamePlayer) {
        this.connectedPlayers.remove(gamePlayer.getPlayerId());
	}
	
    public int getLogonPlayerNum() {
    	return fightLogonPlayers.size() + hallLogonPlayers.size();
    }
    
    public ConcurrentHashMap<Integer, List<ServerMapInfo>> getServerMapInfos() {
		return serverMapInfos;
	}

	public int getFightRoomNum(boolean isHall) {
    	if(isHall) {
    		return this.hallFightRooms.size();
    	} else {
    		return this.fightRooms.size();
    	}
    }
    
    public int getFightRoomPlayerNum(boolean isHall) {
    	int total = 0;
    	if(isHall) {
    		for (FightRoom fightRoom : this.hallFightRooms.values()) {
				total += fightRoom.getFightPlayers().size();
    		}
    	} else {
    		for (FightRoom fightRoom : this.fightRooms.values()) {
				total += fightRoom.getFightPlayers().size();
    		}
    	}
    	return total;
    }
    
    public void refreshServerStates(List<ServerState> serversState) {
    	for (ServerState serverState : serversState) {
    		if(ServerConfig.isDev) {
    			if(serverState.getServerId() != serverInfoData.getSelfServerId()) {
    				continue;
    			}
    		} 
			this.serverStates.put(serverState.getServerId(), serverState);
		}
    }

	public ConcurrentHashMap<Integer, ServerState> getServerStates() {
		return serverStates;
	}

	public DispatchServer borrowLoginDispatchServer() {
		DispatchServer dispatchServer = this.loginDispatchServer4SendMsg.poll();
		if(dispatchServer == null) {
			DispatchServer server = DispatcherClientInitor.initDispatcherClient(ac, DispatchServerType.inLogin);
			return server;
		} else {
			return dispatchServer;
		}
	}

	public void setLoginDispatchServer(DispatchServer loginDispatchServer) {
		this.loginDispatchServer4ReceiveMsg = loginDispatchServer;
	}
	
	public void addLoginDispatchServer4Send(DispatchServer dispatchServer) {
		this.loginDispatchServer4SendMsg.add(dispatchServer);
	}

	public DispatchServer borrowRoomDispatchServer() {
		DispatchServer dispatchServer = this.roomDispatchServer4SendMsg.poll();
		if(dispatchServer == null) {
			DispatchServer server = DispatcherClientInitor.initDispatcherClient(ac, DispatchServerType.inRoom);
			return server;
		} else {
			return dispatchServer;
		}
	}

	public void setRoomDispatchServer(DispatchServer roomDispatchServer) {
		this.roomDispatchServer4ReceiveMsg = roomDispatchServer;
	}
	
	public void addRoomDispatchServer4Send(DispatchServer dispatchServer) {
		this.roomDispatchServer4SendMsg.offer(dispatchServer);
	}

	public DispatchServer borrowHallDispatchServer() {
		DispatchServer dispatchServer = this.hallDispatchServer4SendMsg.poll();
		if(dispatchServer == null) {
			DispatchServer server = DispatcherClientInitor.initDispatcherClient(ac, DispatchServerType.inHall);
			return server;
		} else {
			return dispatchServer;
		}
	}

	public void setHallDispatchServer(DispatchServer hallDispatchServer) {
		this.hallDispatchServer4ReceiveMsg = hallDispatchServer;
	}
	
	public void addHallDispatchServer4Send(DispatchServer dispatchServer) {
		this.hallDispatchServer4SendMsg.offer(dispatchServer);
	}
	
	public RankingServerClient getRankingServerClient4ReceiveMsg() {
		return rankingServerClient4ReceiveMsg;
	}

	public void setRankingServerClient4ReceiveMsg(
			RankingServerClient rankingServerClient4ReceiveMsg) {
		this.rankingServerClient4ReceiveMsg = rankingServerClient4ReceiveMsg;
	}
	
	public void addRankingServerClient4Send(RankingServerClient rankingServerClient) {
		this.rankingServerClient4SendMsg.offer(rankingServerClient);
	}
	
	public RankingServerClient borrowRankingServerClient() {
		RankingServerClient rankingServerClient = this.rankingServerClient4SendMsg.poll();
		if(rankingServerClient == null) {
			rankingServerClient = RankingServerClientInitor.initRankingServerClient(ac);
			return rankingServerClient;
		} else {
			return rankingServerClient;
		}
	}

	public ServerState selectNotBusyServer(RoomType roomType) {
		ServerState result = null;
		int curBusyValue = 0;
		for (ServerState serverState : this.serverStates.values()) {
			ServerInfo serverInfo = serverInfoData.getServerInfo(serverState.getServerId());
			if(serverInfo.getServerType0()!=ServerType.all && serverInfo.getServerType0()!=ServerType.roomAndHallServer){
				continue;
			}
			int busyValue = 0;
			busyValue += serverState.getFightUserNum();
			busyValue += serverState.getFightRoomNum() * 25;
			busyValue += serverState.getHallUserNum();
			busyValue += serverState.getHallRoomNum() * 50;
			if(result == null) {
				result = serverState;
				curBusyValue = busyValue;
			} else {
				if(busyValue < curBusyValue) {
					result = serverState;
					curBusyValue = busyValue;
				}
			}
		}
		return result;
	}
	
	public void addFightRoom(FightRoom fightRoom) {
		this.fightRooms.put(fightRoom.getRoomId(), fightRoom);
	}
	
	public void addHallFightRoom(FightRoom fightRoom) {
		this.hallFightRooms.put(fightRoom.getRoomId(), fightRoom);
	}
	
	public ConcurrentHashMap<Long, FightRoom> getHallFightRooms() {
		return hallFightRooms;
	}

	public FightRoom getFightRoom(long roomId) {
		return this.fightRooms.get(roomId);
	}

	public FightRoom getHallFightRoom(long roomId) {
		return this.hallFightRooms.get(roomId);
	}
	
	public long getServerStartTime() {
		return serverStartTime;
	}

	public void setServerStartTime(long serverStartTime) {
		this.serverStartTime = serverStartTime;
	}

	public void removeFightRoom(FightRoom fightRoom) {
		if(fightRoom.getRoomType() == RoomType.hall) {
			this.hallFightRooms.remove(fightRoom.getRoomId());
		} else {
			this.fightRooms.remove(fightRoom.getRoomId());
		}
	}

	public ConcurrentHashMap<Long, FightRoom> getFightRooms() {
		return fightRooms;
	}

	public void addFightLogonPlayer(GamePlayer gamePlayer) {
		this.fightLogonPlayers.put(gamePlayer.getRoleId(), gamePlayer);
	}

	public GamePlayer findFightLogonPlayer(long roleId) {
		return this.fightLogonPlayers.get(roleId);
	}

	public void removeFightLogonPlayer(GamePlayer gamePlayer) {
		this.fightLogonPlayers.remove(gamePlayer.getRoleId());
	}

	public ConcurrentHashMap<Long, GamePlayer> getHallLogonPlayers() {
		return hallLogonPlayers;
	}

	public ConcurrentHashMap<Long, GamePlayer> getFightLogonPlayers() {
		return fightLogonPlayers;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.ac = applicationContext;
	}

	public void removeRankingServerClient(RankingServerClient client) {
		if(this.rankingServerClient4ReceiveMsg == client) {
			this.rankingServerClient4ReceiveMsg = null;
		} else if(this.rankingServerClient4SendMsg.contains(client)) {
			this.rankingServerClient4SendMsg.remove(client);
		}
	}
	
	public FrontVersion getCurVersion() {
		return curVersion;
	}

	public void setCurVersion(FrontVersion curVersion) {
		this.curVersion = curVersion;
	}
	
	public void refreshCurVersion(FrontVersion newVersion){
		if(curVersion ==null ){
			curVersion = newVersion;
		}else if(newVersion.getVersionNum1() > curVersion.getVersionNum1() ||
				(newVersion.getVersionNum1() == curVersion.getVersionNum1() && 
				newVersion.getVersionNum2() > curVersion.getVersionNum2())){
			curVersion = newVersion;
		}
	}
}

