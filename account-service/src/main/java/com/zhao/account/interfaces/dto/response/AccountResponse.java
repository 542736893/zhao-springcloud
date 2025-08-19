package com.zhao.account.interfaces.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账户响应
 */
@Data
@Schema(description = "账户响应")
public class AccountResponse {
    
    @Schema(description = "账户ID", example = "1")
    private Long id;
    
    @Schema(description = "用户ID", example = "1001")
    private Long userId;
    
    @Schema(description = "可用余额", example = "1000.00")
    private BigDecimal balance;
    
    @Schema(description = "冻结金额", example = "100.00")
    private BigDecimal frozenAmount;
    
    @Schema(description = "总余额", example = "1100.00")
    private BigDecimal totalBalance;
    
    @Schema(description = "账户状态", example = "ACTIVE")
    private String status;
    
    @Schema(description = "状态描述", example = "正常")
    private String statusDescription;
    
    @Schema(description = "货币代码", example = "CNY")
    private String currencyCode;
    
    @Schema(description = "货币符号", example = "¥")
    private String currencySymbol;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
