package com.funcy.g01.base.data;

public class TotemProperty {
	
	private final int id;
	
	private final String name;
	
	private final int type;
	
	private final int index;
	
	private final boolean enabled;
	
	private final int openLv;
	
	public TotemProperty(int id, String name, int type, int index, boolean enabled, int openLv){
		this.id = id;
		this.name = name;
		this.type = type;
		this.index = index;
		this.enabled = enabled;
		this.openLv = openLv;
	}

	public int getId() {
		return id;
	}

	public int getType() {
		return type;
	}

	public int getIndex() {
		return index;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public int getOpenLv() {
		return openLv;
	}

	public String getName() {
		return name;
	}
	
}
