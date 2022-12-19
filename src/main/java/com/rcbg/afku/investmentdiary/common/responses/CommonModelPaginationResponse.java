package com.rcbg.afku.investmentdiary.common.responses;

import com.rcbg.afku.investmentdiary.common.datatransferobjects.CommonPaginationDTO;
import com.rcbg.afku.investmentdiary.common.datatransferobjects.IPaginationDTO;

import java.util.ArrayList;
import java.util.List;

public class CommonModelPaginationResponse extends BasePaginationApiResponse{

    private List<?> data;

    public CommonModelPaginationResponse() {}

    public CommonModelPaginationResponse(int code, String uri, String type, CommonPaginationDTO dto){
        super(code, uri, type,
                dto.getPage(),
                dto.getTotalPages(),
                dto.getSize(),
                dto.getTotalElements(),
                dto.hasNext());
        this.data = dto.getData();
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
