
package com.jianfei.frame;


import com.jianfei.frame.error.MainErrors;
import com.jianfei.frame.error.SubErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * @author libinsong1204@gmail.com
 */
public class InitializingService implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitializingService.class);

    private static final String I18N_ERROR = "META-INF/i18n/error";
    private static final String I18N_EXTRA_ERROR = "META-INF/i18n/extra_error";

    @Override
    public void afterPropertiesSet() throws Exception {
        //hibernate validator 显示中文
        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);

        ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
        bundleMessageSource.setDefaultEncoding("UTF8");
        bundleMessageSource.setBasenames(I18N_ERROR, I18N_EXTRA_ERROR);
        LOGGER.info("加载资源文件：" + I18N_ERROR + "," + I18N_EXTRA_ERROR);

        MessageSourceAccessor messageSourceAccessor = new MessageSourceAccessor(bundleMessageSource);
        MainErrors.setErrorMessageSourceAccessor(messageSourceAccessor);
        SubErrors.setErrorMessageSourceAccessor(messageSourceAccessor);
    }

}
