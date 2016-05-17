package com.jianfei.frame.error.support;



import com.jianfei.frame.error.MainErrorType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by XiaFan on 16-2-19.
 */
public class ExtraSubErrorException extends Exception {
    static final long serialVersionUID = -3568107941122468462L;

    private MainErrorType mainErrorType;
    private Map<String, Object[]> subErrors;
    private Object data;

    public ExtraSubErrorException(MainErrorType mainErrorType) {
        this(mainErrorType, null);
    }

    public ExtraSubErrorException(MainErrorType mainErrorType, Object data) {
        super();
        this.mainErrorType = mainErrorType;
        this.subErrors = new HashMap<>();
//        this.data = data; // 目前不开放data
    }

    public static ExtraSubErrorException getException(MainErrorType mainErrorType, Object[]... subErrors) {
        ExtraSubErrorException exception = new ExtraSubErrorException(mainErrorType);
        for (Object[] subError : subErrors) {
            Object[] params = new Object[subError.length - 1];
            System.arraycopy(subError, 1, params, 0, params.length);
            exception.addSubError((String) subError[0], params);
        }
        return exception;
    }

    public MainErrorType getMainErrorType() {
        return mainErrorType;
    }

    public Object getData() {
        return data;
    }

    public void addSubError(String name, Object... params) {
        subErrors.put(name, params);
    }

    public Map<String, Object[]> getSubErrors() {
        return subErrors;
    }
}
