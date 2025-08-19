package com.zhao.common.web;

/**
 * 统一错误码定义
 * 命名规范：{服务简称}-{模块两位}-{三位编号}
 */
public enum ErrorCode {
    
    // 通用成功
    SUCCESS("000", "成功"),
    
    // 通用错误
    SYSTEM_ERROR("SYS-00-001", "系统内部错误"),
    PARAMETER_ERROR("SYS-00-002", "参数错误"),
    VALIDATION_ERROR("SYS-00-003", "参数校验失败"),
    RESOURCE_NOT_FOUND("SYS-00-004", "资源不存在"),
    UNAUTHORIZED("SYS-00-005", "未授权访问"),
    FORBIDDEN("SYS-00-006", "禁止访问"),
    TOO_MANY_REQUESTS("SYS-00-007", "请求过于频繁"),
    SERVICE_DEGRADED("SYS-00-008", "服务降级"),
    
    // 订单服务错误码 (ORD)
    ORDER_CREATE_FAILED("ORD-01-001", "订单创建失败"),
    ORDER_NOT_FOUND("ORD-01-002", "订单不存在"),
    ORDER_STATUS_INVALID("ORD-01-003", "订单状态无效"),
    INSUFFICIENT_INVENTORY("ORD-01-004", "库存不足"),
    INSUFFICIENT_BALANCE("ORD-01-005", "账户余额不足"),
    ORDER_ALREADY_EXISTS("ORD-01-006", "订单已存在"),
    
    // 库存服务错误码 (INV)
    INVENTORY_NOT_FOUND("INV-01-001", "库存记录不存在"),
    INVENTORY_INSUFFICIENT("INV-01-002", "库存不足"),
    INVENTORY_OPERATION_FAILED("INV-01-003", "库存操作失败"),
    PRODUCT_NOT_FOUND("INV-01-004", "商品不存在"),
    WAREHOUSE_NOT_FOUND("INV-01-005", "仓库不存在"),
    
    // 账户服务错误码 (ACC)
    ACCOUNT_NOT_FOUND("ACC-01-001", "账户不存在"),
    ACCOUNT_ALREADY_EXISTS("ACC-01-002", "账户已存在"),
    ACCOUNT_FROZEN("ACC-01-003", "账户已冻结"),
    ACCOUNT_CLOSED("ACC-01-004", "账户已关闭"),
    INSUFFICIENT_BALANCE_ACC("ACC-01-005", "账户余额不足"),
    TRANSACTION_FAILED("ACC-01-006", "交易失败"),
    TRANSACTION_LIMIT_EXCEEDED("ACC-01-007", "交易限额超限"),
    
    // 网关服务错误码 (GW)
    GATEWAY_ROUTE_NOT_FOUND("GW-01-001", "路由不存在"),
    GATEWAY_SERVICE_UNAVAILABLE("GW-01-002", "服务不可用"),
    GATEWAY_TIMEOUT("GW-01-003", "网关超时"),
    GATEWAY_RATE_LIMITED("GW-01-004", "网关限流");
    
    private final String code;
    private final String message;
    
    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    // Getter methods
    public String getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
    
    /**
     * 根据错误码获取错误信息
     */
    public static String getMessageByCode(String code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode.getMessage();
            }
        }
        return "未知错误";
    }
    
    /**
     * 根据错误码获取ErrorCode枚举
     */
    public static ErrorCode getByCode(String code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode;
            }
        }
        return SYSTEM_ERROR;
    }
}


