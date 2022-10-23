package com.rcbg.afku.investmentdiary.common.datatransferobjects;

import java.util.ArrayList;

public interface IPaginationEntityDTO {
    public int getPageNumber();
    public int getTotalPages();
    public int getPageSize();
    public long getTotalElements();
    public boolean getIsLast();
    public ArrayList<DefaultDTO> getData();
}
