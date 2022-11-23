package com.rcbg.afku.investmentdiary.common.responses;

public class BaseApiErrorResponse extends BaseApiResponse{

    private String message;

    public BaseApiErrorResponse() {}

    public BaseApiErrorResponse(int code, String uri, String type, String message) {
        super(code, uri, type);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
