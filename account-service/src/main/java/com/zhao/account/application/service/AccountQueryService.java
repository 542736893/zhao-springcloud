package com.zhao.account.application.service;

import com.zhao.account.application.assembler.AccountAssembler;
import com.zhao.account.domain.model.aggregate.Account;
import com.zhao.account.domain.model.valueobject.AccountStatus;
import com.zhao.account.domain.repository.AccountRepository;
import com.zhao.account.interfaces.dto.response.AccountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 账户查询服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountQueryService {

    private final AccountRepository accountRepository;
    private final AccountAssembler accountAssembler;

    /**
     * 查询账户统计信息
     */
    public AccountStatistics getAccountStatistics() {
        long totalCount = accountRepository.count();
        long activeCount = accountRepository.countByStatus(AccountStatus.ACTIVE);
        long frozenCount = accountRepository.countByStatus(AccountStatus.FROZEN);
        long closedCount = accountRepository.countByStatus(AccountStatus.CLOSED);
        
        return AccountStatistics.builder()
                .totalCount(totalCount)
                .activeCount(activeCount)
                .frozenCount(frozenCount)
                .closedCount(closedCount)
                .build();
    }

    /**
     * 查询账户预警信息
     */
    public List<AccountResponse> getAccountAlerts() {
        // 查询冻结账户作为预警
        List<Account> frozenAccounts = accountRepository.findFrozenAccounts();
        return accountAssembler.toResponseList(frozenAccounts);
    }

    /**
     * 账户统计信息
     */
    public static class AccountStatistics {
        private long totalCount;
        private long activeCount;
        private long frozenCount;
        private long closedCount;
        
        public static AccountStatisticsBuilder builder() {
            return new AccountStatisticsBuilder();
        }
        
        public long getTotalCount() {
            return totalCount;
        }
        
        public long getActiveCount() {
            return activeCount;
        }
        
        public long getFrozenCount() {
            return frozenCount;
        }
        
        public long getClosedCount() {
            return closedCount;
        }
        
        public static class AccountStatisticsBuilder {
            private long totalCount;
            private long activeCount;
            private long frozenCount;
            private long closedCount;
            
            public AccountStatisticsBuilder totalCount(long totalCount) {
                this.totalCount = totalCount;
                return this;
            }
            
            public AccountStatisticsBuilder activeCount(long activeCount) {
                this.activeCount = activeCount;
                return this;
            }
            
            public AccountStatisticsBuilder frozenCount(long frozenCount) {
                this.frozenCount = frozenCount;
                return this;
            }
            
            public AccountStatisticsBuilder closedCount(long closedCount) {
                this.closedCount = closedCount;
                return this;
            }
            
            public AccountStatistics build() {
                AccountStatistics statistics = new AccountStatistics();
                statistics.totalCount = this.totalCount;
                statistics.activeCount = this.activeCount;
                statistics.frozenCount = this.frozenCount;
                statistics.closedCount = this.closedCount;
                return statistics;
            }
        }
    }
}
