package com.zhao.account.interfaces.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * 增加账户余额请求
 */
@Data
@Schema(description = "增加账户余额请求")
public class CreditAccountRequest {
    
    @Schema(description = "用户ID", example = "1001")
    @NotNull(message = "用户ID不能为空")
    @Positive(message = "用户ID必须大于0")
    private Long userId;
    
    @Schema(description = "增加金额", example = "100.00")
    @NotNull(message = "增加金额不能为空")
    @Positive(message = "增加金额必须大于0")
    private BigDecimal amount;
    
    @Schema(description = "操作原因", example = "充值")
    private String reason;
    
    @Schema(description = "业务流水号", example = "RECHARGE_20231201_001")
    private String businessNo;
}
