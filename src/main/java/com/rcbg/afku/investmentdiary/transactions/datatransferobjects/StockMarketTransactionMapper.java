package com.rcbg.afku.investmentdiary.transactions.datatransferobjects;

import com.rcbg.afku.investmentdiary.transactions.entities.StockMarketTransaction;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StockMarketTransactionMapper {

    StockMarketTransactionMapper INSTANCE = Mappers.getMapper(StockMarketTransactionMapper.class);

    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "account.id", target = "accountId")
    StockMarketTransactionDTO stockMarketTransactionToStockMarketTransactionDTO(StockMarketTransaction transaction);

    @InheritInverseConfiguration
    StockMarketTransaction stockMarketTransactionDTOToStockMarketTransaction(StockMarketTransactionDTO dto);
}
