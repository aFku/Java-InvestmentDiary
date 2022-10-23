package com.rcbg.afku.investmentdiary.common.exceptions;

public class BaseInvestmentDiaryRuntimeException extends RuntimeException{

    private final int code;

    public BaseInvestmentDiaryRuntimeException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
