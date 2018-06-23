package com.funcy.g01.base.net;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.springframework.context.ApplicationContext;
import com.funcy.g01.base.bo.serverconfig.ServerInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.net.codec.DispatchClientPipelineFactory;

public class RankingServerClientInitor {
	private final static Logger logger = Logger.getLogger(RankingServerClientInitor.class);

	public static RankingServerClient initRankingServerClient(ApplicationContext ac) {
		ClientBootstrap clientBootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		RankingServerClientMsgHandler msgHandler = ac.getBean(RankingServerClientMsgHandler.class);
		DispatchClientPipelineFactory pipelineFactory = new DispatchClientPipelineFactory(msgHandler);
		clientBootstrap.setPipelineFactory(pipelineFactory);
		clientBootstrap.setOption("child.tcpNoDelay", true);
		clientBootstrap.setOption("child.receiveBufferSize", 1048576);
		ServerInfoData serverInfoData = ac.getBean(ServerInfoData.class);
		ServerInfo serverInfo = serverInfoData.findDispatchServer();
		InetSocketAddress address = null;
		if(ServerConfig.isDev) {
			address = new InetSocketAddress(ServerConfig.devIp, serverInfo.getRankingServerPort());
		} else {
			address = new InetSocketAddress(serverInfo.getIp(), serverInfo.getRankingServerPort());
		}
		ChannelFuture channelFuture = clientBootstrap.connect(address);
		try {
			channelFuture.await(3, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		}
		if(channelFuture.isSuccess()) {
			Channel channel = channelFuture.getChannel();
			logger.info("connect ranking server success... "+address.toString());
			RankingServerClient rankingServerClient = new RankingServerClient(channel.getPipeline().getContext(msgHandler));
			channel.getPipeline().getContext(msgHandler).setAttachment(rankingServerClient);
			return rankingServerClient;
		} else {
			return null;
		}
	}
}
