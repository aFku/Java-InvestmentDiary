package com.rcbg.afku.investmentdiary.common.datatransferobjects;

import java.util.List;

public class CommonPaginationDTO{

    private int page;
    private int totalPages;
    private int size;
    private long totalElements;

    private boolean hasNext;
    private List<?> data;

    public CommonPaginationDTO() {}

    public CommonPaginationDTO(int page, int totalPages, int size, long totalElements, boolean hasNext, List<?> data){
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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
