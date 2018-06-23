package com.funcy.g01.base.task;

import java.util.LinkedList;
import java.util.List;

import com.funcy.g01.base.bo.email.TimerEmailInfo;

public class EmailSenderConstant {

	public static volatile List<TimerEmailInfo> timerEmailInfos = new LinkedList<TimerEmailInfo>();
	
	public static void addEmail(TimerEmailInfo TimerEmailInfo){
		timerEmailInfos.add(TimerEmailInfo);
	}
	
	public static List<TimerEmailInfo> getTimerEmailInfos() {
		return timerEmailInfos;
	}

	public static void setTimerEmailInfos(List<TimerEmailInfo> timerEmailInfos) {
		EmailSenderConstant.timerEmailInfos = timerEmailInfos;
	}
	
	
}
