package com.funcy.g01.base.data;

import java.util.List;

import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.bo.npc.NpcTaskType;

public class NpcTaskProperty {

	private final int xmlId;
	
	private final int type;
	
	private final List<List<Item>> awards;
	
	private final int needCompleteNum;
	
	private final int needItemXmlId;

	public NpcTaskProperty(int xmlId, int type, List<List<Item>> awards,
			int needCompleteNum, int needItemXmlId) {
		this.xmlId = xmlId;
		this.type = type;
		this.awards = awards;
		this.needCompleteNum = needCompleteNum;
		this.needItemXmlId = needItemXmlId;
	}

	public int getXmlId() {
		return xmlId;
	}

	public int getType() {
		return type;
	}

	public List<List<Item>> getAwards() {
		return awards;
	}

	public int getNeedCompleteNum() {
		return needCompleteNum;
	}

	public int getNeedItemXmlId() {
		return needItemXmlId;
	}

	public NpcTaskType getType0() {
		return NpcTaskType.getByCode(this.type);
	}
	
}
