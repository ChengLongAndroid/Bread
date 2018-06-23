package com.funcy.g01.base.data;

import java.util.List;

import com.funcy.g01.base.bo.item.Item;


public class DressProperty {
	
	private final int id;
	
	private final String name;
	
	private final int type;
	
	private final int quality;
	
	private final boolean isDefault;
	
	private final List<Item> convertItems;
	
	public DressProperty(int id, String name, int quality, int type, boolean isDefault, List<Item> convertItems){
		this.id = id;
		this.name = name;
		this.type = type;
		this.quality = quality;
		this.isDefault = isDefault;
		this.convertItems = convertItems;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	public int getQuality() {
		return quality;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public List<Item> getConvertItems() {
		return convertItems;
	}

}
