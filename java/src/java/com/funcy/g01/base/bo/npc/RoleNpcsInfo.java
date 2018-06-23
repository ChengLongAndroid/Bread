package com.funcy.g01.base.bo.npc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.data.NpcData;
import com.funcy.g01.base.data.NpcProperty;
import com.funcy.g01.base.data.NpcTaskProperty;
import com.funcy.g01.base.net.CmdStatus;
import com.funcy.g01.base.net.GamePlayer;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.NpcProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.NpcTaskProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleNpcsInfoProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

import edu.emory.mathcs.backport.java.util.Collections;

public class RoleNpcsInfo implements ProtobufSerializable {

	private transient long roleId;
	
	private List<Npc> npcs = new ArrayList<Npc>();
	
	private List<NpcTask> npcTasks = new ArrayList<NpcTask>();
	
	private long lastGetTaskTime;
	
	public static final Random random = new Random();
	
	public static final long refresh_task_time = TimeUnit.HOURS.toMillis(1);
	
	public static final int add_task_percent = 30;
	
	public static final int max_task_num = 4;
	
	public RoleNpcsInfo(long roleId) {
		this.roleId = roleId;
	}
	
	public RoleNpcsInfo(byte[] bytes) {
		parseFrom(bytes);
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public List<Npc> getNpcs() {
		return npcs;
	}

	public List<NpcTask> getNpcTasks() {
		return npcTasks;
	}

	public long getLastGetTaskTime() {
		return lastGetTaskTime;
	}
	
	public boolean updateTypeTaskNumAndNotify(NpcTaskType npcTaskType, int num, NpcData npcData, GamePlayer gamePlayer) {
		boolean isChange = false;
		for (NpcTask npcTask : npcTasks) {
			NpcTaskProperty npcTaskProperty = npcData.getNpcTaskProperty(npcTask.getXmlId());
			if(npcTaskProperty.getType0() == npcTaskType && npcTask.getState() == 1) {
				npcTask.addCompleteNum(npcTaskProperty, num);
				if(gamePlayer != null) {
					gamePlayer.forceRespond("notifyNpcTaskChange", CmdStatus.notDecrypt, npcTask.copyTo().toBuilder());
				}
				isChange = true;
			}
		}
		return isChange;
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleNpcsInfoProto proto = (RoleNpcsInfoProto) message;
		this.lastGetTaskTime = proto.getLastGetTaskTime();
		for(NpcProto npcProto : proto.getNpcsList()) {
			Npc npc = new Npc(npcProto);
			this.npcs.add(npc);
		}
		for(NpcTaskProto npcTaskProto : proto.getNpcTasksList()) {
			NpcTask npcTask = new NpcTask(npcTaskProto);
			this.npcTasks.add(npcTask);
		}
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleNpcsInfoProto.Builder builder = RoleNpcsInfoProto.newBuilder();
		builder.setLastGetTaskTime(this.lastGetTaskTime);
		for(Npc npc : this.npcs) {
			builder.addNpcs((NpcProto) npc.copyTo());
		}
		for(NpcTask npcTask : this.npcTasks) {
			builder.addNpcTasks((NpcTaskProto) npcTask.copyTo());
		}
		return builder.build();
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleNpcsInfoProto proto = RoleNpcsInfoProto.parseFrom(bytes);
			copyFrom(proto);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] toByteArray() {
		return this.copyTo().toByteArray();
	}

	public void init(NpcData npcData) {
		tryAddNpc(npcData);
		//初始刷出一个任务
		this.lastGetTaskTime = System.currentTimeMillis();
		int taskCount = 2;
		for(int i = 0; i < taskCount; i++) {
			addTask(npcData);
		}
	}
	
	public NpcTask addTask(NpcData npcData) {
		List<Npc> canRandomNpcs = new ArrayList<Npc>();
		for(Npc npc : npcs) {
			NpcTask npcTask = findNpcTaskByNpcXmlId(npc.getXmlId());
			if(npcTask == null) {
				canRandomNpcs.add(npc);
			}
		}
		if(canRandomNpcs.size() == 0) {
			return null;
		}
		int index = random.nextInt(canRandomNpcs.size());
		Npc npc = canRandomNpcs.get(index);
		NpcProperty npcProperty = npcData.getNpcProperty(npc.getXmlId());
		NpcTask npcTask = npc.randomTask(npcProperty, npcData);
		this.npcTasks.add(npcTask);
		return npcTask;
	}
	
	public NpcTask findNpcTaskByNpcXmlId(int xmlId) {
		for(NpcTask npcTask : npcTasks) {
			if(npcTask.getNpcXmlId() == xmlId) {
				return npcTask;
			}
		}
		return null;
	}
	
	public NpcTask findNpcTaskByNpcXmlIdAndTaskXmlId(int npcXmlId, int taskXmlId) {
		for(NpcTask npcTask : npcTasks) {
			if(npcTask.getXmlId() == taskXmlId && npcTask.getNpcXmlId() == npcXmlId) {
				return npcTask;
			}
		}
		return null;
	}
	
	public void refresh(NpcData npcData) {
		tryAddNpc(npcData);
		refreshTask(npcData);
	}
	
	@SuppressWarnings("unchecked")
	public List<NpcTask> refreshTask(NpcData npcData) {
		long curTime = System.currentTimeMillis();
		long elapsedTime = curTime - this.lastGetTaskTime;
		int tryAddTaskNum = (int) (elapsedTime / refresh_task_time);
		if(tryAddTaskNum <= 0) {
			return Collections.emptyList();
		}
		List<NpcTask> tasks = new ArrayList<NpcTask>();
		for(int i = 0; i < tryAddTaskNum; i++) {
			if(this.npcTasks.size() >= max_task_num) {
				break;
			}
			if(random.nextInt(100) < add_task_percent) {
				NpcTask npcTask = addTask(npcData);
				tasks.add(npcTask);
			}
		}
		this.lastGetTaskTime += tryAddTaskNum * refresh_task_time;
		return tasks;
	}
	
	public Npc findNpcByXmlId(int xmlId) {
		for(Npc npc : this.npcs) {
			if(npc.getXmlId() == xmlId) {
				return npc;
			}
		}
		return null;
	}
	
	public void tryAddNpc(NpcData npcData) {
		for(NpcProperty npcProperty : npcData.getNpcPropertyMap().values()) {
			Npc npc = findNpcByXmlId(npcProperty.getXmlId());
			if(npc == null) {
				npc = new Npc(npcProperty);
				this.npcs.add(npc);
			}
		}
	}

	public void removeNpcTask(NpcTask npcTask) {
		Iterator<NpcTask> it = this.npcTasks.iterator();
		while(it.hasNext()) {
			NpcTask task = it.next();
			if(task == npcTask) {
				it.remove();
				break;
			}
		}
	}
}
