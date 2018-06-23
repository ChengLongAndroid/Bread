package com.funcy.g01.base.performance;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.util.ServerStateCode;

@Component("performanceHandler")
public class PerformanceHandler {
	
	@Autowired
	private ServerContext serverContext;
    
    private ConcurrentHashMap<String, EventInfo> performanceInfo = new ConcurrentHashMap<String, EventInfo>();

    private Map<String, String> nameValueMap = new HashMap<String, String>();
    
    private PerformanceHandler() {
    }
    
    public ConcurrentHashMap<String, EventInfo> getPerformanceInfo() {
        return performanceInfo;
    }

    public Map<String, String> getNameValueMap() {
        return nameValueMap;
    }

    public void handleEvent(long startTime, long endTime, String event, boolean isHadException) {
        long wallTime = endTime - startTime;
        if (!performanceInfo.containsKey(event)) {
        	performanceInfo.put(event, new EventInfo(event));
        }
        EventInfo eventInfo = performanceInfo.get(event);
        if(eventInfo == null) {
        	eventInfo = new EventInfo(event);
        }
        eventInfo.handleCall(isHadException, wallTime);
    }
    
    public void reset(){
    	this.performanceInfo.clear();
    }
    
    public ServerStateCode getBattleServerStateCode() {
    	int totalAveExceptionNum = 0;
    	int totalMinWallTime = 0;
    	int totalMinReqNum = 0;
    	
    	//req
    	for (EventInfo eventInfo : this.performanceInfo.values()) {
    		if(eventInfo.type == 2) {
    			continue;
    		}
    		totalAveExceptionNum += eventInfo.minExceptionNum.get();
    		totalMinWallTime += eventInfo.minTotalWallTime.get();
    		totalMinReqNum += eventInfo.minCorrectNum.get();
		}
    	if(totalAveExceptionNum * 1f / serverContext.getLogonPlayerNum() > 1) {
    		return ServerStateCode.battleMinAveException;
    	}
    	if(totalMinWallTime * 1f / totalMinReqNum > 200) {
    		return ServerStateCode.battleAveReqLag;
    	}
    	
    	//db
    	for (EventInfo eventInfo : this.performanceInfo.values()) {
    		if(eventInfo.type == 1) {
    			continue;
    		}
    		if(eventInfo.minCorrectNum.get() > 10) {
    			if(eventInfo.minTotalWallTime.get() * 1f / eventInfo.minCorrectNum.get() > 10) {
    				return ServerStateCode.battleDbVisitLag;
    			}
    		}
    	}
    	
    	return ServerStateCode.noProblem;
    }
    
    public ServerStateCode getRankingServerStateCode() {
    	EventInfo eventInfo = this.performanceInfo.get("scoreRanking");
    	if(eventInfo != null && eventInfo.minExceptionNum.get() > 0) {
    		return ServerStateCode.rankingTaskHaveException;
    	}
    	return ServerStateCode.noProblem;
    }
    
    public ServerStateCode isLoginHaveProblem() {
    	EventInfo eventInfo = this.performanceInfo.get("signServer");
    	if(eventInfo != null && eventInfo.minTotalWallTime.get() * 1f / eventInfo.minCorrectNum.get() > 100) {
    		return ServerStateCode.loginLag;
    	}
    	EventInfo eventInfo2 = this.performanceInfo.get("refreshAdminRole");
    	if(eventInfo2 != null && eventInfo2.minTotalWallTime.get() * 1f / eventInfo2.minCorrectNum.get() > 100) {
    		return ServerStateCode.refreshRoleInfoLag;
    	}
    	return ServerStateCode.noProblem;
    }
    
    public ServerStateCode isHaveError() {
    	int totalAveExceptionNum = 0;
    	int totalMinWallTime = 0;
    	int totalMinReqNum = 0;
    	
    	//req
    	
    	for (EventInfo eventInfo : this.performanceInfo.values()) {
    		if(eventInfo.type == 2) {
    			continue;
    		}
    		String eventId = eventInfo.eventId;
    		if("accountService.login".equals(eventId)) { //如果登录时间超过1秒
    			if(eventInfo.minTotalWallTime.get() / eventInfo.minCorrectNum.get() > 1000) {
    				return ServerStateCode.loginLag;
    			}
    		} else if("pveService.endFight".equals(eventId)) {
    			if(eventInfo.minTotalWallTime.get() / eventInfo.minCorrectNum.get() > 1000) {
    				return ServerStateCode.fightEndLag;
    			}
    		}
    		totalAveExceptionNum += eventInfo.minExceptionNum.get();
    		totalMinWallTime += eventInfo.minTotalWallTime.get();
    		totalMinReqNum += eventInfo.minCorrectNum.get();
		}
    	int loginNum = serverContext.getLogonPlayerNum();
    	if(loginNum < 100) {
    		loginNum = 100;
    	}
    	if(totalAveExceptionNum * 1f / loginNum > 1) {
    		return ServerStateCode.minAveException;
    	}
    	if(totalMinWallTime * 1f / totalMinReqNum > 200) {
    		return ServerStateCode.aveReqLag;
    	}
    	
    	//db
    	for (EventInfo eventInfo : this.performanceInfo.values()) {
    		if(eventInfo.type == 1) {
    			continue;
    		}
    		if(eventInfo.minCorrectNum.get() > 10) {
    			if(eventInfo.minTotalWallTime.get() * 1f / eventInfo.minCorrectNum.get() > 10) {
    				return ServerStateCode.dbVisitLag;
    			}
    		}
    	}
    	
    	return ServerStateCode.noProblem;
    }
}
