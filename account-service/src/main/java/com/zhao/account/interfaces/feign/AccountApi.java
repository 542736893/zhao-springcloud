package com.zhao.account.interfaces.feign;

import com.zhao.account.interfaces.dto.response.AccountResponse;
import com.zhao.common.web.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 账户对外API接口
 * 供其他服务通过Feign调用
 */
@Tag(name = "账户API", description = "账户服务对外API")
public interface AccountApi {
    
    /**
     * 扣减账户余额
     */
    @Operation(summary = "扣减账户余额", description = "扣减指定用户的账户余额")
    @PostMapping("/api/v1/account/debit")
    ApiResponse<Void> debitAccount(
            @Parameter(description = "用户ID", required = true, example = "1001")
            @RequestParam("userId") Long userId,
            @Parameter(description = "扣减金额", required = true, example = "100.00")
            @RequestParam("amount") Double amount
    );
    
    /**
     * 增加账户余额
     */
    @Operation(summary = "增加账户余额", description = "增加指定用户的账户余额")
    @PostMapping("/api/v1/account/credit")
    ApiResponse<Void> creditAccount(
            @Parameter(description = "用户ID", required = true, example = "1001")
            @RequestParam("userId") Long userId,
            @Parameter(description = "增加金额", required = true, example = "100.00")
            @RequestParam("amount") Double amount
    );
    
    /**
     * 查询账户信息
     */
    @Operation(summary = "查询账户信息", description = "查询指定用户的账户信息")
    @GetMapping("/api/v1/account/user/{userId}")
    ApiResponse<AccountResponse> getAccount(
            @Parameter(description = "用户ID", required = true, example = "1001")
            @PathVariable("userId") Long userId
    );
    
    /**
     * 检查账户余额是否充足
     */
    @Operation(summary = "检查账户余额", description = "检查指定用户的账户余额是否充足")
    @GetMapping("/api/v1/account/check")
    ApiResponse<Boolean> checkBalance(
            @Parameter(description = "用户ID", required = true, example = "1001")
            @RequestParam("userId") Long userId,
            @Parameter(description = "需要金额", required = true, example = "100.00")
            @RequestParam("amount") Double amount
    );
}
