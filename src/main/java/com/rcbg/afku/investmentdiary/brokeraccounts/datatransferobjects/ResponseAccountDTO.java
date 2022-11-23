package com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;


import java.util.Date;

public class ResponseAccountDTO{

    private final int id;
    private final Date creationDate;
    private final String provider;
    private final String accountId;

    public ResponseAccountDTO(Account account){
        this.id = account.getId();
        this.creationDate = account.getCreationDate();
        this.provider = account.getProvider();
        this.accountId = account.getAccountId();
    }

    public int getId() {
        return id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getProvider() {
        return provider;
    }

    public String getAccountId() {
        return accountId;
    }
}
