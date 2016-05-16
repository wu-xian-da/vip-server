
package com.jianfei.frame.web.listener;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.util.StatusPrinter;
import com.jianfei.frame.utils.EnvUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Create on @2014年8月24日 @下午2:53:09
 *
 * @author libinsong1204@gmail.com
 */
public class LogBackLoadConfigureListener implements ServletContextListener {
    final static Logger logger = LoggerFactory.getLogger(LogBackLoadConfigureListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        try {
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);
            context.reset();
            ClassPathResource resource = new ClassPathResource("logback-default.xml");
            if (!resource.exists()) {
                logger.info("未发现默认logback配置文件logback-default.xml");
                String profile = EnvUtil.getProfile();
                resource = new ClassPathResource("META-INF/logback/logback-" + profile + ".xml");
            }

            configurator.doConfigure(resource.getInputStream());
            logger.info("加载logback配置文件：" + resource.getURL().getPath());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        StatusPrinter.printInCaseOfErrorsOrWarnings(context);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
