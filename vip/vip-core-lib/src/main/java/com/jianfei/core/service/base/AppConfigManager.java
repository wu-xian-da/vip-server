package com.jianfei.core.service.base;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppConfig;
import com.jianfei.core.common.enu.RightType;
import com.jianfei.core.dto.VipCardInfoDto;

import java.util.List;
import java.util.Map;

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
   VipCardInfoDto getVipCardInfo(String phone);

    /**
     * 获取配置信息
      * @return
     */
   AppConfig getAppConfig(RightType rightType);
   
   /**
    * 分页获取权益信息
    * @param map
    * @return
    */
   PageInfo<AppConfig> page(int pageNo, int pageSize,Map<String,Object> map);
   
   /**
    * 添加权益
    * @param appConfig
    * @return
    */
   int addRight(AppConfig appConfig);
   
   /**
    * 逻辑删除权益
    * @param id
    * @return
    */
   int delRight(String id);
   
   /**
    * 根据id返回权益信息
    * @param id
    * @return
    */
   AppConfig selectByPrimaryKey(String id);
   
   int updateByPrimaryKeySelective(AppConfig appConfig);
}
