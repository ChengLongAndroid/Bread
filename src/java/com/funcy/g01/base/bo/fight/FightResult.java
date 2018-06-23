package com.funcy.g01.base.bo.fight;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.data.BattleResultData;
import com.funcy.g01.base.data.BattleResultProperty;
import com.funcy.g01.base.data.DropGroupData;
import com.funcy.g01.base.data.DropGroupProperty;
import com.funcy.g01.base.data.PhysicsObjProperty;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.DisplayResultProto;



public class FightResult {
	
	private static Logger logger = Logger.getLogger(FightResult.class);
	
	private  List<Item> items; 
	
	private FightRoom fightRoom;
	
	private BattleResultData battleResultData;
	
	private DropGroupData dropGroupData;
	
	public FightResult(){
		this.setItems(new ArrayList<Item>());
	}
	
	public FightResult(FightRoom fightRoom, BattleResultData battleResultData, DropGroupData dropGroupData ){
		this.fightRoom = fightRoom;
		this.battleResultData = battleResultData;
		this.dropGroupData = dropGroupData;
		this.setItems(new ArrayList<Item>());
	}
	
	public void checkReward(PhysicsObjProperty physicsObjProperty){
		if (physicsObjProperty.getSpecType() == PhysicsObjSpecType.dropItem) {
			BattleResultProperty battleResultProperty  = battleResultData.getBattleResultProperty(physicsObjProperty.getBattleResultId());
			if (battleResultProperty.checkCanDrop(fightRoom.getRoomType())) {
				List<Integer> dropGropIds = battleResultProperty.getDropGroupIds();
				for (Integer dropGroupId : dropGropIds) {
					DropGroupProperty dropGroupProperty = dropGroupData.findDropGroupProperty(dropGroupId);
					Item item = dropGroupProperty.drop();
					if (item != null) {
						this.getItems().add(item);
						logger.info("fightingResult getItems");
					}
				}
			}
		}
	}
	
	public void fightEndReward(int dropGroupId){
		DropGroupProperty dropGroupProperty = dropGroupData.findDropGroupProperty(dropGroupId);
		Item item = dropGroupProperty.drop();
		if (item != null) {
			this.getItems().add(item);
		}
	}
	
	public void reset(){
		this.items = new ArrayList<Item>();
	}
	
	public void addItems(List<Item> items){
		this.getItems().addAll(items);
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

