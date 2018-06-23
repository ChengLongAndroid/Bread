package com.funcy.g01.base.data;

import java.util.List;

import com.funcy.g01.base.bo.item.Item;

public class SkillProperty {
	
	private final int id;
	
	private final int type;
	
	private final String name;
	
	private final int totemId;
	
	private final List<Integer> needCheese;
	
	private final List<String> upstarItems; 
	
	private final int initstar;
	
	private final boolean isFinal;
	
	private final String useObj;
	
	private final int fightSkillId;
	
	private final List<Item> convertItems;
	
	public SkillProperty(int id, int type, String name, int totemId, List<Integer> needCheese, List<String> upstarItems, int initstar, boolean isFinal, String useObj, int fightSkillId, List<Item> convertItems){
		this.id = id;
		this.type = type;
		this.name = name;
		this.totemId = totemId;
		this.needCheese = needCheese;
		this.upstarItems = upstarItems;
		this.initstar = initstar;
		this.isFinal = isFinal;
		this.useObj = useObj;
		this.fightSkillId = fightSkillId;
		this.convertItems = convertItems;
	}

	public int getId() {
		return id;
	}

	public int getType() {
		return type;
	}

	public int getTotemId() {
		return totemId;
	}

	public List<Integer> getNeedCheese() {
		return needCheese;
	}

	public int getInitstar() {
		return initstar;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public String getName() {
		return name;
	}

	public List<String> getUpstarItems() {
		return upstarItems;
	}

	public String getUseObj() {
		return useObj;
	}

	public int getFightSkillId() {
		return fightSkillId;
	}

	public List<Item> getConvertItems() {
		return convertItems;
	}

}
