package com.funcy.g01.upload.global;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.funcy.g01.base.platform.aliyun.OssHelper;
import com.funcy.g01.base.platform.aliyun.OssThreadPool;

public class UploadBootstrap {

	private static final Logger logger = Logger.getLogger(UploadBootstrap.class);
	
	public static final int BUSSNESS_POOL_MAX_THREAD_NUM = 20; //TODO
	
	public static void startServer(ApplicationContext ac) {
		OssThreadPool ossThreadPool = ac.getBean(OssThreadPool.class);
		ossThreadPool.init();
		OssHelper ossHelper = ac.getBean(OssHelper.class);
		ossHelper.init();
		logger.info(" upload image server start ....");
	}
}
