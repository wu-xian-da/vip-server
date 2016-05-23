package com.jianfei.common;

import java.util.Objects;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/23 15:26
 */
public class BaseMsgInfo {
    /**
     * Code
     */
    private int code;

    /**
     * 是否成功
     */
    private String msg;

    /**
     * 失败
     */
    private Object data;

    public BaseMsgInfo() {
    }

    public static BaseMsgInfo success(Object data) {
        BaseMsgInfo baseMsgInfo = new BaseMsgInfo();
        baseMsgInfo.setCode(0);
        baseMsgInfo.setMsg("success");
        baseMsgInfo.setData(data);
        return baseMsgInfo;
    }

    public static BaseMsgInfo fail(Object data){
        BaseMsgInfo baseMsgInfo=new BaseMsgInfo();
        baseMsgInfo.setCode(-1);
        baseMsgInfo.setMsg("fail");
        baseMsgInfo.setData(data);
        return baseMsgInfo;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
