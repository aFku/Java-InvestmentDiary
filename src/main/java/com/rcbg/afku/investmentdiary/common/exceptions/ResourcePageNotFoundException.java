package com.rcbg.afku.investmentdiary.common.exceptions;

public class ResourcePageNotFoundException extends BaseInvestmentDiaryRuntimeException{

    private final String resourceType;

    public ResourcePageNotFoundException(String message, int code, String resourceType){
        super(message, code);
        this.resourceType = resourceType;
    }

    public String getResourceType() {
        return resourceType;
    }
}
