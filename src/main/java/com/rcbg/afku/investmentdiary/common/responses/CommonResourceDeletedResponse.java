package com.rcbg.afku.investmentdiary.common.responses;

import com.rcbg.afku.investmentdiary.common.statuses.ResourceDeletedStatus;

public class CommonResourceDeletedResponse extends BaseApiResponse{

    private ResourceDeletedStatus status;

    public CommonResourceDeletedResponse() {}

    public CommonResourceDeletedResponse(int code, String uri, String type, ResourceDeletedStatus status) {
        super(code, uri, type);
        this.status = status;
    }

    public CommonResourceDeletedResponse(int code, String uri, String type, int id, boolean deleted, String kind) {
        super(code, uri, type);
        this.status = new ResourceDeletedStatus(id, deleted, kind);
    }

    public ResourceDeletedStatus getStatus() {
        return status;
    }

    public void setStatus(ResourceDeletedStatus status) {
        this.status = status;
    }
}
