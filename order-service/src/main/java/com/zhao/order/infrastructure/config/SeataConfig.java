package com.zhao.order.infrastructure.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Seata分布式事务配置类
 */
@Configuration
public class SeataConfig {
    
    /**
     * 配置数据源
     */
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }
    
    /**
     * 配置Seata数据源代理
     */
    @Bean
    @Primary
    public DataSource dataSource(DruidDataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }
}
