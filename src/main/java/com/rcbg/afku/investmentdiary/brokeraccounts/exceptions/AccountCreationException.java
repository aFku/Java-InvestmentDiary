package com.rcbg.afku.investmentdiary.brokeraccounts.exceptions;

public class AccountCreationException extends BrokerAccountsBaseRuntimeException{

    public AccountCreationException(String message, int code){
        super(message, code);
    }
}
