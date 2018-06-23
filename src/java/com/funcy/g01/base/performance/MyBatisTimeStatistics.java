package com.funcy.g01.base.performance;

import java.util.Properties;  
import org.apache.ibatis.executor.Executor;  
import org.apache.ibatis.mapping.MappedStatement;  
import org.apache.ibatis.plugin.Interceptor;  
import org.apache.ibatis.plugin.Intercepts;  
import org.apache.ibatis.plugin.Invocation;  
import org.apache.ibatis.plugin.Plugin;  
import org.apache.ibatis.plugin.Signature;  
import org.apache.ibatis.session.ResultHandler;  
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

@Intercepts( {  
    @Signature(method = "query", type = Executor.class, args = {  
           MappedStatement.class, Object.class, RowBounds.class,  
           ResultHandler.class }) 
     })  
public class MyBatisTimeStatistics implements Interceptor{
	
	@Autowired
	PerformanceHandler performanceHandler;
	
	public Object intercept(Invocation invocation) throws Throwable {  
		boolean isHadException=false;
		long startTime=0;
		long endTime=0;
		Object result=null;
		MappedStatement mappedStatement=(MappedStatement) invocation.getArgs()[0];
		String sqlId=mappedStatement.getId();
		try{
			startTime=System.nanoTime()/1000;
			result=invocation.proceed();  
			endTime=System.nanoTime()/1000;
		}
		catch(Exception e){
			isHadException=true;
		}
		finally{
			performanceHandler.handleEvent(startTime, endTime,sqlId, isHadException);
		}
	    return result;  
	}  
	   
	public Object plugin(Object target) {  
	    return Plugin.wrap(target, this);  
	}  
	   
	public void setProperties(Properties properties) {
		
	}  
	   

}
