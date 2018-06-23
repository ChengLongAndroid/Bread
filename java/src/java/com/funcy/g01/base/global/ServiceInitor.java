package com.funcy.g01.base.global;

import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ebo.synframework.nettybase.codec.ProtoFactory;
import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.bo.serverconfig.ServerInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.base.bo.serverconfig.ServerType;
import com.funcy.g01.base.data.StaticDataInitor;
import com.funcy.g01.base.task.EmailSender;
import com.funcy.g01.base.task.PrintRoomStateTask;
import com.funcy.g01.base.task.RefreshFrontVersionTask;
import com.funcy.g01.base.task.RefreshServerInfoTask;
import com.funcy.g01.base.task.UpdateServerStateTask;
import com.funcy.g01.dispatcher.global.DispatcherBootstrap;
import com.funcy.g01.hall.global.HallBootStrap;
import com.funcy.g01.hall.service.MapService;
import com.funcy.g01.login.global.LoginBootStrap;
import com.funcy.g01.ranking.global.RankingBootstrap;
import com.funcy.g01.room.global.RoomBootstrap;
import com.funcy.g01.upload.global.UploadBootstrap;

@SuppressWarnings("serial")
public class ServiceInitor extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(ServiceInitor.class);
	
	@Override
	public void init() throws ServletException {
		super.init();
		ProtoFactory.init("com.funcy.g01.base.proto");
        ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        ServerInfoData serverInfoData = ac.getBean(ServerInfoData.class);
        ServerInfo serverInfo = serverInfoData.getSelfServerInfo();
        StaticDataInitor staticDataInitor = ac.getBean(StaticDataInitor.class);
		staticDataInitor.init();
		ServerContext serverContext = ac.getBean(ServerContext.class);
		serverContext.setServerStartTime(System.currentTimeMillis());
		MapService serMapService = ac.getBean(MapService.class);
		serMapService.checkLoadMap(ServerConfig.system_map_role_id);
		serMapService.checkLoadMap(ServerConfig.edit_main_city_map_rold_id);
		PlatformConfig platformConfig = ac.getBean(PlatformConfig.class);
		logger.info("platformType is " + platformConfig.getPlatformType());
		if("envdevelop".equals(platformConfig.getPlatformType())) {
			ServerConfig.isDev = true;
		} else {
			ServerConfig.isDev = false;
		}
        BusinessPool businessPool = ac.getBean(BusinessPool.class);
        businessPool.init();
        //刷新服务器状态
        UpdateServerStateTask refreshServerStateTask = ac.getBean(UpdateServerStateTask.class);
        int refreshPeriod = 5;
        businessPool.scheduleAtFixedRate(refreshServerStateTask, 0, refreshPeriod, TimeUnit.SECONDS);
        RefreshServerInfoTask refreshServerInfoTask = ac.getBean(RefreshServerInfoTask.class);
        businessPool.scheduleAtFixedRate(refreshServerInfoTask, 0, refreshPeriod, TimeUnit.SECONDS);
        
        //刷新前端版本
        RefreshFrontVersionTask refreshFrontVersionTask = ac.getBean(RefreshFrontVersionTask.class);
        businessPool.scheduleAtFixedRate(refreshFrontVersionTask, 0, RefreshFrontVersionTask.UPDATE_TIME_SECONDS, TimeUnit.SECONDS);
        
        EmailSender emailSender = ac.getBean(EmailSender.class);
		emailSender.init();
		
        if(serverInfo.getServerType0() == ServerType.all) {
        	RankingBootstrap.startServer(ac);
        	DispatcherBootstrap.startServer(ac);
        	HallBootStrap.startServer(ac);
        	RoomBootstrap.startServer(ac);
        	LoginBootStrap.startServer(ac);
        	UploadBootstrap.startServer(ac);
        }else if(serverInfo.getServerType0() == ServerType.roomAndHallServer){
        	RoomBootstrap.startServer(ac);
        	HallBootStrap.startServer(ac);
        } else if(serverInfo.getServerType0() == ServerType.dispatch){
        	RankingBootstrap.startServer(ac);
        	DispatcherBootstrap.startServer(ac);
        	LoginBootStrap.startServer(ac);
        	UploadBootstrap.startServer(ac);
        }
        
        PrintRoomStateTask printRoomStateTask = ac.getBean(PrintRoomStateTask.class);
        businessPool.scheduleAtFixedRate(printRoomStateTask, 0, 1, TimeUnit.MINUTES);
	}
	

	
}
