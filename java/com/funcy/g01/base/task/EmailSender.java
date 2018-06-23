package com.funcy.g01.base.task;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funcy.g01.base.bo.email.TimerEmailInfo;
import com.funcy.g01.base.global.BusinessPool;
import com.funcy.g01.hall.service.EmailService;


@Component
public class EmailSender {

	private static List<EmailSenderTask> timerEmailInfos = new LinkedList<EmailSenderTask>();
	
//	private volatile List<TimerEmailInfo> cache = new LinkedList<TimerEmailInfo>();
	
//	private ExecutorService executor = Executors.newCachedThreadPool();
	
	private Lock lock = new ReentrantLock();
	
	@Autowired
	private BusinessPool businessPool;
	
	@Autowired
	private EmailService emailService;
	/**
	 * 初始化
	 */
	public void init(){
		EmailSenderTask.setEmailService(emailService);
	}
	
//	private Thread t = new Thread(){
//		public void run(){
//			while(cache.size()>0||timerEmailInfos.size()>0){
//				if(cache.size()>0){
//					timerEmailInfos.addAll(cache);
//					cache.clear();
//				}
//				lock.lock();
//				Iterator<TimerEmailInfo> it = timerEmailInfos.iterator();
//				while(it.hasNext()){
//					final TimerEmailInfo timerEmailInfo = it.next();
//					System.out.println(timerEmailInfo.getEmail().getContent());
//					if(timerEmailInfo.getSendTime()<=System.currentTimeMillis()){
//						executor.execute(new Runnable(){
//							public void run(){
//								Email email = timerEmailInfo.getEmail();
//								RoleEmailInfo roleEmailInfo  = null;
//								if (email.getType() == EmailType.SINGLE) {
//									roleEmailInfo = emailRepo.getRoleEmailInfo(email.getAccepterId());
//								}else if(email.getType() == EmailType.ALL){
//									roleEmailInfo = emailRepo.getRoleEmailInfo(ServerConfig.system_role_id);
//								}
//								roleEmailInfo.addEmail(email);
//								emailRepo.saveRoleEmailInfo(roleEmailInfo);
//								EmailUtil.sendEmail(serverContext.getHallLogonPlayers(), email);
//							}
//						});
//						it.remove();
//					}
//				}
//				lock.unlock();
//			}
//		}
//	};
	
//	public void addEmail(TimerEmailInfo TimerEmailInfo){
//		cache.add(TimerEmailInfo);
//		if(t.getState() == State.NEW){
//			t.start();
//		}
//	}
	
	/**
	 * 添加一个定时邮件任务
	 * @param systemMessageTask
	 */
	public void addTimingExecuteTask(final EmailSenderTask task) {
		//存到缓存
		timerEmailInfos.add(task);
		
		TimerEmailInfo timerEmailInfo = task.getTimerEmailInfo();
		
		//当前时间 < 发送时间
		if(timerEmailInfo.getSendTime() >= System.currentTimeMillis()){
			businessPool.schedule(task, timerEmailInfo.getSendTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		}
		//执行完毕，从队列中移除
		remove(timerEmailInfo.getId());
	}
	
//	public List<TimerEmailInfo> getAll(){
//		List<TimerEmailInfo> infos = new LinkedList<TimerEmailInfo>();
//		infos.addAll(timerEmailInfos);
//		infos.addAll(cache);
//		return infos;
//	}
	
	public void remove(int id){
		lock.lock();
		Iterator<EmailSenderTask> it = timerEmailInfos.iterator();
		while(it.hasNext()){
			EmailSenderTask task = it.next();
			if(task.getTimerEmailInfo().getId() == id){
				it.remove();
			}
		}
		lock.unlock();
	}
}
