package com.rcbg.afku.investmentdiary.brokeraccounts.responses;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.ReadableAccountDTO;

import java.util.List;

public class ListResponse {
    private int code;
    private List<ReadableAccountDTO> data;
    private String message;

    public ListResponse(int code, List<ReadableAccountDTO> data, String message) {
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

    public List<ReadableAccountDTO> getData() {
        return data;
    }

    public void setData(List<ReadableAccountDTO> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
