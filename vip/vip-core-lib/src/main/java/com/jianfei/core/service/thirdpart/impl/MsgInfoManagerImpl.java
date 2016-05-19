package com.jianfei.core.service.thirdpart.impl;

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
public class MsgInfoManagerImpl implements MsgInfoManager {
    /**
     * KEY前缀
     */
    private static final String CODE="";

    /**
     * 发送验证码
     *
     * @param phone
     * @param msgType
     */
    @Override
    public boolean sendValidateCode(String phone, MsgType msgType) {
        //TODO 1、根据配置规则生成验证码
        //TODO 2、根据消息类型 查找相关模板
        //TODO 3、生成发送消息 发送
        //TODO 4、存储验证码 时间可配置
        return false;
    }

    /**
     * 验证验证码
     *
     * @param phone   手机号
     * @param msgType 消息类型
     * @param code    验证码
     * @return
     */
    @Override
    public boolean validateSendCode(String phone, MsgType msgType, String code) {
        //TODO Redis里面获取相关验证码

        return false;
    }

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

    /**
     * 发送并获得短信验证码
     *
     * @param phone
     * @param msgType
     */
    @Override
    public String sendAndGetValidateCode(String phone, MsgType msgType) {
        return null;
    }
}
