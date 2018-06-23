package com.funcy.g01.hall.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funcy.g01.base.bo.map.RoleMapInfo;
import com.funcy.g01.base.bo.map.ServerMapInfo;
import com.funcy.g01.base.dao.redis.FightRepo;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.net.GamePlayer;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.ServerMapInfoProto;
import com.funcy.g01.base.proto.bo.service.MapServiceRespProtoBuffer.GetRoleMapInfoList;
import com.funcy.g01.base.proto.service.ReqCmdProto.LongReqProto;
import com.google.protobuf.Message.Builder;

@Service
public class MapService {

	@Autowired
	private FightRepo fightRepo;
	
	public com.funcy.g01.base.proto.service.ReqCmdProto.LongReqProto.Builder saveMap(ServerMapInfoProto serverMapInfoProto, GamePlayer gamePlayer) {
		return saveMapWithRoleId(serverMapInfoProto, gamePlayer.getRoleId());
	}
	
	public com.funcy.g01.base.proto.service.ReqCmdProto.LongReqProto.Builder saveMapForMaincity(ServerMapInfoProto serverMapInfoProto, GamePlayer gamePlayer) {
		return saveMapWithRoleId(serverMapInfoProto, ServerConfig.edit_main_city_map_rold_id);
	}
	
	
	public com.funcy.g01.base.proto.service.ReqCmdProto.LongReqProto.Builder saveMapWithRoleId(ServerMapInfoProto serverMapInfoProto, long roldId) {
		RoleMapInfo roleMapInfo = fightRepo.getRoleMapInfo(roldId);
		ServerMapInfo serverMapInfo;
		if (serverMapInfoProto.getMapId() == 0) {
			serverMapInfo = new ServerMapInfo(serverMapInfoProto.getMapInfo(), roldId);
			int mapIndex = roleMapInfo.getMapIndex() + 1;
			roleMapInfo.setMapIndex(mapIndex);
			fightRepo.saveServerMapInfo(serverMapInfo, mapIndex);
			roleMapInfo.addMapId(serverMapInfo.getMapId());
			fightRepo.saveRoleMapInfo(roleMapInfo);
		}else{
			serverMapInfo = fightRepo.getServerMapInfo(serverMapInfoProto.getMapId());
			serverMapInfo.setLastModifyTime(System.currentTimeMillis());
			serverMapInfo.setMapInfo(serverMapInfoProto.getMapInfo());
			fightRepo.saveServerMapInfo(serverMapInfo, 0);
		}
		LongReqProto.Builder builder = LongReqProto.newBuilder();
		builder.setIndex(serverMapInfo.getMapId());
		return builder;
	}
	
	public Builder getNewComerStep1Map(GamePlayer gamePlayer) {
		return getRoleMapByMapId(ServerConfig.new_commer_step1_mapId, gamePlayer);
	}
	
	public Builder getNewComerStep2Map(GamePlayer gamePlayer) {
		return getRoleMapByMapId(ServerConfig.new_commer_step2_mapId, gamePlayer);
	}
	
	public Builder getNewComerStep3Map(GamePlayer gamePlayer) {
		return getRoleMapByMapId(ServerConfig.new_commer_step3_mapId, gamePlayer);
	}
	
	public Builder getRoleMapByMapId(long mapId, GamePlayer gamePlayer){
		ServerMapInfo serverMapInfo = fightRepo.getServerMapInfo(mapId);
		ServerMapInfoProto.Builder builder = (ServerMapInfoProto.Builder) serverMapInfo.copyTo().toBuilder();
		return builder;
	}
	
	public void deleteMap(long mapId, GamePlayer gamePlayer){
		fightRepo.deleteServerMapInfo(mapId);
	}
	
	public void deleteRoleAllMap(GamePlayer gamePlayer){
		this.deleteRoleAllMapByRoleId(gamePlayer.getRoleId());
	}
	public void deleteRoleAllMapByRoleId(long roleId){
		RoleMapInfo roleMapInfo = fightRepo.getRoleMapInfo(roleId);
		List<Long> mapIds = roleMapInfo.getMapIds();
		for (Long id : mapIds) {
			fightRepo.deleteServerMapInfo(id);
		}
	}
	
	public Builder getRoleMapList(GamePlayer gamePlayer){
		return this.getRoleBaseMapListByRoleId(gamePlayer.getRoleId());
	}
	
	public Builder getRoleMapListByRoldId(long roleId){
		RoleMapInfo roleMapInfo = fightRepo.getRoleMapInfo(roleId);
		List<Long> mapIds = roleMapInfo.getMapIds();
		Collections.reverse(mapIds);
		GetRoleMapInfoList.Builder builder = GetRoleMapInfoList.newBuilder();
		for (Long mapId : mapIds) {
			ServerMapInfo serverMapInfo = fightRepo.getServerMapInfo(mapId);
			builder.addFightMapInfoList((ServerMapInfoProto)serverMapInfo.copyTo());
		}
		return builder;
	}
	
	
	public Builder getRoleBaseMapList(GamePlayer gamePlayer){
		return getRoleBaseMapListByRoleId(gamePlayer.getRoleId());
	}
	
	public Builder getMaincityBaseMapList(GamePlayer gamePlayer){
		return getRoleBaseMapListByRoleId(ServerConfig.edit_main_city_map_rold_id);
	}
	
	public Builder getRoleBaseMapListByRoleId(long roleId){
		RoleMapInfo roleMapInfo = fightRepo.getRoleMapInfo(roleId);
		List<Long> mapIds = roleMapInfo.getMapIds();
		Collections.reverse(mapIds);
		GetRoleMapInfoList.Builder builder = GetRoleMapInfoList.newBuilder();
		for (Long mapId : mapIds) {
			ServerMapInfo serverMapInfo = fightRepo.getServerMapInfo(mapId);
			builder.addFightMapInfoList((ServerMapInfoProto)serverMapInfo.copyBaseTo());
		}
		return builder;
	}
	
	
	public void saveRoleMapListToFile(long roleId) throws IOException{
		RoleMapInfo roleMapInfo = fightRepo.getRoleMapInfo(roleId);
		List<Long> mapIds = roleMapInfo.getMapIds();
		GetRoleMapInfoList.Builder builder = GetRoleMapInfoList.newBuilder();
		for (Long mapId : mapIds) {
			ServerMapInfo serverMapInfo = fightRepo.getServerMapInfo(mapId);
			builder.addFightMapInfoList((ServerMapInfoProto)serverMapInfo.copyTo());
		}
		if (mapIds.size() > 0) {
			String savePath = "map/" + roleId + ".g06";
			File file = new File(savePath);
	        if(!file.getParentFile().exists()) {  
	        	file.getParentFile().mkdirs();
	        }  
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(roleMapInfo.getMapIndex());
			fos.write(builder.build().toByteArray());
			fos.close();
		}
	}
	
	public void checkLoadMap(long roleId) {
		RoleMapInfo roleMapInfo = fightRepo.getRoleMapInfo(roleId);
		if (roleMapInfo.getMapIds().size() == 0) {
			File file = new File("map/" + roleId + ".g06");
			if (file.exists()) {
				FileInputStream fis;
				try {
					fis = new FileInputStream(file);
					int mapIndex = fis.read();
					byte[] bytes = new byte[fis.available()];
					fis.read(bytes);
					GetRoleMapInfoList roleMapInfoList = GetRoleMapInfoList.parseFrom(bytes);
					List<ServerMapInfoProto>  serverMapInfoProtos = roleMapInfoList.getFightMapInfoListList();
					int index = 1;
					for (ServerMapInfoProto serverMapInfoProto : serverMapInfoProtos) {
						ServerMapInfo serverMapInfo = new ServerMapInfo(serverMapInfoProto.toByteArray());
						long mapId = roleId * 100000 + index;
						serverMapInfo.setMapId(mapId);
						fightRepo.saveServerMapInfo((ServerMapInfoProto) serverMapInfo.copyTo());
						roleMapInfo.addMapId(mapId);
						index++;
					}
					roleMapInfo.setMapIndex(mapIndex);
					fightRepo.saveRoleMapInfo(roleMapInfo);
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
