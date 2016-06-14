package com.jianfei.core.dto;

import java.io.Serializable;

/**
 * 消息队列
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/6/14 11:16
 */
public class VipCardMsgDto implements Serializable {
    private String vipCardNo;
    private String userPhone;
    private String userName;

    public VipCardMsgDto() {
    }

    public VipCardMsgDto(String vipCardNo, String userPhone, String userName) {
        this.vipCardNo = vipCardNo;
        this.userPhone = userPhone;
        this.userName = userName;
    }

    public String getVipCardNo() {
        return vipCardNo;
    }

    public void setVipCardNo(String vipCardNo) {
        this.vipCardNo = vipCardNo;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
