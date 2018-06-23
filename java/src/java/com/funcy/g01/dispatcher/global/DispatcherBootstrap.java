package com.funcy.g01.dispatcher.global;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.springframework.context.ApplicationContext;

import com.funcy.g01.base.bo.serverconfig.ServerInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.base.global.BusinessPool;
import com.funcy.g01.base.task.RefreshServersStateTask;
import com.funcy.g01.dispatcher.bo.AllRoomManager;
import com.funcy.g01.dispatcher.net.DispatcherMsgHandler;
import com.funcy.g01.dispatcher.net.codec.DispatchServerPipelineFactory;
import com.funcy.g01.dispatcher.task.CheckTimeoutDispatcherRoomTask;

public class DispatcherBootstrap {

	private static final Logger logger = Logger.getLogger(DispatcherBootstrap.class);
	
	public static final int BUSSNESS_POOL_MAX_THREAD_NUM = 20; //TODO
	
	public static void startServer(ApplicationContext ac) {
		RefreshServersStateTask refreshServersStateTask = ac.getBean(RefreshServersStateTask.class);
		BusinessPool businessPool = ac.getBean(BusinessPool.class);
		if(!refreshServersStateTask.isHadStart()) {
			refreshServersStateTask.setHadStart(true);
			businessPool.scheduleAtFixedRate(refreshServersStateTask, 0, 10, TimeUnit.SECONDS);
		}
		CheckTimeoutDispatcherRoomTask checkTimeoutDispatcherRoomTask = ac.getBean(CheckTimeoutDispatcherRoomTask.class);
		businessPool.scheduleAtFixedRate(checkTimeoutDispatcherRoomTask, 0, 1, TimeUnit.MINUTES);
		AllRoomManager allRoomManager = ac.getBean(AllRoomManager.class);
		allRoomManager.init();
        startSocketServer(ac);
	}
	
	private static void startSocketServer(ApplicationContext ac) {
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		DispatcherMsgHandler dispatcherMsgHandler = ac.getBean(DispatcherMsgHandler.class);
        DispatchServerPipelineFactory pipelineFactory = new DispatchServerPipelineFactory(dispatcherMsgHandler);
//		SynRoomThreadPool synRoomThreadPool = new SynRoomThreadPool(1, 3, BUSSNESS_POOL_MAX_THREAD_NUM, 10000, "synRoomThreadPool");
//        dispatcherMsgHandler.setSynRoomThreadPool(synRoomThreadPool);
        bootstrap.setPipelineFactory(pipelineFactory);
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.receiveBufferSize", 1048576);
        //bootstrap.setOption("receiveBufferSizePredictorFactory", new FixedReceiveBufferSizePredictorFactory(300 * 1024));
        ServerInfoData serverInfoData = ac.getBean(ServerInfoData.class);
        ServerInfo serverInfo = serverInfoData.getSelfServerInfo();
        InetSocketAddress address = new InetSocketAddress("0.0.0.0", serverInfo.getDispatcherServerPort());
        bootstrap.bind(address);
        logger.info("dispatcher server start..."+address.toString());
	}
	
}
