package com.funcy.g01.base.bo.role;

import java.util.ArrayList;
import java.util.List;

import com.funcy.g01.base.bo.Currency;
import com.funcy.g01.base.bo.CurrencyType;
import com.funcy.g01.base.bo.dress.DressType;
import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.data.RoleData;
import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleInfoVoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleUseItemInfoProto;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.PlayerInfoProto;
import com.funcy.g01.base.util.TimeUtil;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class Role implements ProtobufSerializable {
	
    private long id;

    private String platformId;

    private String roleName;

    private int roleLevel;

    private int vipLevel;

    private String photo;
    
    private int lastSyncAt;

    private long loginTime;

    private long createTime;

    private String regIp;

    private String loginIp;

    private String token;
    
    private long cheese;
    
    private int gem;
    
    private int specMouseTypeId;
    
    private int maxSpecMouseTypeId;
    
    private List<Integer> dresses;

	private int currentExp;
	
	private int shammanLevel;
	
	private int shammanExp;
	
	private int unitXmlId;
	
	private int newComerStep;
	
	private boolean isEnableSpeech = true;
	
	private int sex;
	
	private int charm;
	
	private String declaration;
	
	private String city;
	
	private int championNum;
	
	private int rescueNum;
	
	private int achievePoint;
	
	private int continueLogin;
	
	private int cumulativeLogin;
	
	private int photoFrame;
	
	private int fansNum;
	
	private int followsNum;
	
	private int rechargeMoney;
	
	private boolean haveNewEmail;
	
	private boolean haveBuyOneYuanGift;
	
	private long monthCardExpirationTime;
	
	private long yearCardExpirationTime;
	
	private int checkpointId;
	
	private int recordShammanLevel;
	
	private int newComerDayRebornLeftNum;
	
	private int mouseFightNum;
	
	private int mouseWinNum;
	
	private int samanNum;
	
	private int samanWinNum;
	
	private List<RoleUseItemInfo> useItemInfos = new ArrayList<RoleUseItemInfo>();
	
	public static final int defaultNewComerDayRebornNum = 50;
	
	public static final int newComerRebornSkillId = 999999;

	public static class RoleUseItemInfo {
		public int itemXmlId;
		public int leftCount;
		public RoleUseItemInfo(int itemXmlId, int leftCount) {
			this.itemXmlId = itemXmlId;
			this.leftCount = leftCount;
		}
	}
	
    @SuppressWarnings("unused")
	private Role() {
	}

	public Role(long id, String platformId, String regIp) {
		this.id = id;
		this.platformId = platformId;
		this.roleName = "小灰灰" + id;
		this.roleLevel = 1;
		this.vipLevel = 0;
		this.photo = "1";
		this.lastSyncAt = TimeUtil.getTodayIntValue();
		this.loginTime = System.currentTimeMillis();
		this.createTime = loginTime;
		this.regIp = regIp;
		this.setCheese(10000);
		this.setGem(5000);
		this.initDresses();
		this.currentExp = 0;
		this.shammanLevel = 1;
		this.shammanExp = 0;
		this.unitXmlId = 10004;
		this.sex = 1;
		this.charm = 0;
		this.declaration = "这只小兔子很懒，什么都没写";
		this.city = "未知";
		this.championNum = 0;
		this.rescueNum = 0;
		this.achievePoint = 0;
		this.continueLogin = 0;
		this.cumulativeLogin = 0;
		this.photoFrame = 0;
		this.fansNum = 0;
		this.followsNum = 0;
		this.monthCardExpirationTime = 0;
		this.yearCardExpirationTime = 0;
		this.checkpointId = 0;
		this.specMouseTypeId = 1;
		this.maxSpecMouseTypeId = 1;
		this.recordShammanLevel = 1;
		this.newComerDayRebornLeftNum = defaultNewComerDayRebornNum;
	}

	public Role(byte[] bytes) {
		parseFrom(bytes);
	}
	
	public void initDresses(){
		this.setDresses(new ArrayList<Integer>());
		for (int i = 1; i <= 4; i++) {
			this.getDresses().add(DressType.valueOf(i).getDefaultId());
		}
	}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(int roleLevel) {
        this.roleLevel = roleLevel;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getLastSyncAt() {
        return lastSyncAt;
    }

    public void setLastSyncAt(int lastSyncAt) {
        this.lastSyncAt = lastSyncAt;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
	public int getNewComerStep() {
		return newComerStep;
	}

	public void setNewComerStep(int newComerStep) {
		this.newComerStep = newComerStep;
	}

	public RoleInfoVoProto.Builder buildFrontProto() {
		RoleInfoVoProto.Builder builder = RoleInfoVoProto.newBuilder();
		builder.setId(id).setPlatformId(platformId).setRoleName(roleName).setRoleLevel(roleLevel)
				.setVipLevel(vipLevel).setPhoto(photo).setLastSyncAt(lastSyncAt).setLoginTime(loginTime)
				.setCreateTime(createTime).setRegIp(regIp).setLoginIp(loginIp).setCheese(cheese)
				.setGem(gem).setCurrentExp(currentExp).setShammanExp(shammanExp).setShammanLevel(shammanLevel).setUnitXmlId(unitXmlId).setIsEnableSpeech(isEnableSpeech)
				.setCharm(charm).setCity(city).setSex(sex).setDeclaration(declaration).setContinueLogin(continueLogin).setCumulativeLogin(cumulativeLogin).setPhotoFrame(photoFrame)
				.setAchievePoint(achievePoint).setFansNum(fansNum).setFollowsNum(followsNum).setCheckpointId(checkpointId).setSpecMouseTypeId(this.specMouseTypeId)
				.setMaxSpecMouseTypeId(this.maxSpecMouseTypeId).setRecordShammanLevel(this.recordShammanLevel);
		for (Integer dressId : getDresses()) {
			builder.addDresses(dressId);
		}
		return builder;
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleInfoVoProto proto = (RoleInfoVoProto) message;
		this.id = proto.getId();
		this.platformId = proto.getPlatformId();
		this.roleName = proto.getRoleName();
		this.roleLevel = proto.getRoleLevel();
		this.vipLevel = proto.getVipLevel();
		this.photo = proto.getPhoto();
		this.lastSyncAt = proto.getLastSyncAt();
		this.loginTime = proto.getLoginTime();
		this.createTime = proto.getCreateTime();
		this.regIp = proto.getRegIp();
		this.loginIp = proto.getLoginIp();
		this.token = proto.getToken();
		this.cheese = proto.getCheese();
		this.gem = proto.getGem();
		this.dresses = new ArrayList<Integer>();
		for (Integer dressId : proto.getDressesList()) {
			this.dresses.add(dressId);
		}
		this.currentExp = proto.getCurrentExp();
		this.shammanExp = proto.getShammanExp();
		this.shammanLevel = proto.getShammanLevel();
		this.unitXmlId = proto.getUnitXmlId();
		this.newComerStep = proto.getNewComerStep();
		this.isEnableSpeech = proto.getIsEnableSpeech();
		this.sex = proto.getSex();
		this.charm = proto.getCharm();
		this.city = proto.getCity();
		this.declaration = proto.getDeclaration();
		this.championNum = proto.getChampionNum();
		this.rescueNum = proto.getRescueNum();
		this.achievePoint = proto.getAchievePoint();
		this.continueLogin = proto.getContinueLogin();
		this.cumulativeLogin = proto.getCumulativeLogin();
		this.photoFrame = proto.getPhotoFrame();
		this.fansNum = proto.getFansNum();
		this.followsNum = proto.getFollowsNum();
		this.haveBuyOneYuanGift = proto.getHaveBuyOneYuanGift();
		this.monthCardExpirationTime = proto.getMonthCardExpirationTime();
		this.yearCardExpirationTime = proto.getYearCardExpirationTime();
		this.checkpointId = proto.getCheckpointId();
		for(RoleUseItemInfoProto roleUseItemInfoProto : proto.getUseItemInfoList()) {
			this.useItemInfos.add(new RoleUseItemInfo(roleUseItemInfoProto.getItemXmlId(), roleUseItemInfoProto.getLeftCount()));
		}
		this.specMouseTypeId = proto.getSpecMouseTypeId();
		if(this.specMouseTypeId == 0) {
			this.specMouseTypeId = 1;
		}
		this.maxSpecMouseTypeId = proto.getMaxSpecMouseTypeId();
		if(this.maxSpecMouseTypeId == 0) {
			this.maxSpecMouseTypeId = 1;
		}
		this.recordShammanLevel = proto.getRecordShammanLevel();
		if(this.recordShammanLevel == 0) {
			this.recordShammanLevel = 1;
		}
		this.newComerDayRebornLeftNum = proto.getNewComerDayRebornLeftNum();
		this.mouseFightNum = proto.getMouseFightNum();
		this.mouseWinNum = proto.getMouseWinNum();
		this.samanWinNum = proto.getSamanWinNum();
		this.samanNum = proto.getSamanNum();
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleInfoVoProto.Builder builder = RoleInfoVoProto.newBuilder();
		builder.setId(this.id);
		builder.setPlatformId(this.platformId);
		builder.setRoleName(this.roleName);
		builder.setRoleLevel(this.roleLevel);
		builder.setVipLevel(this.vipLevel);
		builder.setPhoto(this.photo);
		builder.setLastSyncAt(this.lastSyncAt);
		builder.setLoginTime(this.loginTime);
		builder.setCreateTime(this.createTime);
		builder.setCheese(this.cheese);
		builder.setGem(this.gem);
		if(this.regIp != null) {
			builder.setRegIp(this.regIp);
		}
		if(this.loginIp != null) {
			builder.setLoginIp(this.loginIp);
		}
		if(this.token != null) {
			builder.setToken(this.token);
		}
		for (Integer dressId : getDresses()) {
			builder.addDresses(dressId);
		}
		builder.setCurrentExp(currentExp);
		builder.setShammanExp(shammanExp);
		builder.setShammanLevel(shammanLevel);
		builder.setNewComerStep(newComerStep);
		builder.setIsEnableSpeech(isEnableSpeech);
		builder.setCharm(charm);
		builder.setSex(sex);
		builder.setCity(city);
		builder.setDeclaration(declaration);
		
		builder.setChampionNum(this.championNum);
		builder.setRescueNum(this.rescueNum);
		builder.setAchievePoint(this.achievePoint);
		builder.setContinueLogin(continueLogin);
		builder.setCumulativeLogin(cumulativeLogin);
		builder.setPhotoFrame(photoFrame);
		builder.setCheckpointId(this.checkpointId);
		if (fansNum != 0) {
			builder.setFansNum(fansNum);
		}
		if (followsNum != 0) {
			builder.setFollowsNum(followsNum);
		}
		builder.setHaveBuyOneYuanGift(haveBuyOneYuanGift);
		if (monthCardExpirationTime != 0) {
			builder.setMonthCardExpirationTime(monthCardExpirationTime);
		}
		if (yearCardExpirationTime != 0) {
			builder.setYearCardExpirationTime(yearCardExpirationTime);
		}
		for(RoleUseItemInfo useItemInfo : this.useItemInfos) {
			builder.addUseItemInfo(RoleUseItemInfoProto.newBuilder().setItemXmlId(useItemInfo.itemXmlId).setLeftCount(useItemInfo.leftCount));
		}
		builder.setSpecMouseTypeId(this.specMouseTypeId);
		builder.setMaxSpecMouseTypeId(this.maxSpecMouseTypeId);
		builder.setRecordShammanLevel(this.recordShammanLevel);
		builder.setNewComerDayRebornLeftNum(this.newComerDayRebornLeftNum);
		
		builder.setSamanNum(this.samanNum);
		builder.setMouseWinNum(this.mouseWinNum);
		builder.setMouseFightNum(this.mouseFightNum);
		builder.setSamanWinNum(this.samanWinNum);
		return builder.build();
	}

	public int getCheckpointId() {
		return checkpointId;
	}

	public void setCheckpointId(int checkpointId) {
		this.checkpointId = checkpointId;
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleInfoVoProto proto = RoleInfoVoProto.parseFrom(bytes);
			copyFrom(proto);
		} catch (InvalidProtocolBufferException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}
	
	public PlayerInfoProto.Builder buildPlayerInfoProto4Front() {
		PlayerInfoProto.Builder builder = PlayerInfoProto.newBuilder();
		builder.setRoleId(this.id);
		builder.setRoleVo(this.buildFrontProto());
		return builder;
	}

	public long getCheese() {
		return cheese;
	}

	public void setCheese(long cheese) {
		this.cheese = cheese;
	}

	public int getGem() {
		return gem;
	}

	public void setGem(int gem) {
		this.gem = gem;
	}
	
	public void spend(Currency currency){
		CurrencyType currencyType = currency.getCurrencyType0();
		validateCurrencyPrice(currency);
		checkHaveEnoughCurrency(currency);
		int price = currency.getPrice();
		switch (currencyType) {
		case cheese:
			this.cheese -= price;
			break;
		case gem:
			this.gem -= price;
			break;
		default:
			throw new BusinessException(ErrorCode.WRONG_CURRENCY_TYPE);
		}
	}
	
	
	public void earn(Currency currency){
		CurrencyType currencyType = currency.getCurrencyType0();
		validateCurrencyPrice(currency);
		int price = currency.getPrice();
		switch (currencyType) {
		case cheese:
			this.cheese += price;
			break;
		case gem:
			this.gem += price;
			break;
		default:
			throw new BusinessException(ErrorCode.WRONG_CURRENCY_TYPE);
		}
	}
	
	private void validateCurrencyPrice(Currency currency) {
		int price = currency.getPrice();
		if(price < 0) {
			throw new BusinessException(ErrorCode.WRONG_CURRENCY_PRICE);
		}
	}
	
	private void checkHaveEnoughCurrency(Currency cost){
		CurrencyType currencyType = cost.getCurrencyType0();
		int price = cost.getPrice();
		switch (currencyType) {
		case cheese:
			if (this.cheese < price) {
				throw new BusinessException(ErrorCode.NOT_ENOUGH_CHEESE);
			}
			break;
		case gem:
			if (this.gem < price) {
				throw new BusinessException(ErrorCode.NOT_ENOUGH_GEM);
			}
			break;
		default:
			throw new BusinessException(ErrorCode.WRONG_CURRENCY_TYPE);
		}
	}
	
	public void addRechargeMoney(int money){
		this.rechargeMoney += money;
	}
	
	public void addCheese(int num){
		this.cheese += num;
	}
	
	public boolean addExp(int addExp, RoleData roleData){
		boolean levelChange = false;
		if (this.roleLevel >= RoleData.max_level) {
			return levelChange;
		}
		
		int levelUpNeedExp = roleData.findRoleLevelUpProperty(this.roleLevel).getNeedExp();
		this.setCurrentExp(this.getCurrentExp() + addExp);
		while(getCurrentExp() >= levelUpNeedExp) {
			this.roleLevel ++;
			levelChange = true;
			this.setCurrentExp(this.getCurrentExp() - levelUpNeedExp);
			if (this.roleLevel >= RoleData.max_level) {
				this.setCurrentExp(0);
				break;
			}
			levelUpNeedExp = roleData.findRoleLevelUpProperty(this.roleLevel).getNeedExp();
		}
		return levelChange;
		
	}
	public boolean addShammanExp(int addExp, RoleData roleData){
		boolean levelChange = false;
		if (this.shammanLevel >= RoleData.max_shamman_level) {
			return levelChange;
		}
		int levelUpNeedExp = roleData.findShammanLevelUpProperty(this.shammanLevel).getNeedExp();
		this.setShammanExp(this.getShammanExp() + addExp);
		while(getShammanExp() >= levelUpNeedExp) {
			this.shammanLevel ++;
			levelChange = true;
			this.setShammanExp(this.getShammanExp() - levelUpNeedExp);
			if (this.shammanLevel >= RoleData.max_shamman_level) {
				this.setShammanExp(0);
				break;
			}
			levelUpNeedExp = roleData.findShammanLevelUpProperty(this.shammanLevel).getNeedExp();
		}
		return levelChange;
		
	}
	
	public void addAchievePoint(int addNum){
		this.achievePoint += addNum;
	}

	public List<Integer> getDresses() {
		return dresses;
	}

	public void setDresses(List<Integer> dresses) {
		this.dresses = dresses;
	}

	public int getCurrentExp() {
		return currentExp;
	}

	public void setCurrentExp(int currentExp) {
		this.currentExp = currentExp;
	}

	public int getShammanLevel() {
		return shammanLevel;
	}

	public void setShammanLevel(int shammanLevel) {
		this.shammanLevel = shammanLevel;
	}

	public int getShammanExp() {
		return shammanExp;
	}

	public void setShammanExp(int shammanExp) {
		this.shammanExp = shammanExp;
	}

	public int getUnitXmlId() {
		return unitXmlId;
	}

	public void setUnitXmlId(int unitXmlId) {
		this.unitXmlId = unitXmlId;
	}

	public boolean isEnableSpeech() {
		return isEnableSpeech;
	}

	public void setEnableSpeech(boolean isEnableSpeech) {
		this.isEnableSpeech = isEnableSpeech;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getCharm() {
		return charm;
	}

	public void setCharm(int charm) {
		this.charm = charm;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDeclaration() {
		return declaration;
	}

	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}

	public int getChampionNum() {
		return championNum;
	}

	public void setChampionNum(int championNum) {
		this.championNum = championNum;
	}

	public int getRescueNum() {
		return rescueNum;
	}

	public void setRescueNum(int rescueNum) {
		this.rescueNum = rescueNum;
	}

	public int getAchievePoint() {
		return achievePoint;
	}

	public void setAchievePoint(int achievePoint) {
		this.achievePoint = achievePoint;
	}
	
	public int getContinueLogin() {
		return continueLogin;
	}

	public void setContinueLogin(int continueLogin) {
		this.continueLogin = continueLogin;
	}

	public int getCumulativeLogin() {
		return cumulativeLogin;
	}

	public void setCumulativeLogin(int cumulativeLogin) {
		this.cumulativeLogin = cumulativeLogin;
	}

	public int getPhotoFrame() {
		return photoFrame;
	}

	public void setPhotoFrame(int photoFrame) {
		this.photoFrame = photoFrame;
	}

	public int getFansNum() {
		return fansNum;
	}

	public void setFansNum(int fansNum) {
		this.fansNum = fansNum;
	}

	public void incRescueNum(int num) {
		this.rescueNum += num;
	}

	public void incChampionNum(int num) {
		this.championNum += num;
	}

	public int getFollowsNum() {
		return followsNum;
	}

	public void setFollowsNum(int followsNum) {
		this.followsNum = followsNum;
	}

	public int getRechargeMoney() {
		return rechargeMoney;
	}

	public void setRechargeMoney(int rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}

	public boolean isHaveNewEmail() {
		return haveNewEmail;
	}

	public void setHaveNewEmail(boolean haveNewEmail) {
		this.haveNewEmail = haveNewEmail;
	}

	public boolean isHaveBuyOneYuanGift() {
		return haveBuyOneYuanGift;
	}

	public void setHaveBuyOneYuanGift(boolean haveBuyOneYuanGift) {
		this.haveBuyOneYuanGift = haveBuyOneYuanGift;
	}

	public long getMonthCardExpirationTime() {
		return monthCardExpirationTime;
	}

	public void setMonthCardExpirationTime(long monthCardExpirationTime) {
		this.monthCardExpirationTime = monthCardExpirationTime;
	}

	public long getYearCardExpirationTime() {
		return yearCardExpirationTime;
	}

	public void setYearCardExpirationTime(long yearCardExpirationTime) {
		this.yearCardExpirationTime = yearCardExpirationTime;
	}

	public void changeUseItemLeftCount(int xmlId, int delta) {
		for(RoleUseItemInfo useItemInfo : this.useItemInfos) {
			if(useItemInfo.itemXmlId == xmlId) {
				useItemInfo.leftCount += delta;
				return;
			}
		}
		this.useItemInfos.add(new RoleUseItemInfo(xmlId, delta));
	}
	
	public int getUseItemLeftCount(int xmlId) {
		for(RoleUseItemInfo useItemInfo : this.useItemInfos) {
			if(useItemInfo.itemXmlId == xmlId) {
				return useItemInfo.leftCount;
			}
		}
		return 0;
	}

	public int getSpecMouseTypeId() {
		return specMouseTypeId;
	}

	public void setSpecMouseTypeId(int specMouseTypeId) {
		this.specMouseTypeId = specMouseTypeId;
	}

	public List<RoleUseItemInfo> getUseItemInfos() {
		return useItemInfos;
	}
		
	public int getMaxSpecMouseTypeId() {
		return maxSpecMouseTypeId;
	}

	public void setMaxSpecMouseTypeId(int maxSpecMouseTypeId) {
		this.maxSpecMouseTypeId = maxSpecMouseTypeId;
	}

	public int getRecordShammanLevel() {
		return recordShammanLevel;
	}

	public void setRecordShammanLevel(int recordShammanLevel) {
		this.recordShammanLevel = recordShammanLevel;
	}

	public int getNewComerDayRebornLeftNum() {
		return newComerDayRebornLeftNum;
	}

	public void setNewComerDayRebornLeftNum(int newComerDayRebornLeftNum) {
		this.newComerDayRebornLeftNum = newComerDayRebornLeftNum;
	}

	public int getMouseFightNum() {
		return mouseFightNum;
	}

	public void addMouseWinNum() {
		this.mouseWinNum++;
	}

	public int getMouseWinNum() {
		return mouseWinNum;
	}

	public void addSamanNum() {
		this.samanNum++;
	}

	public int getSamanNum() {
		return samanNum;
	}

	public void setSamanNum(int samanNum) {
		this.samanNum = samanNum;
	}
	
	public void addMouseFightNum() {
		this.mouseFightNum++;
	}

	public int getSamanWinNum() {
		return samanWinNum;
	}

	public void setSamanWinNum(int samanWinNum) {
		this.samanWinNum = samanWinNum;
	}
	
	public void addSamanWinNum() {
		this.samanWinNum++;
	}

}
