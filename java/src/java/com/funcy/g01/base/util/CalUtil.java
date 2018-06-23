package com.funcy.g01.base.util;

import org.jbox2d.common.Vec2;

public class CalUtil {

	public static Vec2 pForAngle(float angle) {
		return new Vec2((float)Math.cos(angle), (float)Math.sin(angle));
	}
	
	public static Vec2 pRotate(Vec2 pt1, Vec2 pt2) {
		return new Vec2(pt1.x * pt2.x - pt1.y * pt2.y, pt1.x * pt2.y + pt1.y * pt2.x);
	}
	
	public static Vec2 pRotateByDegree(Vec2 v, float degree) {
		return pRotate(v, pForAngle((float)Math.toRadians(degree)));
	}
	
	public static Vec2 pRotate(Vec2 v, float angle) {
		return pRotate(v, pForAngle(angle));
	}
	
	public static float pDot(Vec2 p1, Vec2 p2) {
		return p1.x * p2.x + p1.y * p2.y;
	}
	
	public static float pLengthSQ(Vec2 p) {
		return pDot(p, p);
	}
	
	public static Vec2 pSub(Vec2 pt1, Vec2 pt2) {
		return new Vec2(pt1.x - pt2.x, pt1.y - pt2.y);
	}
	
	public static float pDistance(Vec2 pt1, Vec2 pt2) {
		return (float)Math.sqrt(pDistanceSQ(pt1, pt2));
	}
	
	public static float pDistanceSQ(Vec2 pt1, Vec2 pt2) {
		return pLengthSQ(pSub(pt1,pt2));
	}
	
	public static float calAngle(Vec2 pt1, Vec2 pt2) {
		return pToAngleSelf(pSub(pt1, pt2));
	}
	
	public static float pToAngleSelf(Vec2 p) {
		return (float)Math.atan2(p.y, p.x);
	}
}
