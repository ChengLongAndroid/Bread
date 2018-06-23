package com.funcy.g01.base.bo.item;

import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;

public enum ItemType {
	
	none(0, "none"), item(1, "i"), skill(2, "t"),dress(3,"d"),frame(4,"f");
	
	private final int code;
	
	private final String shortSpell;
	
	public int getCode() {
		return code;
	}

	public String getShortSpell() {
		return shortSpell;
	}

	private ItemType(int code, String shortSpell) {
		this.code = code;
		this.shortSpell = shortSpell;
	}
	
	public static ItemType valueOf(int typeCode) {
		for (ItemType type : values()) {
			if(type.getCode() == typeCode) {
				return type;
			}
		}
		throw new BusinessException(ErrorCode.WRONG_CODE);
	}
	
	public static ItemType valueOfShortSpell(String shortSpell) {
		for (ItemType type : values()) {
			if(type.getShortSpell().equalsIgnoreCase(shortSpell)) {
				return type;
			}
		}
		throw new BusinessException(ErrorCode.WRONG_SHORTSPELL);
	}
	
}
