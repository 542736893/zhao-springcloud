package com.zhao.account.application.command;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * 扣减账户余额命令
 */
@Data
public class DebitAccountCommand {
    
    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    @Positive(message = "用户ID必须大于0")
    private Long userId;
    
    /**
     * 扣减金额
     */
    @NotNull(message = "扣减金额不能为空")
    @Positive(message = "扣减金额必须大于0")
    private BigDecimal amount;
    
    /**
     * 操作原因
     */
    private String reason;
    
    /**
     * 业务流水号
     */
    private String businessNo;
    
    public DebitAccountCommand() {}
    
    public DebitAccountCommand(Long userId, BigDecimal amount) {
        this.userId = userId;
        this.amount = amount;
    }
    
    public DebitAccountCommand(Long userId, BigDecimal amount, String reason) {
        this.userId = userId;
        this.amount = amount;
        this.reason = reason;
    }
    
    public DebitAccountCommand(Long userId, BigDecimal amount, String reason, String businessNo) {
        this.userId = userId;
        this.amount = amount;
        this.reason = reason;
        this.businessNo = businessNo;
    }
}
