package com.funcy.g01.hall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funcy.g01.base.bo.item.Item;
import com.funcy.g01.base.bo.item.RoleItemInfo;
import com.funcy.g01.base.bo.npc.Npc;
import com.funcy.g01.base.bo.npc.NpcTask;
import com.funcy.g01.base.bo.npc.NpcTaskType;
import com.funcy.g01.base.bo.npc.RoleNpcsInfo;
import com.funcy.g01.base.dao.redis.ItemRepo;
import com.funcy.g01.base.dao.redis.NpcRepo;
import com.funcy.g01.base.data.ItemData;
import com.funcy.g01.base.data.ItemProperty;
import com.funcy.g01.base.data.NpcData;
import com.funcy.g01.base.data.NpcTaskProperty;
import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;
import com.funcy.g01.base.net.EmptyRespMark;
import com.funcy.g01.base.net.GamePlayer;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.NpcProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.NpcTaskProto;
import com.funcy.g01.base.proto.service.AccountServiceRespProtoBuffer.CompleteNeedItemTaskProto;
import com.funcy.g01.base.proto.service.AccountServiceRespProtoBuffer.GivePresentResp;
import com.funcy.g01.base.proto.service.AccountServiceRespProtoBuffer.NpcTryGetNextTaskResp;
import com.funcy.g01.base.proto.service.ReqCmdProto.IntReqProto;
import com.google.protobuf.Message.Builder;

@Service
public class NpcService {
	
	@Autowired
	private NpcRepo npcRepo;
	
	@Autowired
	private ItemDomainService itemDomainService;
	
	@Autowired
	private ItemData itemData;
	
	@Autowired
	private NpcData npcData;
	
	@Autowired
	private ItemRepo itemRepo;

	public Builder tryGetNextTask(GamePlayer gamePlayer) {
		RoleNpcsInfo roleNpcsInfo = npcRepo.getRoleNpcsInfo(gamePlayer.getRoleId());
		List<NpcTask> newTasks = roleNpcsInfo.refreshTask(npcData);
		if(newTasks.size() > 0) {
			npcRepo.saveRoleNpcsInfo(roleNpcsInfo);
		}
		NpcTryGetNextTaskResp.Builder builer = NpcTryGetNextTaskResp.newBuilder();
		for(NpcTask npcTask : newTasks) {
			builer.addNewTasks((NpcTaskProto) npcTask.copyTo());
		}
		return builer;
	}
	
	public Object completeTask(int selection, int npcXmlId, int taskXmlId, GamePlayer gamePlayer) {
		RoleNpcsInfo roleNpcsInfo = npcRepo.getRoleNpcsInfo(gamePlayer.getRoleId());
		NpcTask npcTask = roleNpcsInfo.findNpcTaskByNpcXmlIdAndTaskXmlId(npcXmlId, taskXmlId);
		NpcTaskProperty npcTaskProperty = npcData.getNpcTaskProperty(npcTask.getXmlId());
		if(npcTaskProperty.getType0() == NpcTaskType.item || (npcTaskProperty.getType0() != NpcTaskType.talk && npcTask.getCompleteNum() < npcTaskProperty.getNeedCompleteNum())) {
			throw new BusinessException(ErrorCode.npc_not_talk_task);
		}
		roleNpcsInfo.removeNpcTask(npcTask);
		npcRepo.saveRoleNpcsInfo(roleNpcsInfo);
		List<Item> items = npcTaskProperty.getAwards().get(selection - 1);
		itemDomainService.addItems(items, gamePlayer.getRoleId(), npcXmlId);
		return EmptyRespMark.class;
	}
	
	public Object acceptTask(int npcXmlId, int taskXmlId, GamePlayer gamePlayer) {
		RoleNpcsInfo roleNpcsInfo = npcRepo.getRoleNpcsInfo(gamePlayer.getRoleId());
		NpcTask npcTask = roleNpcsInfo.findNpcTaskByNpcXmlIdAndTaskXmlId(npcXmlId, taskXmlId);
		if(npcTask.getState() != NpcTask.npc_task_state_init) {
			throw new BusinessException(ErrorCode.npc_can_not_accept);
		}
		npcTask.setState(NpcTask.npc_task_state_accept);
		npcRepo.saveRoleNpcsInfo(roleNpcsInfo);
		return EmptyRespMark.class;
	} 
	
	public Object completeSendMailTask(int npcXmlId, int taskXmlId, GamePlayer gamePlayer) {
		RoleNpcsInfo roleNpcsInfo = npcRepo.getRoleNpcsInfo(gamePlayer.getRoleId());
		NpcTask npcTask = roleNpcsInfo.findNpcTaskByNpcXmlIdAndTaskXmlId(npcXmlId, taskXmlId);
		if(npcTask.getState() != NpcTask.npc_task_state_accept) {
			throw new BusinessException(ErrorCode.npc_can_not_complete);
		}
		npcTask.setCompleteNum(1);
		npcRepo.saveRoleNpcsInfo(roleNpcsInfo);
		return EmptyRespMark.class;
	}
	
	public Builder completeNeedItemTask(int npcXmlId, int taskXmlId, GamePlayer gamePlayer) {
		RoleNpcsInfo roleNpcsInfo = npcRepo.getRoleNpcsInfo(gamePlayer.getRoleId());
		NpcTask npcTask = roleNpcsInfo.findNpcTaskByNpcXmlIdAndTaskXmlId(npcXmlId, taskXmlId);
		NpcTaskProperty npcTaskProperty = npcData.getNpcTaskProperty(npcTask.getXmlId());
		int xmlId = npcTaskProperty.getNeedItemXmlId();
		int num = npcTaskProperty.getNeedCompleteNum();
		int result = 0;
		CompleteNeedItemTaskProto.Builder builder = CompleteNeedItemTaskProto.newBuilder();
		try {
			itemDomainService.consumeItem(xmlId, num, gamePlayer.getRoleId());
			roleNpcsInfo.removeNpcTask(npcTask);
			npcRepo.saveRoleNpcsInfo(roleNpcsInfo);
			List<Item> items = npcTaskProperty.getAwards().get(0);
			itemDomainService.addItems(items, gamePlayer.getRoleId(), npcXmlId);
		} catch(BusinessException e) {
			result = e.getCode();
		}
		RoleItemInfo roleItemInfo = itemRepo.getRoleItemInfo(gamePlayer.getRoleId());
		for(Item item : roleItemInfo.getItems()) {
			builder.addItems(item.copyTo());
		}
		builder.setResult(result);
		return builder;
	}
	
	public Object giveUpTheNpcTask(int npcXmlId, int taskXmlId, GamePlayer gamePlayer) {
		RoleNpcsInfo roleNpcsInfo = npcRepo.getRoleNpcsInfo(gamePlayer.getRoleId());
		NpcTask npcTask = roleNpcsInfo.findNpcTaskByNpcXmlIdAndTaskXmlId(npcXmlId, taskXmlId);
		roleNpcsInfo.removeNpcTask(npcTask);
		npcRepo.saveRoleNpcsInfo(roleNpcsInfo);
		return EmptyRespMark.class;
	}
	
	public Builder givePresent(int xmlId, int npcXmlId, GamePlayer gamePlayer) {
		ItemProperty itemProperty = itemData.getItemProperty(xmlId);
		RoleNpcsInfo roleNpcsInfo = npcRepo.getRoleNpcsInfo(gamePlayer.getRoleId());
		Npc npc = roleNpcsInfo.findNpcByXmlId(npcXmlId);
		GivePresentResp.Builder builder = GivePresentResp.newBuilder();
		if(npc.getLikesLevel() == Npc.max_level) {
			builder.setResult(1);
			return builder;
		}
		int result = 0;
		try {
			itemDomainService.consumeItem(xmlId, 1, gamePlayer.getRoleId());
		} catch(BusinessException exception) {
			result = exception.getCode();
		}
		if(result == 0) {
			builder.setNpc((NpcProto) npc.copyTo());
			int addLikes = itemProperty.getAddLikes();
			if(npc.isSpecLike(npcData, xmlId)) {
				addLikes *= 3; //TODO 暂定3倍
			}
			builder.setAddExp(addLikes);
			npc.addExp(addLikes);
			npcRepo.saveRoleNpcsInfo(roleNpcsInfo);
		}
		builder.setResult(result);
		return builder;
	}
	
}
