package com.funcy.g01.dispatcher.bo.selectChannel;

import java.util.ArrayList;
import java.util.List;

public class RoleSelectChannelInfo {
	
	private final long roleId;
	
	private List<SelectChannelInfo> infos = new ArrayList<SelectChannelInfo>();
	
	private long lastGetTime;
	
	/**
	 * 分页展示页大小
	 */
	public static final int pageSize = 10;
	
	public RoleSelectChannelInfo(long roleId, List<SelectChannelInfo> infos){
		this.roleId = roleId;
		this.infos = infos;
		this.lastGetTime = System.currentTimeMillis();
	}
	
	/**
	 *  分页获取大厅路线
	 * @param pageIndex 页码
	 * @param pageSize  页大小
	 * @return
	 */
	public List<SelectChannelInfo> getChannelInfoByPage(int pageIndex, int pageSize){
		List<SelectChannelInfo> result = new ArrayList<SelectChannelInfo>();
		int index = (pageIndex-1)*pageSize;
		int max = pageIndex*pageSize;
		max = max>infos.size()?infos.size():max;
		for(int i=index; i<max; i++){
			result.add(infos.get(i));
		}
		return result;
	}
	
	public void refreshChannelInfo(List<SelectChannelInfo> infos){
		this.infos = infos;
		this.lastGetTime = System.currentTimeMillis();
	}
	
	public int getPageCount(){
		if(infos.size() % pageSize ==0){
			return infos.size()/pageSize;
		}else{
			return infos.size()/pageSize+1;
		}
	}

	public long getRoleId() {
		return roleId;
	}

	public List<SelectChannelInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<SelectChannelInfo> infos) {
		this.infos = infos;
	}

	public long getLastGetTime() {
		return lastGetTime;
	}

	public void setLastGetTime(long lastGetTime) {
		this.lastGetTime = lastGetTime;
	}
	
}
