package com.funcy.g01.base.data;

import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;

public enum WordPosition {
	arenaTop5("竞技场前5的全服通知",1),arenaHistoryReward("竞技场历史排名奖励",2),arenaDailyRankingReward("竞技场每日结算奖励",3),
	peakArenaHistoryReward("巅峰竞技场历史排名奖励",4),applyFriend("好友申请",5),friendsLearnEmail("好友切磋邮件（标题和发送者）",6),
	friendsLearnContent1("好友切磋失败内容1",7),friendsLearnContent2("好友切磋失败内容2",8),friendsLearnContent3("好友切磋失败内容3",9),
	friendsLearnContent4("好友切磋失败内容4",10),makeWishReward("许愿奖励",11),pushRecord("推送情报",12),
	reissueMammonReward("招财进宝奖励",13),systemEmail("系统邮件",14),unBan("解封",15),firstRecharge("首冲",16),
	worldBossReward("世界boss",17);
	
	private String description;
	
	private int code;
	
	private WordPosition(String description,int code){
		this.description = description;
		this.code = code;
	}
	
	public String getDescription(){
		return description;
	}
	
	public int getCode(){
		return code;
	}
	
	public WordPosition valueOf(int code){
		for(WordPosition type : values()){
			if(type.getCode()==code){
				return type;
			}
		}
		throw new BusinessException(ErrorCode.WRONG_CODE);
	}
	
}
