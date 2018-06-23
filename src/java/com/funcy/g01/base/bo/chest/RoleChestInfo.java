package com.funcy.g01.base.bo.chest;

import java.util.ArrayList;
import java.util.List;

import com.funcy.g01.base.dao.redis.base.ProtobufSerializable;
import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.ChestSlotProto;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleChestInfoProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class RoleChestInfo implements ProtobufSerializable {
	
	public static final int max_win_num = 5;
	
	private transient long roleId;
	
	private List<ChestSlot> chestSlots = null;
	
	private int winNum;
	
	private int accumulateChestNum;
	
	public RoleChestInfo(){
		chestSlots = new ArrayList<ChestSlot>();
		for (int i = 1; i <= 4; i++) {
			chestSlots.add(new ChestSlot(ChestType.none,-1,i));
		}
		this.accumulateChestNum = 0;
	}
	
	public RoleChestInfo(byte[] bytes){
		parseFrom(bytes);
	}

	@Override
	public void copyFrom(GeneratedMessage message) {
		RoleChestInfoProto proto = (RoleChestInfoProto) message;
		this.chestSlots = new ArrayList<ChestSlot>() ;
		for (ChestSlotProto chestSlotProto : proto.getChestSlotsList()) {
			this.chestSlots.add(new ChestSlot(ChestType.valueOf(chestSlotProto.getType()),chestSlotProto.getCanOpenTime(), chestSlotProto.getIndex()));
		}
		this.winNum = proto.getWinNum();
		this.accumulateChestNum = proto.getAccumulateChestNum();
	}

	@Override
	public GeneratedMessage copyTo() {
		RoleChestInfoProto.Builder buidler = RoleChestInfoProto.newBuilder();
		for (ChestSlot chestSlot : chestSlots) {
			buidler.addChestSlots(chestSlot.copyTo());
		}
		buidler.setWinNum(winNum);
		buidler.setAccumulateChestNum(accumulateChestNum);
		return buidler.build();
	}
	
	public ChestSlot getEmptySlot(){
		for (ChestSlot chestSlot : chestSlots) {
			if (chestSlot.getChestType() == ChestType.none) {
				return chestSlot;
			}
		}
		return null;
	}
	
	public boolean checkHadOtherOpening(){
		for (ChestSlot chestSlot : chestSlots) {
			if (chestSlot.getChestType() != ChestType.none && chestSlot.getCanOpenTime() > System.currentTimeMillis()) {
				return true;
			}
		}
		return false;
	}
	
	public void addWinNum(){
		this.winNum += 1;
		if (this.winNum >= max_win_num) {
			this.winNum = max_win_num;
		}
	}
	
	public void addAccumulateChestNum(){
		this.accumulateChestNum += 1;
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			RoleChestInfoProto roleChestInfoProto = RoleChestInfoProto.parseFrom(bytes);
			copyFrom(roleChestInfoProto);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}
	
	public ChestSlot findChestSlotByIndex(int index){
		return this.chestSlots.get(index-1);
	}
	

	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}

	public long getRoleId() {
		return roleId;
	}

	public List<ChestSlot> getChestSlots() {
		return chestSlots;
	}

	public void setChestSlots(List<ChestSlot> chestSlots) {
		this.chestSlots = chestSlots;
	}

	public int getWinNum() {
		return winNum;
	}

	public void setWinNum(int winNum) {
		this.winNum = winNum;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public int getAccumulateChestNum() {
		return accumulateChestNum;
	}

	public void setAccumulateChestNum(int accumulateChestNum) {
		this.accumulateChestNum = accumulateChestNum;
	}
}
