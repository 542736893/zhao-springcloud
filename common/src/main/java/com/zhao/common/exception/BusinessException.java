package com.zhao.common.exception;

import com.zhao.common.web.ErrorCode;

/**
 * 业务异常类
 */
public class BusinessException extends RuntimeException {
    
    private final String errorCode;
    private final Object[] args;

    /**
     * Getter for errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Getter for args
     */
    public Object[] getArgs() {
        return args;
    }
    
    public BusinessException(String message) {
        super(message);
        this.errorCode = ErrorCode.SYSTEM_ERROR.getCode();
        this.args = new Object[0];
    }
    
    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.args = new Object[0];
    }
    
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
        this.args = new Object[0];
    }
    
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode.getCode();
        this.args = new Object[0];
    }
    
    public BusinessException(ErrorCode errorCode, Object... args) {
        super(String.format(errorCode.getMessage(), args));
        this.errorCode = errorCode.getCode();
        this.args = args;
    }
    
    public BusinessException(String errorCode, String message, Object... args) {
        super(String.format(message, args));
        this.errorCode = errorCode;
        this.args = args;
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = ErrorCode.SYSTEM_ERROR.getCode();
        this.args = new Object[0];
    }
    
    public BusinessException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.args = new Object[0];
    }
    
    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode.getCode();
        this.args = new Object[0];
    }
    
    public BusinessException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode.getCode();
        this.args = new Object[0];
    }
}


