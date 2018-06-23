package com.funcy.g01.base.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.BoFactory;
import com.funcy.g01.base.bo.chest.ChestType;
import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.util.DocumentBuilder;

@Component
public class ActiveData {
	
	private static final Logger logger  = Logger.getLogger(ActiveData.class);
	
	private Map<Integer, ChestProperty> chestInfoMap = new HashMap<Integer, ChestProperty>();
	
	private Map<Integer, NewerChestProperty> newerChestInfoMap = new HashMap<Integer, NewerChestProperty>();
	
	private Map<Integer, SigninProperty> signinInfoMap = new HashMap<Integer, SigninProperty>();
	
	private Map<String, SigninPeriodProperty> signinPeriodInfoMap = new HashMap<String, SigninPeriodProperty>();
	
	private Map<Integer, MysteriousShopProperty> mysteriousShopInfoMap = new HashMap<Integer, MysteriousShopProperty>();
	
	private Map<Integer, MysteriousShopPeriodProperty> mysteriousShopPeriodInfoMap = new HashMap<Integer, MysteriousShopPeriodProperty>();
	
	public void init() {
		this.initChest();
		this.initNewerChest();
		this.initSignin();
		this.initSigninPeriod();
		this.initMysteriousShop();
		this.initMysteriousShopPeriod();
	}
	
	public void initChest(){
		String url = "com/funcy/g01/xml/chest.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				String dropGroups = e.elementText("dropGroups");
				List<Integer> dropGroupIds = new ArrayList<Integer>();
				for (String dropId : dropGroups.split(",")) {
					dropGroupIds.add(Integer.parseInt(dropId));
				}
				String name = e.elementText("name");
				int openTime = Integer.parseInt(e.elementText("time"));
				int type = Integer.parseInt(e.elementText("type"));
				this.chestInfoMap.put(type, new ChestProperty(id, name, ChestType.valueOf(type), openTime, dropGroupIds));
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	public void initNewerChest(){
		String url = "com/funcy/g01/xml/newerChest.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				int type = Integer.parseInt(e.elementText("type"));
				this.newerChestInfoMap.put(type, new NewerChestProperty(id, ChestType.valueOf(type)));
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	public void initSignin(){
		String url = "com/funcy/g01/xml/signinRewards.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				int periodId = Integer.parseInt(e.elementText("periodId"));
				String startTimeStr = e.elementText("startTime");
				String endTimeStr = e.elementText("endTime");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
				long startTime = 0;
				long endTime = 0;
				try {
					startTime = sdf.parse(startTimeStr).getTime();
					endTime = sdf.parse(endTimeStr).getTime();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				this.signinInfoMap.put(id, new SigninProperty(id, startTime, endTime, periodId));
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	

	public void initSigninPeriod(){
		String url = "com/funcy/g01/xml/signinRewardPeriods.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				int periodId = Integer.parseInt(e.elementText("periodId"));
				String dropGroups = e.elementText("dropGroups");
				List<Integer> dropGroupIds = new ArrayList<Integer>();
				if (!dropGroups.equals("")) {
					for (String dropId : dropGroups.split(",")) {
						dropGroupIds.add(Integer.parseInt(dropId));
					}
				}
				String reward = e.elementText("reward");
				int day = Integer.parseInt(e.elementText("day"));
				String key = periodId + "_" + day;
				this.signinPeriodInfoMap.put(key, new SigninPeriodProperty(id, periodId, reward, day, dropGroupIds));
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	
	public void initMysteriousShop(){
		String url = "com/funcy/g01/xml/mysteriousShops.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				int periodId = Integer.parseInt(e.elementText("periodId"));
				String startTimeStr = e.elementText("startTime");
				String endTimeStr = e.elementText("endTime");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
				long startTime = 0;
				long endTime = 0;
				try {
					startTime = sdf.parse(startTimeStr).getTime();
					endTime = sdf.parse(endTimeStr).getTime();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				this.mysteriousShopInfoMap.put(id, new MysteriousShopProperty(id, startTime, endTime, periodId));
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	
	public void initMysteriousShopPeriod(){
		String url = "com/funcy/g01/xml/mysteriousShopPeriods.xml";
		try {
			List<Element> elements = DocumentBuilder.build(url);
			for (Element e : elements) {
				int id = Integer.parseInt(e.elementText("id"));
				int periodId = Integer.parseInt(e.elementText("periodId"));
				int index = Integer.parseInt(e.elementText("index"));
				int exchangeNum = Integer.parseInt(e.elementText("exchangeNum"));
				int type = Integer.parseInt(e.elementText("type"));
				Item exchangeItem = BoFactory.createSingleItem(e.elementText("exchangeItem"));
				List<Item> needs = BoFactory.createMultiItems(e.elementText("needs"));
				this.mysteriousShopPeriodInfoMap.put(id, new MysteriousShopPeriodProperty(id, periodId, index, exchangeItem, needs, exchangeNum, type));
			}
			logger.info("init " + url + " success");
		} catch (DocumentException e) {
			logger.error("init " + url + " fail");
			e.printStackTrace();
		}
	}
	
	
	public ChestProperty getChestProperty(int type) {
		return this.chestInfoMap.get(type);
	}

	public Map<Integer, ChestProperty> getchestInfoMap() {
		return chestInfoMap;
	}
	
	public Map<Integer, SigninProperty> getSigninInfoMap() {
		return signinInfoMap;
	}
	
	public Map<Integer, NewerChestProperty> getNewerChestInfoMap() {
		return newerChestInfoMap;
	}
	
	
	public NewerChestProperty getNewerChestProperty(int id) {
		return this.newerChestInfoMap.get(id);
	}
	
	public SigninProperty getSigninProperty(int day) {
		return this.signinInfoMap.get(day);
	}
	
	public SigninProperty getCurrentSigninProperty(){
		long curTime = System.currentTimeMillis();
		for (SigninProperty signinProperty : this.signinInfoMap.values()) {
			if (curTime >= signinProperty.getStartTime() && curTime <= signinProperty.getEndTime()) {
				return signinProperty;
			}
		}
		return null;
	}
	
	
	public SigninPeriodProperty getSigninPeriodProperty(int periodId,int day) {
		String key = periodId + "_" + day;
		return this.signinPeriodInfoMap.get(key);
	}
	
	public MysteriousShopProperty getMysteriousShopProperty(int id) {
		return this.mysteriousShopInfoMap.get(id);
	}
	
	public MysteriousShopPeriodProperty getMysteriousShopPeriodProperty(int id) {
		return this.mysteriousShopPeriodInfoMap.get(id);
	}
	
}
