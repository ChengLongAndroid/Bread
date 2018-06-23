package com.funcy.g01.base.bo.item;

import java.util.ArrayList;
import java.util.List;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleItemInfoProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class RoleItemInfo implements ProtobufSerializable {
	
	
	private long roleId;
	
	private List<Item> items = null;
	
	
	@SuppressWarnings("unused")
	private RoleItemInfo() {
	}
	
	public RoleItemInfo(long roleId){
		this.roleId = roleId;
		setItems(new ArrayList<Item>());
	}
	
	public RoleItemInfo(byte[] bytes){
		parseFrom(bytes);
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleItemInfoProto proto = (RoleItemInfoProto) message;
		this.roleId = proto.getRoleId();
		this.setItems(new ArrayList<Item>()) ;
		for (ItemVoProto itemVoProto : proto.getItemsList()) {
			Item item = new Item(ItemType.item, itemVoProto.getXmlId(), itemVoProto.getNum());
			this.getItems().add(item);
		}
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleItemInfoProto.Builder buidler = RoleItemInfoProto.newBuilder();
		buidler.setRoleId(roleId);
		for (Item item : getItems()) {
			if (item.getXmlId() != Item.NULL_ITEM_XMLID) {
				buidler.addItems(item.copyTo());
			}
		}
		return buidler.build();
	}
	
	public void use(int itemXmlId, int num) {
		Item item = findItemByXmlId(itemXmlId);
		item.checkHaveItem();
		item.use(num);
		if(item.checkIsUseUp()) {
			getItems().remove(item);
		}
	}
	
	public boolean checkHaveEnoughItem(Item item){
		for (Item tempItem : getItems()) {
			if (tempItem.getXmlId() == item.getXmlId()) {
				return tempItem.getNum() >= item.getNum();
			}
		}
		return false;
	}
	
	public void addItem(Item item){
		this.getItems().add(item);
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleItemInfoProto roleItemInfoProto = RoleItemInfoProto.parseFrom(bytes);
			copyFrom(roleItemInfoProto);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}
	
	public Item findItemByXmlId(int xmlId){
		for (Item item : getItems()) {
			if (item.getXmlId() == xmlId) {
				return item;
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

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
