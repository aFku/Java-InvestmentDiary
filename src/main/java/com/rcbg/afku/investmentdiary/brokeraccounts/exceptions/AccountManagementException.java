package com.rcbg.afku.investmentdiary.brokeraccounts.exceptions;

public class AccountManagementException extends BrokerAccountsBaseRuntimeException{

    public AccountManagementException(String message, int code){
        super(message, code);
    }
}
