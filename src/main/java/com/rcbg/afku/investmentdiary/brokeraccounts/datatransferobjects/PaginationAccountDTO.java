package com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects;

import com.rcbg.afku.investmentdiary.common.datatransferobjects.IPaginationDTO;

import java.util.ArrayList;

public class PaginationAccountDTO implements IPaginationDTO {

    private int page;
    private int totalPages;
    private int size;
    private long totalElements;
    private boolean isLast;
    private ArrayList<ResponseAccountDTO> data;

    public PaginationAccountDTO() {}

    public PaginationAccountDTO(int page, int totalPages, int size, long totalElements, boolean isLast, ArrayList<ResponseAccountDTO> data){
        this.page = page;
        this.totalPages = totalPages;
        this.size = size;
        this.totalElements = totalElements;
        this.isLast = isLast;
        this.data = data;
    }

    @Override
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean getIsLast() {
        return isLast;
    }

    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }

    public ArrayList<ResponseAccountDTO> getData() {
        return data;
    }

    public void setData(ArrayList<ResponseAccountDTO> data) {
        this.data = data;
    }
}
