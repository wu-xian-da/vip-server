package com.jianfei.frame.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by XiaFan on 16-2-25.
 */
public class OpenIdUtil {
    private static final Logger log = LoggerFactory.getLogger(OpenIdUtil.class);
    private static final String key = "ew@2016";

    public OpenIdUtil() {
    }

    public static String checkOpenId(String openId, String appKey) {
        if (StringUtils.isEmpty(openId)) return null;
        try {
            String value = com.iflytek.edu.ew.DESUtil.decrypt(openId, key);
            if (value == null) {
                log.warn("Invalid openId:{} for appKey:{}", openId, appKey);
                return null;
            }
            int index = value.indexOf('_');
            if (index != -1 && appKey.equals(value.substring(index+1))) {
                return value.substring(0, index);
            }
        } catch (Exception e) {
        }
        log.warn("Invalid openId:{} for appKey:{}", openId, appKey);
        return null;
    }

    public static String getOpenId(String userId, String appKey) {
        String openId = null;
        try {
            openId = com.iflytek.edu.ew.DESUtil.ENCRYPTMethod(userId + "_" + appKey, key).toLowerCase();
        } catch (Exception e) {
            log.error("Failed to encode openId, userId:{}, appKey{}", userId, appKey, e);
        }
        return openId;
    }
}
