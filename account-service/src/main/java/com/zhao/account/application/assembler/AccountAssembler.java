package com.zhao.account.application.assembler;

import com.zhao.account.application.command.CreditAccountCommand;
import com.zhao.account.application.command.CreateAccountCommand;
import com.zhao.account.application.command.DebitAccountCommand;
import com.zhao.account.application.query.AccountQuery;
import com.zhao.account.domain.model.aggregate.Account;
import com.zhao.account.interfaces.dto.request.CreditAccountRequest;
import com.zhao.account.interfaces.dto.request.DebitAccountRequest;
import com.zhao.account.interfaces.dto.response.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.List;

/**
 * 账户装配器
 */
@Mapper(componentModel = "spring")
public interface AccountAssembler {
    
    // ========== Request to Command ==========
    
    /**
     * 扣减账户请求转命令
     */
    DebitAccountCommand toCommand(DebitAccountRequest request);
    
    /**
     * 增加账户请求转命令
     */
    CreditAccountCommand toCommand(CreditAccountRequest request);
    
    // ========== Domain to Response ==========
    
    /**
     * 领域对象转响应
     */
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "userId", source = "userId.value")
    @Mapping(target = "balance", source = "balance.amount")
    @Mapping(target = "frozenAmount", source = "frozenAmount.amount")
    @Mapping(target = "totalBalance", expression = "java(calculateTotalBalance(account))")
    @Mapping(target = "status", source = "status.code")
    @Mapping(target = "statusDescription", source = "status.description")
    @Mapping(target = "currencyCode", source = "balance.currency.code")
    @Mapping(target = "currencySymbol", source = "balance.currency.symbol")
    AccountResponse toResponse(Account account);
    
    /**
     * 领域对象列表转响应列表
     */
    List<AccountResponse> toResponseList(List<Account> accounts);
    
    // ========== Command Factory Methods ==========
    
    /**
     * 创建账户创建命令
     */
    default CreateAccountCommand createAccountCommand(Long userId, BigDecimal initialBalance, String reason) {
        return new CreateAccountCommand(userId, initialBalance, reason);
    }
    
    // ========== Helper Methods ==========
    
    /**
     * 计算总余额
     */
    default BigDecimal calculateTotalBalance(Account account) {
        return account.getTotalBalance().getAmount();
    }
}
