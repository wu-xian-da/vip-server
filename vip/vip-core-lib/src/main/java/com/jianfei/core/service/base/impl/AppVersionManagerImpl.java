package com.jianfei.core.service.base.impl;

import com.jianfei.core.bean.AppVersion;
import com.jianfei.core.mapper.AppVersionMapper;
import com.jianfei.core.mapper.AppVipcardMapper;
import com.jianfei.core.service.base.AppVersionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/6/6 17:27
 */
@Service
public class AppVersionManagerImpl implements AppVersionManager {
    @Autowired
   private AppVersionMapper appVersionMapper;
    /**
     * 根据渠道号获取最新的版本
     *
     * @param channel 渠道版本号
     * @return
     */
    @Override
    public AppVersion getLastVersion(String channel) {
        List<AppVersion> list=appVersionMapper.getVersions(channel);
        return list==null || list.isEmpty()?new AppVersion():list.get(0);
    }
}
