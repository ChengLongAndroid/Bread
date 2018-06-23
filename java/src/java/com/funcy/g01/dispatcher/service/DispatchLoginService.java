package com.funcy.g01.dispatcher.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funcy.g01.base.global.BusinessPool;
import com.funcy.g01.base.net.DispatchClientType;
import com.funcy.g01.dispatcher.bo.AllRoomManager;
import com.funcy.g01.dispatcher.bo.DispatchClient;
import com.funcy.g01.dispatcher.bo.DispatchServerContext;
import com.funcy.g01.dispatcher.task.RefreshServerDispatchRoomStateTask;

@Service
public class DispatchLoginService {

	@Autowired
	private DispatchServerContext dispatchServerContext;
	
	@Autowired
	private BusinessPool businessPool;
	
	@Autowired
	private AllRoomManager allRoomManager;
	
	public void login(String type, int serverId, DispatchClient dispatchClient) {
		DispatchClientType dispatchClientType = DispatchClientType.valueOf(type);
		dispatchClient.setType(dispatchClientType);
		dispatchClient.setServerId(serverId);
		boolean isFirst = dispatchServerContext.addDispatchClient(dispatchClient);
		if(isFirst) {
			if(dispatchClientType == DispatchClientType.hall || dispatchClientType == DispatchClientType.room) {
				RefreshServerDispatchRoomStateTask refreshServerDispatchRoomStateTask = new RefreshServerDispatchRoomStateTask(allRoomManager, serverId, dispatchServerContext, DispatchClientType.room);
				businessPool.scheduleAtFixedRate(refreshServerDispatchRoomStateTask, 20, 1, TimeUnit.SECONDS);
				
				RefreshServerDispatchRoomStateTask refreshServerDispatchRoomStateTask2 = new RefreshServerDispatchRoomStateTask(allRoomManager, serverId, dispatchServerContext, DispatchClientType.hall);
				businessPool.scheduleAtFixedRate(refreshServerDispatchRoomStateTask2, 20, 1, TimeUnit.SECONDS);
			}
		}
	}
	
}
