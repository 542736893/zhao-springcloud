package com.zhao.common.web;

/**
 * 已移除公共的全局异常处理器，改为各服务内定义本地异常处理器。
 * 该占位类无任何 Spring 注解与逻辑，避免对任意模块产生影响。
 */
public final class GlobalExceptionHandler {
    private GlobalExceptionHandler() {}
}


