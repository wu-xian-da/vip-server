package com.jianfei.frame.error.support;

/**
 * Created by XiaFan on 15-12-21.
 */
public class ErrorCodeException extends Exception {
    static final long serialVersionUID = -4312169147091467431L;
    private int errorCode = -1;
    private String solution;

    public ErrorCodeException() {
        super("Internal Error");
    }

    public ErrorCodeException(int errorCode, String message) {
        this(errorCode, message, "");
    }

    public ErrorCodeException(int errorCode, String message, String solution) {
        super(message);
        this.errorCode = errorCode;
        this.solution = solution;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
