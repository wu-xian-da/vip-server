package com.jianfei.core.service.base;

import com.jianfei.core.bean.AppConfig;
import com.jianfei.core.dto.VipCardInfoDto;

import java.util.List;

/**
 * APP Config manager
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/31 10:19
 */
public interface AppConfigManager {

    /**
     * 获取配置信息VIP权益
     * @return
     */
   VipCardInfoDto getVipCardInfo();

    /**
     * 获取常见问题
      * @return
     */
   AppConfig getQAInfo();
}
