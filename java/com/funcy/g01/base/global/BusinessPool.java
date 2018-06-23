package com.funcy.g01.base.global;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.base.sdk.talkingdata.TalkingDataInfo;
import com.funcy.g01.base.util.JSONUtils;
import com.funcy.g01.base.util.MessageGZIP;

@Component("businessPool")
public class BusinessPool {
	@Autowired
	private ServerInfoData serverInfoData;
	
	private static final String TALKING_DATA_URL = "http://api.talkinggame.com/api/charge/";
	
	@Autowired
	private PlatformConfig platformConfig;
	
	public static class BusinessPoolThreadFactory implements ThreadFactory {
		final ThreadGroup group = Thread.currentThread().getThreadGroup();
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix = "business-pool";
        
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
	
    private ScheduledThreadPoolExecutor executor;
    
    public BusinessPool() {
    }
    
    public void init() {
    	this.executor = new ScheduledThreadPoolExecutor(1, new BusinessPoolThreadFactory());
    }
    
    public void schedule(Runnable runnable, long delay, TimeUnit unit) {
        executor.schedule(runnable, delay, unit);
    }
    
    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }
    
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command,
            long initialDelay,
            long period,
            TimeUnit unit) {
        return executor.scheduleAtFixedRate(command, initialDelay, period, unit);
    }
    
    
    public int getActiveThreadNum() {
        return executor.getActiveCount();
    }
    
    public int getMaxThreadNum() {
        return executor.getMaximumPoolSize();
    }
    
    public void talkingData(final TalkingDataInfo info) {
		this.execute(new Runnable(){
			@Override
			public void run() {
				DataOutputStream out = null;
				InputStream is = null;
				BufferedReader reader = null;
				try {
					String str = JSONUtils.toJSON(info);
					str = "["+str+"]";
					byte[] bytes = MessageGZIP.compressToByte(str);
					System.out.println(MessageGZIP.uncompressToString(bytes));				
					URL dataUrl = new URL(TALKING_DATA_URL+platformConfig.getTalkingDataId());
					HttpURLConnection con = (HttpURLConnection) dataUrl.openConnection();
							
					// 设置请求头信息
					con.setRequestMethod("POST");
					con.setRequestProperty("content-type", "application/msgpack");
					con.setRequestProperty("Content-Length",String.valueOf(bytes.length));
					con.setDoOutput(true);
					con.setDoInput(true);
					out = new DataOutputStream(con.getOutputStream());
					
					out.write(bytes);
					out.flush();
					if(con.getResponseCode() != HttpURLConnection.HTTP_OK) {
						throw new Exception("HTTP Request is not success, Response code is " + con.getResponseCode());
					}
					is = con.getInputStream();
					String responseStr = MessageGZIP.uncompressToString(is);
					System.out.println(responseStr);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				finally {
					try {
						if(out != null) out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}	
					try {
						if(is != null) is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						if(reader != null) reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

	}
    
}
