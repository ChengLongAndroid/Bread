package com.funcy.g01.base.data;

import java.util.List;

public class NpcProperty {

	private final int xmlId;
	
	private final int initLikes;
	
	private final List<Integer> taskIds;
	
	private final List<Integer> favoriteItems;
	
	private final List<Integer> talkTaskIds;

	public NpcProperty(int xmlId, int initLikes, List<Integer> taskIds,
			List<Integer> favoriteItems, List<Integer> talkTaskIds) {
		this.xmlId = xmlId;
		this.initLikes = initLikes;
		this.taskIds = taskIds;
		this.favoriteItems = favoriteItems;
		this.talkTaskIds = talkTaskIds;
	}

	public int getXmlId() {
		return xmlId;
	}

	public int getInitLikes() {
		return initLikes;
	}

	public List<Integer> getTaskIds() {
		return taskIds;
	}

	public List<Integer> getFavoriteItems() {
		return favoriteItems;
	}

	public List<Integer> getTalkTaskIds() {
		return talkTaskIds;
	}
	
}
