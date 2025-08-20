package com.zhao.account.infrastructure.persistence.converter;

import com.zhao.account.domain.model.aggregate.Account;
import com.zhao.account.infrastructure.persistence.po.AccountDO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-20T16:39:01+0800",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.16 (Homebrew)"
)
@Component
public class AccountPOConverterImpl implements AccountPOConverter {

    @Override
    public AccountDO toPO(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDO accountDO = new AccountDO();

        accountDO.setId( accountIdToLong( account.getId() ) );
        accountDO.setUserId( userIdToLong( account.getUserId() ) );
        accountDO.setBalance( moneyToBigDecimal( account.getBalance() ) );
        accountDO.setFrozenAmount( moneyToBigDecimal( account.getFrozenAmount() ) );
        accountDO.setStatus( statusToString( account.getStatus() ) );
        accountDO.setCreateTime( account.getCreateTime() );
        accountDO.setUpdateTime( account.getUpdateTime() );

        return accountDO;
    }
}
