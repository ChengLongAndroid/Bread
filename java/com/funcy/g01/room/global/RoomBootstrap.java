package com.funcy.g01.room.global;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jbox2d.common.Settings;
import org.springframework.context.ApplicationContext;

import com.ebo.synframework.synroom.executor.pool.SynRoomThreadPool;
import com.funcy.g01.base.bo.serverconfig.ServerInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.base.global.BusinessPool;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.net.DispatchClientType;
import com.funcy.g01.base.net.DispatchServer;
import com.funcy.g01.base.net.DispatchServerType;
import com.funcy.g01.base.net.DispatcherClientInitor;
import com.funcy.g01.base.net.GameServerPipelineFactory;
import com.funcy.g01.base.net.WebSocketGameServerPipelineFactory;
import com.funcy.g01.base.proto.service.ReqCmdProto.StrIntProto;
import com.funcy.g01.base.task.CheckAndForceFightPlayerOfflineTask;
import com.funcy.g01.room.net.RoomMsgHandler;
import com.funcy.g01.room.task.RefreshFightMapTask;

public class RoomBootstrap {

	private static final Logger logger = Logger.getLogger(RoomBootstrap.class);
	
	public static final int BUSSNESS_POOL_MAX_THREAD_NUM = 20; //TODO
	
	public static void startServer(ApplicationContext ac) {
		Settings.maxTranslation = 20.0f;
        startSocketServer(ac, false);
        if(ServerConfig.isOpenH5) {
        	startSocketServer(ac, true);
        }
        initDispatcherServerConnect(ac);
        
        BusinessPool businessPool = ac.getBean(BusinessPool.class);
        CheckAndForceFightPlayerOfflineTask checkAndForceFightPlayerOfflineTask = ac.getBean(CheckAndForceFightPlayerOfflineTask.class);
        businessPool.scheduleAtFixedRate(checkAndForceFightPlayerOfflineTask, 0, 10, TimeUnit.SECONDS);
        RefreshFightMapTask refreshFightMapTask = ac.getBean(RefreshFightMapTask.class);
        businessPool.scheduleAtFixedRate(refreshFightMapTask, 0, 30, TimeUnit.MINUTES);
	}
	
	private static void initDispatcherServerConnect(ApplicationContext ac) {
		DispatchServerType dispatchServerType = DispatchServerType.inRoom;
		DispatchServer server = DispatcherClientInitor.initDispatcherClient(ac, dispatchServerType);
		if(server == null) {
			while(server == null) {
				server = DispatcherClientInitor.initDispatcherClient(ac, dispatchServerType);
			}
		} 
		ServerContext serverContext = ac.getBean(ServerContext.class);
		serverContext.setRoomDispatchServer(server);
		ServerInfoData serverInfoData = ac.getBean(ServerInfoData.class);
		server.send("dispatchLoginService.login", StrIntProto.newBuilder().setParams1(DispatchClientType.room.toString()).setParams2(serverInfoData.getSelfServerId()));
	
		for(int i = 0; i < 4; i++) {
			DispatchServer server4Send = DispatcherClientInitor.initDispatcherClient(ac, dispatchServerType);
			serverContext.addRoomDispatchServer4Send(server4Send);
		}
	}
	
	private static void startSocketServer(ApplicationContext ac, boolean isWebSocket) {
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        RoomMsgHandler gameMsgHandler = ac.getBean(RoomMsgHandler.class);
        SynRoomThreadPool synRoomThreadPool = new SynRoomThreadPool(1, 3, BUSSNESS_POOL_MAX_THREAD_NUM, 10000, "synRoomThreadPool");
        gameMsgHandler.setSynRoomThreadPool(synRoomThreadPool);
        
        if(isWebSocket) {
        	WebSocketGameServerPipelineFactory pipelineFactory = new WebSocketGameServerPipelineFactory(gameMsgHandler);
        	bootstrap.setPipelineFactory(pipelineFactory);
        } else {
        	GameServerPipelineFactory pipelineFactory = new GameServerPipelineFactory(gameMsgHandler);
            bootstrap.setPipelineFactory(pipelineFactory);
        }
        
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.receiveBufferSize", 1048576);
        //bootstrap.setOption("receiveBufferSizePredictorFactory", new FixedReceiveBufferSizePredictorFactory(300 * 1024));
        ServerInfoData serverInfoData = ac.getBean(ServerInfoData.class);
        ServerInfo serverInfo = serverInfoData.getSelfServerInfo();
        int port = serverInfo.getRoomServerPort();
        if(isWebSocket) {
        	port = serverInfo.getH5RoomServerPort();
        }
        InetSocketAddress address = new InetSocketAddress("0.0.0.0", port);
        bootstrap.bind(address);
        logger.info("room server start..."+address.toString());
	}
	
}
