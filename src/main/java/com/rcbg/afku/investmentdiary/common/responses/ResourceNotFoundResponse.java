package com.rcbg.afku.investmentdiary.common.responses;

public class ResourceNotFoundResponse extends BaseErrorResponse{

    private final String resourceType;

    public ResourceNotFoundResponse(String url, String message, int code, String resourceType){
        super(url, message, code);
        this.resourceType = resourceType;
    }

    public String getResourceType() {
        return resourceType;
    }
}

