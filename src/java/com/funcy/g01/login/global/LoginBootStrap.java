package com.funcy.g01.login.global;

import org.springframework.context.ApplicationContext;

import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.net.DispatchClientType;
import com.funcy.g01.base.net.DispatchServer;
import com.funcy.g01.base.net.DispatchServerType;
import com.funcy.g01.base.net.DispatcherClientInitor;
import com.funcy.g01.base.proto.service.ReqCmdProto.StrIntProto;

public class LoginBootStrap {

	public static void startServer(ApplicationContext ac) {
		DispatchServerType dispatchServerType = DispatchServerType.inLogin;
		DispatchServer server = DispatcherClientInitor.initDispatcherClient(ac, dispatchServerType);
		if(server == null) {
			while(server == null) {
				server = DispatcherClientInitor.initDispatcherClient(ac, dispatchServerType);
			}
		} 
		ServerContext serverContext = ac.getBean(ServerContext.class);
		serverContext.setLoginDispatchServer(server);
		ServerInfoData serverInfoData = ac.getBean(ServerInfoData.class);
		server.send("dispatchLoginService.login", StrIntProto.newBuilder().setParams1(DispatchClientType.login.toString()).setParams2(serverInfoData.getSelfServerId()));
	
		for(int i = 0; i < 3; i++) {
			DispatchServer server4Send = DispatcherClientInitor.initDispatcherClient(ac, dispatchServerType);
			serverContext.addLoginDispatchServer4Send(server4Send);
		}
	}
	
}
