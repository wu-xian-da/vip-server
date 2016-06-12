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
     * KEY前缀
     */
    private static final String CODE="PHONE:";

    /**
     * 发送验证码
     *
     * @param phone
     * @param msgType
     */
    @Override
    public boolean sendValidateCode(String phone, MsgType msgType) {
        // 1、根据配置规则生成验证码 6位
        String code=String.valueOf(new java.util.Random().nextInt(1000000));
        //TODO 2、根据消息类型 查找相关模板

        //TODO 3、生成发送消息 发送
        //TODO 4、存储验证码 时间可配置
        JedisUtils.setObject(CODE+phone+":"+msgType.getName(),code,5000000);
        return true;
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
        String validateCode = (String) JedisUtils.getObject(CODE + phone + ":" + msgType.getName());
        return code.equals(validateCode);
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
        // 1、根据配置规则生成验证码 6位
        String code=String.valueOf(new java.util.Random().nextInt(1000000));
        //TODO 2、根据消息类型 查找相关模板

        //TODO 3、生成发送消息 发送
        //TODO 4、存储验证码 时间可配置
        JedisUtils.setObject(CODE+phone+":"+msgType.getName(),code,5000000);
        return code;
    }

    /**
     * 获得发送的短信验证码
     *
     * @param phone
     * @param msgType
     */
    @Override
    public String getValidateCode(String phone, MsgType msgType) {
        return JedisUtils.getObject(CODE+phone+":"+msgType.getName()).toString();
    }
}
