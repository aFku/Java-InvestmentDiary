package com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;

import java.sql.Date;

public class ReadableAccountDTO extends ResponseAccountDTO {

    private Date creationDate;

    public ReadableAccountDTO(int id, Date creationDate, String provider, String accountId) {
        super(id, provider, accountId);
        this.creationDate = creationDate;
    }

    public ReadableAccountDTO(Account account){
        super(account);
        this.creationDate = account.getCreationDate();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
