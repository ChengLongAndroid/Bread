package com.funcy.g01.base.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.util.ProbabilityGenerator;

public class DropGroupProperty {
	
	private final int id;
	
	private final List<Item> items;
	
	private final int[] weights;
	
	public DropGroupProperty(List<DropGroupProperty> groups) {
		this.id = -10000;
		List<Item> totalItems = new LinkedList<Item>();
		List<Integer> totalWeights = new LinkedList<Integer>();
		for(DropGroupProperty dropGroupProperty : groups) {
			totalItems.addAll(dropGroupProperty.items);
			for(int weight : dropGroupProperty.weights) {
				totalWeights.add(weight);
			}
		}
		this.items = totalItems;
		this.weights = new int[totalWeights.size()];
		int index = 0;
		for(int weight : totalWeights) {
			this.weights[index] = weight;
			index++;
		}
	}

	public List<Item> getItems() {
		return items;
	}

	public int[] getWeights() {
		return weights;
	}

	public int getId() {
		return id;
	}

	public DropGroupProperty(int id, List<Item> items, int[] weights) {
		this.id = id;
		this.items = items;
		this.weights = weights;
	}
	
	public Item drop() {
		int index = ProbabilityGenerator.getRandomChoiceWithRatioArr(this.weights);
		Item item = new Item(this.items.get(index));
		if(item.getXmlId() == Item.NULL_ITEM_XMLID) {
			return null;
		}
		return item;
	}
	
	public List<Item> drops(int num) {
		int[] indexs = ProbabilityGenerator.getMutilRandomChoiceWithRatioArr(this.weights, num);
		List<Item> items = new ArrayList<Item>();
		for (Integer index : indexs) {
			Item item = new Item(this.items.get(index));
			if(item.getXmlId() == Item.NULL_ITEM_XMLID) {
				continue;
			}
			items.add(item);
		}
		return items;
	}
	
	public List<Item> dropMore(int num) {
		int quotient = num/3;
		int[] indexs = ProbabilityGenerator.getMutilRandomChoiceWithRatioArrWithLargerNumber(this.weights, quotient);
		List<Item> items = new ArrayList<Item>();
		for (Integer index : indexs) {
			Item item = new Item(this.items.get(index));
			if(item.getXmlId() == Item.NULL_ITEM_XMLID) {
				continue;
			}
			item.setNum(item.getNum()*3);
			items.add(item);
		}
		int remainder = num%3;
		if(remainder != 0) {
			int index = ProbabilityGenerator.getRandomChoiceWithRatioArr(this.weights);
			Item item = new Item(this.items.get(index));
			if(item.getXmlId() != Item.NULL_ITEM_XMLID) {
				item.setNum(item.getNum()*remainder);
				items.add(item);
			}
		}
		return items;
	}
	
}
