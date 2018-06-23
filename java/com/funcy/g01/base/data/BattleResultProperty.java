package com.funcy.g01.base.data;

import java.util.List;

import com.funcy.g01.base.bo.fight.FightRoomState;
import com.funcy.g01.dispatcher.bo.RoomType;


public class BattleResultProperty {
	
	private final int id;
	
	private final List<Integer> dropGroupIds;
	
	private final List<RoomType> roomTypes;
	
	private final FightRoomState dropFightState;
	
	private final float dropRandom;
	
	public BattleResultProperty(int id, List<Integer> dropGroupIds, List<RoomType> roomTypes, FightRoomState dropFightState,float dropRandom){
		this.id = id;
		this.dropGroupIds = dropGroupIds;
		this.roomTypes = roomTypes;
		this.dropFightState = dropFightState;
		this.dropRandom = dropRandom;
	}
	
	public boolean checkCanDrop(RoomType roomType){
		boolean isCanDrop = false;
		for (RoomType tempRoomType : roomTypes) {
			if (roomType == tempRoomType) {
				isCanDrop =  true;
			}
		}
		if (isCanDrop) {
			isCanDrop = Math.random() * 100 < dropRandom;
		}
		return isCanDrop;
	}

	public int getId() {
		return id;
	}

	public List<Integer> getDropGroupIds() {
		return dropGroupIds;
	}


	public FightRoomState getDropFightState() {
		return dropFightState;
	}

	public float getDropRandom() {
		return dropRandom;
	}

	public List<RoomType> getRoomTypes() {
		return roomTypes;
	}

}
