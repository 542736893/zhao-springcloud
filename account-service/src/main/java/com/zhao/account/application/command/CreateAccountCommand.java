package com.zhao.account.application.command;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

/**
 * 创建账户命令
 */
@Data
public class CreateAccountCommand {
    
    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    @Positive(message = "用户ID必须大于0")
    private Long userId;
    
    /**
     * 初始余额
     */
    @PositiveOrZero(message = "初始余额不能为负数")
    private BigDecimal initialBalance;
    
    /**
     * 创建原因
     */
    private String reason;
    
    public CreateAccountCommand() {}
    
    public CreateAccountCommand(Long userId) {
        this.userId = userId;
        this.initialBalance = BigDecimal.ZERO;
    }
    
    public CreateAccountCommand(Long userId, BigDecimal initialBalance) {
        this.userId = userId;
        this.initialBalance = initialBalance;
    }
    
    public CreateAccountCommand(Long userId, BigDecimal initialBalance, String reason) {
        this.userId = userId;
        this.initialBalance = initialBalance;
        this.reason = reason;
    }
}
