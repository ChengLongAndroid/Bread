package com.funcy.g01.base.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.fight.FightRoomType;
import com.funcy.g01.base.bo.serverconfig.ServerInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.base.bo.serverconfig.ServerState;
import com.funcy.g01.base.dao.sql.ServerDao;
import com.funcy.g01.base.global.ServerContext;

@Component
public class UpdateServerStateTask implements Runnable {
	
	@Autowired
	private ServerInfoData serverInfoData;
	
	@Autowired
	private ServerContext serverContext;
	
	@Autowired
	private ServerDao serverDao;
	
	@Override
	public void run() {
		try {
			ServerInfo serverInfo = serverInfoData.getSelfServerInfo();
			ServerState serverState = new ServerState(serverInfo.getId());
			serverState.setFightRoomNum(serverContext.getFightRoomNum(false));
			serverState.setFightUserNum(serverContext.getFightRoomPlayerNum(false));
			serverState.setHallRoomNum(serverContext.getFightRoomNum(true));
			serverState.setHallUserNum(serverContext.getFightRoomPlayerNum(true));
			serverDao.updateServerState(serverState);
		} catch(Exception e)  {
			e.printStackTrace();
		}
	}

}
