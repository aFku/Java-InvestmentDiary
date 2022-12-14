package com.rcbg.afku.investmentdiary.transactions.datatransferobjects;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.brokeraccounts.repositories.AccountRepository;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.AccountBrowseService;
import com.rcbg.afku.investmentdiary.subjects.entities.StockMarketSubject;
import com.rcbg.afku.investmentdiary.transactions.entities.StockMarketTransaction;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface StockMarketTransactionMapper {

    StockMarketTransactionMapper INSTANCE = Mappers.getMapper(StockMarketTransactionMapper.class);

    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "account.id", target = "accountId")
    StockMarketTransactionDTO toDTO(StockMarketTransaction transaction);

    @InheritInverseConfiguration
    @Mapping(source = "account", target = "account")
    @Mapping(source = "subject", target = "subject")
    StockMarketTransaction toEntity(StockMarketTransactionDTO dto, Account account, StockMarketSubject subject);
}
