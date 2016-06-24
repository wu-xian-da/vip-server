package com.jianfei.core.service.thirdpart.impl;

import cn.emay.sdk.client.api.Client;

import com.jianfei.core.common.utils.EmaySMSUtils;
import com.jianfei.core.service.thirdpart.MsgInfoManager;

import org.springframework.stereotype.Service;

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
		Client client = EmaySMSUtils.getClient();
		int result = client.sendSMS(new String[] {phone}, content,"0013", 1);
        //TODO 集成短信接口
		return result==0?true:false;
    }

}
