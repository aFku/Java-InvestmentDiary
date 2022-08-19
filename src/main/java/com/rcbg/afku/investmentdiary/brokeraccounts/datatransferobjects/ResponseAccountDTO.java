package com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;

public class ResponseAccountDTO extends EditableAccountDTO{

    private int id;

    public ResponseAccountDTO(int id, String provider, String accountId) {
        super(provider, accountId);
        this.id = id;
    }

    public ResponseAccountDTO(Account account){
        super(account);
        this.id = account.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
