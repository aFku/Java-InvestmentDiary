package com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.subjects.datatransferobjects.StockMarketSubjectMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BrokerAccountMapper {

    BrokerAccountMapper INSTANCE = Mappers.getMapper(BrokerAccountMapper.class);

    BrokerAccountDTO toDTO(Account account);

    @InheritInverseConfiguration
    Account toEntity(BrokerAccountDTO dto);
}
