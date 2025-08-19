package com.zhao.account.application.query;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 账户查询条件
 */
@Data
public class AccountQuery {
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 账户状态
     */
    private String status;
    
    /**
     * 最小余额
     */
    private BigDecimal minBalance;
    
    /**
     * 最大余额
     */
    private BigDecimal maxBalance;
    
    /**
     * 是否只查询活跃账户
     */
    private Boolean activeOnly;
    
    /**
     * 是否只查询冻结账户
     */
    private Boolean frozenOnly;
    
    public AccountQuery() {}
    
    public AccountQuery(Long userId) {
        this.userId = userId;
    }
    
    public AccountQuery(Long userId, String status) {
        this.userId = userId;
        this.status = status;
    }
}
