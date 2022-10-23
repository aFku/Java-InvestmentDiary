package com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.common.datatransferobjects.DefaultDTO;
import com.rcbg.afku.investmentdiary.common.datatransferobjects.IPaginationEntityDTO;

import java.util.ArrayList;

public class PaginationAccountDTO extends DefaultDTO implements IPaginationEntityDTO{

    private final int pageNumber;
    private final int totalPages;
    private final int pageSize;
    private final long totalElements;
    private final boolean isLast;
    private final ArrayList<DefaultDTO> data;


    public PaginationAccountDTO(int pageNumber, int totalPages, int pageSize, long totalElements, boolean isLast, ArrayList<DefaultDTO> data){
        this.pageNumber = pageNumber;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.isLast = isLast;
        this.data = data;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public boolean getIsLast() {
        return isLast;
    }

    public ArrayList<DefaultDTO> getData() {
        return data;
    }
}
