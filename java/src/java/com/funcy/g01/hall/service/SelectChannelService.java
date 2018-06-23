package com.funcy.g01.hall.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funcy.g01.base.bo.fight.FightRoom;
import com.funcy.g01.base.bo.serverconfig.ServerInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.net.DispatchServer;
import com.funcy.g01.base.net.GamePlayer;
import com.funcy.g01.base.proto.dispatcher.DispatcherRespCmdProtoBuffer.GetSelectChannelInfoRespProto;
import com.funcy.g01.base.proto.dispatcher.DispatcherRespCmdProtoBuffer.changeChannelRespProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.IntReqProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.LongIntReqProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.LongReqProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.StrProto;
import com.funcy.g01.base.util.HttpUtils;
import com.google.protobuf.Message.Builder;

@Service
public class SelectChannelService {
	
	@Autowired
	private ServerInfoData serverInfoData;
	
	@Autowired
	private ServerContext serverContext;
	
	/**
	 * 获取大厅路线信息
	 * @param pageIndex 初次获取就是1
	 * @param gamePlayer
	 * @return
	 */
	public Builder getSelectChannelInfo(int pageIndex, GamePlayer gamePlayer){
		DispatchServer dispatchServer = serverContext.borrowHallDispatchServer();
		GetSelectChannelInfoRespProto respProto = null;
		try{
			LongIntReqProto.Builder builder = LongIntReqProto.newBuilder();
			builder.setParams1(gamePlayer.getRoleId());
			builder.setParams2(pageIndex);
			respProto = (GetSelectChannelInfoRespProto) dispatchServer.sendAndWaitResp("roomDispatchService.getDispatcherRoomInfo", builder);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			serverContext.addHallDispatchServer4Send(dispatchServer);
		}
		return respProto.toBuilder().setPageIndex(pageIndex);
	}
	
	/**
	 * 更换大厅路线
	 * @param roomId
	 * @param gamePlayer
	 * @return -1 路线异常   -2 人数已满   0正常
	 */
	public Builder changeChannel(int roomId, GamePlayer gamePlayer){
		changeChannelRespProto.Builder respBuilder = changeChannelRespProto.newBuilder();
		respBuilder.setStatus(0);
		try{
			int serverId = 0;
			DispatchServer dispatchServer = serverContext.borrowHallDispatchServer();
			try{
				IntReqProto respProto = (IntReqProto) dispatchServer.sendAndWaitResp("roomDispatchService.getRoomServerId", LongReqProto.newBuilder().setIndex(roomId));
				serverId = respProto.getIndex();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				serverContext.addHallDispatchServer4Send(dispatchServer);
			}
			if(serverId > 0){
				String sendUrlSuffix = ServerConfig.http_domain_name + "/service/isRoomExist.jsp";
				ServerInfo hallServerInfo = serverInfoData.getServerInfo(serverId);
				String hallUrl = ("http://" + hallServerInfo.getIp() + ":" + hallServerInfo.getHttpPort() + sendUrlSuffix);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("roomId", roomId);
				String resp = HttpUtils.doGet(hallUrl, params, "utf-8");
				int respNum = Integer.parseInt(resp.trim());
				if (respNum<=0) { // 不存在
					respBuilder.setStatus(-1);
				}else if(respNum >= ServerConfig.hall_room_max_player_num){
					respBuilder.setStatus(-2);
				}
			}else{
				respBuilder.setStatus(-1);
			}
			respBuilder.setServerInfo(serverInfoData.getServerInfo(serverId).buildHallServerInfo());
		}catch (Exception e) {
			respBuilder.setStatus(-1);
			e.printStackTrace();
		}
		return respBuilder;
	}
	
	/**
	 * 获取当前所在路线
	 * @param gamePlayer
	 * @return
	 */
	public Builder getCurChannelInfo(GamePlayer gamePlayer){
		FightRoom fightRoom = gamePlayer.getSynRoom();
		return StrProto.newBuilder().setParams1(fightRoom.getChannelName());
	}
}
