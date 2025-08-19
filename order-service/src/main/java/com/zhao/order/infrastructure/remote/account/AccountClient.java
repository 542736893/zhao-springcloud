package com.zhao.order.infrastructure.remote.account;

import com.zhao.common.web.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 账户服务Feign客户端
 */
@FeignClient(
    name = "account-service",
    contextId = "AccountClient",
    path = "/api/v1/account",
    fallbackFactory = AccountClientFallbackFactory.class
)
public interface AccountClient {
    
    /**
     * 扣减账户余额
     */
    @PostMapping("/debit")
    ApiResponse<Void> debitAccount(@RequestParam("userId") Long userId, 
                                 @RequestParam("amount") Double amount);
}


