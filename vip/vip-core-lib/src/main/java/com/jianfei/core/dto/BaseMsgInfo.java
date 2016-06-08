package com.jianfei.core.dto;

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
     * Code 0 成功 -1 失败 其他使用HTTP状态码
     */
    private int code;

    /**
     * 是否成功
     */
    private String msg;

    /**
     * 数据
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
        baseMsgInfo.setMsg("失败");
        baseMsgInfo.setData(data);
        return baseMsgInfo;
    }

    public static BaseMsgInfo fail(String msg,Object data){
        BaseMsgInfo baseMsgInfo=new BaseMsgInfo();
        baseMsgInfo.setCode(-1);
        baseMsgInfo.setMsg(msg);
        baseMsgInfo.setData(data);
        return baseMsgInfo;
    }
    public int getCode() {
        return code;
    }

    public BaseMsgInfo setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public BaseMsgInfo setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public BaseMsgInfo setData(Object data) {
        this.data = data;
        return this;
    }
}
