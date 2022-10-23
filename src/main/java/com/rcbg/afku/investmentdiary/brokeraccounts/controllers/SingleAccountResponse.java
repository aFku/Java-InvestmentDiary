package com.rcbg.afku.investmentdiary.brokeraccounts.controllers;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.ResponseAccountDTO;
import com.rcbg.afku.investmentdiary.common.responses.ApiEntityDefaultResponse;

public class SingleAccountResponse extends ApiEntityDefaultResponse {

    private final ResponseAccountDTO data;

    public SingleAccountResponse(String url, String object, int code, ResponseAccountDTO data){
        super(url, object, code);
        this.data = data;
    }

    public ResponseAccountDTO getData() {
        return data;
    }

}
