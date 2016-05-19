package com.jianfei.core.service.thirdpart;

import com.jianfei.core.common.enu.MsgType;

/**
 *  短信相关接口
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 17:12
 */
public interface MsgInfoManager {

    /**
     * 发送验证码
     * @param phone
     */
    boolean sendValidateCode(String phone, MsgType msgType);

    /**
     * 验证验证码
     * @param phone 手机号
     * @param msgType 消息类型
     * @param code 验证码
     * @return
     */
    boolean validateSendCode(String phone, MsgType msgType,String code);

    /**
     * 发送短信
     * @param phone 短信号码
     * @param content 短信内容
     * @return
     */
    boolean sendMsgInfo(String phone,String content);

    /**
     * 发送并获得短信验证码
     * @param phone
     */
    String sendAndGetValidateCode(String phone, MsgType msgType);

}
