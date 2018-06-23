package com.funcy.g01.base.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.fight.FightPlayer;
import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.net.GamePlayer;

@Component
public class CheckAndForceFightPlayerOfflineTask implements Runnable {

	@Autowired
	private ServerContext serverContext;
	
	public static final Logger logger = Logger.getLogger(CheckAndForceFightPlayerOfflineTask.class);
	
	@Override
	public void run() {
		long curTime = System.currentTimeMillis();
		for(GamePlayer gamePlayer : serverContext.getFightLogonPlayers().values()) {
			if(gamePlayer.isCutoutTimeout(curTime)) {
				gamePlayer.destroy();
				logger.info("player(" + gamePlayer.getRoleId() +") cut out timeout");
				continue;
			}
			if(gamePlayer.isReceiveMsgTimeout(curTime)) {
				FightPlayer fightPlayer = gamePlayer.getFightPlayer();
				if(fightPlayer != null) {
					if(!fightPlayer.isDead()) {
						gamePlayer.destroy();
					}
				} else {
					gamePlayer.destroy();
				}
				logger.info("player(" + gamePlayer.getRoleId() +") receive msg timeout");
			} 
		}
	}
	
	

}
