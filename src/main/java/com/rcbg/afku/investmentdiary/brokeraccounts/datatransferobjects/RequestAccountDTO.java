package com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects;

public class RequestAccountDTO {

    private final String provider;
    private final String accountId;

    public RequestAccountDTO(String provider, String accountId){
        this.provider = provider;
        this.accountId = accountId;
    }

    public String getProvider() {
        return provider;
    }

    public String getAccountId() {
        return accountId;
    }
}
