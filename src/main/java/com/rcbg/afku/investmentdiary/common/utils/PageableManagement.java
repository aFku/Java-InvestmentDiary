package com.rcbg.afku.investmentdiary.common.utils;

import com.rcbg.afku.investmentdiary.common.datatransferobjects.CommonPaginationDTO;
import com.rcbg.afku.investmentdiary.common.exceptions.PaginationPageNotFoundException;
import org.springframework.data.domain.Page;

public class PageableManagement {
    public static CommonPaginationDTO createPaginationDTO(Page<?> page){
        CommonPaginationDTO dto = new CommonPaginationDTO();
        validateIfPageOutOfRange(page);
        dto.setPage(page.getNumber());
        dto.setHasNext(page.isLast());
        dto.setSize(page.getSize());
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalElements(page.getTotalElements());
        dto.setData(page.getContent());
        return dto;
    }

    public static <T> void validateIfPageOutOfRange(Page<T> page){
        int givenIndex = page.getNumber();
        int maxIndex;
        if (page.getTotalPages() > 0)
        { maxIndex = page.getTotalPages() - 1;} else { maxIndex = 0;}
        if (givenIndex > maxIndex){
            throw new PaginationPageNotFoundException(String.format("There is no page with number %d for this resource. Max page index with size %d is %d", givenIndex, page.getSize(), maxIndex));
        }
    }
}
