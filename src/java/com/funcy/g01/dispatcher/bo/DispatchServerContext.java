package com.funcy.g01.dispatcher.bo;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.net.DispatchClientType;

@Component
public class DispatchServerContext {
	
	public static final Logger logger = Logger.getLogger(DispatchServerContext.class);

	private ConcurrentHashMap<Integer, CopyOnWriteArrayList<DispatchClient>> clientMap = new ConcurrentHashMap<Integer, CopyOnWriteArrayList<DispatchClient>>();
	
	public boolean addDispatchClient(DispatchClient client) {
		CopyOnWriteArrayList<DispatchClient> clients = this.clientMap.get(client.getServerId());
//		logger.info("add dispatch client,serverId:" + client.getServerId() + ",type:" + client.getType());
		if(clients == null) {
			clients = new CopyOnWriteArrayList<DispatchClient>();
			this.clientMap.put(client.getServerId(), clients);
			clients.add(client);
			return true;
		}
		clients.add(client);
		return false;
	}

	public DispatchClient borrowClient(int serverId, DispatchClientType dispatchClientType) {
		List<DispatchClient> clients = this.clientMap.get(serverId);
		if(clients == null || clients.size() == 0) {
			return null;
		}
		Iterator<DispatchClient> it = clients.iterator();
		while(it.hasNext()) {
			DispatchClient dispatchClient = it.next();
			if(dispatchClient.getType() == dispatchClientType) {
				clients.remove(dispatchClient);
//				logger.info("borrow client,serverId:" + serverId + ",type:" + dispatchClientType);
				return dispatchClient;
			}
		}
		return null;
	}
	
}
