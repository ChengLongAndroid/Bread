package com.funcy.g01.base.data;

import java.util.List;

import com.funcy.g01.base.bo.chest.ChestType;


public class ChestProperty {
	
	private final int id;
	
	private final ChestType type;
	
	private final String name;
	
	private final int openTime;
	
	private final List<Integer> dropGroupIds;
	
	public ChestProperty(int id, String name, ChestType type, int openTime, List<Integer> dropGroupIds){
		this.id = id;
		this.name = name;
		this.openTime = openTime;
		this.type = type;
		this.dropGroupIds = dropGroupIds;
	}

	public int getId() {
		return id;
	}

	public ChestType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public int getOpenTime() {
		return openTime;
	}

	public List<Integer> getDropGroupIds() {
		return dropGroupIds;
	}

}
