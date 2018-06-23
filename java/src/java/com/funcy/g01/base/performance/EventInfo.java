package com.funcy.g01.base.performance;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.funcy.g01.base.util.TimeUtil;

public class EventInfo {
    public AtomicInteger callNum = new AtomicInteger();

    public AtomicInteger correctNum = new AtomicInteger();

    public AtomicInteger exceptionNum = new AtomicInteger();

    public AtomicLong totalWallTime = new AtomicLong();

    public AtomicLong[] wallTimeRangeNum = new AtomicLong[10];
    
    public AtomicInteger currentMin = new AtomicInteger();
    
    public AtomicInteger minCallNum = new AtomicInteger();
    
    public AtomicInteger minCorrectNum = new AtomicInteger();
    
    public AtomicInteger minExceptionNum = new AtomicInteger();
    
    public AtomicLong minTotalWallTime = new AtomicLong();
    
    public final String eventId;
    
    public final int type; //1 req 2 db
    
    public EventInfo(String eventId) {
        this.eventId = eventId;
        if(eventId.contains("com.ebo.g04.dao.mapper")) {
        	type = 2;
        } else {
        	type = 1;
        }
        for (int i = 0; i < 10; i++) {
            wallTimeRangeNum[i] = new AtomicLong();
        }
    }
    
    public int getCallNum() {
        return callNum.get();
    }
    
    public int getCorrectNum() {
        return correctNum.get();
    }
    
    public int getExceptionNum() {
        return exceptionNum.get();
    }
    
    public long getTotalWallTime() {
        return totalWallTime.get();
    }
    
    public void handleCall(boolean isHadException, long wallTime) {
        callNum.incrementAndGet();
        if (!isHadException) {
            totalWallTime.addAndGet(wallTime);
            int index = (int) (wallTime / 500);
            if (index < wallTimeRangeNum.length) {
                wallTimeRangeNum[index].incrementAndGet();
            }
        }
        if (isHadException) {
            exceptionNum.incrementAndGet();
        } else {
            correctNum.incrementAndGet();
        }
        
        int min = TimeUtil.getHourMinutes();
        if(min != currentMin.get()) {
            minCallNum.set(0);
            minCorrectNum.set(0);
            minExceptionNum.set(0);
            minTotalWallTime.set(0);
            currentMin.set(min);
        }
        minCallNum.incrementAndGet();
        if(!isHadException) {
            minTotalWallTime.addAndGet(wallTime);
        }
        if(isHadException) {
            minExceptionNum.incrementAndGet();
        } else {
            minCorrectNum.incrementAndGet();
        }
    }
}
