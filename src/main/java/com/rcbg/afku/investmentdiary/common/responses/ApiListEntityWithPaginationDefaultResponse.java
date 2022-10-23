package com.rcbg.afku.investmentdiary.common.responses;

import com.rcbg.afku.investmentdiary.common.datatransferobjects.IPaginationEntityDTO;

public class ApiListEntityWithPaginationDefaultResponse extends ApiEntityDefaultResponse{

    private final int pageNumber;
    private final int totalPages;
    private final int pageSize;
    private final long totalElements;
    private final boolean isLast;

    public ApiListEntityWithPaginationDefaultResponse(String url, String object, int code, IPaginationEntityDTO dto){
        super(url, object, code);
        this.pageNumber = dto.getPageNumber();
        this.totalPages = dto.getTotalPages();
        this.pageSize = dto.getPageSize();
        this.totalElements = dto.getTotalElements();
        this.isLast = dto.getIsLast();
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

    public boolean isLast() {
        return isLast;
    }
}
