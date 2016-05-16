
package com.jianfei.frame.error;

/**
 * 子错误码定义.
 */
public enum SubErrorType {

    /**
     * 服务不可用.
     */
    ISP_SERVICE_UNAVAILABLE("isp.xxx-service-unavailable"),

    /**
     * 服务超时.
     */
    ISP_SERVICE_TIMEOUT("isp.xxx-service-timeout"),

    /**
     * 缺少参数.
     */
    ISV_MISSING_PARAMETER("isv.missing-parameter"),

    /**
     * 无效的参数.
     */
    ISV_INVALID_PARAMETER( "isv.invalid-parameter"),

    /**
     * 参数不匹配.
     */
    ISV_PARAMETERS_MISMATCH("isv.parameters-mismatch"),

    /**
     * 返回值转换错误.
     */
    ISV_RETURN_VALUE_CONVERT("isv.return-value-convert"),

    /**
     * CyCore业务出错.
     */
    ISV_CYCORE_ERROR("isv.cycore-error"),

    /**
     * 未知的子错误
     */
    ISV_UNKNOWN_SUB_ERROR("isv.unknown-sub-error");

    private String value;

    SubErrorType(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}

