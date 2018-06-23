package com.funcy.g01.ranking.net;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ebo.synframework.nettybase.dispatcher.CmdDispatcher;
import com.ebo.synframework.nettybase.dispatcher.ReqCmd;
import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.net.DispatchServer;
import com.funcy.g01.base.net.RankingServerClient;
import com.funcy.g01.base.performance.PerformanceHandler;
import com.funcy.g01.dispatcher.bo.DispatchClient;
import com.funcy.g01.dispatcher.bo.DispatchServerContext;
import com.funcy.g01.ranking.bo.RankingClient;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;

@Component
public class RankingMsgHandler extends SimpleChannelUpstreamHandler {
	
	private static final Logger logger = Logger.getLogger(RankingMsgHandler.class);
	
	@Autowired
	private PerformanceHandler performanceHandler;
	
	@Autowired
	private CmdDispatcher cmdDispatcher;
	
	@Autowired
	private ServerContext serverContext;
	
	@Autowired
	private DispatchServerContext dispatchServerContext;
	
	private Executor executor = Executors.newCachedThreadPool();
	
	public static final String login_service_and_method = "dispatchLoginService.login";
	
	public static final long start_wait_time = TimeUnit.SECONDS.toMillis(10);
	
	@Override
	public void messageReceived(final ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		final long startTime = System.currentTimeMillis();
		final ReqCmd reqCmd = (ReqCmd) e.getMessage();
		final String serviceAndMethod = reqCmd.getServiceAndMethodStr();
		printServiceAndMethod(reqCmd, serviceAndMethod);
		if(startTime - serverContext.getServerStartTime() <= start_wait_time && !login_service_and_method.equals(serviceAndMethod)) {
			throw new RuntimeException("the dispatch server just start, need to wait some time");
		}
		final RankingClient client = (RankingClient)ctx.getAttachment();
		if(serviceAndMethod.equals(client.getWaitServiceAndMethodStr())) {
			if(reqCmd.getMessage()!=null){
				client.setRespAndNotify((Message)reqCmd.getMessage());
			}else if(reqCmd.getReusedMessage()!=null){
				client.setRespAndNotify(reqCmd.getReusedMessage().getBuilder().build());
			}
			return;
		}
		this.executor.execute(new Runnable() {
			@Override
			public void run() {
				dispatch0(ctx, client, reqCmd, serviceAndMethod, startTime);
			}
		});
	}
	
	private void printServiceAndMethod(
			final ReqCmd reqCmd, final String serviceAndMethod) {
		GeneratedMessage message = reqCmd.getMessage();
		if(message==null){
			if(reqCmd.getReusedMessage()!=null){
				message= (GeneratedMessage) reqCmd.getReusedMessage().getBuilder().build();
			}
		}
		List<FieldDescriptor> fields = message.getDescriptorForType().getFields();
		StringBuilder sb = new StringBuilder("[");
		
		//打印log
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
		logger.info("call dispatch server("+reqCmd.getLength()+"):" + serviceAndMethod + "," + sb.toString());
	}

	private void dispatch0(final ChannelHandlerContext ctx,
			final RankingClient client, final ReqCmd reqCmd,
			final String serviceAndMethod, final long startTime) {
		long endTime=0;
		boolean isHadException=false;
		try {
			Object returnObj = cmdDispatcher.dispatch(reqCmd, client);
			if (returnObj == null || returnObj instanceof Void) {
				return;
			}
			Builder builder = (Builder) returnObj;
			client.respond(new Object[] {serviceAndMethod, 0, builder});
		} catch (BusinessException e) {
			isHadException=true;
			logger.error("business exception, event=" + serviceAndMethod + " msg:" + e.getMsg(), e);
		} catch (Throwable throwable) {
			isHadException = true;
			logger.error("unknown exception, event=" + serviceAndMethod, throwable);
			throwable.printStackTrace();
		}
		finally{
			endTime=System.currentTimeMillis();
			performanceHandler.handleEvent(startTime, endTime, serviceAndMethod, isHadException);
		}
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		logger.info("dispatch client connected," + e.toString());
		RankingClient client = new RankingClient(ctx);
		ctx.setAttachment(client);
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		logger.info("dispatch client disconnected," + e.toString());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		logger.error("dispatch server meet an uncatch exception!", e.getCause());
	}
	
}
