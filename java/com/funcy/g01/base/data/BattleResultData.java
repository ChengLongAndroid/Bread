package com.funcy.g01.base.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.fight.FightRoomState;
import com.funcy.g01.base.util.DocumentBuilder;
import com.funcy.g01.dispatcher.bo.RoomType;

@Component
public class BattleResultData {
	
	private static final Logger logger  = Logger.getLogger(BattleResultData.class);
	
	private Map<Integer, BattleResultProperty> battleResultInfoMap = new HashMap<Integer, BattleResultProperty>();
	private Map<Integer, BattleResultValueProperty> battleResultVauleInfoMap = new HashMap<Integer, BattleResultValueProperty>();
	
	public void init(){
		this.initBattleResult();
		this.initBattleResultValue();
	}
	
	public void initBattleResult() {
		String url = "com/funcy/g01/xml/battleResult.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				String dropGroups = e.elementText("dropGroups");
				List<Integer> dropGroupIds = new ArrayList<Integer>();
				for (String dropId : dropGroups.split(",")) {
					dropGroupIds.add(Integer.parseInt(dropId));
				}
				List<RoomType> roomTypes = new ArrayList<RoomType>();
				String roomTypeStr = e.elementText("roomType");
				for (String roomType : roomTypeStr.split(",")) {
					roomTypes.add(RoomType.valueOf(Integer.parseInt(roomType)));
				}
				int dropFightState = Integer.parseInt(e.elementText("dropFightState"));
				FightRoomState fightRoomState = FightRoomState.valueOf(dropFightState);
				float dropRandom = Float.parseFloat(e.elementText("dropRandom"));
				this.battleResultInfoMap.put(id, new BattleResultProperty(id, dropGroupIds, roomTypes, fightRoomState, dropRandom));
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	public BattleResultProperty getBattleResultProperty(int xmlId) {
		return this.battleResultInfoMap.get(xmlId);
	}
	public void initBattleResultValue() {
		String url = "com/funcy/g01/xml/battleResultValue.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				int baseWinExp = Integer.parseInt(e.elementText("baseWinExp"));
				int shammanBaseWinExp = Integer.parseInt(e.elementText("shammanBaseWinExp"));
				int deadExp = Integer.parseInt(e.elementText("deadExp"));
				int baseWinCheese = Integer.parseInt(e.elementText("baseWinCheese"));
				int normalTypeExp = Integer.parseInt(e.elementText("normalTypeExp"));
				int advanceTypeExp = Integer.parseInt(e.elementText("advanceTypeExp"));
				List<Integer> rankingAddExpList = new ArrayList<Integer>();
				rankingAddExpList.add(Integer.valueOf(e.elementText("no1AddExp")));
				rankingAddExpList.add(Integer.valueOf(e.elementText("no2AddExp")));
				rankingAddExpList.add(Integer.valueOf(e.elementText("no3AddExp")));
				int helpMiceExp = Integer.parseInt(e.elementText("helpMiceExp"));
				this.battleResultVauleInfoMap.put(id, new BattleResultValueProperty(id, baseWinExp, shammanBaseWinExp, deadExp
						, baseWinCheese, normalTypeExp, advanceTypeExp, rankingAddExpList, helpMiceExp));
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	public BattleResultValueProperty getBattleResultValueProperty(int xmlId) {
		return this.battleResultVauleInfoMap.get(xmlId);
	}
	
}
