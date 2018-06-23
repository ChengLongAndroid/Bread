package com.funcy.g01.base.util;

public enum ServerStateCode {
	noProblem(100, "no problem"),
	nettyDie(101, "netty connection num is 0"),
	logonUserFull(102, "logon num is full"), 
	unknownError(103, "unknown error"), 
	bussnessThreadPoolFull(104, "bussness thread pool full"), 
	dbConnectionPoolBusy(105, "db connection pool busy"), 
	loginLag(106, "login lag"), 
	fightEndLag(107, "fight end lag"), 
	minAveException(108, "min ave exception exceed"), 
	aveReqLag(109, "ave req lag"), 
	dbVisitLag(110, "db visit lag"),
	signServerLag(111, "sign server lag"),
	refreshRoleInfoLag(112, "refresh role info lag"),
	rankingTaskHaveException(113, "ranking task have exception"),
	battleDbVisitLag(114, "battle db visit lag"),
	battleMinAveException(115, "battle min ave exception exceed"),
	battleAveReqLag(116, "battle ave req lag");
	
	private final int code;
	
	private final String msg;

	private ServerStateCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
}
