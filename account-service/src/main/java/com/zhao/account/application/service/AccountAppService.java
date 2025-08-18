package com.zhao.account.application.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhao.account.infrastructure.persistence.mapper.AccountMapper;
import com.zhao.account.infrastructure.persistence.po.AccountDO;
import com.zhao.common.exception.BusinessException;
import com.zhao.common.web.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountAppService {

    private final AccountMapper accountMapper;

    public AccountAppService(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    public AccountDO findByUser(Long userId) {
        return accountMapper.selectOne(new LambdaQueryWrapper<AccountDO>()
                .eq(AccountDO::getUserId, userId));
    }

    @Transactional
    public void debit(Long userId, BigDecimal amount) {
        AccountDO acc = findByUser(userId);
        if (acc == null || acc.getBalance().compareTo(amount) < 0) {
            throw new BusinessException(ErrorCode.SERVER_ERROR.getCode(), "余额不足");
        }
        acc.setBalance(acc.getBalance().subtract(amount));
        accountMapper.updateById(acc);
    }
}


