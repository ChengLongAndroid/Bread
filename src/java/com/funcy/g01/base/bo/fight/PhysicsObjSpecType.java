package com.funcy.g01.base.bo.fight;

public enum PhysicsObjSpecType {
	//特殊类别:0，默认；1，气球；2，气功，3：炸弹，4：发射器，5：老鼠出生点，6：奶酪，7：萨满出生点，8：鼠洞,9：掉落配置,10.巧克力,11.符文 15熔岩
	normal(0), 
	balloon(1), 
	bomb(2), 
	bullet(3), 
	emitter(4), 
	mouseBorn(5), 
	cheese(6), 
	specMouseBorn(7), 
	cheeseDestination(8), 
	dropItem(9), 
	chocolates(10),
	fuwen(11),
	springBall(12), //弹簧球
	transferDoorA(13),
	transferDoorB(14),
	lava(15), //熔岩
	water(16),
	cloud(17),
	star(18);
	
	private final int code;
	
	private PhysicsObjSpecType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	public static PhysicsObjSpecType getByCode(int code) {
		for (PhysicsObjSpecType type : values()) {
			if(type.getCode() == code) {
				return type;
			}
		}
		throw new RuntimeException();
	}
}
