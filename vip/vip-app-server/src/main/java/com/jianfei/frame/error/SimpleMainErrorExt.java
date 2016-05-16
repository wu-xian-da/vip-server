package com.jianfei.frame.error;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by XiaFan on 16-2-19.
 */
@XmlRootElement(name = "mainError")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class SimpleMainErrorExt extends SimpleMainError {
    @XmlElement
    private Object data;

    public SimpleMainErrorExt(int code, String message, String solution, Object data) {
        super(code, message, solution);
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
