package com.rcbg.afku.investmentdiary.subjects.datatransferobjects;

import com.rcbg.afku.investmentdiary.subjects.entities.StockMarketSubject;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StockMarketSubjectMapper {

    StockMarketSubjectMapper INSTANCE = Mappers.getMapper(StockMarketSubjectMapper.class);

    @Mapping(source = "id", target = "id")
    StockMarketSubjectDTO toDTO(StockMarketSubject subject);

    @InheritInverseConfiguration
    StockMarketSubject toEntity(StockMarketSubjectDTO dto);
}
