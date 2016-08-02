/**
 *
 */
package com.jianfei.core.common.security.oauth2;

import com.jianfei.core.common.utils.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.oauth2.provider.token.TokenStore;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

/**
 * Create on @2014年8月1日 @下午8:13:17
 *
 * @author dongsongliu2012@gmail.com
 */
public class TokenStoreFactoryBean implements FactoryBean<TokenStore>, InitializingBean, ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenStoreFactoryBean.class);

    private ApplicationContext applicationContext;

    private TokenStore tokenStore;

    @Override
    public void afterPropertiesSet() throws Exception {
        JedisSentinelPool jedisPool = applicationContext.getBean(JedisSentinelPool.class);
        tokenStore = new RedisTokenStore(jedisPool);
        LOGGER.info("使用redis存储token");
    }

    @Override
    public TokenStore getObject() throws Exception {
        return tokenStore;
    }

    @Override
    public Class<?> getObjectType() {
        return TokenStore.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
}
