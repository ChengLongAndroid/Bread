package com.funcy.g01.base.global;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.google.protobuf.GeneratedMessage;

@Component
public class ServiceConfig {

	private Set<String> roomServiceAndMethodNotSyn = new HashSet<String>();
	
	private Set<String> notPrintServiceAndMethod = new HashSet<String>();
	
	public static final String roomServiceName = "synFightingService";
	
	private ServiceConfig() {
		//房间内事件
		roomServiceAndMethodNotSyn.add("synFightingService.playerEnterFight");
//		roomServiceAndMethodNotSyn.add("synFightingService.playerSendConjureObjEvent");
//		roomServiceAndMethodNotSyn.add("synFightingService.playerSendCommonEvent");
//		roomServiceAndMethodNotSyn.add("synFightingService.playerSendChatEvent");
//		roomServiceAndMethodNotSyn.add("synFightingService.playerSendFightSkillObjEvent");
		
		
		//不打印的ServiceAndMethod
		notPrintServiceAndMethod.add("accountService.ping");
		notPrintServiceAndMethod.add("synFightingService.playerSendCommonEvent");
		notPrintServiceAndMethod.add("synFightingService.playerSendConjureObjEvent");
		notPrintServiceAndMethod.add("synFightingService.playerSendChatEvent");
		notPrintServiceAndMethod.add("synFightingService.playerSendFightSkillObjEvent");
	}
	
	public boolean isCanPrint(String serviceAndMethod, GeneratedMessage generatedMessage) {
//		if(serviceAndMethod.equals("synFightingService.playerSendConjureObjEvent")) {
//			ConjurePhysicsObjReqProto proto = (ConjurePhysicsObjReqProto) generatedMessage;
//			if(proto.getParams1().getEventType() == EventType.conjure_physicsobj_conjuring_update_event_type.getCode()) {
//				return false;
//			} else if(proto.getParams1().getEventType() == EventType.conjure_physicsobj_update_event_type.getCode()) {
//				return false;
//			}
//		}
//		if(serviceAndMethod.equals("synFightingService.playerSendCommonEvent")) {
//			CommonEventReqProto proto = (CommonEventReqProto) generatedMessage;
//			if(proto.getParams1().getEventType() == EventType.start_moving_left_event_type.getCode()) {
//				return true;
//			} else if(proto.getParams1().getEventType() == EventType.start_moving_right_event_type.getCode()) {
//				return true;
//			} else if(proto.getParams1().getEventType() == EventType.contact_syn_unit_info_event_type.getCode()) {
//				return true;
//			} else {
//				return false;
//			}
//		}
		return !this.notPrintServiceAndMethod.contains(serviceAndMethod);
	}
	
	public boolean isRoomServiceAndMethod(String serviceAndMethod) {
		if(serviceAndMethod.contains(roomServiceName)) {
			if(this.roomServiceAndMethodNotSyn.contains(serviceAndMethod)) {
				return false;
			}
			return true;
		}
		return false;
	}
	
}
