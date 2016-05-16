
package com.jianfei.frame.web.filter.stat;

import com.jianfei.frame.utils.RestContextHolder;
import jsr166e.LongAdder;

import java.util.Date;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class ServiceMethodStat {
	private final String appkey;
	private final String method;

    private volatile int                               runningCount;
    final static AtomicIntegerFieldUpdater<ServiceMethodStat> runningCountUpdater = 
    		AtomicIntegerFieldUpdater.newUpdater(ServiceMethodStat.class, "runningCount");
    
    private volatile LongAdder requestCount = new LongAdder();
    private volatile LongAdder requestTimeNano = new LongAdder();
    private volatile LongAdder errorCount = new LongAdder();
    private volatile LongAdder cycoreCount = new LongAdder();
    private volatile LongAdder cycoreTimeMillis = new LongAdder();
    private volatile long lastAccessTimeMillis = -1;
    
    public ServiceMethodStat(String appkey, String method){
    	this.appkey = appkey;
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
    
    public String getAppkey() {
    	return appkey;
    }

    public void beforeInvoke(long startMillis) {
        runningCountUpdater.incrementAndGet(this);
        requestCount.increment();
        this.setLastAccessTimeMillis(startMillis);
    }

    public void afterInvoke(Throwable error, long nanos) {
        runningCountUpdater.decrementAndGet(this);
        requestTimeNano.add(nanos);
        cycoreCount.add(RestContextHolder.getContext().getCallCycoreCount());
        cycoreTimeMillis.add(RestContextHolder.getContext().getCallCycoreTime());
        if (error != null) {
            errorCount.increment();
        }
    }

    public int getRunningCount() {
        return this.runningCount;
    }

    public long getRequestCountThenReset() {
        return requestCount.sumThenReset();
    }

    public long getRequestTimeMillisThenReset() {
        return requestTimeNano.sumThenReset() / (1000 * 1000);
    }
    
    public long getCycoreCountThenReset() {
        return cycoreCount.sumThenReset();
    }

    public long getCycoreTimeMillisThenReset() {
        return cycoreTimeMillis.sumThenReset();
    }

    public void setLastAccessTimeMillis(long lastAccessTimeMillis) {
        this.lastAccessTimeMillis = lastAccessTimeMillis;
    }

    public Date getLastAccessTime() {
        if (lastAccessTimeMillis < 0L) {
            return null;
        }

        return new Date(lastAccessTimeMillis);
    }

    public long getLastAccessTimeMillis() {
        return lastAccessTimeMillis;
    }

    public long getErrorCountThenReset() {
        return errorCount.sumThenReset();
    }

}
