package com.rcbg.afku.investmentdiary.brokeraccounts.controllers;

public class ErrorResponse {

    private final int code;
    private final String message;

    public ErrorResponse(String message, int code){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
