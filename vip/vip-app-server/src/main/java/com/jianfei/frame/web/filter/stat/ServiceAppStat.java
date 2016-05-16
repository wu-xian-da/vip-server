
package com.jianfei.frame.web.filter.stat;


import com.jianfei.frame.jetty.JettyConfigUtil;
import com.jianfei.frame.support.NamedThreadFactory;
import com.jianfei.frame.utils.NetUtils;
import jsr166e.LongAdder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class ServiceAppStat {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAppStat.class);

    private volatile int runningCount;
    private volatile int concurrentMax;
    final static AtomicIntegerFieldUpdater<ServiceAppStat> runningCountUpdater =
            AtomicIntegerFieldUpdater.newUpdater(ServiceAppStat.class, "runningCount");
    final static AtomicIntegerFieldUpdater<ServiceAppStat> concurrentMaxUpdater =
            AtomicIntegerFieldUpdater.newUpdater(ServiceAppStat.class, "concurrentMax");

    private final LongAdder requestCount = new LongAdder();
    private final ConcurrentMap<String, ServiceMethodStat> methodStatMap = new ConcurrentHashMap<String, ServiceMethodStat>();

    private final ScheduledExecutorService expireExecutor =
            Executors.newScheduledThreadPool(1, new NamedThreadFactory("CloudServiceRegistryExpireTimer", true));
    private final ScheduledFuture<?> expireFuture;

    private static final String insert_sql = "INSERT INTO mon_app_service_stat(server_host,server_port,appkey,"
            + "method,req_count,req_time,error_count,cycore_time,cycore_count,last_access_time,update_time) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

    private static final String update_sql = "UPDATE mon_app_service_stat SET req_count = req_count + ?, req_time = req_time + ?, "
            + "error_count = error_count + ?, cycore_time = cycore_time + ?, cycore_count = cycore_count + ?, last_access_time = ?, update_time = ? "
            + "WHERE server_host = ? AND server_port = ? AND appkey = ? AND method = ?";

    public ServiceAppStat(final JdbcTemplate jdbcTemplate) {
        final String serverHost = NetUtils.getLocalHost();
        final int serverPort = JettyConfigUtil.getServerPort();
        this.expireFuture = expireExecutor.scheduleWithFixedDelay(new Runnable() {
            public void run() {

                for (ServiceMethodStat methodStat : methodStatMap.values()) {
                    long reqCount = methodStat.getRequestCountThenReset();
                    long reqTime = methodStat.getRequestTimeMillisThenReset();
                    long errorTime = methodStat.getErrorCountThenReset();
                    long cycoreTime = methodStat.getCycoreTimeMillisThenReset();
                    long cycoreCount = methodStat.getCycoreCountThenReset();
                    int result = jdbcTemplate.update(update_sql, reqCount, reqTime,
                            errorTime, cycoreTime, cycoreCount, methodStat.getLastAccessTime(), new Date(),
                            serverHost, serverPort, methodStat.getAppkey(), methodStat.getMethod());

                    if (result == 0) {
                        jdbcTemplate.update(insert_sql, serverHost, serverPort, methodStat.getAppkey(), methodStat.getMethod(),
                                reqCount, reqTime, errorTime, cycoreTime, cycoreCount, methodStat.getLastAccessTime(), new Date());
                    }
                }
            }
        }, 10, 10, TimeUnit.SECONDS); // 每10s进行一次更新

        LOGGER.info("启动统计服务调用信息存储数据库");
    }

    public void beforeInvoke() {
        int running = runningCountUpdater.incrementAndGet(this);

        for (; ; ) {
            int max = concurrentMaxUpdater.get(this);
            if (running > max) {
                if (concurrentMaxUpdater.compareAndSet(this, max, running)) {
                    break;
                } else {
                    continue;
                }
            } else {
                break;
            }
        }

        requestCount.increment();
    }

    public ServiceMethodStat getMethodStat(String appkey, String method) {
        final String mapkey = appkey + method;
        ServiceMethodStat methodStat = methodStatMap.get(mapkey);

        if (methodStat != null) {
            return methodStat;
        }

        if (methodStat == null) {
            methodStatMap.putIfAbsent(mapkey, new ServiceMethodStat(appkey, method));
            methodStat = methodStatMap.get(method);
        }

        return methodStat;
    }

    public void afterInvoke(Throwable error, long nanoSpan) {
        runningCountUpdater.decrementAndGet(this);
    }

    public void destroy() {
        try {
            expireFuture.cancel(true);
        } catch (Throwable t) {
            LOGGER.warn(t.getMessage(), t);
        }
    }

    public int getRunningCount() {
        return runningCount;
    }

    public long getConcurrentMax() {
        return concurrentMax;
    }

    public long getRequestCount() {
        return requestCount.sum();
    }
}
