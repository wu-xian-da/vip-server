package com.jianfei.core.service.base.impl;

import com.jianfei.core.bean.AppVersion;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.mapper.AppVersionMapper;
import com.jianfei.core.mapper.AppVipcardMapper;
import com.jianfei.core.service.base.AppVersionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //KEY为:APP_VERSION_版本号
        //查询缓存是否存在
        AppVersion appVersion = (AppVersion) JedisUtils.getObject("APP_VERSION_" + channel);
        //不存在查询数据库
        if (appVersion == null) {
            List<AppVersion> list = appVersionMapper.getVersions(channel);
            appVersion = list == null || list.isEmpty() ? new AppVersion() : list.get(0);
            if ("003".equals(channel)){
                Map map=new HashMap();
                map.put("vip_load_url","www.baidu.com");
                appVersion.setMap(map);
            }
            //数据库无论是否存在都设置值 防止缓存穿透
            JedisUtils.setObject("APP_VERSION_" + channel, appVersion, 0);
        }
        return appVersion;
    }

    @Override
    public BaseMsgInfo updateVersion(AppVersion version) {
        AppVersion appVersion=getLastVersion(version.getChannel());
        version.setId(appVersion.getId());
        appVersionMapper.updateByPrimaryKeySelective(version);
        JedisUtils.del("APP_VERSION_"+version.getChannel());
        return BaseMsgInfo.success(true);
    }
}
