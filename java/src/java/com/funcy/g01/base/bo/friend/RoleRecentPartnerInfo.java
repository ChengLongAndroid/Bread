package com.funcy.g01.base.bo.friend;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.service.FriendProtoBuffer.PartnerVoProto;
import com.funcy.g01.base.proto.service.FriendProtoBuffer.RoleRecentPartnerInfoProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class RoleRecentPartnerInfo implements ProtobufSerializable{
	
	private long roleId;
	
	private List<Partner> partners = new LinkedList<Partner>();
	
	public static final int limitCount = 50;
	
	public RoleRecentPartnerInfo(long roleId){
		this.roleId = roleId;
	}

	public RoleRecentPartnerInfo(long roleId, List<Partner> partners) {
		this.roleId = roleId;
		this.partners = partners;
	}
	
	public void updateRecentPartner(List<Long> partnerIds, long playTime){
		if(partnerIds==null){
			return;
		}
		partners.sort(new Comparator<Partner>() {	//按照时间先后排序
			@Override
			public int compare(Partner o1, Partner o2) {
				if(o1.getRecentPlayTime() > o2.getRecentPlayTime()){
					return -1;
				}else if(o1.getRecentPlayTime() < o2.getRecentPlayTime()){
					return 1;
				}
				return 0;
			}
			
		});
		if(partnerIds.size() + this.partners.size() > limitCount){	//超出时先删除超出较久远的记录
			for(int i=0;i<partnerIds.size()+this.partners.size()-limitCount;i++){
				this.partners.remove(i);
			}
		}
		for (long id : partnerIds) {
			if(id==this.roleId){
				continue;
			}
			Partner oldPartner = null;
			for (Partner partner : this.partners) {
				if (partner.getRoleId() == id) {
					oldPartner = partner;
				}
			}
			if (oldPartner == null) {
				this.partners.add(new Partner(id, playTime));
			} else {
				oldPartner.setRecentPlayTime(playTime);
			}
		}
		
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public List<Partner> getPartners() {
		return partners;
	}

	public void setPartners(List<Partner> partners) {
		this.partners = partners;
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleRecentPartnerInfoProto proto = (RoleRecentPartnerInfoProto) message;
		this.roleId = proto.getRoleId();
		for(PartnerVoProto partner: proto.getPartnersList()){
			this.partners.add(new Partner(partner));
		}
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleRecentPartnerInfoProto.Builder builder = RoleRecentPartnerInfoProto.newBuilder();
		builder.setRoleId(this.roleId);
		for(Partner partner: this.partners){
			builder.addPartners(partner.build());
		}
		return builder.build();
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleRecentPartnerInfoProto proto = RoleRecentPartnerInfoProto.parseFrom(bytes);
			copyFrom(proto);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}
	
	
}
