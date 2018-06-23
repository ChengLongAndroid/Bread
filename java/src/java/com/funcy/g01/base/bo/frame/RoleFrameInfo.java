package com.funcy.g01.base.bo.frame;

import java.util.ArrayList;
import java.util.List;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.FrameVoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleFrameInfoProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class RoleFrameInfo implements ProtobufSerializable {
	
	
	private long roleId;
	
	private List<FrameVo> frames = null;
	
	
	@SuppressWarnings("unused")
	private RoleFrameInfo() {
	}
	
	public RoleFrameInfo(long roleId){
		this.roleId = roleId;
		frames = new ArrayList<FrameVo>();
	}
	
	public RoleFrameInfo(byte[] bytes){
		parseFrom(bytes);
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleFrameInfoProto proto = (RoleFrameInfoProto) message;
		this.roleId = proto.getRoleId();
		this.frames = new ArrayList<FrameVo>() ;
		for (FrameVoProto frameVoProto : proto.getFramesList()) {
			this.frames.add(new FrameVo(frameVoProto.getXmlId()));
		}
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleFrameInfoProto.Builder buidler = RoleFrameInfoProto.newBuilder();
		buidler.setRoleId(roleId);
		for (FrameVo frameVo : frames) {
			buidler.addFrames(frameVo.copyTo());
		}
		return buidler.build();
	}
	
	public void addPhotoFrame(FrameVo frameVo){
		this.frames.add(frameVo);
	}
	
	public boolean checkHadFrame(int xmlId){
		return findFrameVoByXmlId(xmlId) != null;
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleFrameInfoProto roleFrameInfoProto = RoleFrameInfoProto.parseFrom(bytes);
			copyFrom(roleFrameInfoProto);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}
	
	public FrameVo findFrameVoByXmlId(int xmlId){
		for (FrameVo frameVo : frames) {
			if (frameVo.getXmlId() == xmlId) {
				return frameVo;
			}
		}
		return null;
	}
	

	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}

	public long getRoleId() {
		return roleId;
	}

	public List<FrameVo> getFrames() {
		return frames;
	}

	public void setFrames(List<FrameVo> frames) {
		this.frames = frames;
	}

}
