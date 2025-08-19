package com.zhao.account.infrastructure.config;

import io.seata.spring.boot.autoconfigure.properties.SeataProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * Seata分布式事务配置
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(SeataProperties.class)
public class SeataConfig {

    private final SeataProperties seataProperties;

    public SeataConfig(SeataProperties seataProperties) {
        this.seataProperties = seataProperties;
    }

    @PostConstruct
    public void init() {
        log.info("Seata配置初始化完成");
        log.info("事务服务组: {}", seataProperties.getTxServiceGroup());
        log.info("Seata启用状态: {}", seataProperties.isEnabled());
    }
}
