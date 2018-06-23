package com.funcy.g01.base.bo.serverconfig;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.dao.sql.ServerDao;
import com.google.common.annotations.Beta;

@Component
public class ServerInfoData {
	
	private static final Logger logger = Logger.getLogger(ServerInfoData.class);

	private volatile Map<Integer, ServerInfo> serverInfoMap = new HashMap<Integer, ServerInfo>();
	
	@Autowired
    private ServerDao serverConfigDao;
    
    private int selfServerId;
    
    private String serverName;
    
    private static ServerInfoData instance;
    
    public static final Random random = new Random();
    
    private ServerInfoData() {
    	instance = this;
	}
    
	public static ServerInfoData getInstance() {
		return instance;
	}

	@PostConstruct
    public void init() {
        reInit();
        loadServerIpAndPort();
        logger.info("init ServerInfoData success");
    }

	private void loadServerIpAndPort() {
		try {
            boolean hadInit = false;
            Enumeration<NetworkInterface> localInterfaces = NetworkInterface.getNetworkInterfaces();   
            a1:while (localInterfaces.hasMoreElements()) {   
                NetworkInterface ni = localInterfaces.nextElement();   
                Enumeration<InetAddress> ipAddresses = ni.getInetAddresses();   
                while (ipAddresses.hasMoreElements()) {   
                    InetAddress adderss = ipAddresses.nextElement();
                    String ip = adderss.getHostAddress();
                    for(ServerInfo serverInfo : this.serverInfoMap.values()) {
                        if(serverInfo.getIp().equals(ip)) {
                            hadInit = true;
                            this.selfServerId = serverInfo.getServerId();
                            this.serverName = serverInfo.getName();
                            break a1;
                        }
                    }
                }
            }
            if(!hadInit) {
                new RuntimeException("find areaId fail!").printStackTrace();
                System.exit(-1);
            }
        } catch (Exception e) {
            new RuntimeException("find areaId fail!").printStackTrace();
            System.exit(-1);
        }
	}
    
    public ServerInfo getSelfServerInfo() {
        return this.serverInfoMap.get(this.selfServerId);
    }
    
    public int getSelfServerId(){
    	return this.selfServerId;
    }
    
    public String getSelfServerName(){
    	return this.serverName;
    }
    
	public void reInit() {
        for(ServerInfo serverInfo : serverConfigDao.getAllServerInfo()) {
            serverInfoMap.put(serverInfo.getServerId(), serverInfo);
        }
    }
	
	public ServerInfo getServerInfo(int serverId) {
		return this.serverInfoMap.get(serverId);
	}
	
	public List<ServerInfo> findAllServerInfo(){
		List<ServerInfo> list = new ArrayList<ServerInfo>();
		for(ServerInfo serverInfo : serverInfoMap.values()){
			list.add(serverInfo);
		}
		return list;
	}
	
	public ServerInfo findDispatchServer() {
		for(ServerInfo serverInfo : serverInfoMap.values()) {
			if(serverInfo.getServerType0() == ServerType.dispatch || serverInfo.getServerType0() == ServerType.all) {
				return serverInfo;
			}
		}
		return null;
	}
	
	public ServerInfo findServerInfoByIp(String ip) {
		for(ServerInfo serverInfo : serverInfoMap.values())
			if(serverInfo.getIp().equals(ip)) return serverInfo;
		return null;
	}
	
}
