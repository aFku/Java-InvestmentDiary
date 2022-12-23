package com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.BrokerAccount;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BrokerAccountMapper {

    BrokerAccountMapper INSTANCE = Mappers.getMapper(BrokerAccountMapper.class);

    BrokerAccountDTO toDTO(BrokerAccount brokerAccount);

    @InheritInverseConfiguration
    BrokerAccount toEntity(BrokerAccountDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "marketOperations", ignore = true)
    @Mapping(target = "ownedMarketSubjects", ignore = true)
    BrokerAccount updateEntity(BrokerAccountDTO dto, @MappingTarget BrokerAccount brokerAccount);
}
