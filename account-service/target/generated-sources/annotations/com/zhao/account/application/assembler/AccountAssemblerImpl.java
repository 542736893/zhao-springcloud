package com.zhao.account.application.assembler;

import com.zhao.account.application.command.CreditAccountCommand;
import com.zhao.account.application.command.DebitAccountCommand;
import com.zhao.account.domain.model.aggregate.Account;
import com.zhao.account.domain.model.valueobject.AccountId;
import com.zhao.account.domain.model.valueobject.AccountStatus;
import com.zhao.account.domain.model.valueobject.Currency;
import com.zhao.account.domain.model.valueobject.Money;
import com.zhao.account.domain.model.valueobject.UserId;
import com.zhao.account.interfaces.dto.request.CreditAccountRequest;
import com.zhao.account.interfaces.dto.request.DebitAccountRequest;
import com.zhao.account.interfaces.dto.response.AccountResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-20T21:09:37+0800",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.16 (Homebrew)"
)
@Component
public class AccountAssemblerImpl implements AccountAssembler {

    @Override
    public DebitAccountCommand toCommand(DebitAccountRequest request) {
        if ( request == null ) {
            return null;
        }

        DebitAccountCommand debitAccountCommand = new DebitAccountCommand();

        debitAccountCommand.setUserId( request.getUserId() );
        debitAccountCommand.setAmount( request.getAmount() );
        debitAccountCommand.setReason( request.getReason() );
        debitAccountCommand.setBusinessNo( request.getBusinessNo() );

        return debitAccountCommand;
    }

    @Override
    public CreditAccountCommand toCommand(CreditAccountRequest request) {
        if ( request == null ) {
            return null;
        }

        CreditAccountCommand creditAccountCommand = new CreditAccountCommand();

        creditAccountCommand.setUserId( request.getUserId() );
        creditAccountCommand.setAmount( request.getAmount() );
        creditAccountCommand.setReason( request.getReason() );
        creditAccountCommand.setBusinessNo( request.getBusinessNo() );

        return creditAccountCommand;
    }

    @Override
    public AccountResponse toResponse(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountResponse accountResponse = new AccountResponse();

        accountResponse.setId( accountIdValue( account ) );
        accountResponse.setUserId( accountUserIdValue( account ) );
        accountResponse.setBalance( accountBalanceAmount( account ) );
        accountResponse.setFrozenAmount( accountFrozenAmountAmount( account ) );
        accountResponse.setStatus( accountStatusCode( account ) );
        accountResponse.setStatusDescription( accountStatusDescription( account ) );
        accountResponse.setCurrencyCode( accountBalanceCurrencyCode( account ) );
        accountResponse.setCurrencySymbol( accountBalanceCurrencySymbol( account ) );
        accountResponse.setCreateTime( account.getCreateTime() );
        accountResponse.setUpdateTime( account.getUpdateTime() );

        accountResponse.setTotalBalance( calculateTotalBalance(account) );

        return accountResponse;
    }

    @Override
    public List<AccountResponse> toResponseList(List<Account> accounts) {
        if ( accounts == null ) {
            return null;
        }

        List<AccountResponse> list = new ArrayList<AccountResponse>( accounts.size() );
        for ( Account account : accounts ) {
            list.add( toResponse( account ) );
        }

        return list;
    }

    private Long accountIdValue(Account account) {
        AccountId id = account.getId();
        if ( id == null ) {
            return null;
        }
        return id.getValue();
    }

    private Long accountUserIdValue(Account account) {
        UserId userId = account.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId.getValue();
    }

    private BigDecimal accountBalanceAmount(Account account) {
        Money balance = account.getBalance();
        if ( balance == null ) {
            return null;
        }
        return balance.getAmount();
    }

    private BigDecimal accountFrozenAmountAmount(Account account) {
        Money frozenAmount = account.getFrozenAmount();
        if ( frozenAmount == null ) {
            return null;
        }
        return frozenAmount.getAmount();
    }

    private String accountStatusCode(Account account) {
        AccountStatus status = account.getStatus();
        if ( status == null ) {
            return null;
        }
        return status.getCode();
    }

    private String accountStatusDescription(Account account) {
        AccountStatus status = account.getStatus();
        if ( status == null ) {
            return null;
        }
        return status.getDescription();
    }

    private String accountBalanceCurrencyCode(Account account) {
        Money balance = account.getBalance();
        if ( balance == null ) {
            return null;
        }
        Currency currency = balance.getCurrency();
        if ( currency == null ) {
            return null;
        }
        return currency.getCode();
    }

    private String accountBalanceCurrencySymbol(Account account) {
        Money balance = account.getBalance();
        if ( balance == null ) {
            return null;
        }
        Currency currency = balance.getCurrency();
        if ( currency == null ) {
            return null;
        }
        return currency.getSymbol();
    }
}
