package com.rcbg.afku.investmentdiary.brokeraccounts.responses;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.ResponseAccountDTO;

public class SuccessResponse {

    private int code;
    private ResponseAccountDTO data;
    private String message;

    public SuccessResponse(int code, ResponseAccountDTO data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResponseAccountDTO getData() {
        return data;
    }

    public void setData(ResponseAccountDTO data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
