package com.funcy.g01.hall.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funcy.g01.base.bo.BoFactory;
import com.funcy.g01.base.bo.Currency;
import com.funcy.g01.base.bo.CurrencyType;
import com.funcy.g01.base.bo.chest.ChestSlot;
import com.funcy.g01.base.bo.chest.ChestType;
import com.funcy.g01.base.bo.chest.RoleChestInfo;
import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.bo.item.RoleItemInfo;
import com.funcy.g01.base.bo.mysteriousshop.ExchangeRecord;
import com.funcy.g01.base.bo.mysteriousshop.RoleMysteriousShopInfo;
import com.funcy.g01.base.bo.reward.DisplayReward;
import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.bo.signin.RoleSigninInfo;
import com.funcy.g01.base.dao.redis.ActiveRepo;
import com.funcy.g01.base.dao.redis.ItemRepo;
import com.funcy.g01.base.dao.redis.RoleRepo;
import com.funcy.g01.base.data.ActiveData;
import com.funcy.g01.base.data.ChestProperty;
import com.funcy.g01.base.data.DropGroupData;
import com.funcy.g01.base.data.DropGroupProperty;
import com.funcy.g01.base.data.MysteriousShopPeriodProperty;
import com.funcy.g01.base.data.MysteriousShopProperty;
import com.funcy.g01.base.data.SigninPeriodProperty;
import com.funcy.g01.base.data.SigninProperty;
import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;
import com.funcy.g01.base.net.GamePlayer;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ActiveInfoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleChestInfoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleMysteriousShopInfoProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleSigninInfoProto;
import com.funcy.g01.base.proto.service.ActiveServiceRespProtoBuffer.OpenChestOnSlotProto;
import com.google.protobuf.Message.Builder;


@Service("activeService")
public class ActiveService {

	public static final long one_day_time = 24*60*60*1000; 
	
	@Autowired
	private ActiveRepo activeRepo;//
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private ActiveData activeData;//
	
	@Autowired
	private ItemDomainService itemDomainService;
	
	@Autowired
	private DropGroupData dropGroupData;
	
	@Autowired
	private ItemRepo itemRepo;
	
	public Builder  openChestOnSlot(int index, GamePlayer gamePlayer){  //3
		RoleChestInfo roleChestInfo = activeRepo.getRoleChestInfo(gamePlayer.getRoleId());
		ChestSlot chestSlot = roleChestInfo.findChestSlotByIndex(index);
		if (chestSlot.getChestType() == ChestType.none) {
			throw new BusinessException(ErrorCode.chest_slot_no_chest);
		}
		OpenChestOnSlotProto.Builder builder = OpenChestOnSlotProto.newBuilder();
		List<Item> rewards = new ArrayList<Item>();
		ChestProperty chestProperty = activeData.getChestProperty(chestSlot.getChestType().getCode());
		if (chestSlot.getCanOpenTime() == -1) {
			if (roleChestInfo.checkHadOtherOpening()) {
				rewards = this.costGemOpenChest(chestSlot, gamePlayer.getRoleId());
				builder.setIsOpen(true);
			} else {
				chestSlot.setCanOpenTime(chestProperty.getOpenTime() * 60 * 1000 + System.currentTimeMillis());
			}
		}else{
			rewards = this.costGemOpenChest(chestSlot, gamePlayer.getRoleId());
			builder.setIsOpen(true);
		}
		for (Item item : rewards) {
			builder.addItems(item.copyTo());
		}
		builder.setChestSlot(chestSlot.copyTo());
		activeRepo.saveRoleChestInfo(roleChestInfo);
		return builder;
	}
	
	public List<Item> costGemOpenChest(ChestSlot chestSlot,long roleId){ //2
		long leftTime = 0;
		ChestProperty chestProperty = activeData.getChestProperty(chestSlot.getChestType().getCode());
		if (chestSlot.getCanOpenTime() == -1) {
			leftTime = chestProperty.getOpenTime() * 60 * 1000;
		}else{
			leftTime = chestSlot.getCanOpenTime() - System.currentTimeMillis();
		}
		//TODO
		int needGem = (int) Math.ceil(leftTime / 1000 / 60 / 5);//5分钟一个钻
		if (needGem > 0) {
			Role role = roleRepo.getRole(roleId);
			role.spend(new Currency(CurrencyType.gem, needGem));
			roleRepo.saveRole(role);
		}
		List<Item> rewards = this.openChest(chestSlot.getChestType());
		chestSlot.setChestType(ChestType.none);
		return rewards;
	}
	
	public ChestSlot fightWin(long roleId){
		return this.addChestAfterFightWin(roleId);
	}
	
	public ChestSlot addChestAfterFightWin(long roleId){
		RoleChestInfo roleChestInfo = activeRepo.getRoleChestInfo(roleId);
		ChestSlot emptyChestSlot = roleChestInfo.getEmptySlot();
		boolean isDropChest = false;
		if (emptyChestSlot != null) {
			roleChestInfo.addWinNum();
			if (roleChestInfo.getWinNum() == RoleChestInfo.max_win_num) {
				isDropChest = true;
				roleChestInfo.addAccumulateChestNum();
				ChestType chestType;
				int newerChestMapSize = activeData.getNewerChestInfoMap().size();
				if (roleChestInfo.getAccumulateChestNum() <= newerChestMapSize) {
					chestType = activeData.getNewerChestProperty(roleChestInfo.getAccumulateChestNum()).getType();
				}else{
					//概率为白银宝箱（65%），黄金宝箱（30%），超级宝箱（5%）
					 Random random = new Random();
					 int randomNum = random.nextInt(100);
					 if (randomNum >= 95) {
						 chestType = ChestType.giant;
					 }else if (randomNum >= 65 && randomNum < 95){
						 chestType = ChestType.gold;
					 }else{
						 chestType = ChestType.silver;
					 }
				}
				roleChestInfo.setWinNum(0);
				emptyChestSlot.setChestType(chestType);
				emptyChestSlot.setCanOpenTime(-1);
			}
			activeRepo.saveRoleChestInfo(roleChestInfo);
		}
		if (isDropChest) {
			return emptyChestSlot;
		}else{
			return null;
		}
	}
	
	
	public void addChest(long roleId, int index, int type){ //1
		RoleChestInfo roleChestInfo = activeRepo.getRoleChestInfo(roleId);
		if (index < 1 || index > 4) {
			throw new BusinessException(ErrorCode.WRONG_CODE);
		}
		ChestType chestType = ChestType.valueOf(type);
		ChestSlot chestSlot  = roleChestInfo.findChestSlotByIndex(index);
		chestSlot.setCanOpenTime(-1);
		chestSlot.setChestType(chestType);
		roleChestInfo.addAccumulateChestNum();
		activeRepo.saveRoleChestInfo(roleChestInfo);
	}
	
	public List<Item> openChest(ChestType chestType){
		ChestProperty chestProperty  = activeData.getChestProperty(chestType.getCode());
		List<Integer> dropGropIds = chestProperty.getDropGroupIds();
		List<Item> items = new ArrayList<Item>();
		for (Integer dropGroupId : dropGropIds) {
			DropGroupProperty dropGroupProperty = dropGroupData.findDropGroupProperty(dropGroupId);
			Item item = dropGroupProperty.drop();
			if (item != null) {
				items.add(item);
			}
		}
		return items;
	}
	
	public void resetSigninState(long roleId){
		RoleSigninInfo roleSigninInfo = activeRepo.getRoleSigninfo(roleId);
		roleSigninInfo.setTodaySignin(false);
		SigninProperty signinProperty = activeData.getCurrentSigninProperty();
		if (roleSigninInfo.getSigninRewardId() != signinProperty.getId()) {
			roleSigninInfo.setCurrentSigninDay(1);
			roleSigninInfo.setSigninRewardId(signinProperty.getId());
		}
		activeRepo.saveRoleSigninfo(roleSigninInfo);
	}
	
	public Builder signin(GamePlayer gamePlayer){
		RoleSigninInfo roleSigninInfo = activeRepo.getRoleSigninfo(gamePlayer.getRoleId());
		if (roleSigninInfo.isTodaySignin()) {
			throw new BusinessException(ErrorCode.today_had_sign_in);
		}
		SigninProperty signinProperty = activeData.getCurrentSigninProperty();
		if (signinProperty == null) {
			throw new BusinessException(ErrorCode.signin_time_out);
		}
		int day = roleSigninInfo.getCurrentSigninDay();
		SigninPeriodProperty signinPeriodProperty = activeData.getSigninPeriodProperty(signinProperty.getPeriodId(), day);
		DisplayReward displayReward = new DisplayReward();
		if (signinPeriodProperty.getDropGroupIds().size() > 0) {
			List<Integer> dropGropIds = signinPeriodProperty.getDropGroupIds();
			List<Item> items = new ArrayList<Item>();
			for (Integer dropGroupId : dropGropIds) {
				DropGroupProperty dropGroupProperty = dropGroupData.findDropGroupProperty(dropGroupId);
				Item item = dropGroupProperty.drop();
				if (item != null) {
					items.add(item);
				}
			}
			displayReward.setItems(items);
		}else{
			displayReward.setItems(BoFactory.createMultiItems(signinPeriodProperty.getReward()));
		}
		itemDomainService.checkNeedConvertItems(displayReward.getItems(), gamePlayer.getRoleId());
		itemDomainService.addItems(displayReward.getItems(), gamePlayer.getRoleId());
		roleSigninInfo.setTodaySignin(true);
		roleSigninInfo.setCurrentSigninDay(roleSigninInfo.getCurrentSigninDay() + 1);
		activeRepo.saveRoleSigninfo(roleSigninInfo);
		return displayReward.copyTo().toBuilder();
	}
	
	public void exchange(int activeShopId, int periodId, GamePlayer gamePlayer){
		MysteriousShopProperty mysteriousShopProperty = activeData.getMysteriousShopProperty(activeShopId);
		long nowTime = System.currentTimeMillis();
		if (mysteriousShopProperty.getStartTime() < (nowTime - one_day_time) || mysteriousShopProperty.getEndTime() > (nowTime + one_day_time)) {
			throw new BusinessException(ErrorCode.exchange_time_out);
		}
		
		RoleItemInfo roleItemInfo = itemRepo.getRoleItemInfo(gamePlayer.getRoleId());
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		MysteriousShopPeriodProperty mysteriousShopPeriodProperty = activeData.getMysteriousShopPeriodProperty(periodId);
		boolean isEnough = true;
		for (Item item : mysteriousShopPeriodProperty.getNeeds()) {
			if (item.getXmlId() == Item.CHEESE_XMLID) {
				if (role.getCheese() < item.getNum()) {
					isEnough = false;
					break;
				}
			}else if(item.getXmlId() == Item.GEM_XMLID){
				if (role.getGem() < item.getNum()) {
					isEnough = false;
					break;
				}
			}else{
				Item itemVo = roleItemInfo.findItemByXmlId(item.getXmlId());
				if (itemVo == null || itemVo.getNum() < item.getNum()) {
					isEnough = false;
					break;
				}
			}
		}
		if (!isEnough) {
			throw new BusinessException(ErrorCode.exchange_needs_not_enough);
		}
		
		RoleMysteriousShopInfo roleMysteriousShopInfo = activeRepo.getRoleMysteriousShopInfo(gamePlayer.getRoleId());
		ExchangeRecord exchangeRecord = roleMysteriousShopInfo.findExchangeRecord(activeShopId, periodId);
		if (exchangeRecord == null) {
			exchangeRecord = new ExchangeRecord(activeShopId, periodId, 0);
			roleMysteriousShopInfo.getRecords().add(exchangeRecord);
		}
		if (mysteriousShopPeriodProperty.getType() == 1) {
			if (!itemDomainService.checkIsHaveItem(mysteriousShopPeriodProperty.getExchangeItem(), gamePlayer.getRoleId())) {
				throw new BusinessException(ErrorCode.can_not_exsit_exchange);
			}
		}else{
			if (exchangeRecord.getExchangeNum() >= mysteriousShopPeriodProperty.getExchangeNum() ) {
				throw new BusinessException(ErrorCode.out_exchange_num);
			}
		}
		
		List<Item> exchangeItems = new ArrayList<Item>();
		exchangeItems.add(mysteriousShopPeriodProperty.getExchangeItem());
		itemDomainService.addItems(exchangeItems, gamePlayer.getRoleId());
		for (Item item : mysteriousShopPeriodProperty.getNeeds()) {
			if (item.getXmlId() == Item.CHEESE_XMLID ) {
				role.spend(new Currency(CurrencyType.cheese, item.getNum()));
			}else if(item.getXmlId() == Item.GEM_XMLID){
				role.spend(new Currency(CurrencyType.gem, item.getNum()));
			}else{
				itemDomainService.consumeItem(item.getXmlId(), item.getNum(), gamePlayer.getRoleId());
			}
		}
		exchangeRecord.setExchangeNum(exchangeRecord.getExchangeNum() + 1);
		roleRepo.saveRole(role);
		activeRepo.saveRoleMysteriousShopInfo(roleMysteriousShopInfo);
	}
	
	public ActiveInfoProto getActiveInfoProto(long roleId){
		ActiveInfoProto.Builder  builder = ActiveInfoProto.newBuilder();
		RoleChestInfo roleChestInfo = activeRepo.getRoleChestInfo(roleId);
		RoleSigninInfo roleSigninInfo = activeRepo.getRoleSigninfo(roleId);
		RoleMysteriousShopInfo roleMysteriousShopInfo = activeRepo.getRoleMysteriousShopInfo(roleId);
		builder.setRoleChestInfo((RoleChestInfoProto) roleChestInfo.copyTo());
		builder.setRoleSigninInfo((RoleSigninInfoProto) roleSigninInfo.copyTo());
		builder.setRoleMysteriousShopInfo((RoleMysteriousShopInfoProto) roleMysteriousShopInfo.copyTo());
		return builder.build();
	}
}
