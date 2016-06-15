package com.jianfei.core.service.base;

import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.dto.BaseMsgInfo;

/**
 * 验证码相关服务
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/6/15 15:28
 */
public interface ValidateCodeManager {
    /**
     * 生成验证码 并存储
     * @param phone
     */
    String getValidateCode(String phone, MsgType msgType);

    /**
     * 验证验证码 并把验证码设置无效
     * @param phone 手机号
     * @param msgType 消息类型
     * @param code 验证码
     * @return
     */
    boolean validateSendCode(String phone, MsgType msgType,String code);

    /**
     * 获取发送的验证码
     * @param phone
     */
    String getSendValidateCode(String phone, MsgType msgType);


    /**
     * 发送相关短信
     * @param phone
     * @param msgType
     * @return
     */
    BaseMsgInfo sendValidateCode(String phone, MsgType msgType);
}
