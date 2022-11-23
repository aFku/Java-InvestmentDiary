package com.rcbg.afku.investmentdiary.common.datatransferobjects;

import java.util.ArrayList;

public interface IPaginationDTO {
    public int getPage();
    public int getTotalPages();
    public int getSize();
    public long getTotalElements();
    public boolean getIsLast();
    public <T> ArrayList<T> getData();
}
