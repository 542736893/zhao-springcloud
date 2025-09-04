package com.zhao.account.infrastructure.persistence.converter;

import com.zhao.account.domain.model.aggregate.Account;
import com.zhao.account.infrastructure.persistence.po.AccountDO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-04T15:52:35+0800",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
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
