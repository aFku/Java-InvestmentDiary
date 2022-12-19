package com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.subjects.datatransferobjects.StockMarketSubjectMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BrokerAccountMapper {

    BrokerAccountMapper INSTANCE = Mappers.getMapper(BrokerAccountMapper.class);

    BrokerAccountDTO toDTO(Account account);

    @InheritInverseConfiguration
    Account toEntity(BrokerAccountDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "currentlyOwnedSubjects", ignore = true)
    Account updateEntity(BrokerAccountDTO dto, @MappingTarget Account account);
}
