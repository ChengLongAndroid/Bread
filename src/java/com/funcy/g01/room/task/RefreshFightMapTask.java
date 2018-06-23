package com.funcy.g01.room.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.map.RoleMapInfo;
import com.funcy.g01.base.bo.map.ServerMapInfo;
import com.funcy.g01.base.dao.redis.FightRepo;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.global.ServerContext;

@Component
public class RefreshFightMapTask implements Runnable {
	
	private Logger logger = Logger.getLogger(RefreshFightMapTask.class);
	
	@Autowired
	private ServerContext serverContext;
	
	@Autowired
	private FightRepo fightRepo;

	@Override
	public void run() {
		RoleMapInfo roleMapInfo = null;
		if(ServerConfig.isDev) {
			roleMapInfo = fightRepo.getRoleMapInfo(ServerConfig.test_system_map_role_id);
		} else {
			roleMapInfo = fightRepo.getRoleMapInfo(ServerConfig.system_map_role_id);
		}
		Map<Integer, List<ServerMapInfo>> starMaps = new HashMap<Integer, List<ServerMapInfo>>();
		for(long mapId : roleMapInfo.getMapIds()) {
			ServerMapInfo serverMapInfo = fightRepo.getServerMapInfo(mapId);
			int star = serverMapInfo.getMapInfo().getSceneSettingInfo().getStar();
			if(star == 0) {
				star = 2;
			}
			if(star < 1 || star > 5) {
				logger.info(String.format("refresh map info find error star map:roleId=%d,star=%d,mapId=%d", serverMapInfo.getCreateRoleId(), star, serverMapInfo.getMapId()));
				continue;
			}
			List<ServerMapInfo> list = starMaps.get(star);
			if(list == null) {
				list = new ArrayList<ServerMapInfo>();
				starMaps.put(star, list);
			}
			list.add(serverMapInfo);
		}
		for (Entry<Integer, List<ServerMapInfo>> entry : starMaps.entrySet()) {
			int star = entry.getKey();
			List<ServerMapInfo> value = entry.getValue();
			serverContext.getServerMapInfos().put(star, value);
			logger.info(String.format("refresh map info, star %d map num: %d", star, value.size()));
		}
	}

}
