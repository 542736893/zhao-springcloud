package com.zhao.account.interfaces.web;

import com.zhao.account.application.assembler.AccountAssembler;
import com.zhao.account.application.command.CreateAccountCommand;
import com.zhao.account.application.command.CreditAccountCommand;
import com.zhao.account.application.command.DebitAccountCommand;
import com.zhao.account.application.query.AccountQuery;
import com.zhao.account.application.service.AccountAppService;
import com.zhao.account.domain.model.aggregate.Account;
import com.zhao.account.domain.model.valueobject.Money;
import com.zhao.account.domain.model.valueobject.UserId;
import com.zhao.account.domain.repository.AccountRepository;
import com.zhao.account.interfaces.dto.request.CreditAccountRequest;
import com.zhao.account.interfaces.dto.request.DebitAccountRequest;
import com.zhao.account.interfaces.dto.response.AccountResponse;
import com.zhao.account.interfaces.feign.AccountApi;
import com.zhao.common.web.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 账户控制器
 */
@Tag(name = "账户管理", description = "账户管理相关接口")
@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@Validated
public class AccountController implements AccountApi {

    private final AccountAppService accountAppService;
    private final AccountAssembler accountAssembler;
    private final AccountRepository accountRepository;

    @Operation(summary = "健康检查", description = "检查服务是否正常运行")
    @GetMapping("/ping")
    public ApiResponse<String> ping() {
        return ApiResponse.ok("pong");
    }

    @Override
    @Operation(summary = "扣减账户余额", description = "扣减指定用户的账户余额")
    @PostMapping("/debit")
    public ApiResponse<Void> debitAccount(
            @Parameter(description = "用户ID", required = true)
            @RequestParam("userId") Long userId,
            @Parameter(description = "扣减金额", required = true)
            @RequestParam("amount") Double amount) {

        DebitAccountCommand command = new DebitAccountCommand(userId, BigDecimal.valueOf(amount));
        accountAppService.debitAccount(command);
        return ApiResponse.ok();
    }

    @Override
    @Operation(summary = "增加账户余额", description = "增加指定用户的账户余额")
    @PostMapping("/credit")
    public ApiResponse<Void> creditAccount(
            @Parameter(description = "用户ID", required = true)
            @RequestParam("userId") Long userId,
            @Parameter(description = "增加金额", required = true)
            @RequestParam("amount") Double amount) {

        CreditAccountCommand command = new CreditAccountCommand(userId, BigDecimal.valueOf(amount));
        accountAppService.creditAccount(command);
        return ApiResponse.ok();
    }

    @Override
    @Operation(summary = "查询账户信息", description = "查询指定用户的账户信息")
    @GetMapping("/user/{userId}")
    public ApiResponse<AccountResponse> getAccount(
            @Parameter(description = "用户ID", required = true)
            @PathVariable("userId") Long userId) {

        AccountQuery query = new AccountQuery(userId);
        AccountResponse response = accountAppService.queryAccount(query);
        return ApiResponse.ok(response);
    }

    @Override
    @Operation(summary = "检查账户余额", description = "检查指定用户的账户余额是否充足")
    @GetMapping("/check")
    public ApiResponse<Boolean> checkBalance(
            @Parameter(description = "用户ID", required = true)
            @RequestParam("userId") Long userId,
            @Parameter(description = "需要金额", required = true)
            @RequestParam("amount") Double amount) {

        Optional<Account> accountOpt = accountRepository.findByUserId(UserId.of(userId));
        if (!accountOpt.isPresent()) {
            return ApiResponse.ok(false);
        }

        boolean hasEnough = accountOpt.get().hasEnoughBalance(Money.ofCNY(amount));
        return ApiResponse.ok(hasEnough);
    }

    @Operation(summary = "创建账户", description = "为用户创建新账户")
    @PostMapping("/create")
    public ApiResponse<AccountResponse> createAccount(
            @Parameter(description = "用户ID", required = true)
            @RequestParam("userId") Long userId,
            @Parameter(description = "初始余额", example = "0.00")
            @RequestParam(value = "initialBalance", defaultValue = "0") Double initialBalance) {

        CreateAccountCommand command = new CreateAccountCommand(userId, BigDecimal.valueOf(initialBalance));
        AccountResponse response = accountAppService.createAccount(command);
        return ApiResponse.ok(response);
    }

    @Operation(summary = "扣减账户余额（请求体）", description = "通过请求体扣减账户余额")
    @PostMapping("/debit-body")
    public ApiResponse<Void> debitAccountByBody(@Valid @RequestBody DebitAccountRequest request) {
        DebitAccountCommand command = accountAssembler.toCommand(request);
        accountAppService.debitAccount(command);
        return ApiResponse.ok();
    }

    @Operation(summary = "增加账户余额（请求体）", description = "通过请求体增加账户余额")
    @PostMapping("/credit-body")
    public ApiResponse<Void> creditAccountByBody(@Valid @RequestBody CreditAccountRequest request) {
        CreditAccountCommand command = accountAssembler.toCommand(request);
        accountAppService.creditAccount(command);
        return ApiResponse.ok();
    }

    @Operation(summary = "查询活跃账户", description = "查询所有活跃状态的账户")
    @GetMapping("/active")
    public ApiResponse<List<AccountResponse>> getActiveAccounts() {
        List<AccountResponse> responses = accountAppService.queryActiveAccounts();
        return ApiResponse.ok(responses);
    }

    @Operation(summary = "查询冻结账户", description = "查询所有冻结状态的账户")
    @GetMapping("/frozen")
    public ApiResponse<List<AccountResponse>> getFrozenAccounts() {
        List<AccountResponse> responses = accountAppService.queryFrozenAccounts();
        return ApiResponse.ok(responses);
    }
}


