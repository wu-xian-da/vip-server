/**
 * Copyright (C) 2013-2014 科大讯飞股份有限公司 - All rights reserved.
 */

package com.jianfei.frame.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.EnumMap;
import java.util.Locale;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author 陈雄华
 * @version 1.0
 */
public class SubErrors {

    protected static Logger logger = LoggerFactory.getLogger(SubErrors.class);

    /**
     * 子错误和主错误对应Map,key为子错误代码，值为主错误代码
     */
    private static final EnumMap<SubErrorType, MainErrorType> SUB_ERROR_MAIN_ERROR_MAPPINGS =
            new EnumMap<SubErrorType, MainErrorType>(SubErrorType.class);

    static {
        SUB_ERROR_MAIN_ERROR_MAPPINGS.put(SubErrorType.ISP_SERVICE_UNAVAILABLE,
                MainErrorType.SERVICE_CURRENTLY_UNAVAILABLE);

        SUB_ERROR_MAIN_ERROR_MAPPINGS.put(SubErrorType.ISP_SERVICE_TIMEOUT,
                MainErrorType.SERVICE_CURRENTLY_UNAVAILABLE);

        SUB_ERROR_MAIN_ERROR_MAPPINGS.put(SubErrorType.ISV_MISSING_PARAMETER,
                MainErrorType.MISSING_REQUIRED_ARGUMENTS);

        SUB_ERROR_MAIN_ERROR_MAPPINGS.put(SubErrorType.ISV_PARAMETERS_MISMATCH,
                MainErrorType.INVALID_ARGUMENTS);

        SUB_ERROR_MAIN_ERROR_MAPPINGS.put(SubErrorType.ISV_INVALID_PARAMETER,
                MainErrorType.MISSING_REQUIRED_ARGUMENTS);

        SUB_ERROR_MAIN_ERROR_MAPPINGS.put(SubErrorType.ISV_CYCORE_ERROR,
                MainErrorType.CYCORE_ERROR);

        SUB_ERROR_MAIN_ERROR_MAPPINGS.put(SubErrorType.ISV_RETURN_VALUE_CONVERT,
                MainErrorType.RETURN_VALUE_CONVERT);
    }

    private static MessageSourceAccessor messageSourceAccessor;

    public static void setErrorMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
        SubErrors.messageSourceAccessor = messageSourceAccessor;
    }

    /**
     * 获取对应子错误的主错误.
     */
    public static MainError getMainError(SubErrorType subErrorType, Locale locale) {
        return MainErrors.getError(SUB_ERROR_MAIN_ERROR_MAPPINGS.get(subErrorType), locale);
    }


    /**
     *  获取子错误.
     *
     * @param subErrorKey 子错误信息键
     * @param locale      本地化
     * @param params      本地化消息参数
     * @return 子错误描述
     */
    public static SubError getSubError(String subErrorKey, Locale locale, Object... params) {
        try {
            String parsedSubErrorMessage = messageSourceAccessor.getMessage(subErrorKey, params, locale);
            return new SubError(subErrorKey, parsedSubErrorMessage);
        } catch (NoSuchMessageException e) {
            logger.error("不存在对应的错误键：{}，请检查是否正确配置了应用的错误资源，"
                    + "默认位置：i18n/ropError", subErrorKey);
            throw e;
        }
    }
}

