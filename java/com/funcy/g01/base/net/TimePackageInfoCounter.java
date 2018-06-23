package com.funcy.g01.base.net;

import java.util.concurrent.atomic.AtomicLong;

import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicInteger;

public class TimePackageInfoCounter {

	private final int second;
	
	private AtomicLong packageCount;
	
	private AtomicLong packageBytesCount;
	
	private AtomicInteger index;

	public TimePackageInfoCounter(int second) {
		this.second = second;
		this.index = new AtomicInteger(0);
		this.packageBytesCount = new AtomicLong(0);
		this.packageCount = new AtomicLong(0);
	}
	
	public void count(int bytes, int curTimeInSecond) {
		int curIndex = curTimeInSecond / second;
		while(true) {
			int tempCurIndex = this.index.get();
			if(curIndex == tempCurIndex) {
				break;
			} else {
				if(this.index.compareAndSet(tempCurIndex, curIndex)) {
					this.packageBytesCount.set(0);
					this.packageCount.set(0);
					break;
				}
			}
		}
		this.packageCount.incrementAndGet();
		this.packageBytesCount.addAndGet(bytes);
	}

	public int getSecond() {
		return second;
	}

	public AtomicLong getPackageCount() {
		return packageCount;
	}

	public AtomicLong getPackageBytesCount() {
		return packageBytesCount;
	}

	public AtomicInteger getIndex() {
		return index;
	}
	
}
