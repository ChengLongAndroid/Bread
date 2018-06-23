package com.funcy.g01.login.dao;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.CommonConstant;
import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.base.bo.user.FrontVersion;
import com.funcy.g01.base.bo.user.FrontVersionExample;
import com.funcy.g01.base.dao.sql.mapper.FrontVersionMapper;

import edu.emory.mathcs.backport.java.util.Collections;

@Component
public class FrontVersionDao {
	
	@Autowired
	private FrontVersionMapper frontVersionMapper;
	
	@Autowired
	private ServerInfoData serverInfoData;
	
	public List<FrontVersion> getAllVersions() {
		FrontVersionExample example = new FrontVersionExample();
		return frontVersionMapper.selectByExample(example);
	}
	
	public FrontVersion getVersionByVersionNums(int versionNum1, int versionNum2) {
		FrontVersionExample example = new FrontVersionExample();
		example.createCriteria().andVersionNum1EqualTo(versionNum1).andVersionNum2EqualTo(versionNum2);
		List<FrontVersion> versions = this.frontVersionMapper.selectByExample(example);
		if(versions.size() == 0) {
			return null;
		} else {
			return versions.get(0);
		}
	}

	public List<FrontVersion> findAllFrontVersion(){
		FrontVersionExample example = new FrontVersionExample();
		List<FrontVersion> list = frontVersionMapper.selectByExample(example);
		Collections.sort(list,new Comparator<FrontVersion>(){
			public int compare(FrontVersion a ,FrontVersion b){
				return (int)(b.getTime().getTime()-a.getTime().getTime());
			}
		});
		return list;
	}
	
	public void save(FrontVersion version){
		if(version.getId()==CommonConstant.NO_EXIST_PRIMARY_ID){
			frontVersionMapper.insert(version);
		}else{
			frontVersionMapper.updateByPrimaryKey(version);
		}
	}
	
}
