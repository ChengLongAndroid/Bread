package com.funcy.g01.base.bo.friend;

import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.bo.role.RoleNetInfo;
import com.funcy.g01.base.bo.role.RoleState;
import com.funcy.g01.base.bo.role.RoleStateInfo;
import com.funcy.g01.base.proto.service.FriendProtoBuffer.FriendVoProto;

public class Friend {
	
	private long roleId;	// 好友的ROLE ID

	private transient String platformId;
	
	private transient String roleName;
	
	private transient int roleLevel;

    private transient int vipLevel;

    private transient String photo;
	
	private transient int sex;

	private transient int photoFrame;
	
	private transient RoleState state;
	
	private transient long lastPlayOpenTime;
	
	private transient long roomId;
	
	private transient int licenseLevel;
	
	private transient long lastOnlineTime;
	
	@SuppressWarnings("unused")
	private Friend() {
	}
	
	public Friend(Role role, RoleStateInfo roleStateInfo, RoleNetInfo roleNetInfo) {
		this.roleId = role.getId();
		this.platformId = role.getPlatformId();
		this.roleName = role.getRoleName();
		this.roleLevel = role.getRoleLevel();
		this.vipLevel = role.getVipLevel();
		this.photo = role.getPhoto();
		this.sex = role.getSex();
		this.photoFrame = role.getPhotoFrame();
		this.state = roleStateInfo.getState();
		this.lastPlayOpenTime = roleStateInfo.getLastGameOpenTime();
		this.roomId = roleNetInfo.getFightRoomId();
		this.licenseLevel = role.getShammanLevel();
		this.lastOnlineTime = roleStateInfo.getLastGameOpenTime();
	}
	
	public Friend(Role role) {
		this.roleId = role.getId();
		this.platformId = role.getPlatformId();
		this.roleName = role.getRoleName();
		this.roleLevel = role.getRoleLevel();
		this.vipLevel = role.getVipLevel();
		this.photo = role.getPhoto();
		this.sex = role.getSex();
		this.photoFrame = role.getPhotoFrame();
		this.state = RoleState.ONLINE;
		this.lastPlayOpenTime = 0L;
		this.roomId = -1;
		this.licenseLevel = role.getShammanLevel();
		this.lastOnlineTime = role.getLoginTime();
	}
	
	public void initFriend(Role role, RoleStateInfo roleStateInfo, RoleNetInfo roleNetInfo) {
		this.platformId = role.getPlatformId();
		this.roleName = role.getRoleName();
		this.roleLevel = role.getRoleLevel();
		this.vipLevel = role.getVipLevel();
		this.photo = role.getPhoto();
		this.sex = role.getSex();
		this.photoFrame = role.getPhotoFrame();
		this.state = roleStateInfo.getState();
		this.lastPlayOpenTime = roleStateInfo.getLastGameOpenTime();
		this.roomId = roleNetInfo.getFightRoomId();
		this.licenseLevel = role.getShammanLevel();
		this.lastOnlineTime = roleStateInfo.getLastGameOpenTime();
	}
	
	public Friend(long roleId) {
		this.roleId = roleId;
	}
	
	public FriendVoProto buildProto4Front() {
		FriendVoProto.Builder builder = FriendVoProto.newBuilder();
		builder.setRoleId(this.roleId);
		builder.setPlatformId(this.platformId);
		builder.setRoleName(this.roleName);
		builder.setRoleLevel(this.roleLevel);
		builder.setVipLevel(this.vipLevel);
		builder.setPhoto(this.photo);
		builder.setSex(this.sex);
		builder.setPhotoFrame(this.photoFrame);
		builder.setState(this.state.getCode());
		builder.setLastPlayOpenTime(this.lastPlayOpenTime);
		builder.setRoomId(this.roomId);
		builder.setLicenseLevel(this.licenseLevel);
		builder.setLastOnlineTime(this.lastOnlineTime);
		return builder.build();
	}
	
	public Friend(FriendVoProto proto){
		this.roleId = proto.getRoleId();
		this.platformId = proto.getPlatformId();
		this.roleName = proto.getRoleName();
		this.roleLevel = proto.getRoleLevel();
		this.vipLevel = proto.getVipLevel();
		this.photo = proto.getPhoto();
		this.sex = proto.getSex();
		this.photoFrame = proto.getPhotoFrame();
		this.state = RoleState.valueOf(proto.getState());
		this.lastPlayOpenTime = proto.getLastPlayOpenTime();
		this.roomId = proto.getRoomId();
		this.licenseLevel = proto.getLicenseLevel();
		this.lastOnlineTime = proto.getLastOnlineTime();
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getPhotoFrame() {
		return photoFrame;
	}

	public void setPhotoFrame(int photoFrame) {
		this.photoFrame = photoFrame;
	}

	public RoleState getState() {
		return state;
	}

	public void setState(RoleState state) {
		this.state = state;
	}

	public long getLastPlayOpenTime() {
		return lastPlayOpenTime;
	}

	public void setLastPlayOpenTime(long lastPlayOpenTime) {
		this.lastPlayOpenTime = lastPlayOpenTime;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}
	
}
