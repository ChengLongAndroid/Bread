package com.funcy.g01.base.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.serverconfig.ServerState;
import com.funcy.g01.base.dao.sql.ServerDao;
import com.funcy.g01.base.global.ServerContext;

@Component
public class RefreshServersStateTask implements Runnable {

	@Autowired
	private ServerDao serverDao;
	
	@Autowired
	private ServerContext serverContext;
	
	private boolean hadStart = false;

	@Override
	public void run() {
		List<ServerState> serverStates = serverDao.getAllServerState();
		serverContext.refreshServerStates(serverStates);
	}

	public boolean isHadStart() {
		return hadStart;
	}

	public void setHadStart(boolean hadStart) {
		this.hadStart = hadStart;
	}
}
