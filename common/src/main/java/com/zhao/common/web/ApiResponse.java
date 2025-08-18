package com.zhao.common.web;

public class ApiResponse<T> {

    private String code;
    private String message;
    private String traceId;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(String code, String message, String traceId, T data) {
        this.code = code;
        this.message = message;
        this.traceId = traceId;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T data) {
        ApiResponse<T> r = new ApiResponse<>();
        r.setCode(ErrorCode.SUCCESS.getCode());
        r.setMessage("OK");
        r.setData(data);
        return r;
    }

    public static <T> ApiResponse<T> fail(String code, String message) {
        ApiResponse<T> r = new ApiResponse<>();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}


