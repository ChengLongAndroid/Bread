package com.funcy.g01.base.dao.sql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.funcy.g01.base.bo.serverconfig.ServerInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfoExample;
import com.funcy.g01.base.bo.serverconfig.ServerState;
import com.funcy.g01.base.bo.serverconfig.ServerStateExample;
import com.funcy.g01.base.dao.sql.mapper.ServerInfoMapper;
import com.funcy.g01.base.dao.sql.mapper.ServerStateMapper;

@Repository
public class ServerDao {

	@Autowired
    private ServerInfoMapper serverInfoMapper;
	
	@Autowired
    private ServerStateMapper serverStateMapper;
	
    public List<ServerInfo> getAllServerInfo() {
        return serverInfoMapper.selectByExample(new ServerInfoExample());
    }
    
    public List<ServerState> getAllServerState() {
    	return serverStateMapper.selectByExample(new ServerStateExample());
    }
    
    public void updateServerState(ServerState serverState) {
    	ServerStateExample example = new ServerStateExample();
    	example.createCriteria().andServerIdEqualTo(serverState.getServerId());
    	if(this.serverStateMapper.updateByExampleSelective(serverState, example) == 0) {
    		this.serverStateMapper.insertSelective(serverState);
    	} else {
    		return;
    	}
    }
    
}
