package com.funcy.g01.hall.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funcy.g01.base.bo.BoFactory;
import com.funcy.g01.base.bo.Currency;
import com.funcy.g01.base.bo.CurrencyType;
import com.funcy.g01.base.bo.achieve.Achievement;
import com.funcy.g01.base.bo.achieve.AchievementType;
import com.funcy.g01.base.bo.achieve.RoleAchieveInfo;
import com.funcy.g01.base.bo.achieve.UpdateAchievement;
import com.funcy.g01.base.bo.dress.DressType;
import com.funcy.g01.base.bo.dress.DressVo;
import com.funcy.g01.base.bo.dress.RoleDressInfo;
import com.funcy.g01.base.bo.frame.FrameVo;
import com.funcy.g01.base.bo.frame.RoleFrameInfo;
import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.bo.item.ItemType;
import com.funcy.g01.base.bo.item.RoleItemInfo;
import com.funcy.g01.base.bo.npc.Npc;
import com.funcy.g01.base.bo.npc.NpcTask;
import com.funcy.g01.base.bo.npc.NpcTaskType;
import com.funcy.g01.base.bo.npc.RoleNpcsInfo;
import com.funcy.g01.base.bo.reward.DisplayReward;
import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.bo.totem.RoleSkillInfo;
import com.funcy.g01.base.bo.totem.SkillVo;
import com.funcy.g01.base.bo.totem.TotemType;
import com.funcy.g01.base.dao.redis.AchievementRepo;
import com.funcy.g01.base.dao.redis.DressRepo;
import com.funcy.g01.base.dao.redis.ItemRepo;
import com.funcy.g01.base.dao.redis.NpcRepo;
import com.funcy.g01.base.dao.redis.PhotoFrameRepo;
import com.funcy.g01.base.dao.redis.RoleRepo;
import com.funcy.g01.base.dao.redis.TotemRepo;
import com.funcy.g01.base.data.DressData;
import com.funcy.g01.base.data.DressProperty;
import com.funcy.g01.base.data.ItemData;
import com.funcy.g01.base.data.ItemProperty;
import com.funcy.g01.base.data.NpcData;
import com.funcy.g01.base.data.NpcTaskProperty;
import com.funcy.g01.base.data.PhotoFrameData;
import com.funcy.g01.base.data.SkillData;
import com.funcy.g01.base.data.SkillProperty;
import com.funcy.g01.base.data.TotemData;
import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;
import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.net.CmdStatus;
import com.funcy.g01.base.net.GamePlayer;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.DisplayResultProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.DisplayResultProto.Builder;

@Service
public class ItemDomainService {

	@Autowired
	private TotemRepo totemRepo;
	
	@Autowired
	private ItemRepo itemRepo;
	
	@Autowired
	private DressRepo dressRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private ItemData itemData;
	
	@Autowired
	private DressData dressData;
	
	@Autowired
	private TotemData totemData;
	
	@Autowired
	private SkillData skillData;
	
	@Autowired
	private PhotoFrameData photoFrameData;
	
	@Autowired
	private PhotoFrameRepo photoFrameRepo;
	
	@Autowired
	private AchievementService achievementService;
	
	@Autowired
	private AchievementRepo achievementRepo;
	
	@Autowired
	private NpcRepo npcRepo;
	
	@Autowired
	private NpcData npcData;
	
	@Autowired
	private ServerContext serverContext;
	
	private final Logger logger = Logger.getLogger(ItemDomainService.class);
	
	public void addItems(List<Item> items, long roleId) {
		this.addItems(items, roleId, -1);
	}
	
	public void addItems(List<Item> items, long roleId, int npcId){
		RoleNpcsInfo roleNpcsInfo = npcRepo.getRoleNpcsInfo(roleId);
		boolean isNpcInfoNeedSave = false;
		List<UpdateAchievement> updateAchievements = new ArrayList<UpdateAchievement>();
		RoleAchieveInfo  roleAchieveInfo = achievementRepo.getRoleAchieveInfo(roleId);
		RoleItemInfo roleItemInfo = null;
		
		for (Item item : items) {
			if (item.checkIsCheese()) {
				Role role = roleRepo.getRole(roleId);
				Currency currency = new Currency(CurrencyType.cheese, item.getNum());
				role.earn(currency);
				roleRepo.saveRole(role);
			} else if(item.checkIsGem()){
				Role role = roleRepo.getRole(roleId);
				Currency currency = new Currency(CurrencyType.gem, item.getNum());
				role.earn(currency);
				roleRepo.saveRole(role);
			} else if(item.isNpcLikes()){
				Npc npc = roleNpcsInfo.findNpcByXmlId(npcId);
				isNpcInfoNeedSave = true;
				npc.addExp(item.getNum());
			}else{
				switch (item.getType()) {
				case item:
					if(roleItemInfo == null) {
						roleItemInfo = itemRepo.getRoleItemInfo(roleId);
					}
					int xmlId = item.getXmlId();
					Item tempItem = roleItemInfo.findItemByXmlId(xmlId);
					if (tempItem == null) {
						roleItemInfo.addItem(item);
					}else{
						tempItem.addNum(item.getNum());
					}
					break;
				case frame:
					FrameVo frameVo = photoFrameRepo.addFrame(roleId, item);
					if (frameVo != null) {
						updateAchievements.add(new UpdateAchievement(roleId, AchievementType.photo_frame_quality, 1));
					}
					break;
				case skill:
					SkillVo skillVo = totemRepo.addSkill(roleId, item);
					if (skillVo != null) {
						SkillProperty skillProperty = skillData.getSkillProperty(skillVo.getXmlId());
						switch (TotemType.valueOf(skillProperty.getType())) {
						case fire:
							updateAchievements.add(new UpdateAchievement(roleId, AchievementType.totem_fire_quality, 1));
							break;
						case wind:
							updateAchievements.add(new UpdateAchievement(roleId, AchievementType.totem_wind_quality, 1));
							break;
						case water:
							updateAchievements.add(new UpdateAchievement(roleId, AchievementType.totem_water_quality, 1));
							break;
						default:
							break;
						}
						updateAchievements.add(new UpdateAchievement(roleId, AchievementType.totem_total_quality, 1));
					}
					break;
				case dress:
					DressVo dressVo = dressRepo.addDress(roleId, item);
					if (dressVo != null ) {
						DressProperty dressProperty = dressData.getDressProperty(dressVo.getXmlId());
						switch (DressType.valueOf(dressProperty.getType())) {
						case tail:
							updateAchievements.add(new UpdateAchievement(roleId, AchievementType.dress_tail_quality, 1));
							Achievement specAchievement1 = roleAchieveInfo.findAchievementByType(AchievementType.dress_tail_spec);
							if (specAchievement1.getAchievementProperty().getGradeParam2().equals(dressVo.getXmlId()+"")) {
								updateAchievements.add(new UpdateAchievement(roleId, AchievementType.dress_tail_spec, 1));
							}
							break;
						case skin:
							updateAchievements.add(new UpdateAchievement(roleId, AchievementType.dress_skin_quality, 1));
							Achievement specAchievement2 = roleAchieveInfo.findAchievementByType(AchievementType.dress_skin_spec);
							if (specAchievement2.getAchievementProperty().getGradeParam2().equals(dressVo.getXmlId()+"")) {
								updateAchievements.add(new UpdateAchievement(roleId, AchievementType.dress_skin_spec, 1));
							}
							break;
						case face:
							updateAchievements.add(new UpdateAchievement(roleId, AchievementType.dress_face_quality, 1));
							Achievement specAchievement3 = roleAchieveInfo.findAchievementByType(AchievementType.dress_face_spec);
							if (specAchievement3.getAchievementProperty().getGradeParam2().equals(dressVo.getXmlId()+"")) {
								updateAchievements.add(new UpdateAchievement(roleId, AchievementType.dress_face_spec, 1));
							}
							break;
						case hair:
							updateAchievements.add(new UpdateAchievement(roleId, AchievementType.dress_hair_quality, 1));
							Achievement specAchievement4 = roleAchieveInfo.findAchievementByType(AchievementType.dress_hair_spec);
							if (specAchievement4.getAchievementProperty().getGradeParam2().equals(dressVo.getXmlId()+"")) {
								updateAchievements.add(new UpdateAchievement(roleId, AchievementType.dress_hair_quality, 1));
							}
							break;
						default:
							break;
						}
					}
					break;
				default:
					break;
				}
			}
		}
		
		if(roleItemInfo != null) {
			itemRepo.saveRoleItemInfo(roleItemInfo);
		}
		
		//检查npc任务
		if(roleItemInfo != null) {
			for (NpcTask npcTask : roleNpcsInfo.getNpcTasks()) {
				NpcTaskProperty npcTaskProperty = npcData.getNpcTaskProperty(npcTask.getXmlId());
				if(npcTask.getState() == 1 && npcTaskProperty.getType0() == NpcTaskType.item) {
					for (Item addItem : items) {
						if(addItem.getXmlId() == npcTaskProperty.getNeedItemXmlId()) {
							Item item = roleItemInfo.findItemByXmlId(npcTaskProperty.getNeedItemXmlId());
							int completeNum = Math.min(npcTaskProperty.getNeedCompleteNum(), item.getNum());
							if(completeNum != npcTask.getCompleteNum()) {
								npcTask.setCompleteNum(completeNum);
								isNpcInfoNeedSave = true;
								GamePlayer gamePlayer = serverContext.findHallLogonPlayer(roleId);
								if(gamePlayer == null) {
									gamePlayer = serverContext.findFightLogonPlayer(roleId);
								}
								if(gamePlayer != null) {
									gamePlayer.forceRespond("notifyNpcTaskChange", CmdStatus.notDecrypt, npcTask.copyTo().toBuilder());
								}
							}
						}
					}
					
					
				}
			}
		}
		
		if(isNpcInfoNeedSave) {
			npcRepo.saveRoleNpcsInfo(roleNpcsInfo);
		} 
		
		
		achievementService.updateAchievements(roleId, updateAchievements);
	}
	
	public void cell(int xmlId, int num, GamePlayer gamePlayer){
		ItemProperty itemProperty = itemData.getItemProperty(xmlId);
		if (!itemProperty.isSell()) {
			throw new BusinessException(ErrorCode.ITEM_CAN_NOT_CELL);
		}
		List<Item> items = new ArrayList<Item>();
		items.add(new Item(ItemType.item, xmlId, num));
		this.consumeItems(items, gamePlayer.getRoleId());
		int totalPrice = itemProperty.getPrice() * num;
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		Currency currency = new Currency(CurrencyType.cheese, totalPrice);
		role.earn(currency);
		roleRepo.saveRole(role);
	}
	
	public void consumeItem(int xmlId, int num, long roleId) {
		List<Item> items = new ArrayList<Item>();
		items.add(new Item(ItemType.item, xmlId, num));
		this.consumeItems(items, roleId);
	}
	
	public void consumeItems(List<Item> needItems, long roleId) {
		RoleItemInfo roleItemInfo  = itemRepo.getRoleItemInfo(roleId);
		for(Item needItem : needItems) {
			Item item = roleItemInfo.findItemByXmlId(needItem.getXmlId());
			if(item == null) {
				throw new BusinessException(ErrorCode.NOT_ENOUGH_ITEM);
			}
			roleItemInfo.use(needItem.getXmlId(), needItem.getNum());
		}
		itemRepo.saveRoleItemInfo(roleItemInfo);
		
		boolean isNpcInfoNeedSave = false;
		RoleNpcsInfo roleNpcsInfo = npcRepo.getRoleNpcsInfo(roleId);
		for (NpcTask npcTask : roleNpcsInfo.getNpcTasks()) {
			NpcTaskProperty npcTaskProperty = npcData.getNpcTaskProperty(npcTask.getXmlId());
			if(npcTask.getState() == 1 && npcTaskProperty.getType0() == NpcTaskType.item) {
				for (Item addItem : needItems) {
					if(addItem.getXmlId() == npcTaskProperty.getNeedItemXmlId()) {
						Item item = roleItemInfo.findItemByXmlId(npcTaskProperty.getNeedItemXmlId());
						int completeNum = Math.min(npcTaskProperty.getNeedCompleteNum(), item.getNum());
						if(completeNum != npcTask.getCompleteNum()) {
							npcTask.setCompleteNum(completeNum);
							isNpcInfoNeedSave = true;
							GamePlayer gamePlayer = serverContext.findHallLogonPlayer(roleId);
							if(gamePlayer == null) {
								gamePlayer = serverContext.findFightLogonPlayer(roleId);
							}
							if(gamePlayer != null) {
								gamePlayer.forceRespond("notifyNpcTaskChange", CmdStatus.notDecrypt, npcTask.copyTo().toBuilder());
							}
						}
					}
				}
				
			}
		}
		if(isNpcInfoNeedSave) {
			npcRepo.saveRoleNpcsInfo(roleNpcsInfo);
		}
	}
	
	public Builder useItem(int xmlId, int num, GamePlayer gamePlayer){
		ItemProperty itemProperty = itemData.getItemProperty(xmlId);
		if (!itemProperty.isUse()) {
			throw new BusinessException(ErrorCode.ITEM_CAN_NOT_USE);
		}
		this.consumeItem(xmlId, num, gamePlayer.getRoleId());
		DisplayReward displayReward = new DisplayReward();
		if (!itemProperty.getAwardStr().equals("")) {
			List<Item> itemList = itemProperty.getAwardItemList();
			for (Item item2 : itemList) {
				item2.setNum(item2.getNum() * num);
			}
			displayReward.addItems(itemList);
		}
		this.addItems(displayReward.getItems(), gamePlayer.getRoleId());
		return displayReward.copyTo().toBuilder();
	}
	
	public boolean checkIsHaveItem(Item item, long roleId){
		boolean isHave = false;
		switch (item.getType()) {
		case item:
			RoleItemInfo roleItemInfo = itemRepo.getRoleItemInfo(roleId);
			if (roleItemInfo.findItemByXmlId(item.getXmlId()) != null) {
				isHave = true;
			}
			break;
		case skill:
			RoleSkillInfo roleSkillInfo = totemRepo.getRoleSkillInfo(roleId);
			if (roleSkillInfo.checkHadSkill(item.getXmlId())) {
				isHave = true;
			}
			break;
		case dress:
			RoleDressInfo roleDressInfo = dressRepo.getRoleDressInfo(roleId);
			if (roleDressInfo.checkHadDress(item.getXmlId())) {
				isHave = true;
			}
			break;
		case frame:
			RoleFrameInfo roleFrameInfo = photoFrameRepo.getRoleFrameInfo(roleId);
			if (roleFrameInfo.checkHadFrame(item.getXmlId())) {
				isHave = true;
			}
			break;
		default:
			break;
		}
		return isHave;
	}
	
	public void checkNeedConvertItems(List<Item> items,long roleId){
		List<Item> convertItems = new ArrayList<Item>();
		for (Item item : items) {
			if (item.getType() == ItemType.skill) {
				if (this.checkIsHaveItem(item, roleId)) {
					SkillProperty skillProperty = skillData.getSkillProperty(item.getXmlId());
					convertItems.addAll(skillProperty.getConvertItems());
					items.remove(item);
				}
			}else if(item.getType() == ItemType.dress){
				if (this.checkIsHaveItem(item, roleId)) {
					DressProperty dressProperty = dressData.getDressProperty(item.getXmlId());
					convertItems.addAll(dressProperty.getConvertItems());
					items.remove(item);
				}
			}
		}
		items.addAll(convertItems);
	}
}
