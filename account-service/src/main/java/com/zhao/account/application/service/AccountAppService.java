package com.zhao.account.application.service;

import com.zhao.account.application.assembler.AccountAssembler;
import com.zhao.account.application.command.CreateAccountCommand;
import com.zhao.account.application.command.CreditAccountCommand;
import com.zhao.account.application.command.DebitAccountCommand;
import com.zhao.account.application.query.AccountQuery;
import com.zhao.account.domain.model.aggregate.Account;
import com.zhao.account.domain.model.valueobject.Money;
import com.zhao.account.domain.model.valueobject.UserId;
import com.zhao.account.domain.repository.AccountRepository;
import com.zhao.account.interfaces.dto.response.AccountResponse;
import com.zhao.common.exception.BusinessException;
import com.zhao.common.web.ErrorCode;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 账户应用服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountAppService {

    private final AccountRepository accountRepository;
    private final AccountAssembler accountAssembler;

    /**
     * 创建账户
     */
    @Transactional
    public AccountResponse createAccount(CreateAccountCommand command) {
        log.info("开始创建账户，用户ID: {}, 初始余额: {}", command.getUserId(), command.getInitialBalance());

        UserId userId = UserId.of(command.getUserId());

        // 检查用户是否已有账户
        if (accountRepository.existsByUserId(userId)) {
            log.warn("用户已存在账户，用户ID: {}", command.getUserId());
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_EXISTS);
        }

        // 创建账户
        Money initialBalance = command.getInitialBalance() != null ?
                Money.ofCNY(command.getInitialBalance()) : Money.ZERO;
        Account account = Account.create(userId, initialBalance);

        // 保存账户
        accountRepository.save(account);

        log.info("账户创建成功，用户ID: {}, 账户ID: {}", command.getUserId(), account.getId().getValue());
        return accountAssembler.toResponse(account);
    }

    /**
     * 扣减账户余额
     */
    @Transactional
    public void debitAccount(DebitAccountCommand command) {
        log.info("开始扣减账户余额，用户ID: {}, 金额: {}", command.getUserId(), command.getAmount());

        // 查找账户
        Optional<Account> accountOpt = accountRepository.findByUserId(UserId.of(command.getUserId()));
        if (!accountOpt.isPresent()) {
            log.warn("账户不存在，用户ID: {}", command.getUserId());
            throw new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND);
        }

        Account account = accountOpt.get();

        // 扣减余额（业务逻辑在领域对象中）
        try {
            Money amount = Money.ofCNY(command.getAmount());
            account.debit(amount, command.getReason());
            accountRepository.save(account);
            log.info("账户余额扣减成功，用户ID: {}, 扣减金额: {}", command.getUserId(), command.getAmount());
        } catch (IllegalStateException e) {
            log.warn("账户余额扣减失败: {}", e.getMessage());
            throw new BusinessException(ErrorCode.INSUFFICIENT_BALANCE_ACC, e.getMessage());
        }
    }

    /**
     * 增加账户余额
     */
    @Transactional
    public void creditAccount(CreditAccountCommand command) {
        log.info("开始增加账户余额，用户ID: {}, 金额: {}", command.getUserId(), command.getAmount());

        // 查找账户
        Optional<Account> accountOpt = accountRepository.findByUserId(UserId.of(command.getUserId()));
        if (!accountOpt.isPresent()) {
            log.warn("账户不存在，用户ID: {}", command.getUserId());
            throw new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND);
        }

        Account account = accountOpt.get();

        // 增加余额
        Money amount = Money.ofCNY(command.getAmount());
        account.credit(amount, command.getReason());
        accountRepository.save(account);
        log.info("账户余额增加成功，用户ID: {}, 增加金额: {}", command.getUserId(), command.getAmount());
    }

    /**
     * 查询账户
     */
    public AccountResponse queryAccount(AccountQuery query) {
        if (query.getUserId() == null) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR, "用户ID不能为空");
        }

        Optional<Account> accountOpt = accountRepository.findByUserId(UserId.of(query.getUserId()));
        if (!accountOpt.isPresent()) {
            throw new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND);
        }

        return accountAssembler.toResponse(accountOpt.get());
    }

    /**
     * 查询活跃账户列表
     */
    public List<AccountResponse> queryActiveAccounts() {
        List<Account> accounts = accountRepository.findActiveAccounts();
        return accountAssembler.toResponseList(accounts);
    }

    /**
     * 查询冻结账户列表
     */
    public List<AccountResponse> queryFrozenAccounts() {
        List<Account> accounts = accountRepository.findFrozenAccounts();
        return accountAssembler.toResponseList(accounts);
    }

    // ========== 兼容性方法（保持与现有代码的兼容性）==========

    /**
     * 扣减余额（兼容性方法）
     */
    @Transactional
    public void debit(Long userId, BigDecimal amount) {
        DebitAccountCommand command = new DebitAccountCommand(userId, amount);
        debitAccount(command);
    }

    /**
     * 根据用户ID查找账户（兼容性方法）
     */
    public AccountResponse findByUser(Long userId) {
        AccountQuery query = new AccountQuery(userId);
        return queryAccount(query);
    }
}


