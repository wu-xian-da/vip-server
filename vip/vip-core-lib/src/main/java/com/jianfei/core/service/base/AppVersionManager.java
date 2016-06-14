package com.jianfei.core.service.base;

import com.jianfei.core.bean.AppVersion;
import com.jianfei.core.dto.BaseMsgInfo;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/6/6 17:25
 */
public interface AppVersionManager {
    /**
     * 根据渠道号获取最新的版本
     * @param channel 渠道版本号
     * @return
     */
    AppVersion getLastVersion(String channel);

    /**
     * 根据Version更新
     * @param version
     * @return
     */
    BaseMsgInfo updateVersion(AppVersion version);

}
