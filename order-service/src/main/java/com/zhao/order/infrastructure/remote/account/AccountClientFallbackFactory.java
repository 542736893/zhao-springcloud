package com.zhao.order.infrastructure.remote.account;

import com.zhao.common.web.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 账户服务降级工厂
 */
@Slf4j
@Component
public class AccountClientFallbackFactory implements FallbackFactory<AccountClient> {
    
    @Override
    public AccountClient create(Throwable throwable) {
        return new AccountClient() {
            @Override
            public ApiResponse<Void> debitAccount(Long userId, Double amount) {
                log.warn("账户服务调用失败，用户ID: {}, 金额: {}, 异常: {}", 
                        userId, amount, throwable.getMessage());
                return ApiResponse.serviceDegraded("账户服务暂时不可用，请稍后重试");
            }
        };
    }
}


