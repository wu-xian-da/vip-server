
package com.jianfei.frame.error.support;

/**
 * Cycore业务异常
 *
 * @author dsliu@iflytek.com
 */
@SuppressWarnings("serial")
public class CycoreErrorException extends RuntimeException {

    private String errorMessage;

    public CycoreErrorException(String errorMessage) {
        this(errorMessage, errorMessage);
    }

    public CycoreErrorException(String errorMessage, String message) {
        super(message);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
