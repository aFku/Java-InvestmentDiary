package com.rcbg.afku.investmentdiary.common.responses;

public class CommonSingleModelResponse<T> extends BaseApiResponse{

    private T data;

    public CommonSingleModelResponse() {
    }

    public CommonSingleModelResponse(int code, String uri, String type, T dto) {
        super(code, uri, type);
        this.data = dto;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
