package com.zhao.account.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhao.account.domain.model.aggregate.Account;
import com.zhao.account.domain.model.valueobject.AccountId;
import com.zhao.account.domain.model.valueobject.AccountStatus;
import com.zhao.account.domain.model.valueobject.UserId;
import com.zhao.account.domain.repository.AccountRepository;
import com.zhao.account.infrastructure.persistence.converter.AccountPOConverter;
import com.zhao.account.infrastructure.persistence.mapper.AccountMapper;
import com.zhao.account.infrastructure.persistence.po.AccountDO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 账户仓储实现
 */
@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {
    
    private final AccountMapper accountMapper;
    private final AccountPOConverter converter;
    
    @Override
    public Account save(Account account) {
        AccountDO accountDO = converter.toPO(account);
        
        if (accountDO.getId() == null) {
            // 新增
            accountMapper.insert(accountDO);
            account.getId().setValue(accountDO.getId());
        } else {
            // 更新
            accountMapper.updateById(accountDO);
        }
        
        return account;
    }
    
    @Override
    public Optional<Account> findById(AccountId id) {
        if (id == null || id.getValue() == null) {
            return Optional.empty();
        }
        
        AccountDO accountDO = accountMapper.selectById(id.getValue());
        return accountDO != null ? Optional.of(converter.toDomain(accountDO)) : Optional.empty();
    }
    
    @Override
    public Optional<Account> findByUserId(UserId userId) {
        if (userId == null || userId.getValue() == null) {
            return Optional.empty();
        }
        
        LambdaQueryWrapper<AccountDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AccountDO::getUserId, userId.getValue());
        
        AccountDO accountDO = accountMapper.selectOne(wrapper);
        return accountDO != null ? Optional.of(converter.toDomain(accountDO)) : Optional.empty();
    }
    
    @Override
    public List<Account> findByStatus(AccountStatus status) {
        LambdaQueryWrapper<AccountDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AccountDO::getStatus, status.getCode());
        
        List<AccountDO> accountDOs = accountMapper.selectList(wrapper);
        return accountDOs.stream()
                .map(converter::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Account> findActiveAccounts() {
        return findByStatus(AccountStatus.ACTIVE);
    }
    
    @Override
    public List<Account> findFrozenAccounts() {
        return findByStatus(AccountStatus.FROZEN);
    }
    
    @Override
    public void delete(AccountId id) {
        if (id != null && id.getValue() != null) {
            accountMapper.deleteById(id.getValue());
        }
    }
    
    @Override
    public boolean existsById(AccountId id) {
        if (id == null || id.getValue() == null) {
            return false;
        }
        return accountMapper.selectById(id.getValue()) != null;
    }
    
    @Override
    public boolean existsByUserId(UserId userId) {
        if (userId == null || userId.getValue() == null) {
            return false;
        }
        
        LambdaQueryWrapper<AccountDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AccountDO::getUserId, userId.getValue());
        
        return accountMapper.selectCount(wrapper) > 0;
    }
    
    @Override
    public long count() {
        return accountMapper.selectCount(null);
    }
    
    @Override
    public long countByStatus(AccountStatus status) {
        LambdaQueryWrapper<AccountDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AccountDO::getStatus, status.getCode());
        
        return accountMapper.selectCount(wrapper);
    }
}
