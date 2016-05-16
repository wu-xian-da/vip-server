/**
 *
 */
package com.jianfei.frame.support;

import com.alibaba.druid.pool.DruidDataSource;
import com.jianfei.frame.spring.EncryptPropertyPlaceholderConfigurer;
import com.jianfei.frame.utils.EnvUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Create on @2014年11月11日 @下午4:20:45
 *
 * @author libinsong1204@gmail.com
 */
public class DruidDataSourceFactoryBean implements FactoryBean<DataSource>, InitializingBean, DisposableBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(DruidDataSourceFactoryBean.class);

    private DruidDataSource dataSource;
    private EncryptPropertyPlaceholderConfigurer propertyConfigurer;

    @Override
    public void afterPropertiesSet() throws Exception {
        Properties props =propertyConfigurer.getProperties();
        String prefix = EnvUtil.getJdbcPrefix().trim();
        if (StringUtils.isNotBlank(prefix)) {
            LOGGER.info("加载数据源配置，配置模块为：" + prefix);
            prefix += ".";
        }

        String url = props.getProperty(prefix + "jdbc.url");
        String username = props.getProperty(prefix + "jdbc.username");
        String password = props.getProperty(prefix + "jdbc.password");
        int initialSize = Integer.valueOf(props.getProperty(prefix + "jdbc.initialSize"));
        int minIdle = Integer.valueOf(props.getProperty(prefix + "jdbc.minIdle"));
        int maxActive = Integer.valueOf(props.getProperty(prefix + "jdbc.maxActive"));

        dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setTestOnBorrow(false);
        dataSource.setFilters("stat");
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxOpenPreparedStatements(10);
    }

    @Override
    public DataSource getObject() throws Exception {
        return dataSource;
    }

    @Override
    public Class<DataSource> getObjectType() {
        return DataSource.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void destroy() throws Exception {
        dataSource.close();
    }

    public void setPropertyConfigurer(EncryptPropertyPlaceholderConfigurer propertyConfigurer) {
        this.propertyConfigurer = propertyConfigurer;
    }
}
