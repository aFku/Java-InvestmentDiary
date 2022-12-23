package com.rcbg.afku.investmentdiary.marketsubjects.datatransferobjects;

import com.rcbg.afku.investmentdiary.marketsubjects.entities.MarketSubject;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MarketSubjectMapper {

    MarketSubjectMapper INSTANCE = Mappers.getMapper(MarketSubjectMapper.class);

    @Mapping(source = "id", target = "id")
    MarketSubjectDTO toDTO(MarketSubject marketSubject);

    @InheritInverseConfiguration
    MarketSubject toEntity(MarketSubjectDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "marketOperations", ignore = true)
    @Mapping(target = "brokerAccounts", ignore = true)
    MarketSubject updateEntity(MarketSubjectDTO dto, @MappingTarget MarketSubject marketSubject);

}
