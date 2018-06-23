package com.funcy.g01.base.bo.reward;

import java.util.ArrayList;
import java.util.List;

import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.DisplayResultProto;


public class DisplayReward {

	private List<Item> items;
	
	public DisplayReward(){
		this.items = new ArrayList<Item>();
	}
	
	public void addItems(List<Item> items){
		this.items.addAll(items);
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public DisplayResultProto copyTo(){
		DisplayResultProto.Builder builder = DisplayResultProto.newBuilder();
		for (Item item : items) {
			builder.addItems(item.copyTo());
		}
		return builder.build();
	} 
	
}
