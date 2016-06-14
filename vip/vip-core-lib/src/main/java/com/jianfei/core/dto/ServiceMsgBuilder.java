package com.jianfei.core.dto;

import com.jianfei.core.common.enu.MsgType;

import java.io.Serializable;

/**
 * 服务消息DTO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/6/14 11:16
 */
public class ServiceMsgBuilder implements Serializable {

    private static final long serialVersionUID = 2413634329113929714L;
    /**
     * 用户手机号
     * 必填
     */
    private String userPhone;
    /**
     * 消息类型
     * @see MsgType
     * 必填
     */
    private String msgType;
    /**
     * 消息内容
     * 可选
     */
    private String msgBody;
    /**
     * VIP卡号
     * 可选
     */
    private String vipCardNo;
    /**
     * 用户姓名
     * 可选
     */
    private String userName;

    public String getUserPhone() {
        return userPhone;
    }

    public ServiceMsgBuilder setUserPhone(String userPhone) {
        this.userPhone = userPhone;
        return this;
    }

    public String getMsgType() {
        return msgType;
    }

    public ServiceMsgBuilder setMsgType(String msgType) {
        this.msgType = msgType;
        return this;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public ServiceMsgBuilder setMsgBody(String msgBody) {
        this.msgBody = msgBody;
        return this;
    }

    public String getVipCardNo() {
        return vipCardNo;
    }

    public ServiceMsgBuilder setVipCardNo(String vipCardNo) {
        this.vipCardNo = vipCardNo;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public ServiceMsgBuilder setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
