package com.zhao.account.domain.model.aggregate;

import com.zhao.account.domain.model.valueobject.*;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 账户聚合根
 */
@Getter
public class Account {
    
    private AccountId id;
    private UserId userId;
    private Money balance;
    private Money frozenAmount;
    private AccountStatus status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    
    // 私有构造函数，强制使用工厂方法创建
    private Account() {}
    
    /**
     * 创建账户
     */
    public static Account create(UserId userId, Money initialBalance) {
        Account account = new Account();
        account.id = AccountId.generate();
        account.userId = userId;
        account.balance = initialBalance != null ? initialBalance : Money.ZERO;
        account.frozenAmount = Money.ZERO;
        account.status = AccountStatus.ACTIVE;
        account.createTime = LocalDateTime.now();
        account.updateTime = LocalDateTime.now();
        return account;
    }
    
    /**
     * 重建账户（从持久化数据）
     */
    public static Account rebuild(AccountId id, UserId userId, Money balance, Money frozenAmount, 
                                AccountStatus status, LocalDateTime createTime, LocalDateTime updateTime) {
        Account account = new Account();
        account.id = id;
        account.userId = userId;
        account.balance = balance;
        account.frozenAmount = frozenAmount != null ? frozenAmount : Money.ZERO;
        account.status = status;
        account.createTime = createTime;
        account.updateTime = updateTime;
        return account;
    }
    
    /**
     * 扣减余额
     */
    public void debit(Money amount, String reason) {
        if (amount == null || amount.isZero()) {
            throw new IllegalArgumentException("扣减金额不能为空或为零");
        }
        
        if (!status.canTransact()) {
            throw new IllegalStateException("账户状态不允许交易，当前状态: " + status.getDescription());
        }
        
        if (balance.isLessThan(amount)) {
            throw new IllegalStateException("账户余额不足，当前余额: " + balance + ", 需要扣减: " + amount);
        }
        
        this.balance = this.balance.subtract(amount);
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 增加余额
     */
    public void credit(Money amount, String reason) {
        if (amount == null || amount.isZero()) {
            throw new IllegalArgumentException("增加金额不能为空或为零");
        }
        
        if (!status.canTransact()) {
            throw new IllegalStateException("账户状态不允许交易，当前状态: " + status.getDescription());
        }
        
        this.balance = this.balance.add(amount);
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 冻结金额
     */
    public void freeze(Money amount, String reason) {
        if (amount == null || amount.isZero()) {
            throw new IllegalArgumentException("冻结金额不能为空或为零");
        }
        
        if (!status.canTransact()) {
            throw new IllegalStateException("账户状态不允许交易，当前状态: " + status.getDescription());
        }
        
        if (balance.isLessThan(amount)) {
            throw new IllegalStateException("可用余额不足，无法冻结");
        }
        
        this.balance = this.balance.subtract(amount);
        this.frozenAmount = this.frozenAmount.add(amount);
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 解冻金额
     */
    public void unfreeze(Money amount, String reason) {
        if (amount == null || amount.isZero()) {
            throw new IllegalArgumentException("解冻金额不能为空或为零");
        }
        
        if (frozenAmount.isLessThan(amount)) {
            throw new IllegalStateException("冻结金额不足，无法解冻");
        }
        
        this.frozenAmount = this.frozenAmount.subtract(amount);
        this.balance = this.balance.add(amount);
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 冻结账户
     */
    public void freezeAccount(String reason) {
        if (status == AccountStatus.CLOSED) {
            throw new IllegalStateException("已关闭的账户不能冻结");
        }
        
        this.status = AccountStatus.FROZEN;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 解冻账户
     */
    public void unfreezeAccount(String reason) {
        if (status != AccountStatus.FROZEN) {
            throw new IllegalStateException("只有冻结状态的账户才能解冻");
        }
        
        this.status = AccountStatus.ACTIVE;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 关闭账户
     */
    public void close(String reason) {
        if (!balance.isZero()) {
            throw new IllegalStateException("账户余额不为零，不能关闭");
        }
        
        if (!frozenAmount.isZero()) {
            throw new IllegalStateException("账户有冻结金额，不能关闭");
        }
        
        this.status = AccountStatus.CLOSED;
        this.updateTime = LocalDateTime.now();
    }
    
    /**
     * 检查是否有足够余额
     */
    public boolean hasEnoughBalance(Money amount) {
        return balance.isGreaterThanOrEqualTo(amount);
    }
    
    /**
     * 获取可用余额
     */
    public Money getAvailableBalance() {
        return balance;
    }
    
    /**
     * 获取总余额（包含冻结金额）
     */
    public Money getTotalBalance() {
        return balance.add(frozenAmount);
    }
    
    /**
     * 是否为活跃状态
     */
    public boolean isActive() {
        return status.isActive();
    }
    
    /**
     * 是否为冻结状态
     */
    public boolean isFrozen() {
        return status.isFrozen();
    }
    
    /**
     * 是否为关闭状态
     */
    public boolean isClosed() {
        return status.isClosed();
    }
    
    /**
     * 设置ID（用于持久化）
     */
    public void setId(AccountId id) {
        this.id = id;
    }
}
