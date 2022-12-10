package com.rcbg.afku.investmentdiary.common.responses;

import com.rcbg.afku.investmentdiary.common.datatransferobjects.CommonPaginationDTO;
import com.rcbg.afku.investmentdiary.common.datatransferobjects.IPaginationDTO;

import java.util.ArrayList;

public class CommonModelPaginationResponse<T> extends BasePaginationApiResponse{

    private ArrayList<T> data;

    public CommonModelPaginationResponse() {}

    public CommonModelPaginationResponse(int code, String uri, String type, CommonPaginationDTO<T> dto){
        super(code, uri, type,
                dto.getPage(),
                dto.getTotalPages(),
                dto.getSize(),
                dto.getTotalElements(),
                dto.hasNext());
        this.data = dto.getData();
    }

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }
}
