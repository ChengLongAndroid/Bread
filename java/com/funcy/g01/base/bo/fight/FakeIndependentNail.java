package com.funcy.g01.base.bo.fight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import com.funcy.g01.base.data.PhysicsNailProperty;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.FakeIndependentNailProto;

public class FakeIndependentNail {

	private FakeIndependentNailProto proto;
	
	private FightRoom fightRoom;
	
	private PhysicsNailProperty physicsNailProperty;

	public FakeIndependentNail(FightRoom fightRoom, FakeIndependentNailProto proto) {
		this.fightRoom = fightRoom;
		this.proto = proto;
		this.physicsNailProperty = fightRoom.getPhysicsNailData().getPhysicsNailProperty(proto.getXmlId());
	}
	
	public void activate() {
		Vec2 pos = new Vec2(proto.getPosX() / 1000f, proto.getPosY() / 1000f);
		
		int connectObjType = this.physicsNailProperty.getConnectObjType();
		switch (connectObjType) {
		case 1: //墙
		{
			for(PhysicsObj physicsObj : this.fightRoom.getPhysicsObjs().values()) {
				if(physicsObj.getBody().getType() != BodyType.STATIC && physicsObj.getLayerNum() < this.proto.getLayerNum() || (physicsObj.getLayerNum() == this.proto.getLayerNum() && physicsObj.getId() < this.proto.getId())) {
					if(physicsObj.getBody().getFixtureList().testPoint(pos)) {
						RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
						revoluteJointDef.initialize(this.fightRoom.getBackgroundPhysicsObj().getBody(), physicsObj.getBody(), pos);
						World world = fightRoom.getWorld();
						RevoluteJoint joint = (RevoluteJoint) world.createJoint(revoluteJointDef);
						joint.enableLimit(false);
						Vec2 localPos = physicsObj.getBody().getLocalPoint(pos);
						PhysicsNail nail = new PhysicsNail(fightRoom, physicsNailProperty, physicsObj, joint, localPos);
						nail.initMotor(joint);
						joint.setUserData(nail);
						nail.setActivate();
						physicsObj.addNail(nail);
						physicsObj.getBody().getFixtureList().getFilterData().groupIndex = -this.proto.getId();
					}
				}
			}
			break;
		}
		case 2://物体
		{
			List<PhysicsObj> physicsObjs = getSortedPhysicsObjs(pos);
			if(physicsObjs.size() < 2) {
				return;
			}
			PhysicsObj base = physicsObjs.get(0);
			int groupIndex = -this.proto.getId();
			if(base.getBody().getFixtureList().getFilterData().groupIndex != 0) {
				groupIndex = base.getBody().getFixtureList().getFilterData().groupIndex;
			} else {
				base.getBody().getFixtureList().getFilterData().groupIndex = groupIndex;
			}
			for (int i = 1; i < physicsObjs.size(); i++) {
				PhysicsObj physicsObj = physicsObjs.get(i);
				RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
				revoluteJointDef.initialize(base.getBody(), physicsObj.getBody(), pos);
				World world = fightRoom.getWorld();
				RevoluteJoint joint = (RevoluteJoint) world.createJoint(revoluteJointDef);
				joint.enableLimit(!this.physicsNailProperty.isCanRotate());
				if(!this.physicsNailProperty.isCanRotate()) {
					joint.setLimits((float)Math.toRadians(-3), (float)Math.toRadians(3));
				}
				Vec2 localPos = physicsObj.getBody().getLocalPoint(pos);
				PhysicsNail nail = new PhysicsNail(fightRoom, physicsNailProperty, physicsObj, joint,localPos);
				Vec2 otherLocalPos = base.getBody().getLocalPoint(pos);
				nail.initOtherObjInfo(base, otherLocalPos);
				nail.initMotor(joint);
				joint.setUserData(nail);
				nail.setActivate();
				physicsObj.addNail(nail);
				physicsObj.getBody().getFixtureList().getFilterData().groupIndex = groupIndex;
			}
			break;
		}
		}
	}
	
	public List<PhysicsObj> getSortedPhysicsObjs(Vec2 pos) {
		List<PhysicsObj> result = new ArrayList<PhysicsObj>();
		for(PhysicsObj physicsObj : this.fightRoom.getPhysicsObjs().values()) {
			if(physicsObj.getBody().getType() != BodyType.STATIC && physicsObj.getBody().getFixtureList().testPoint(pos)) {
				result.add(physicsObj);
			}
		}
		Collections.sort(result, new Comparator<PhysicsObj>() {
			@Override
			public int compare(PhysicsObj o1, PhysicsObj o2) {
				if(o1.getLayerNum() < o2.getLayerNum()) {
					return -1;
				} else if(o1.getLayerNum() == o2.getLayerNum()) {
					if(o1.getId() < o2.getId()) {
						return -1;
					} else {
						return 1;
					}
				} else {
					return 1;
				}
			}
		});
		return result;
	}
	
}
