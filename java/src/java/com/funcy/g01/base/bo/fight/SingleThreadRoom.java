package com.funcy.g01.base.bo.fight;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.ebo.synframework.synroom.executor.SynRoom;
import com.funcy.g01.base.global.ServerConfig;

abstract class SingleThreadRoom extends SynRoom implements Runnable {
	
	private static final Logger logger = Logger.getLogger(SingleThreadRoom.class);

	private Thread thread;
	
	private ReentrantLock lock = new ReentrantLock(true);
	
	private Condition condition;
	
	public static final double updatePeriod = TimeUnit.SECONDS.toNanos(1) * 1d / FightConfig.fps;
	
	protected long roomStartTime;
	
	private int frameIndex = 0;
	
	private boolean isStop = false;
	
	private HashMap<Integer, List<FixFrameEvent>> fixFrameEvents = new HashMap<Integer, List<FixFrameEvent>>();
	
	private LinkedBlockingDeque<SynEvent> rightNowExcuteEvents = new LinkedBlockingDeque<SynEvent>();
	
	private LinkedBlockingDeque<SynPeriodEvent> timeEvents = new LinkedBlockingDeque<SynPeriodEvent>();
	
	private long curTime;
	
	private long actRoomCreateTime = System.currentTimeMillis();
	
	public static final long fixFrameEventMaxExcutetime = TimeUnit.MILLISECONDS.toNanos(1);
	
	public static final long allFixFrameEventMaxTime = TimeUnit.MILLISECONDS.toNanos(3);
	
	public static final long updateWorldMaxTime = TimeUnit.MILLISECONDS.toNanos(3);
	
	public static final long synEventMaxTime = TimeUnit.MILLISECONDS.toNanos(1);
	
	public static final long allSynEventMaxTime = TimeUnit.MILLISECONDS.toNanos(3);
	
	public static final long doSomethingMaxTime = TimeUnit.MILLISECONDS.toNanos(2);
	
	public static final long maxFrameTime = (long) (updatePeriod * 1.5f);
	
	private long lastUpdateEndTime = 0;
	
	public SingleThreadRoom(long roomId) {
		super(roomId);
		this.thread = new Thread(this, "single-thread-room-" + roomId);
		this.condition = this.lock.newCondition();
	}

	@Override
	public void run() {
		this.roomStartTime = System.nanoTime();
		this.lock.lock();
		while(true) {
			if(isStop) {
				break;
			}
			this.frameIndex++;
			List<FixFrameEvent> events = fixFrameEvents.get(this.frameIndex);
			if(events != null) {
				if(ServerConfig.isStat) {
					long allStartNano = System.nanoTime();
					for (FixFrameEvent event : events) {
						try {
							long startNano = System.nanoTime();
							event.executeEvent();
							long endNano = System.nanoTime();
							long executeTime = endNano - startNano;
							if(executeTime > fixFrameEventMaxExcutetime) {
								logger.info(String.format("event(%s) execute time(%d) is exceet the max time", event.toString(), executeTime));
							}
						} catch (Exception e) {
							logger.error(event.toString(), e);
						}
					}
					long allEndNano = System.nanoTime();
					long allExecuteTime = allEndNano - allStartNano;
					if(allExecuteTime > allFixFrameEventMaxTime) {
						StringBuffer sb = new StringBuffer();
						for (FixFrameEvent event : events) {
							sb.append(event.getEventType().toString()).append(" ");
						}
						logger.info(String.format("all event(count:%d,all events:%s) execute time(%d) is exceet the max time", events.size(), sb.toString(), allExecuteTime));
					}
				} else {
					for (FixFrameEvent event : events) {
						try {
							event.executeEvent();
						} catch (Exception e) {
							logger.error(event.toString(), e);
						}
					}
				}
				events.clear();
			}
			try {
				if(ServerConfig.isStat) {
					long startNano = System.nanoTime();
					this.update();
					long endNano = System.nanoTime();
					long executeTime = endNano - startNano;
					if(executeTime > updateWorldMaxTime) {
						logger.info(String.format("update world(frameIndex:%d) execute time(%d) is exceet the max time", this.getFrameIndex(), executeTime));
					}
				} else {
					this.update();
				}
				
			} catch (Exception e) {
				logger.error(String.format("room Id:%d, frameIndex: %d", this.getRoomId(), this.frameIndex) , e);
				e.printStackTrace();
			}
			
			while(true) {
				this.curTime = System.nanoTime();
				long waitTime = this.roomStartTime + (long)(updatePeriod * this.frameIndex) - curTime;
				if(waitTime > 0) {
					try {
//						logger.info(String.format("frameIndex:%d wait time: %d", this.getFrameIndex(), waitTime));
						this.condition.await(waitTime, TimeUnit.NANOSECONDS);
					} catch (InterruptedException e) {
					}
					try {
						tryExecuteSynEvents();
					} catch(Exception e) {
						logger.error(String.format("room Id:%d, frameIndex: %d", this.getRoomId(), this.frameIndex) , e);
					}
				} else {
					try {
						tryExecuteSynEvents();
						tryExecuteTimeEvents();
						if(ServerConfig.isStat) {
							long startNano = System.nanoTime();
							doSomething();
							long endNano = System.nanoTime();
							long executeTime = endNano - startNano;
							if(executeTime > doSomethingMaxTime) {
								logger.info(String.format("dosomething(frameIndex:%d) execute time(%d) is exceet the max time", this.getFrameIndex(), executeTime));
							}
						} else {
							doSomething();
						}
						
					} catch(Exception e) {
						logger.error(String.format("room Id:%d, frameIndex: %d", this.getRoomId(), this.frameIndex) , e);
						e.printStackTrace();
					}
					break;
				}
			}
			
			if(this.lastUpdateEndTime == 0) {
				this.lastUpdateEndTime = System.nanoTime();
			} else {
				long cur = System.nanoTime();
				if(ServerConfig.isStat) {
					long executeTime = cur - this.lastUpdateEndTime;
					if(executeTime > maxFrameTime) {
						logger.info(String.format("cur frame (frameIndex:%d) execute time(%d) is exceet the max time", this.getFrameIndex(), executeTime));
					}
					this.lastUpdateEndTime = System.nanoTime();
				}
			}
		}
		this.lock.unlock();
		logger.info(String.format("thread run end, room Id:%d, frameIndex: %d", this.getRoomId(), this.frameIndex));
	}
	
	public void waitToDead() {
		try {
			this.thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void end() {
		this.isStop = true;
	}
	
	public void clearState() {
		this.timeEvents.clear();
		this.fixFrameEvents.clear();
		this.frameIndex = 0;
		this.roomStartTime = System.nanoTime();
	}
	
	public void restart() {
		this.isStop = false;
		this.thread = new Thread(this, "single-thread-room-" + this.getRoomId());
		this.thread.start();
	}
	
	private void tryExecuteTimeEvents() {
		if(this.timeEvents.size() > 0) {
			Iterator<SynPeriodEvent> it = this.timeEvents.iterator();
			while(it.hasNext()) {
				SynPeriodEvent event = it.next();
				event.tryExecute(this.curTime);
			}
		}
	}

	private void tryExecuteSynEvents() {
		if(this.rightNowExcuteEvents.size() > 0) {
			if(ServerConfig.isStat) {
				List<SynEvent> rightNowExecuteEvents = new LinkedList<SynEvent>();
				this.rightNowExcuteEvents.drainTo(rightNowExecuteEvents);
				long allStartNano = System.nanoTime();
				for (SynEvent synEvent : rightNowExecuteEvents) {
					try {
						long startNano = System.nanoTime();
						synEvent.executeEvent();
						long endNano = System.nanoTime();
						long executeTime = endNano - startNano;
						if(executeTime > synEventMaxTime) {
							logger.info(String.format("execute rightnow execute synevent(%s) execute time(%d) is exceet the max time", synEvent.toString(), executeTime));
						}
					} catch (Exception e) {
						logger.error(synEvent.toString(), e);
						e.printStackTrace();
					}
				}
				long allEndNano = System.nanoTime();
				long allExecuteTime = allEndNano - allStartNano;
				if(allExecuteTime > allSynEventMaxTime) {
					StringBuffer sb = new StringBuffer();
					for (SynEvent synEvent : rightNowExecuteEvents) {
						sb.append(synEvent.getServiceAndMethod()).append(" ");
					}
					logger.info(String.format("execute all rightnow execute synevent(count:%d,events:%s) execute time(%d) is exceet the max time", rightNowExecuteEvents.size(), sb.toString(), allExecuteTime));
				}
			} else {
				List<SynEvent> rightNowExecuteEvents = new LinkedList<SynEvent>();
				this.rightNowExcuteEvents.drainTo(rightNowExecuteEvents);
				for (SynEvent synEvent : rightNowExecuteEvents) {
					try {
						synEvent.executeEvent();
					} catch (Exception e) {
						logger.error(synEvent.toString(), e);
						e.printStackTrace();
					}
				}
			}
			
			
		}
	}
	
	public void addPeriodTimeExecuteEvent(SynPeriodEvent synPeriodEvent) {
		synPeriodEvent.setStartTime(this.curTime);
		timeEvents.add(synPeriodEvent);
	}
	
	public void removePeriodTimeExecuteEvent(SynPeriodEvent synPeriodEvent) {
		timeEvents.remove(synPeriodEvent);
	}
	
	public void performAtFixFrameIndex(FixFrameEvent event) {
		if(event.getFrameIndex() <= this.frameIndex) {
//			logger.error(event.toString());
			event.setFrameIndex(this.frameIndex + 1);
		}
		List<FixFrameEvent> events = this.fixFrameEvents.get(event.getFrameIndex());
		if(events == null) {
			events = new LinkedList<FixFrameEvent>();
			this.fixFrameEvents.put(event.getFrameIndex(), events);
		}
		events.add(event);
	}
	
	public void stop() {
		this.isStop = true;
	}
	
	public void startUpdate() {
		this.thread.start();
	}
	
	public void executeRightNow(SynEvent event) {
		this.rightNowExcuteEvents.add(event);
		if(this.lock.tryLock()) {
			try {
				this.condition.signalAll();
			} catch (Exception e) {
			} finally {
				this.lock.unlock();
			}
		}
	}
	
	public int getFrameIndex() {
		return frameIndex;
	}

	public abstract void doSomething();
	
	public abstract void update();

	public long getActRoomCreateTime() {
		return actRoomCreateTime;
	}
	
}
