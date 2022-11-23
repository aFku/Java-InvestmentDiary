package com.rcbg.afku.investmentdiary.common.responses;

import org.springframework.beans.factory.annotation.Value;

public abstract class BaseApiResponse {

    private String version = "v1";
    private int code;
    private String uri;
    private String type;

    public BaseApiResponse(){}

    public BaseApiResponse(int code, String uri, String type) {
        this.code = code;
        this.uri = uri;
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
