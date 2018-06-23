package com.funcy.g01.hall.global;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
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
import com.funcy.g01.base.net.RankingServerClient;
import com.funcy.g01.base.net.RankingServerClientInitor;
import com.funcy.g01.base.net.WebSocketGameServerPipelineFactory;
import com.funcy.g01.base.proto.service.ReqCmdProto.StrIntProto;
import com.funcy.g01.base.task.CheckAndForceHallPlayerOfflineTask;
import com.funcy.g01.hall.net.LogicMsgHandler;

public class HallBootStrap {
	
    private static final Logger logger = Logger.getLogger("bootStrap");
    
    public static final int BUSSNESS_POOL_MAX_THREAD_NUM = 20;
    
    public static final int MAX_LOGIN_NUM = 20000;
    

	public static void startServer(ApplicationContext ac) {
		startSocketServer(ac, false);
		if(ServerConfig.isOpenH5) {
			startSocketServer(ac, true);
		}
		initDispatcherServerConnect(ac);
		initRankingServerConnect(ac);
		BusinessPool businessPool = ac.getBean(BusinessPool.class);
        CheckAndForceHallPlayerOfflineTask checkAndForceHallPlayerOfflineTask = ac.getBean(CheckAndForceHallPlayerOfflineTask.class);
        businessPool.scheduleAtFixedRate(checkAndForceHallPlayerOfflineTask, 0, 10, TimeUnit.SECONDS);
	}

	private static void initDispatcherServerConnect(ApplicationContext ac) {
		DispatchServerType dispatchServerType = DispatchServerType.inHall;
		DispatchServer server = DispatcherClientInitor.initDispatcherClient(ac, dispatchServerType);
		if(server == null) {
			while(server == null) {
				server = DispatcherClientInitor.initDispatcherClient(ac, dispatchServerType);
			}
		} 
		ServerContext serverContext = ac.getBean(ServerContext.class);
		serverContext.setHallDispatchServer(server);
		ServerInfoData serverInfoData = ac.getBean(ServerInfoData.class);
		server.send("dispatchLoginService.login", StrIntProto.newBuilder().setParams1(DispatchClientType.hall.toString()).setParams2(serverInfoData.getSelfServerId()));
	
		for(int i = 0; i < 4; i++) {
			DispatchServer server4Send = DispatcherClientInitor.initDispatcherClient(ac, dispatchServerType);
			serverContext.addHallDispatchServer4Send(server4Send);
		}
	}
	
	private static void initRankingServerConnect(ApplicationContext ac) {
		RankingServerClient rankingServerClient = RankingServerClientInitor.initRankingServerClient(ac);
		ServerContext serverContext = ac.getBean(ServerContext.class);
		serverContext.setRankingServerClient4ReceiveMsg(rankingServerClient);
		for(int i = 0; i < 3; i++) {
			RankingServerClient client = RankingServerClientInitor.initRankingServerClient(ac);
			serverContext.addRankingServerClient4Send(client);
		}
	}

	private static void startSocketServer(ApplicationContext ac, boolean isWebSocket) {
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		SynRoomThreadPool synRoomThreadPool = new SynRoomThreadPool(5, 10, BUSSNESS_POOL_MAX_THREAD_NUM, 10000, "synRoomThreadPool");
        LogicMsgHandler gameMsgHandler = ac.getBean(LogicMsgHandler.class);
        gameMsgHandler.setApplicationContext(ac);
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
        int port = serverInfo.getHallServerPort();
        if(isWebSocket) {
        	port = serverInfo.getH5HallServerPort();
        }
        InetSocketAddress address = new InetSocketAddress("0.0.0.0", port);

        bootstrap.bind(address);
        logger.info("hall server start... "+address.toString());
	}	
}
