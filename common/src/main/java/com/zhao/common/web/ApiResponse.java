package com.zhao.common.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.slf4j.MDC;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 统一API响应结构
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private String code;
    private String message;
    private String traceId;
    private T data;
    private String timestamp;
    private String path;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.traceId = MDC.get("traceId");
    }

    public ApiResponse(String code, String message, T data) {
        this();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String code, String message, String traceId, T data) {
        this(code, message, data);
        if (traceId != null) {
            this.traceId = traceId;
        }
    }

    /**
     * 成功响应
     */
    public static <T> ApiResponse<T> ok() {
        return ok(null);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), data);
    }

    /**
     * 失败响应
     */
    public static <T> ApiResponse<T> fail(String code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    public static <T> ApiResponse<T> fail(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> ApiResponse<T> fail(ErrorCode errorCode, String customMessage) {
        return new ApiResponse<>(errorCode.getCode(), customMessage, null);
    }

    /**
     * 系统错误响应
     */
    public static <T> ApiResponse<T> systemError() {
        return fail(ErrorCode.SYSTEM_ERROR);
    }

    public static <T> ApiResponse<T> systemError(String message) {
        return fail(ErrorCode.SYSTEM_ERROR, message);
    }

    /**
     * 参数错误响应
     */
    public static <T> ApiResponse<T> parameterError(String message) {
        return fail(ErrorCode.PARAMETER_ERROR, message);
    }

    /**
     * 资源不存在响应
     */
    public static <T> ApiResponse<T> notFound(String message) {
        return fail(ErrorCode.RESOURCE_NOT_FOUND, message);
    }

    /**
     * 未授权响应
     */
    public static <T> ApiResponse<T> unauthorized(String message) {
        return fail(ErrorCode.UNAUTHORIZED, message);
    }

    /**
     * 禁止访问响应
     */
    public static <T> ApiResponse<T> forbidden(String message) {
        return fail(ErrorCode.FORBIDDEN, message);
    }

    /**
     * 请求过于频繁响应
     */
    public static <T> ApiResponse<T> tooManyRequests(String message) {
        return fail(ErrorCode.TOO_MANY_REQUESTS, message);
    }

    /**
     * 服务降级响应
     */
    public static <T> ApiResponse<T> serviceDegraded(String message) {
        return fail(ErrorCode.SERVICE_DEGRADED, message);
    }

    /**
     * 设置请求路径
     */
    public ApiResponse<T> path(String path) {
        this.path = path;
        return this;
    }

    /**
     * 设置自定义traceId
     */
    public ApiResponse<T> traceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    // Getter and Setter methods
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}


