package com.funcy.g01.base.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.net.GamePlayer;

@Component
public class CheckAndForceHallPlayerOfflineTask implements Runnable {

	@Autowired
	private ServerContext serverContext;
	
	public static final Logger logger = Logger.getLogger(CheckAndForceHallPlayerOfflineTask.class);
	
	@Override
	public void run() {
		long curTime = System.currentTimeMillis();
		for(GamePlayer gamePlayer : serverContext.getHallLogonPlayers().values()) {
			if(gamePlayer.isReceiveMsgTimeout(curTime)) {
				gamePlayer.destroy();
				logger.info("player(" + gamePlayer.getRoleId() +") receive msg timeout");
			} else if(gamePlayer.isCutoutTimeout(curTime)) {
				gamePlayer.destroy();
				logger.info("player(" + gamePlayer.getRoleId() +") cut out timeout");
			}
		}
	}
	
	

}
