package com.funcy.g01.base.net;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.ebo.synframework.nettybase.dispatcher.CmdDispatcher;
import com.ebo.synframework.nettybase.dispatcher.ReqCmd;
import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.performance.PerformanceHandler;
import com.funcy.g01.dispatcher.bo.DispatchClient;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;

@Component
public class RankingServerClientMsgHandler extends SimpleChannelUpstreamHandler implements ApplicationContextAware {

	private static final Logger logger = Logger.getLogger(RankingServerClientMsgHandler.class);
	
	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	
	@Autowired
	private CmdDispatcher cmdDispatcher;

	@Autowired
	private PerformanceHandler performanceHandler;
	
	@Autowired
	private ServerContext serverContext;
	
	private ApplicationContext ac;
	
	@Override
	public void messageReceived(final ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		final long startTime = System.currentTimeMillis();
		final ReqCmd reqCmd = (ReqCmd) e.getMessage();
		final String serviceAndMethod = reqCmd.getServiceAndMethodStr();
		RankingServerClient client = (RankingServerClient)ctx.getAttachment();
		printServiceAndMethod(reqCmd, serviceAndMethod);
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
				dispatch0(ctx, (RankingServerClient)ctx.getAttachment(), reqCmd, serviceAndMethod, startTime);
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
		logger.info("call("+reqCmd.getLength()+"):" + serviceAndMethod + "," + sb.toString());
	}

	private void dispatch0(final ChannelHandlerContext ctx,
			final RankingServerClient client, final ReqCmd reqCmd,
			final String serviceAndMethod, final long startTime) {
		long endTime=0;
		boolean isHadException=false;
		try {
			Object returnObj = cmdDispatcher.dispatch(reqCmd, client);
			if (returnObj == null || returnObj instanceof Void) {
				return;
			}
			Builder builder = (Builder) returnObj;
			client.send(new Object[] {serviceAndMethod, 0, builder});
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
		logger.info("people connected," + e.toString());
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		logger.info("people disconnected," + e.toString());
		final RankingServerClient client = (RankingServerClient)ctx.getAttachment();
		serverContext.removeRankingServerClient(client);
		ctx.setAttachment(null);
		executor.schedule(new Runnable() {
			@Override
			public void run() {
				RankingServerClient client = RankingServerClientInitor.initRankingServerClient(ac);
				serverContext.addRankingServerClient4Send(client);
			}
		}, 3, TimeUnit.SECONDS);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		logger.error("meet an uncatch exception!", e.getCause());
	}

	public void setApplicationContext(ApplicationContext ac) {
		this.ac = ac;
	}
	
}
