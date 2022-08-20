package com.rcbg.afku.investmentdiary.brokeraccounts.exceptions;

public class AccountSearchException extends Exception{

    private final int code;
    public AccountSearchException(String message, int code){
        super(message);
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }
}
