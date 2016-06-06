package com.jianfei.core.service.base.impl;

import com.jianfei.core.bean.AppConfig;
import com.jianfei.core.bean.AppPicture;
import com.jianfei.core.common.enu.PictureType;
import com.jianfei.core.dto.VipCardInfoDto;
import com.jianfei.core.mapper.AppConfigMapper;
import com.jianfei.core.service.base.AppConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/6/6 18:26
 */
@Service
public class AppConfigManagerImpl implements AppConfigManager {
    @Autowired
   private AppPictureManagerImpl appPictureManager;
    @Autowired
  private  AppConfigMapper appConfigMapper;
    /**
     * 获取配置信息VIP权益
     *
     * @return
     */
    @Override
    public VipCardInfoDto getVipCardInfo() {
        List<AppPicture> list=appPictureManager.getPicture(PictureType.VIP_APP_HOME);
        VipCardInfoDto vipCardInfoDto=appConfigMapper.selectVipCardInfo(1);
        vipCardInfoDto.setImages(list);
        return vipCardInfoDto;
    }

    /**
     * 获取常见问题
     *
     * @return
     */
    @Override
    public AppConfig getQAInfo() {
        List<AppConfig> list=appConfigMapper.selectByType(2);
        return list == null || list.isEmpty() ? new AppConfig() : list.get(0);
    }
}
