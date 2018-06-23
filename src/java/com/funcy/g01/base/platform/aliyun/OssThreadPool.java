package com.funcy.g01.base.platform.aliyun;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;



import org.springframework.stereotype.Component;


@Component
public class OssThreadPool {

	private ScheduledThreadPoolExecutor executor;
	
	private static final int OSS_POOL_MAX_THREAD_NUM = 20;
	
	//线程工厂
	public static class OssPoolThreadFactory implements ThreadFactory {
		final ThreadGroup group = Thread.currentThread().getThreadGroup();
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix = "oss-thread-pool";
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
            		namePrefix + threadNumber.getAndIncrement(),
                                  0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
	}
	
	//初始化上传线程池，最大线程数为20
	public void init() {
		this.executor = new ScheduledThreadPoolExecutor(OSS_POOL_MAX_THREAD_NUM , new OssPoolThreadFactory());
	}
	
	public Future<?> execute(Runnable runnable) {
		if(executor != null) {
			return executor.submit(runnable);
		}
		return null;
	}
	
}
