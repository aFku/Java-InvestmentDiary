package com.rcbg.afku.investmentdiary.common.datatransferobjects;

import java.util.ArrayList;

public class CommonPaginationDTO <T> {

    private int page;
    private int totalPages;
    private int size;
    private long totalElements;

    private boolean hasNext;
    private ArrayList<T> data;

    public CommonPaginationDTO() {}

    public CommonPaginationDTO(int page, int totalPages, int size, long totalElements, boolean hasNext, ArrayList<T> data){
        this.page = page;
        this.totalPages = totalPages;
        this.size = size;
        this.totalElements = totalElements;
        this.hasNext = hasNext;
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }
}
