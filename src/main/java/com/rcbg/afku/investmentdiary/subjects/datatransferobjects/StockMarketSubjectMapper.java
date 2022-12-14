package com.rcbg.afku.investmentdiary.subjects.datatransferobjects;

import com.rcbg.afku.investmentdiary.subjects.entities.StockMarketSubject;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StockMarketSubjectMapper {

    StockMarketSubjectMapper INSTANCE = Mappers.getMapper(StockMarketSubjectMapper.class);

    @Mapping(source = "id", target = "id")
    StockMarketSubjectDTO toDTO(StockMarketSubject subject);

    @InheritInverseConfiguration
    StockMarketSubject toEntity(StockMarketSubjectDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "holdingAccounts", ignore = true)
    StockMarketSubject updateEntity(StockMarketSubjectDTO dto, @MappingTarget StockMarketSubject subject);

}
