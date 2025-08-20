package com.zhao.order.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Nacos配置类，用于读取Nacos配置中心的配置信息
 * 通过@ConfigurationProperties注解自动绑定Nacos中的配置属性
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "order")
public class NacosConfig {
    
    /**
     * 订单超时时间（毫秒）
     * 默认值：5000ms
     */
    private int timeout = 5000;
    
    /**
     * 最大重试次数
     * 默认值：3次
     */
    private int maxRetry = 3;
    
    /**
     * 是否启用日志
     * 默认值：true
     */
    private boolean enableLogging = true;
    
    /**
     * 自定义配置示例
     * 可以根据业务需要添加更多配置项
     */
    private String customProperty = "default-value";
}