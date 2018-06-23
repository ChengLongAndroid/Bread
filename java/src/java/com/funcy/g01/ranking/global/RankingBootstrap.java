package com.funcy.g01.ranking.global;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.funcy.g01.base.bo.serverconfig.ServerInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.ranking.bo.RankingInfoManager;
import com.funcy.g01.ranking.net.RankingMsgHandler;
import com.funcy.g01.ranking.net.codec.RankingServerPipelineFactory;
import com.funcy.g01.ranking.task.RankingTask;
import com.funcy.g01.ranking.task.RefreshRoleRankingInfoTask;

public class RankingBootstrap {
	
	private static final Logger logger = Logger.getLogger(RankingBootstrap.class);
	
	public static void startServer(ApplicationContext ac) {
        startSocketServer(ac);
        RankingInfoManager rankingInfoManager = ac.getBean(RankingInfoManager.class);
        rankingInfoManager.init();
        scheduleTask(ac);
	}

	public static void scheduleTask(ApplicationContext ac) {
		SchedulerFactoryBean factoryBean = ac.getBean(SchedulerFactoryBean.class);
        Scheduler scheduler = factoryBean.getScheduler();
        Lock lock = new ReentrantLock();
        RankingTask rankingTask = ac.getBean(RankingTask.class);
        rankingTask.setLock(lock);
        RefreshRoleRankingInfoTask refreshRoleRankingInfoTask = ac.getBean(RefreshRoleRankingInfoTask.class);
        refreshRoleRankingInfoTask.setLock(lock);
        CronTriggerBean refreshRoleRankingInfoJob = (CronTriggerBean)ac.getBean("refreshRoleRankingInfoTrigger");
        CronTriggerBean periodRankingJob = (CronTriggerBean)ac.getBean("periodRankingTrigger");
        CronTriggerBean monthRankingJob = (CronTriggerBean)ac.getBean("monthRankingTrigger");
        CronTriggerBean weekRankingJob = (CronTriggerBean)ac.getBean("weekRankingTrigger");
        try {
        	scheduler.addJob(refreshRoleRankingInfoJob.getJobDetail(), true);
        	scheduler.scheduleJob(refreshRoleRankingInfoJob);
        	scheduler.addJob(periodRankingJob.getJobDetail(), true);
        	scheduler.scheduleJob(periodRankingJob);
        	scheduler.addJob(monthRankingJob.getJobDetail(), true);
			scheduler.scheduleJob(monthRankingJob);
        	scheduler.addJob(weekRankingJob.getJobDetail(), true);
			scheduler.scheduleJob(weekRankingJob);
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void startSocketServer(ApplicationContext ac) {
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		RankingMsgHandler rankingMsgHandler = ac.getBean(RankingMsgHandler.class);
        RankingServerPipelineFactory pipelineFactory = new RankingServerPipelineFactory(rankingMsgHandler);
        bootstrap.setPipelineFactory(pipelineFactory);
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.receiveBufferSize", 1048576);
        ServerInfoData serverInfoData = ac.getBean(ServerInfoData.class);
        ServerInfo serverInfo = serverInfoData.getSelfServerInfo();
        InetSocketAddress address = new InetSocketAddress("0.0.0.0", serverInfo.getRankingServerPort()); 
        bootstrap.bind(address);
        logger.info("dispatcher server start..."+address.toString());
	}
	
}

