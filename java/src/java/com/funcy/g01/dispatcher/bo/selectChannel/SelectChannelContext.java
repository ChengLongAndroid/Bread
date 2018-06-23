package com.funcy.g01.dispatcher.bo.selectChannel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.friend.Friend;
import com.funcy.g01.base.bo.friend.RoleFriendInfo;
import com.funcy.g01.base.dao.redis.FriendRepo;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.dispatcher.bo.AllRoomManager;
import com.funcy.g01.dispatcher.bo.ChangeTrendType;
import com.funcy.g01.dispatcher.bo.DispatcherRoom;
import com.funcy.g01.dispatcher.bo.RoomType;


@Component
public class SelectChannelContext {
	
	@Autowired
	private AllRoomManager allRoomManager;
	
	@Autowired
	private FriendRepo friendInfoRepo;
	
	// K: roleId
	private Map<Long, RoleSelectChannelInfo> roleSelectChannelInfos = new HashMap<Long, RoleSelectChannelInfo>();
	
	private final static long refreshTime = 1*60*1000;
	
	
	public List<SelectChannelInfo> getRoleSelectChannelInfoByPage(long roleId, int pageIndex){
		return getRoleSelectChannelInfo(roleId).getChannelInfoByPage(pageIndex, RoleSelectChannelInfo.pageSize);
	}
	
	public RoleSelectChannelInfo getRoleSelectChannelInfo(long roleId){
		long curTime = System.currentTimeMillis();
		RoleSelectChannelInfo info = roleSelectChannelInfos.get(roleId);
		if(info == null){
			info = new RoleSelectChannelInfo(roleId, initRoleSelectChannelInfo(roleId));
			roleSelectChannelInfos.put(roleId, info);
		}else if(curTime - info.getLastGetTime() > refreshTime){
			//刷新
			info.refreshChannelInfo(initRoleSelectChannelInfo(roleId));
			roleSelectChannelInfos.replace(roleId, info);
		}
		return info;
	}
	
	private List<SelectChannelInfo> initRoleSelectChannelInfo(long roleId){
		List<SelectChannelInfo> result = getDispatcherRoomInfo(roleId);
		
		result.sort(new Comparator<SelectChannelInfo>() {

			@Override
			public int compare(SelectChannelInfo o1, SelectChannelInfo o2) {
				if(o1.getFriendsNum() < o2.getFriendsNum()){
					return -1;
				}else if(o1.getFriendsNum() > o2.getFriendsNum()){
					return 1;
				}else{
					if(o1.getRoomId() < o2.getRoomId()){
						return -1;
					}else if(o1.getRoomId() > o2.getRoomId()){
						return 1;
					}
					return 0;
				}
			}
			
		});
		return result;
	}
	
	private List<SelectChannelInfo> getDispatcherRoomInfo(long roleId){
		List<SelectChannelInfo> resp = new ArrayList<SelectChannelInfo>();
		for(DispatcherRoom room : allRoomManager.getHallRooms().values()){
			ChangeTrendType changeTrendType = allRoomManager.getBaseTypeRoomManager(RoomType.hall).getChangeTrendType();
			if(changeTrendType== ChangeTrendType.decrease && room.getUserNum() <= ServerConfig.hall_room_merge_threshold){
				continue;
			}
//	 		if(room.getUserNum() >= ServerConfig.hall_room_max_player_num){
//	 			continue;
//	 		}
			int friendsNum = 0;
			RoleFriendInfo friendInfo = friendInfoRepo.getRoleFriendInfo(roleId);
			for(Friend friend :friendInfo.getFriends()){
				for(long id:room.getUsers()){
					if(id==friend.getRoleId()){
						friendsNum++;
					}
				}
			}
			resp.add(new SelectChannelInfo(room.getRoomId(), room.getServerId(), friendsNum));
		}
		return resp;
	}
	
	public void removeRoleSelectChannelInfo(long roleId){
		this.roleSelectChannelInfos.remove(roleId);
	}
}
