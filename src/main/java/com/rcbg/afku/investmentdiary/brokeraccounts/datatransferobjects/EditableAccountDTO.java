package com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects;


import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;

public class EditableAccountDTO {
    String provider;
    String accountId;

    public EditableAccountDTO(String provider, String accountId) {
        this.provider = provider;
        this.accountId = accountId;
    }

    public EditableAccountDTO(Account account){
        this.provider = account.getProvider();
        this.accountId = account.getAccountId();
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}
