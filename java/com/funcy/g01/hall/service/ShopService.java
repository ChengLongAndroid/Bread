package com.funcy.g01.hall.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebo.synframework.nettybase.protoPool.ProtoBuilderPoolManager;
import com.ebo.synframework.nettybase.protoPool.ReusedProtoBuilder;
import com.funcy.g01.base.bo.BoFactory;
import com.funcy.g01.base.bo.Currency;
import com.funcy.g01.base.bo.CurrencyType;
import com.funcy.g01.base.bo.achieve.AchievementType;
import com.funcy.g01.base.bo.achieve.UpdateAchievement;
import com.funcy.g01.base.bo.chest.ChestType;
import com.funcy.g01.base.bo.email.Email;
import com.funcy.g01.base.bo.email.EmailType;
import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.bo.npc.Npc;
import com.funcy.g01.base.bo.npc.RoleNpcsInfo;
import com.funcy.g01.base.bo.recharge.RechargeError;
import com.funcy.g01.base.bo.recharge.RechargeOrder;
import com.funcy.g01.base.bo.recharge.RechargeState;
import com.funcy.g01.base.bo.recharge.RechargeType;
import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.base.dao.redis.EmailRepo;
import com.funcy.g01.base.dao.redis.ItemRepo;
import com.funcy.g01.base.dao.redis.NpcRepo;
import com.funcy.g01.base.dao.redis.RoleRepo;
import com.funcy.g01.base.dao.redis.TotemRepo;
import com.funcy.g01.base.dao.sql.RechargeDao;
import com.funcy.g01.base.data.NpcData;
import com.funcy.g01.base.data.NpcShopProperty;
import com.funcy.g01.base.data.ShopData;
import com.funcy.g01.base.data.ShopProperty;
import com.funcy.g01.base.data.WordData;
import com.funcy.g01.base.data.WordPosition;
import com.funcy.g01.base.data.WordProperty;
import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;
import com.funcy.g01.base.global.BusinessPool;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.net.GamePlayer;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.DisplayResultProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.IntReqProto;
import com.funcy.g01.base.sdk.RechargeEnv;
import com.funcy.g01.base.sdk.ios.ValiDatedReceipt;
import com.funcy.g01.base.sdk.ios.ValidateInfo;
import com.funcy.g01.base.util.JSONUtils;
import com.funcy.g01.base.util.LoggerType;
import com.google.protobuf.Message.Builder;

@Service
public class ShopService {
	private final Logger logger = Logger.getLogger(LoggerType.recharge.name());
	@Autowired
	private ShopData shopData;
	
	@Autowired
	private NpcData npcData;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private ItemRepo itemRepo;
	
	@Autowired
	private TotemRepo totemRepo;
	
	@Autowired
	private RechargeDao rechargeDao;
	
	@Autowired
	private NpcRepo npcRepo;
	
	@Autowired
	private ItemDomainService itemDomainService;
	
	@Autowired
	private AchievementService achievementService;
	
	@Autowired
	private ActiveService activeService;
	
	@Autowired
	private BusinessPool businessPool;
	
	@Autowired
	private ServerInfoData serverInfoData;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private WordData wordData;
	
	@Autowired
	private EmailRepo emailRepo;
	
	private static final String IOS_VERIFY_URL = "https://buy.itunes.apple.com/verifyReceipt";
	
	private static final String IOS_SANDBOX_URL = "https://sandbox.itunes.apple.com/verifyReceipt";
		
	private static final int IOS_CHANNEL_ID = 1000;
	
	public void buy(int xmlId, GamePlayer gamePlayer){
		ShopProperty shopProperty = shopData.getShopProperty(xmlId);
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		if (role.getRoleLevel() < shopProperty.getLimitLv()) {
			throw new BusinessException(ErrorCode.BUY_LEVEL_LIMIT);
		}
		int price = (int) (shopProperty.getDiscount() * shopProperty.getPrice().intValue() * 0.1);
		Currency currency = null;
		if (shopProperty.getSellType() == 1) {
			currency = new Currency(CurrencyType.gem, price);
		}else if(shopProperty.getSellType() == 2){
			currency = new Currency(CurrencyType.cheese, price);
		}
		role.spend(currency);
		roleRepo.saveRole(role);
		List<Item> goodList = BoFactory.createMultiItems(shopProperty.getGoods());
		itemDomainService.addItems(goodList, gamePlayer.getRoleId());
		List<UpdateAchievement> updateAchievements = new ArrayList<UpdateAchievement>();
		for (Item item : goodList) {
			if (item.getXmlId() == Item.CHEESE_XMLID) {
				updateAchievements.add(new UpdateAchievement(gamePlayer.getRoleId(), AchievementType.gem_buy_cheese_quality, item.getNum()));
			}
		}
		updateAchievements.add(new UpdateAchievement(gamePlayer.getRoleId(), AchievementType.shopping_quality, 1));
		achievementService.updateAchievements(gamePlayer.getRoleId(), updateAchievements);
	}
	
	public void buyAtNpcShop(int xmlId, int npcXmlId, GamePlayer gamePlayer){
		NpcShopProperty shopProperty = npcData.getNpcShopProperty(xmlId);
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		RoleNpcsInfo roleNpcsInfo = npcRepo.getRoleNpcsInfo(gamePlayer.getRoleId());
		Npc npc = roleNpcsInfo.findNpcByXmlId(npcXmlId);
		if (npc.getLikesLevel() < shopProperty.getLikeLv()) {
			throw new BusinessException(ErrorCode.BUY_LEVEL_LIMIT);
		}
		int price = (int) (shopProperty.getDiscount() * shopProperty.getPrice().intValue() * 0.1);
		Currency currency = null;
		if (shopProperty.getSellType() == 1) {
			currency = new Currency(CurrencyType.gem, price);
		}else if(shopProperty.getSellType() == 2){
			currency = new Currency(CurrencyType.cheese, price);
		}
		role.spend(currency);
		roleRepo.saveRole(role);
		itemDomainService.addItems(BoFactory.createMultiItems(shopProperty.getGoods()), gamePlayer.getRoleId());
		achievementService.updateOneAchievement(new UpdateAchievement(gamePlayer.getRoleId(), AchievementType.shopping_quality, 1));
	}
	
	public Builder buyChest(int xmlId, GamePlayer gamePlayer){
		ShopProperty shopProperty = shopData.getShopProperty(xmlId);
		Role role = roleRepo.getRole(gamePlayer.getRoleId());
		if (role.getRoleLevel() < shopProperty.getLimitLv()) {
			throw new BusinessException(ErrorCode.BUY_LEVEL_LIMIT);
		}
		int price = (int) (shopProperty.getDiscount() * shopProperty.getPrice().intValue() * 0.1);
		Currency currency = null;
		if (shopProperty.getSellType() == 1) {
			currency = new Currency(CurrencyType.gem, price);
		}else if(shopProperty.getSellType() == 2){
			currency = new Currency(CurrencyType.cheese, price);
		}
		role.spend(currency);
		ChestType chestType = ChestType.valueOf(Integer.parseInt(shopProperty.getGoods()));
		List<Item> items = activeService.openChest(chestType);
		roleRepo.saveRole(role);
		itemDomainService.addItems(items, gamePlayer.getRoleId());
		DisplayResultProto.Builder builder = DisplayResultProto.newBuilder();
		for (Item item : items) {
			builder.addItems(item.copyTo());
		}
		return builder;
	}
	
	public Object iosRecharge(String receipt, GamePlayer gamePlayer){
		logger.info("roleId :"+gamePlayer.getRoleId()+" recharge start");
		ReusedProtoBuilder reusedProtoBuilder = ProtoBuilderPoolManager.getBuilder(IntReqProto.class);
		IntReqProto.Builder builder = (IntReqProto.Builder) reusedProtoBuilder.getBuilder();
		String url = "";

		RechargeEnv rechargeEnv = RechargeEnv.product_ios;
		try {
			String str = new String(Base64.decodeBase64(receipt), "UTF-8");
			if(str.contains("Sandbox")) {
				url = IOS_SANDBOX_URL;
				rechargeEnv = RechargeEnv.sandbox_ios;
			}
			else url = IOS_VERIFY_URL;
		}
		catch(Exception e) {
			e.printStackTrace();
			builder.setIndex(-1);
			return reusedProtoBuilder;
		}
		
		String responseStr = "";
		
		long roleId = gamePlayer.getRoleId();
		Role role = roleRepo.getRole(roleId);
		String cpOrderId = UUID.randomUUID().toString().replace("-", "");
		OutputStreamWriter out = null;
		InputStream is = null;
		BufferedReader reader = null;
		try {
			URL dataUrl = new URL(url);
			HttpURLConnection con = (HttpURLConnection) dataUrl.openConnection();
					
			// 设置请求头信息
			con.setRequestMethod("POST");
			con.setRequestProperty("content-type", "text/json");
			con.setRequestProperty("Proxy-Connection", "Keep-Alive");
			con.setDoOutput(true);
			con.setDoInput(true);
			out = new OutputStreamWriter(con.getOutputStream());
			String str = String.format(Locale.CHINA, "{\"receipt-data\":\""+ receipt + "\"}");
			out.write(str);
			out.flush();
			if(con.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new Exception("HTTP Request is not success, Response code is " + con.getResponseCode());
			}
			is = con.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while ((line = reader.readLine()) != null) {
				responseStr += line;
			}
		}
		catch(Exception e) {
			RechargeOrder order = new RechargeOrder(cpOrderId, role.getPlatformId(), role.getRoleLevel(), role.getVipLevel(), -1, role.getRoleName(), rechargeEnv);
			order.setState(RechargeState.connection_error.code);
			rechargeDao.insertRechargeOrder(order);
			logger.info("roleId :"+gamePlayer.getRoleId()+" cannot connect to ios");
			e.printStackTrace();
			builder.setIndex(-1);
			return reusedProtoBuilder;
		}
		finally {
			try {
				if(out != null) out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
			try {
				if(is != null) is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(reader != null) reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String channelOrderId;
		int channelId = IOS_CHANNEL_ID;
		Map<String, String> params = new HashMap<String, String>();
		int type = 0;
		try {
			ValidateInfo info = JSONUtils.fromJSON(responseStr, ValidateInfo.class);
			ValiDatedReceipt validatedReceipt = info.getReceipt();
			boolean orderState = info.getStatus()==0;
			if(!orderState) {
				logger.info("roleId :"+gamePlayer.getRoleId()+" validate fail");
			}
			
			channelOrderId = validatedReceipt.getTransaction_id();
			String accountId = role.getPlatformId();
			String orderId = validatedReceipt.getTransaction_id();
			ShopProperty shopProperty = shopData.getRechargeProperty(validatedReceipt.getProduct_id());
			type = shopProperty.getId();
			String resp = receipt+","+responseStr;
			
			params.put("accountId", accountId);
			params.put("cpOrderId", cpOrderId);
			params.put("orderId", orderId);
			params.put("channelId", String.valueOf(channelId));
			params.put("type", String.valueOf(type));
			params.put("money", shopProperty.getPrice().toPlainString());
			params.put("resp", resp);
			params.put("orderState", String.valueOf(orderState));
			params.put("isIOS", "true");
		}
		catch(Exception e) {
			logger.info("roleId :"+gamePlayer.getRoleId()+" validate fail");
			e.printStackTrace();
			builder.setIndex(-1);
			return reusedProtoBuilder;
		}
		int result;
		synchronized(this) {
			RechargeOrder order = rechargeDao.findRechargeOrder(channelOrderId, channelId);
			if(order == null || order.getState()==RechargeState.submit.code) {
				order = new RechargeOrder(cpOrderId, role.getPlatformId(), role.getRoleLevel(), role.getVipLevel(), type, role.getRoleName(), rechargeEnv);
				rechargeDao.insertRechargeOrder(order);
				String rechargeResult = serverRecharge(params);
				if(rechargeResult.equals("SUCCESS")) {
					result = 0;
					logger.info("roleId :"+gamePlayer.getRoleId()+" end:success");
				}
				else {
					result = -1;
					logger.info("roleId :"+gamePlayer.getRoleId()+" end:fail");
				}
			}
			else {
				logger.info("roleId :"+gamePlayer.getRoleId()+" receipt has been used, cannot recharge again");
				result = 2;
			}
		}

		builder.setIndex(result);
		return reusedProtoBuilder;
	}
	
	public String serverRecharge(Map<String, String> params){
		String result = "SUCCESS";
		long roleId = -1;
		String accountId;
		String cpOrderId;
		String orderId; 
		int channelId; 
		int type; 
		BigDecimal money; 
		String resp; 
		boolean orderState;
		
		RechargeError error = RechargeError.none_error;
		RechargeState state = RechargeState.success;
		String rechargeResult = "SUCCESS";
		try{
			accountId = params.get("accountId");
			cpOrderId = params.get("cpOrderId");
			orderId = params.get("orderId"); 
			resp = params.get("resp"); 
			if(accountId==null  || cpOrderId==null || orderId==null || resp==null) {
				result = "FAILURE";
				return result;
			}
			channelId = Integer.parseInt(params.get("channelId")); 
			type = Integer.parseInt(params.get("type")); 
			money = new BigDecimal(params.get("money")); 
			orderState = Boolean.parseBoolean(params.get("orderState"));
		}
		catch(Exception e){
			e.printStackTrace();
			result = "FAILURE";
			return result;
		}
		
		RechargeOrder order = rechargeDao.getRechargeOrder(cpOrderId);
		order.complete(accountId, orderId, channelId, money, 0, state.code, resp, error.code);
		rechargeDao.saveRechargeOrder(order);

		int diamond = -1;
		String platformId = accountId;
		int level = 0;
		ShopProperty shopProperty = null;
		try{ 
			if(orderState) {
				Role role = null;
				try{
					role = roleRepo.getRole(roleId);
					if(role == null) {
//						platformId = channelId + "@" + accountId;
						role = roleRepo.getRole(platformId);
					}
					level = role.getRoleLevel();
					roleId = role.getId();
					logger.info("roleId :"+roleId+" product type:"+type);
					shopProperty = shopData.getShopProperty(type);
					role.addRechargeMoney(money.intValue());
					if (!shopProperty.getGoods().equals("")) {
						int currentGem = role.getGem();
						Item item  = BoFactory.createSingleItem(shopProperty.getGoods());
						diamond = item.getNum();
						Currency currency = new Currency(CurrencyType.gem,item.getNum());
						role.earn(currency);
						logger.info("roleId :"+roleId+" gemBefore:"+currentGem+" after:"+role.getGem());
					}
					if (shopProperty.getRechargeType() == RechargeType.libao) {
						WordProperty word = wordData.getWordProperty(WordPosition.firstRecharge.getCode());
						String[] words = word.getWord().split("\\*");
						int emailId = emailRepo.newEmailId();
						Email email = new Email(emailId,words[0], words[1]
								, ServerConfig.system_role_id, words[2], EmailType.SINGLE);
						email.setAccepterId(role.getId());
						email.setRewards(shopProperty.getReward());
						emailService.sendRoleEmail(email);
						role.setHaveBuyOneYuanGift(true);
					}else if(shopProperty.getRechargeType() == RechargeType.monthcard)
					{
						int continueDay = 30;
						role.setMonthCardExpirationTime(System.currentTimeMillis() + continueDay *24 * 60 * 60 * 1000);
						WordProperty word = wordData.getWordProperty(WordPosition.firstRecharge.getCode());
						String[] words = word.getWord().split("\\*");
						int emailId = emailRepo.newEmailId();
						Email email = new Email(emailId,words[0], words[1]
								, ServerConfig.system_role_id, words[2], EmailType.SINGLE);
						email.setAccepterId(role.getId());
						email.setRewards(shopData.getMonthcardShopProperty().getReward());
						emailService.sendRoleEmail(email);
					}else if(shopProperty.getRechargeType() == RechargeType.yearcard){
						int continueDay = 365;
						role.setYearCardExpirationTime(System.currentTimeMillis() + continueDay *24 * 60 * 60 * 1000);
						WordProperty word = wordData.getWordProperty(WordPosition.firstRecharge.getCode());
						String[] words = word.getWord().split("\\*");
						int emailId = emailRepo.newEmailId();
						Email email = new Email(emailId,words[0], words[1]
								, ServerConfig.system_role_id, words[2], EmailType.SINGLE);
						email.setAccepterId(role.getId());
						email.setRewards(shopData.getYearcardShopProperty().getReward());
						emailService.sendRoleEmail(email);
					}
					roleRepo.saveRole(role);
				}
				catch(Exception e){
					error = RechargeError.error_in_add_diamond;
					throw e;
				}
				achievementService.updateOneAchievement(new UpdateAchievement(roleId, AchievementType.shopping_quality, 1));
				
//				try{
//					CoinRecord record = new CoinRecord(role, CoinRecordType.gain, DiamondRecordPosition.recharge, diamond,CurrencyType.DIAMOND);
//					batchInsertCoinRecordTask.addBatch(record);
//				}
//				catch(Exception e){
//					error = RechargeError.error_in_diamond_record;
//					throw e;
//				}
				
				
			}
			else {
				logger.info(" validate fail");
				state = RechargeState.sdk_fail;
			}
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		finally{
			try{
				if(error.code != RechargeError.none_error.code) state = RechargeState.business_fail;
				order.setDiamond(diamond);
				order.setState(state.code);
				order.setErrorCode(error.code);
				order.complete(accountId, orderId, channelId, money, diamond, state.code, resp, error.code);
				rechargeDao.saveRechargeOrder(order);
				logger.info("roleId :"+roleId+" save record");
			}
			catch(Exception e){
				logger.error(e);
				e.printStackTrace();
			}
			try{
				if(!"true".equals(params.get("isIOS"))) {
//					if(state.code == RechargeState.sdk_fail.code) rechargeResult = "FAILURE";
//					StrProto.Builder builder = StrProto.newBuilder();
//					builder.setParams1(rechargeResult);
//					GamePlayer gamePlayer = serverContext.getLogonPlayers().get(roleId);
//					if(gamePlayer != null) {
//						gamePlayer.respond(new Object[]{"RoleService.rechargeSuccess", CmdStatus.success.ordinal(), builder});
//					}else{
//						logger.info("roleId:"+roleId+"is not logon!Cache recharge result!");
//						SdkContext.addResult(roleId,builder);
//					}
				}
			}
			catch(Exception e){
				logger.error(e);
				e.printStackTrace();
			}
			
//			try {
//				String OS;
//				if("true".equals(params.get("isIOS"))) OS = "ios";
//				else OS = "android";
//				TalkingDataInfo info = new TalkingDataInfo(cpOrderId, "success", OS, accountId, cpOrderId, money.doubleValue(), "CNY", 
//						(double)diamond, String.valueOf(type), String.valueOf(serverInfoData.getSelfServerName()), level);
//				businessPool.talkingData(info);
//			}
//			catch(Exception e) {
//				logger.error(e);
//				e.printStackTrace();
//			}
		}

		return result;
	}

	
}
