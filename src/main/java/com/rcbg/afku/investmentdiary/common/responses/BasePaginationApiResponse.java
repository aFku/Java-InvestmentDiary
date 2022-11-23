package com.rcbg.afku.investmentdiary.common.responses;

public abstract class BasePaginationApiResponse extends BaseApiResponse{

    private int page;
    private int totalPages;
    private int size;
    private long totalElements;
    private boolean isLast;

    public BasePaginationApiResponse() {}

    public BasePaginationApiResponse(int code, String uri, String type, int page, int totalPages, int size, long totalElements, boolean isLast) {
        super(code, uri, type);
        this.page = page;
        this.totalPages = totalPages;
        this.size = size;
        this.totalElements = totalElements;
        this.isLast = isLast;
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

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }
}
