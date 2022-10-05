package com.rcbg.afku.investmentdiary.brokeraccounts.exceptions;

public class BrokerAccountsBaseRuntimeException extends RuntimeException{

    private final int code;

    public BrokerAccountsBaseRuntimeException(String message, int code){
        super(message);
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }
}
