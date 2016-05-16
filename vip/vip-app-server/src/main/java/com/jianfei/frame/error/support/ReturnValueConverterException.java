package com.jianfei.frame.error.support;

/**
 * Author: yuwang@iflytek.com
 * Date: 2016/1/8 13:53
 */
public class ReturnValueConverterException extends RuntimeException {

    private Class returnValueType;

    public ReturnValueConverterException(Class returnValueType){
        this.returnValueType = returnValueType;
    }

    public Class getReturnValueType() {
        return returnValueType;
    }

    public void setReturnValueType(Class returnValueType) {
        this.returnValueType = returnValueType;
    }
}
