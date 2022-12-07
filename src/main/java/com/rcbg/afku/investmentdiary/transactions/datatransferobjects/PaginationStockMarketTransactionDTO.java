package com.rcbg.afku.investmentdiary.transactions.datatransferobjects;

import com.rcbg.afku.investmentdiary.common.datatransferobjects.IPaginationDTO;

import java.util.ArrayList;

public class PaginationStockMarketTransactionDTO implements IPaginationDTO {
    private int page;
    private int totalPages;
    private int size;
    private long totalElements;
    private boolean isLast;
    private ArrayList<StockMarketTransactionDTO> data;

    public PaginationStockMarketTransactionDTO() {}

    public PaginationStockMarketTransactionDTO(int page, int totalPages, int size, long totalElements, boolean isLast, ArrayList<StockMarketTransactionDTO> data){
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

    public ArrayList<StockMarketTransactionDTO> getData() {
        return this.data;
    }
}
