package com.rcbg.afku.investmentdiary.brokeraccounts.exceptions;

public class AccountSearchException extends BrokerAccountsBaseRuntimeException{

    public AccountSearchException(String message, int code){
        super(message, code);
    }
}
