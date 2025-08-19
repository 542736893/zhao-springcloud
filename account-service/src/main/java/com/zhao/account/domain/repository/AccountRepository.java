package com.zhao.account.domain.repository;

import com.zhao.account.domain.model.aggregate.Account;
import com.zhao.account.domain.model.valueobject.AccountId;
import com.zhao.account.domain.model.valueobject.AccountStatus;
import com.zhao.account.domain.model.valueobject.UserId;

import java.util.List;
import java.util.Optional;

/**
 * 账户仓储接口
 */
public interface AccountRepository {
    
    /**
     * 保存账户
     */
    Account save(Account account);
    
    /**
     * 根据ID查找账户
     */
    Optional<Account> findById(AccountId id);
    
    /**
     * 根据用户ID查找账户
     */
    Optional<Account> findByUserId(UserId userId);
    
    /**
     * 根据状态查找账户列表
     */
    List<Account> findByStatus(AccountStatus status);
    
    /**
     * 查找所有活跃账户
     */
    List<Account> findActiveAccounts();
    
    /**
     * 查找所有冻结账户
     */
    List<Account> findFrozenAccounts();
    
    /**
     * 删除账户
     */
    void delete(AccountId id);
    
    /**
     * 检查账户是否存在
     */
    boolean existsById(AccountId id);
    
    /**
     * 检查用户是否已有账户
     */
    boolean existsByUserId(UserId userId);
    
    /**
     * 统计账户总数
     */
    long count();
    
    /**
     * 根据状态统计账户数量
     */
    long countByStatus(AccountStatus status);
}
