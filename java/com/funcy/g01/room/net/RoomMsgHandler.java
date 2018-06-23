package com.funcy.g01.room.net;

import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.fight.EventType;
import com.funcy.g01.base.bo.fight.FightRoom;
import com.funcy.g01.base.bo.fight.SynEvent;
import com.funcy.g01.base.net.BaseGameMsgHandler;
import com.funcy.g01.base.net.GamePlayer;
import com.funcy.g01.base.proto.bo.SynFightBoProtoBuffer.EmptyEventProto;
import com.funcy.g01.dispatcher.bo.RoomType;

@Component
public class RoomMsgHandler extends BaseGameMsgHandler {
	
	public static final String service_package_name = "com.funcy.g01.room.service";
	
	@Override
	public boolean checkSeriveIsInTheServer(String serviceAndMethod) {
		int pointIndex = 0;
        for(int i = 0;i<serviceAndMethod.length();i++) {
            if(serviceAndMethod.charAt(i) == '.') {
                pointIndex = i;
                break;
            }
        }
        String sericeName = serviceAndMethod.substring(0, pointIndex);
		Object service = ac.getBean(sericeName);
		if(service.getClass().getPackage().getName().equals(service_package_name)) {
			return true;
		} else {
			if(serviceAndMethod.equals("roomServiceInRoom4Dispatcher.getServerRoomsInfo")) {
				return true;
			}
			return false;
		}
	}

	@Override
	public boolean isNeedDecrypt(String serviceAndMethod) {
		return false;
	}

	@Override
	public void playerOffLine(final GamePlayer gamePlayer) {
		serverContext.removeFightLogonPlayer(gamePlayer);
		serverContext.removeConnectedPlayer(gamePlayer);
		gamePlayer.destroy();
		if(gamePlayer.getFightPlayer() != null) {
			final FightRoom fightRoom = (FightRoom)gamePlayer.getSynRoom();
			if(fightRoom != null) {
				fightRoom.executeRightNow(new SynEvent(gamePlayer.getRoleId(), "RoomMsgHandler.playerOffLine") {
					@Override
					public void executeEvent() {
						if(fightRoom.getRoomType() == RoomType.tryPlay) {
							EmptyEventProto.Builder builder = EmptyEventProto.newBuilder();
							builder.setEventType(EventType.quit_event_type.getCode());
							builder.setRoleId(gamePlayer.getRoleId());
							fightRoom.playerQuit(gamePlayer.getFightPlayer(), builder.build());
						} else {
							fightRoom.playerOffline4FightRoom(gamePlayer.getFightPlayer());
						}
					}
				});
			}
		}
	}

	@Override
	public boolean isHallServer() {
		return false;
	}

	
}
