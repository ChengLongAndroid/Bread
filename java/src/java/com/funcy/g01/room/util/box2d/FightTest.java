package com.funcy.g01.room.util.box2d;

import org.jbox2d.common.Vec2;
import org.jbox2d.testbed.framework.TestbedTest;

import com.funcy.g01.base.bo.fight.FightRoom;

public class FightTest extends TestbedTest {
	
	private FightRoom fightRoom;
	
	public FightTest(FightRoom fightRoom) {
		this.fightRoom = fightRoom;
	}

	@Override
	public void initTest(boolean deserialized) {
//		this.fightRoom.initLand();
	}

	@Override
	public String getTestName() {
		return "temp";
	}

	@Override
	public Vec2 getDefaultCameraPos() {
		return new Vec2(1334 / 10f / 2, 750 / 10f / 2);
	}

	@Override
	public float getDefaultCameraScale() {
		return 10f;
	}
	
	
	
}
