package com.rcbg.afku.investmentdiary.common.responses;

import org.springframework.beans.factory.annotation.Value;

public class BaseErrorResponse implements IApiResponse {

    @Value("${api.version}")
    private String version;
    private final String url;
    private final int code;
    private final String message;

    public BaseErrorResponse(String url, String message, int code){
        this.url = url;
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getVersion() {
        return version;
    }

    public String getUrl() {
        return url;
    }
}
