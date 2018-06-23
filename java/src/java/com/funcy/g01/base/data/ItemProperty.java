package com.funcy.g01.base.data;

import java.util.List;

import com.funcy.g01.base.bo.BoFactory;
import com.funcy.g01.base.bo.item.Item;


public class ItemProperty {
	
	private final int id;
	
	private final String name;
	
	private final int quality;
	
	private final boolean isSell;
	
	private final int price;
	
	private final boolean isUse;
	
	private final boolean isBtnLayerUse;
	
	private final String awardStr;
	
	private final int fightSkillId;
	
	private final int addLikes;
	
	private final boolean isRealItem;
	
	private final int useAddNum;
	
	private final int useAddParam;
	
	public ItemProperty(int id, String name, int quality, boolean isSell, int price, boolean isUse, boolean isBtnLayerUse, String awardStr, int fightSkillId, int addLikes, boolean isRealItem, int useAddNum, int useAddParam){
		this.id = id;
		this.name = name;
		this.quality = quality;
		this.price = price;
		this.isUse = isUse;
		this.isBtnLayerUse = isBtnLayerUse;
		this.isSell = isSell;
		this.awardStr = awardStr;
		this.fightSkillId = fightSkillId;
		this.addLikes = addLikes;
		this.isRealItem = isRealItem;
		this.useAddNum = useAddNum;
		this.useAddParam = useAddParam;
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

	public boolean isSell() {
		return isSell;
	}

	public int getPrice() {
		return price;
	}

	public boolean isUse() {
		return isUse;
	}
	
	public boolean isBtnLayerUse() {
		return isBtnLayerUse;
	}

	public String getAwardStr() {
		return awardStr;
	}
	
	public List<Item> getAwardItemList(){
		return BoFactory.createMultiItems(awardStr);
	}

	public int getFightSkillId() {
		return fightSkillId;
	}

	public int getAddLikes() {
		return addLikes;
	}


	public boolean isRealItem() {
		return isRealItem;
	}

	public int getUseAddNum() {
		return useAddNum;
	}

	public int getUseAddParam() {
		return useAddParam;
	}

}
