package com.funcy.g01.base.bo.fight;

public enum NailPosType {
	middle("m"),
	left1("l1"),
	left2("l2"),
	right1("r1"),
	right2("r2"),
	down1("d1")
	;
	
	private final String code;
	
	private NailPosType(String code) {
		this.code = code;
	}

	public static NailPosType getNailPosTypeByCode(String code) {
		for (NailPosType posType : values()) {
			if(posType.code.equals(code)) {
				return posType;
			}
		}
		throw new RuntimeException("code is:" + code);
	}
	
	public String getCode() {
		return code;
	}
	
}
