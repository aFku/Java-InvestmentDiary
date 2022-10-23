package com.rcbg.afku.investmentdiary.common.responses;

import org.springframework.beans.factory.annotation.Value;

public class ApiEntityDefaultResponse implements IApiResponse{
    private final String url;

    @Value("${api.version}")
    private String version;
    private final String object;
    private final int code;

    public ApiEntityDefaultResponse(String url, String object, int code){
        this.url = url;
        this.object = object;
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }

    public String getObject() {
        return object;
    }

    public int getCode() { return code;}
}
