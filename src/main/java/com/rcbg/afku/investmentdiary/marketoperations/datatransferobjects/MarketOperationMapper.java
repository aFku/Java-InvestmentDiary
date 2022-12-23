package com.rcbg.afku.investmentdiary.marketoperations.datatransferobjects;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.BrokerAccount;
import com.rcbg.afku.investmentdiary.marketsubjects.entities.MarketSubject;
import com.rcbg.afku.investmentdiary.marketoperations.entities.MarketOperation;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MarketOperationMapper {

    MarketOperationMapper INSTANCE = Mappers.getMapper(MarketOperationMapper.class);

    @Mapping(source = "marketSubject.id", target = "subjectId")
    @Mapping(source = "brokerAccount.id", target = "accountId")
    MarketOperationDTO toDTO(MarketOperation marketOperation);

    @InheritInverseConfiguration
    @Mapping(source = "brokerAccount", target = "brokerAccount")
    @Mapping(source = "marketSubject", target = "marketSubject")
    MarketOperation toEntity(MarketOperationDTO dto, BrokerAccount brokerAccount, MarketSubject marketSubject);
}
