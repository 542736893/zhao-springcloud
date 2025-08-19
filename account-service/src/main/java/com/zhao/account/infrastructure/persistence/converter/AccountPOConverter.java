package com.zhao.account.infrastructure.persistence.converter;

import com.zhao.account.domain.model.aggregate.Account;
import com.zhao.account.domain.model.valueobject.*;
import com.zhao.account.infrastructure.persistence.po.AccountDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 账户PO转换器
 */
@Mapper(componentModel = "spring")
@Component
public interface AccountPOConverter {
    
    /**
     * 领域对象转PO
     */
    @Mapping(target = "id", source = "id", qualifiedByName = "accountIdToLong")
    @Mapping(target = "userId", source = "userId", qualifiedByName = "userIdToLong")
    @Mapping(target = "balance", source = "balance", qualifiedByName = "moneyToBigDecimal")
    @Mapping(target = "frozenAmount", source = "frozenAmount", qualifiedByName = "moneyToBigDecimal")
    @Mapping(target = "status", source = "status", qualifiedByName = "statusToString")
    AccountDO toPO(Account account);
    
    /**
     * PO转领域对象
     */
    default Account toDomain(AccountDO accountDO) {
        if (accountDO == null) {
            return null;
        }

        return Account.rebuild(
                longToAccountId(accountDO.getId()),
                longToUserId(accountDO.getUserId()),
                bigDecimalToMoney(accountDO.getBalance()),
                bigDecimalToMoney(accountDO.getFrozenAmount()),
                stringToStatus(accountDO.getStatus()),
                accountDO.getCreateTime(),
                accountDO.getUpdateTime()
        );
    }
    
    // 辅助转换方法
    @Named("accountIdToLong")
    default Long accountIdToLong(AccountId accountId) {
        return accountId != null ? accountId.getValue() : null;
    }
    
    @Named("longToAccountId")
    default AccountId longToAccountId(Long id) {
        return id != null ? AccountId.of(id) : null;
    }
    
    @Named("userIdToLong")
    default Long userIdToLong(UserId userId) {
        return userId != null ? userId.getValue() : null;
    }
    
    @Named("longToUserId")
    default UserId longToUserId(Long userId) {
        return userId != null ? UserId.of(userId) : null;
    }
    
    @Named("moneyToBigDecimal")
    default BigDecimal moneyToBigDecimal(Money money) {
        return money != null ? money.getAmount() : BigDecimal.ZERO;
    }
    
    @Named("bigDecimalToMoney")
    default Money bigDecimalToMoney(BigDecimal amount) {
        return amount != null ? Money.ofCNY(amount) : Money.ZERO;
    }
    
    @Named("statusToString")
    default String statusToString(AccountStatus status) {
        return status != null ? status.getCode() : AccountStatus.ACTIVE.getCode();
    }
    
    @Named("stringToStatus")
    default AccountStatus stringToStatus(String status) {
        return status != null ? AccountStatus.fromCode(status) : AccountStatus.ACTIVE;
    }
}
