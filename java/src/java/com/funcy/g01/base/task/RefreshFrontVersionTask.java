package com.funcy.g01.base.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.user.FrontVersion;
import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.login.dao.FrontVersionDao;

@Component
public class RefreshFrontVersionTask implements Runnable{
	
	public static final int UPDATE_TIME_SECONDS = 10;
	
	@Autowired
	private FrontVersionDao frontVersionDao;
	
	@Autowired
	private ServerContext serverContext;
	
	@Override
	public void run() {
		List<FrontVersion> versions = frontVersionDao.getAllVersions();
		FrontVersion temp = versions.get(0);
		for(int i=1;i<versions.size();i++){
			FrontVersion version = versions.get(i);
			if(version.getVersionNum1()>temp.getVersionNum1() || 
					(version.getVersionNum1()==temp.getVersionNum1() && version.getVersionNum2()>temp.getVersionNum2())){
				temp = version;
			}
		}
		if(temp!=null){
			serverContext.refreshCurVersion(temp);
		}
	}
	
}
