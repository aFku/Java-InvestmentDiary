package com.rcbg.afku.investmentdiary.common.responses;

import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;

public class MapResponse implements IApiResponse {

    @Value("${api.version}")
    private String version;
    private final String url;
    private final int code;
    private final HashMap<String, Object> data;

    public MapResponse(String url, int code, HashMap<String, Object> data){
        this.url = url;
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getVersion() {
        return version;
    }

    public String getUrl() {
        return url;
    }

    public HashMap<String, Object> getData() {
        return data;
    }
}
