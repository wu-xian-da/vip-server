package com.jianfei.core.service.thirdpart.impl;

import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.utils.StringUtils;
import org.springframework.stereotype.Service;

import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.service.thirdpart.MsgInfoManager;

/**
 * 消息短信相关功能
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 17:31
 */
@Service
public class MsgInfoManagerImpl implements MsgInfoManager {

    /**
     * 发送短信
     *
     * @param phone   短信号码
     * @param content 短信内容
     * @return
     */
    @Override
    public boolean sendMsgInfo(String phone, String content) {
        return false;
    }

}
