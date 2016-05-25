

package com.jianfei.core.common.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 使用jedis shard连接到redis服务器。
 * 
 * Create on @2013-10-10 @上午10:21:20 
 * @author libinsong1204@gmail.com
 */
public class JedisPoolFactoryBean implements InitializingBean, DisposableBean, FactoryBean<JedisPool> {
	private static final Logger LOGGER = LoggerFactory.getLogger(JedisPoolFactoryBean.class);
	
	private String redisAddress;
	
	private int timeout = 2000;
	
	private JedisPoolConfig config;
	
	private JedisPool jedisPool;

	private int database = 0;

	@Override
	public void afterPropertiesSet() throws Exception {
		String[] arrs = redisAddress.split(":");
		jedisPool = new JedisPool(config, arrs[0], Integer.valueOf(arrs[1]), timeout, null, database);
	}
	
	@Override
	public void destroy() throws Exception {
		if(jedisPool != null) {
			try {
				jedisPool.destroy();
			} catch (Exception ex) {
				LOGGER.warn("Cannot properly close Jedis pool", ex);
			}
			jedisPool = null;
		}
	}
	
	@Override
	public JedisPool getObject() throws Exception {
		return jedisPool;
	}

	@Override
	public Class<?> getObjectType() {
		return JedisPool.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public String getRedisAddress() {
		return redisAddress;
	}

	public void setRedisAddress(String redisAddress) {
		this.redisAddress = redisAddress;
	}

	public JedisPoolConfig getConfig() {
		return config;
	}

	public void setConfig(JedisPoolConfig config) {
		this.config = config;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}
}
