package com.funcy.g01.base.bo.friend;

import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.proto.service.FriendProtoBuffer.RecommendFriendVoProto;
import com.google.protobuf.GeneratedMessage;

public class RecommendFriend {
	
	private long roleId;
	
	private String roleName;
	
	private int roleLevel;

    private int shammanLevel;

    private String photo;
	
	private int sex;

	private int photoFrame;
	
	private int type;// 1 最近一起玩的玩家   2 附近玩家  3 有缘分玩家 
	
	public RecommendFriend(Role role, int type){
		this.type = type;
		this.roleId = role.getId();
		this.roleName = role.getRoleName();
		this.roleLevel = role.getRoleLevel();
		this.sex = role.getSex();
		this.photo = role.getPhoto();
		this.photoFrame = role.getPhotoFrame();
		this.shammanLevel = role.getShammanLevel();
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
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

	public int getShammanLevel() {
		return shammanLevel;
	}

	public void setShammanLevel(int shammanLevel) {
		this.shammanLevel = shammanLevel;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public GeneratedMessage build(){
		RecommendFriendVoProto.Builder builder = RecommendFriendVoProto.newBuilder();
		builder.setRoleId(this.roleId);
		builder.setRoleName(this.roleName);
		builder.setRoleLevel(this.roleLevel);
		builder.setSex(this.sex);
		builder.setPhoto(this.photo);
		builder.setPhotoFrame(this.photoFrame);
		builder.setShammanLevel(this.shammanLevel);
		builder.setType(this.type);
		return builder.build();
	}
}
