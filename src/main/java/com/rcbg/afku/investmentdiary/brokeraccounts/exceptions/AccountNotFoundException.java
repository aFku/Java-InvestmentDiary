package com.rcbg.afku.investmentdiary.brokeraccounts.exceptions;

public class AccountNotFoundException extends BrokerAccountsBaseRuntimeException{

    public AccountNotFoundException(String message, int code){
        super(message, code);
    }
}
