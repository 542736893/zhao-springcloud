package com.zhao.common.util;

import org.slf4j.MDC;

import java.util.UUID;

/**
 * 请求追踪ID工具类
 */
public class TraceIdUtil {
    
    private static final String TRACE_ID_KEY = "traceId";
    
    /**
     * 生成新的追踪ID
     */
    public static String generateTraceId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }
    
    /**
     * 设置当前线程的追踪ID
     */
    public static void setTraceId(String traceId) {
        if (traceId != null && !traceId.trim().isEmpty()) {
            MDC.put(TRACE_ID_KEY, traceId);
        }
    }
    
    /**
     * 获取当前线程的追踪ID
     */
    public static String getTraceId() {
        String traceId = MDC.get(TRACE_ID_KEY);
        if (traceId == null || traceId.trim().isEmpty()) {
            traceId = generateTraceId();
            setTraceId(traceId);
        }
        return traceId;
    }
    
    /**
     * 清除当前线程的追踪ID
     */
    public static void clearTraceId() {
        MDC.remove(TRACE_ID_KEY);
    }
    
    /**
     * 为当前线程设置新的追踪ID
     */
    public static void setNewTraceId() {
        setTraceId(generateTraceId());
    }
}
