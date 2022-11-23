package com.rcbg.afku.investmentdiary.common.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableManagement {

    public static Pageable createPageableObject(int page, int size, int maxSize, String sort){
        if(size > maxSize){
            throw new IllegalArgumentException("Max value for page size is " + maxSize);
        }
        return PageRequest.of(page, size, Sort.by(sort));
    }
}
