package com.rcbg.afku.investmentdiary.brokeraccounts.controllers;

import com.rcbg.afku.investmentdiary.common.datatransferobjects.DefaultDTO;
import com.rcbg.afku.investmentdiary.common.datatransferobjects.IPaginationEntityDTO;
import com.rcbg.afku.investmentdiary.common.responses.ApiListEntityWithPaginationDefaultResponse;

import java.util.ArrayList;

public class ListAccountResponse extends ApiListEntityWithPaginationDefaultResponse {
    private final ArrayList<DefaultDTO> data;

    public ListAccountResponse(String url, String object, int code, IPaginationEntityDTO dto){
        super(url, object, code, dto);
        this.data = dto.getData();
    }

    public ArrayList<DefaultDTO> getData() {
        return data;
    }
}
