package com.rcbg.afku.investmentdiary.brokeraccounts.exceptions;

public class AccountManagementException extends Exception{

    private final int code;
    public AccountManagementException(String message, int code){
        super(message);
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }
}
