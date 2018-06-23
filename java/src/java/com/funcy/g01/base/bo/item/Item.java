package com.funcy.g01.base.bo.item;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.funcy.g01.base.bo.Currency;
import com.funcy.g01.base.bo.CurrencyType;
import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ItemVoProto;

public class Item {
	
	public static final int CHEESE_XMLID = 400001;

	public static final int GEM_XMLID = 400002;
	
	public static final int npc_likes_xml_id = 400003;
	
	public static final int saman_potion_xml_id = 3003001;
	
	public static final int base_exp_book_xml_id = 3003006;
	
	public static final int advance_exp_book_xml_id = 3003007;
	
	public static final int base_cheese_book_xml_id = 3003008;
	
	public static final int advance_cheese_book_xml_id = 3003009;
	
	
	/**
	 * 用于掉落作为不掉的概率
	 */
	public static final int NULL_ITEM_XMLID = 9999;

	private ItemType type;
	
	private int xmlId;
	
	private int num;
	
	
	@SuppressWarnings("unused")
	private Item() {
	}
	
	public Item(Item item) {
		this.type = item.type;
		this.xmlId = item.xmlId;
		this.num = item.num;
	}

	public Item(ItemType type, int id, int num) {
		this.type = type;
		this.xmlId = id;
		this.num = num;
	}
	
	public int getXmlId() {
		return xmlId;
	}

	public void setXmlId(int id) {
		this.xmlId = id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}


	public boolean checkIsCheese() {
		return this.xmlId == Item.CHEESE_XMLID;
	}

	public boolean checkIsGem() {
		return this.xmlId == Item.GEM_XMLID;
	}
	
	public boolean isNpcLikes() {
		return this.xmlId == Item.npc_likes_xml_id;
	}
	
	public void addNum(int num) {
		this.num += num;
	}	
	public ItemVoProto copyTo() {
		ItemVoProto.Builder builder = ItemVoProto.newBuilder();
		builder.setXmlId(this.xmlId);
		builder.setNum(this.num);
		builder.setType(this.type.getCode());
		return builder.build();
	}

	public boolean checkIsExist() {
		return this.type == ItemType.none;
	}

	public void fill(Item item) {
		this.xmlId = item.getXmlId();
		this.num = item.getNum();
		this.type = item.getType();
	}
	
	public void use(int num) {
		this.checkHaveEnoughNum(num);
		this.num -= num;
	}
	
	private void checkHaveEnoughNum(int needNum) {
		if(this.num < needNum) {
			throw new BusinessException(ErrorCode.NOT_ENOUGH_ITEM);
		}
	}

	public boolean checkIsUseUp() {
		return this.num == 0;
	}
	
	@JsonIgnore
	public boolean isCurrency() {
		return this.xmlId == Item.CHEESE_XMLID || this.xmlId == Item.GEM_XMLID;
	}
	
	public Currency toCurrency() {
		
		if(this.xmlId == Item.CHEESE_XMLID) {
			return new Currency(CurrencyType.cheese, this.num);
		} else if(this.xmlId == Item.GEM_XMLID) {
			return new Currency(CurrencyType.gem, this.num);
		}else {
			throw new BusinessException(ErrorCode.WRONG_CODE);
		}
	}
	
	public String toString(){
		return this.type.getShortSpell()+"*"+this.xmlId+"*"+this.num;
	}
	
	public void checkHaveItem() {
		if(checkIsExist()) {
			throw new BusinessException(ErrorCode.NO_ITEM);
		}
	}

}
