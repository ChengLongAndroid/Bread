package com.funcy.g01.base.data;

import com.funcy.g01.base.bo.chest.ChestType;


public class NewerChestProperty {
	
	private final int id;
	
	private final ChestType type;
	
	public NewerChestProperty(int id, ChestType type){
		this.id = id;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public ChestType getType() {
		return type;
	}


}
