package com.funcy.g01.base.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class LongTimeHttpPool {
	
	private static LongTimeHttpPool httpPool = new LongTimeHttpPool();
	
	private static final int maxThread = 10;
	
	private LongTimeHttpPool(){
		
	}
	
	public static LongTimeHttpPool getInstance(){
		return httpPool;
	}
	
	public class HttpPoolThreadFactory implements ThreadFactory{
		final ThreadGroup group = Thread.currentThread().getThreadGroup();
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix = "http-pool";
        
		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(group, r,namePrefix + threadNumber.getAndIncrement(),0);
			if (thread.isDaemon())
			  thread.setDaemon(false);
			if (thread.getPriority() != Thread.NORM_PRIORITY)
			  thread.setPriority(Thread.NORM_PRIORITY);
			return thread;
		}
		
	}
	
	private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(maxThread, new HttpPoolThreadFactory());
	
	public Future<?> execute(final Callable<String> task) throws InterruptedException, ExecutionException, TimeoutException{
		return executor.submit(task);
	}
}
