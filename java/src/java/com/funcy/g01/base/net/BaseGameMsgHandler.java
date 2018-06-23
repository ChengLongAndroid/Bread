package com.funcy.g01.base.net;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.WriteCompletionEvent;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.ebo.synframework.nettybase.codec.ProtoDecoder;
import com.ebo.synframework.nettybase.dispatcher.CmdDispatcher;
import com.ebo.synframework.nettybase.dispatcher.ReqCmd;
import com.ebo.synframework.nettybase.protoPool.ReusedProtoBuilder;
import com.ebo.synframework.synroom.executor.SynRoom;
import com.ebo.synframework.synroom.executor.SynRoomTask;
import com.ebo.synframework.synroom.executor.pool.SynRoomThreadPool;
import com.funcy.g01.base.bo.fight.SynEvent;
import com.funcy.g01.base.bo.role.RoleNetInfo;
import com.funcy.g01.base.bo.serverconfig.ServerInfoData;
import com.funcy.g01.base.dao.redis.RoleRepo;
import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;
import com.funcy.g01.base.global.BusinessPool;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.global.ServiceConfig;
import com.funcy.g01.base.performance.PerformanceHandler;
import com.funcy.g01.base.proto.global.RespErrorProtoBuffer;
import com.funcy.g01.base.proto.service.AccountServiceRespProtoBuffer.EmptyResp;
import com.funcy.g01.hall.service.AccountService;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message.Builder;

public abstract class BaseGameMsgHandler extends SimpleChannelUpstreamHandler implements ApplicationContextAware {

	private static final Logger logger = Logger.getLogger(BaseGameMsgHandler.class);
	
	private final String USER_LOGIN = "accountService.loginAndEnterHall";
	
	private final String room_user_login = "synFightingService.playerEnterFight";
	
    private static final int DEFAULT_MAX_FRAME_LENGTH = 81920;

    private static final int DEFAULT_LENGTH_FIELD_LENGTH = 4;

	@Autowired
	protected ServerContext serverContext;

	@Autowired
	private CmdDispatcher cmdDispatcher;

	@Autowired
	private PerformanceHandler performanceHandler;
	
	@Autowired
	private BusinessPool businessPool;
	
	@Autowired
	private ServerInfoData serverInfoData;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ServiceConfig serviceConfig;
	
	@Autowired
	private RoleRepo roleRepo;
	
	protected ApplicationContext ac;
	
	protected SynRoomThreadPool synRoomThreadPool;
	
	private List<TimePackageInfoCounter> receiveCounters;
	
	private List<TimePackageInfoCounter> sendCounters;
	
	private ProtoDecoder protoDecoder;
	
	public enum ErrorMsgType {
		backToGame(10), closeGame(11);
		private final int code;
		private ErrorMsgType(int code) {
			this.code = code;
		}
		public int getCode() {
			return code;
		}
	}
	
	public BaseGameMsgHandler() {
		if(ServerConfig.isStat) {
			receiveCounters = new ArrayList<TimePackageInfoCounter>();
    		receiveCounters.add(new TimePackageInfoCounter(1));
    		receiveCounters.add(new TimePackageInfoCounter(3));
    		receiveCounters.add(new TimePackageInfoCounter(8));
    		
    		sendCounters = new ArrayList<TimePackageInfoCounter>();
    		sendCounters.add(new TimePackageInfoCounter(1));
    		sendCounters.add(new TimePackageInfoCounter(3));
    		sendCounters.add(new TimePackageInfoCounter(8));
		}
	}
	
	public List<TimePackageInfoCounter> getReceiveCounters() {
		return receiveCounters;
	}

	public List<TimePackageInfoCounter> getSendCounters() {
		return sendCounters;
	}

	public abstract boolean checkSeriveIsInTheServer(String serviceAndMethod);
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.ac = applicationContext;
	}

	@Override
	public void messageReceived(final ChannelHandlerContext ctx, final MessageEvent e) throws Exception {
		final long startTime = System.currentTimeMillis();
		final GamePlayer gamePlayer = (GamePlayer) ctx.getAttachment();
		gamePlayer.setLastReceiveMsgTime(System.currentTimeMillis());
		
		ReqCmd reqCmd;
		if (e.getMessage() instanceof WebSocketFrame) {
			WebSocketFrame webSocketFrame = (WebSocketFrame) e.getMessage();
			ChannelBuffer channelBuffer = webSocketFrame.getBinaryData();
			if(this.protoDecoder == null) {
				this.protoDecoder = new ProtoDecoder(ServerConfig.getLengthOptimizeConfig());
			}
			reqCmd = (ReqCmd)protoDecoder.decode0(null, channelBuffer);
			if(!gamePlayer.isWebSocket()) {
				gamePlayer.setWebSocket(true);			
			}
        } else {
        	reqCmd = (ReqCmd) e.getMessage();
        }
		
		handleMsg0(ctx, reqCmd, startTime, gamePlayer);
	}

	public void handleMsg0(final ChannelHandlerContext ctx,
			final ReqCmd reqCmd, final long startTime,
			final GamePlayer gamePlayer) {
		if(ServerConfig.isStat) {
			int curTimeInSecond = (int) (System.currentTimeMillis() / 1000);
			int length = reqCmd.getLength() + 4;
			for (TimePackageInfoCounter timePackageInfoCounter : receiveCounters) {
				timePackageInfoCounter.count(length, curTimeInSecond);
			}
			gamePlayer.countReceivePackage(reqCmd);
		}
		final String serviceAndMethod =  reqCmd.getServiceAndMethodStr();
		if(serviceConfig.isCanPrint(serviceAndMethod, reqCmd.getMessage())) {
			printServiceAndMethod(gamePlayer, reqCmd, serviceAndMethod);
		}
		checkCmdIndex(gamePlayer, reqCmd);
		
		if (!USER_LOGIN.equalsIgnoreCase(serviceAndMethod) && !room_user_login.equalsIgnoreCase(serviceAndMethod)) { //除了登陆都需要检查
			if(gamePlayer.getState() != PlayerState.logon) { //未登录状态
				SynRoomTask synRoomTask = new SynRoomTask(gamePlayer.getLocation()) {
					@Override
					public void processTask() {
						try {
//							checkCmdIndex(gamePlayer, reqCmd);
							dispatch0(ctx, gamePlayer, reqCmd, serviceAndMethod, startTime);
						} catch (Exception e) {
							logger.error("", e);
						}
					}
				};
				this.synRoomThreadPool.execute(synRoomTask);
				return;
			}
		}
		
		if(serviceConfig.isRoomServiceAndMethod(serviceAndMethod)) {
			if(gamePlayer.getSynRoom() != null) {
				SynEvent synEvent = new SynEvent(gamePlayer.getRoleId(), serviceAndMethod) {
					@Override
					public void executeEvent() {
						try {
							dispatch0(ctx, gamePlayer, reqCmd, serviceAndMethod, startTime);
						} catch (Exception e) {
							logger.error("handle msg meet an uncatchexception", e);
						}
					}
				};
				gamePlayer.getSynRoom().executeRightNow(synEvent);
			}
		} else {
			SynRoomTask synRoomTask = new SynRoomTask(gamePlayer.getLocation()) {
				@Override
				public void processTask() {
					try {
						dispatch0(ctx, gamePlayer, reqCmd, serviceAndMethod, startTime);
					} catch (Exception e) {
						logger.error("handle msg meet an uncatchexception", e);
					}
				}
			};
			this.synRoomThreadPool.execute(synRoomTask);
		}
	}

	private Builder buildErrorcodeResp(int errorCode) {
		RespErrorProtoBuffer.RespErrorResultProto.Builder builder = RespErrorProtoBuffer.RespErrorResultProto.newBuilder();
		builder.setErrorCode(errorCode);
		builder.setType(ErrorMsgType.backToGame.getCode());
		String msgStr = "错误码："+errorCode+"\n网络异常。请重新请求。";
		builder.setMsg(msgStr);
		return builder;
	}
	
	private Builder buildErrorMsgAndNotClose(String msg, ErrorMsgType type) {
		RespErrorProtoBuffer.RespErrorResultProto.Builder builder = RespErrorProtoBuffer.RespErrorResultProto.newBuilder();
		builder.setErrorCode(0);
		builder.setType(type.getCode());
		builder.setMsg(msg);
		return builder;
	}
	
	private void buildErrorMsgRespAndClose(String serviceAndMethod, int status, String msg, ErrorMsgType type, final ChannelHandlerContext ctx) {
		RespErrorProtoBuffer.RespErrorResultProto.Builder builder = RespErrorProtoBuffer.RespErrorResultProto.newBuilder();
		builder.setErrorCode(0);
		builder.setType(type.getCode());
		builder.setMsg(msg);
		Object[] objs = new Object[] {serviceAndMethod, status, builder, null};
		ctx.getChannel().write(objs).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				businessPool.schedule(new Runnable() {
					@Override
					public void run() {
						ctx.getChannel().close();
					}
				}, 1, TimeUnit.SECONDS);
			}
		});
	}

	private String checkCmdIndex(final GamePlayer gamePlayer,
			final ReqCmd reqCmd) {
		int cmdIndex = reqCmd.getCmdIndex();
		final String serviceAndMethod = reqCmd.getServiceAndMethodStr();
		if(!serviceAndMethod.equals("accountService.tick")) {
			if (!gamePlayer.trySetLastCmdIndex(cmdIndex)) {
				throw new BusinessException(ErrorCode.CMD_INDEX_ILLEGAL);
			}
		}
		return serviceAndMethod;
	}

	private void printServiceAndMethod(final GamePlayer gamePlayer,
			final ReqCmd reqCmd, final String serviceAndMethod) {
		GeneratedMessage message = reqCmd.getMessage();
		if(message==null){
			if(reqCmd.getReusedMessage()!=null){
				message = (GeneratedMessage) reqCmd.getReusedMessage().getBuilder().build();
			}else{
				throw new RuntimeException("all message of reqCmd is null");
			}
		}
		List<FieldDescriptor> fields = message.getDescriptorForType().getFields();
		StringBuilder sb = new StringBuilder("[");
		
		//打印log
		if(reqCmd.getServiceAndMethodStr().equals("accountService.tick") == false) {
			for(FieldDescriptor fieldDescriptor : fields) {
				Object fieldValue = message.getField(fieldDescriptor);
				if(fieldValue instanceof List) {
					sb.append("{");
					@SuppressWarnings("unchecked")
					List<Object> list = (List<Object>) fieldValue;
					for(Object listValue : list) {
						sb.append(listValue.toString()).append(",");
					}
					sb.deleteCharAt(sb.length() - 1).append("}");
					sb.append(",");
				} else {
					sb.append(fieldValue.toString()).append(",");
				}
			}
			if(sb.length() != 1) {
				sb.deleteCharAt(sb.length() - 1);
			}
			sb.append("]");
			logger.info("call("+reqCmd.getLength()+"):" + serviceAndMethod + ",roleId:" + gamePlayer.getRoleId() + "," + sb.toString());
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		logger.error("meet an uncatch exception!", e.getCause());
	}

	@Override
	public void channelConnected(final ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		logger.info("people connected," + e.toString());
		GamePlayer gamePlayer = new GamePlayer(ctx);
		gamePlayer.setConnectedTime(System.currentTimeMillis());
		ctx.setAttachment(gamePlayer);
		gamePlayer.setLocation(new SynRoom(gamePlayer.getPlayerId()));
		serverContext.addConnectedPlayer(gamePlayer);
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		logger.info("people disconnected," + e.toString());
		GamePlayer gamePlayer = (GamePlayer) ctx.getAttachment();
		if (gamePlayer == null) {
			ctx.getChannel().close();
			return;
		}
		if(gamePlayer.getRoleId() != 0) {
			RoleNetInfo roleNetInfo = roleRepo.getRoleNetInfo(gamePlayer.getRoleId());
			if(isHallServer()) {
				roleNetInfo.setHallRoomId(-1);
				roleNetInfo.setHallServerId(-1);
				roleRepo.saveRoleNetInfo(roleNetInfo);
			} else {
				roleNetInfo.setFightRoomId(-1);
				roleNetInfo.setFightServerId(-1);
				roleRepo.saveRoleNetInfo(roleNetInfo);
			}
		}
		
		playerOffLine(gamePlayer);
	}
	
	public abstract void playerOffLine(GamePlayer gamePlayer);
	
	public abstract boolean isNeedDecrypt(String serviceAndMethod);
	
	public abstract boolean isHallServer();

	private void dispatch0(final ChannelHandlerContext ctx,
			final GamePlayer gamePlayer, final ReqCmd reqCmd,
			final String serviceAndMethod, final long startTime) {
		long endTime=0;
		boolean isHadException=false;
		CmdStatus status = CmdStatus.notDecrypt;
		if (USER_LOGIN.equalsIgnoreCase(serviceAndMethod) || !isNeedDecrypt(serviceAndMethod)) {  
			status = CmdStatus.notDecrypt;
		}
		try {
			Object returnObj = cmdDispatcher.dispatch(reqCmd, gamePlayer);
			if (returnObj == null) {
				return;
			} else if(returnObj == EmptyRespMark.class) {
				gamePlayer.respond(serviceAndMethod, status, EmptyResp.newBuilder());
				return;
			}
//			Builder builder = (Builder) returnObj;
			// TODO 返回可能是重用builder，在下层做处理
			gamePlayer.respond(serviceAndMethod, status, returnObj);
//			logger.info("resp " + serviceAndMethod + ", roleId:" + gamePlayer.getRoleId());
		} catch (BusinessException e) {
			isHadException=true;
			Builder builder = buildErrorcodeResp(e.getCode());
			gamePlayer.respond(serviceAndMethod, status, builder);
			logger.error("business exception, event=" + serviceAndMethod + " msg:" + e.getMsg(), e);
		} catch (Throwable throwable) {
			isHadException = true;
			String msgStr = "网络异常。请重新请求。";
			Builder builder = buildErrorMsgAndNotClose(msgStr, ErrorMsgType.backToGame);
			gamePlayer.respond(serviceAndMethod, status, builder);
			logger.error("unknown exception, event=" + serviceAndMethod, throwable);
			throwable.printStackTrace();
		}
		finally{
			endTime=System.currentTimeMillis();
			performanceHandler.handleEvent(startTime, endTime, serviceAndMethod, isHadException);
		}
	}
	
	@Override
	public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e)
			throws Exception {
		super.writeComplete(ctx, e);
		if(ServerConfig.isStat) {
			int curTimeInSecond = (int) (System.currentTimeMillis() / 1000);
			int length = (int) e.getWrittenAmount();
			for (TimePackageInfoCounter timePackageInfoCounter : sendCounters) {
				timePackageInfoCounter.count(length, curTimeInSecond);
			}
			GamePlayer gamePlayer = (GamePlayer) ctx.getAttachment();
			gamePlayer.countSendPackage((int) e.getWrittenAmount());
		}
	}

	public void setSynRoomThreadPool(SynRoomThreadPool synRoomThreadPool) {
		this.synRoomThreadPool = synRoomThreadPool;
	}

}
