package com.funcy.g01.base.data;

import java.util.List;

import com.funcy.g01.base.bo.item.Item;

public class CheckpointProperty {

	private final int id;
	
	private final String name;
	
	private final List<Item> awards;

	public CheckpointProperty(int id, String name, List<Item> awards) {
		this.id = id;
		this.name = name;
		this.awards = awards;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Item> getAwards() {
		return awards;
	}
	
}
