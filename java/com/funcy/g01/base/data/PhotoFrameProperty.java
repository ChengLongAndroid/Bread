package com.funcy.g01.base.data;


public class PhotoFrameProperty {
	
	private final int id;
	
	private final String name;
	
	private final int quality;
	
	public PhotoFrameProperty(int id, String name, int quality){
		this.id = id;
		this.name = name;
		this.quality = quality;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getQuality() {
		return quality;
	}

}
