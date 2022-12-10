package com.rcbg.afku.investmentdiary.common.utils;

import com.rcbg.afku.investmentdiary.common.datatransferobjects.CommonPaginationDTO;
import com.rcbg.afku.investmentdiary.common.exceptions.PaginationPageNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PageableManagement {
    public static <T, S> CommonPaginationDTO<T> createPaginationDTO(Page<S> page){
        CommonPaginationDTO<T> dto = new CommonPaginationDTO<>();
        validateIfPageOutOfRange(page);
        dto.setPage(page.getNumber());
        dto.setHasNext(page.hasNext());
        dto.setSize(page.getSize());
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalElements(page.getTotalElements());
        ModelMapper modelMapper = new ModelMapper();
        Type listType = new TypeToken<ArrayList<T>>() {}.getType();
        dto.setData(modelMapper.map(page.getContent(), listType));
        return dto;
    }

    public static <T> void validateIfPageOutOfRange(Page<T> page){
        int givenIndex = page.getNumber(), maxIndex = page.getTotalPages() - 1;
        if (givenIndex > maxIndex){
            throw new PaginationPageNotFoundException(String.format("There is no page with number %d for this resource. Max page index with size %d is %d", givenIndex, page.getSize(), maxIndex));
        }
    }
}
