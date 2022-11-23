package com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects;

public class RequestAccountDTO{

    private String provider;
    private String accountId;

    public RequestAccountDTO(String provider, String accountId){
        this.provider = provider;
        this.accountId = accountId;
    }

    public RequestAccountDTO() {}

    public String getProvider() {
        return provider;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
