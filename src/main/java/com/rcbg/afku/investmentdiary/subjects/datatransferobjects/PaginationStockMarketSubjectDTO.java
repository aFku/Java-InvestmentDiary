package com.rcbg.afku.investmentdiary.subjects.datatransferobjects;

import com.rcbg.afku.investmentdiary.common.datatransferobjects.IPaginationDTO;

import java.util.ArrayList;

public class PaginationStockMarketSubjectDTO implements IPaginationDTO {

    private int page;
    private int totalPages;
    private int size;
    private long totalElements;
    private boolean isLast;
    private ArrayList<StockMarketSubjectDTO> data;

    public PaginationStockMarketSubjectDTO() {}

    public PaginationStockMarketSubjectDTO(int page, int totalPages, int size, long totalElements, boolean isLast, ArrayList<StockMarketSubjectDTO> data){
        this.page = page;
        this.totalPages = totalPages;
        this.size = size;
        this.totalElements = totalElements;
        this.isLast = isLast;
        this.data = data;
    }

    public int getPage() {
        return this.page;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public int getSize() {
        return this.size;
    }

    public long getTotalElements() {
        return this.totalElements;
    }

    public boolean getIsLast() {
        return this.isLast;
    }

    public ArrayList<StockMarketSubjectDTO> getData() {
        return this.data;
    }
}
