package com.funcy.g01.base.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.bo.item.ItemType;


@Component
public class BoFactory {

	
	public static List<Item> createMultiItems(String itemsStr) {

        List<Item> itemList = new ArrayList<Item>();
        if(itemsStr==null||itemsStr.equals(""))
        	return itemList;
        	
        String[] multipleItemStr = itemsStr.split(",");
        
        for(String singleItemStr : multipleItemStr) {
        	if("".equals(singleItemStr)) {
        		continue;
        	}
        	Item item = createSingleItem(singleItemStr);
        	itemList.add(item);
        }
        
        return itemList;
    
	}

	public static Item createSingleItem(String itemStr) {
		String[] itemElements = itemStr.split("\\*");
    	
		String itemTypeShortSpell = itemElements[0];
		
		ItemType itemType = ItemType.valueOfShortSpell(itemTypeShortSpell);
    	int itemXmlId = Integer.parseInt(itemElements[1]);
    	int num = Integer.parseInt(itemElements[2]);
    	
    	return new Item(itemType, itemXmlId, num);
	}

}
