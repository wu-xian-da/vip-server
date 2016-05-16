package com.jianfei.frame.spring;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by XiaFan on 15-12-21.
 */
@XmlRootElement(name = "return")
public class DefaultReturnModel {
    private Integer code = 0;
    private Object data;

    public DefaultReturnModel(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
