package com.zhao.account.interfaces.web;

import com.zhao.account.application.service.AccountAppService;
import com.zhao.account.infrastructure.persistence.po.AccountDO;
import com.zhao.common.web.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountAppService accountAppService;

    public AccountController(AccountAppService accountAppService) {
        this.accountAppService = accountAppService;
    }

    @GetMapping("/ping")
    public ApiResponse<String> ping() {
        return ApiResponse.ok("pong");
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<AccountDO> get(@PathVariable Long userId) {
        return ApiResponse.ok(accountAppService.findByUser(userId));
    }

    @PostMapping("/debit")
    public ApiResponse<Void> debit(@RequestParam Long userId, @RequestParam BigDecimal amount) {
        accountAppService.debit(userId, amount);
        return ApiResponse.ok(null);
    }
}


