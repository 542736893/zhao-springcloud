package com.zhao.order.infrastructure.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Sentinel流控配置类
 */
@Configuration
public class SentinelConfig {
    
    /**
     * 初始化流控规则
     */
    @PostConstruct
    public void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        
        // 订单创建接口流控规则
        FlowRule createOrderRule = new FlowRule();
        createOrderRule.setResource("createOrder");
        createOrderRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        createOrderRule.setCount(100); // 每秒100个请求
        rules.add(createOrderRule);
        
        // 订单查询接口流控规则
        FlowRule queryOrderRule = new FlowRule();
        queryOrderRule.setResource("queryOrder");
        queryOrderRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        queryOrderRule.setCount(200); // 每秒200个请求
        rules.add(queryOrderRule);
        
        // 订单状态变更接口流控规则
        FlowRule updateOrderRule = new FlowRule();
        updateOrderRule.setResource("updateOrder");
        updateOrderRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        updateOrderRule.setCount(50); // 每秒50个请求
        rules.add(updateOrderRule);
        
        FlowRuleManager.loadRules(rules);
    }
}
