package com.funcy.g01.base.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.serverconfig.ServerInfoData;

@Component
public class RefreshServerInfoTask implements Runnable {

	@Autowired
	private ServerInfoData serverInfoData;
	
	@Override
	public void run() {
		serverInfoData.reInit();
	}

}
