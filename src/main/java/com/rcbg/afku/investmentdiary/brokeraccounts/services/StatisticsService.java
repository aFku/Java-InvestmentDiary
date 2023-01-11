package com.rcbg.afku.investmentdiary.brokeraccounts.services;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.DateRangeParam;
import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.StatisticsDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.BrokerAccountNotFoundException;
import com.rcbg.afku.investmentdiary.brokeraccounts.repositories.BrokerAccountRepository;
import com.rcbg.afku.investmentdiary.common.search.SpecificationsBuilder;
import com.rcbg.afku.investmentdiary.common.utils.DateParser;
import com.rcbg.afku.investmentdiary.marketoperations.entities.MarketOperation;
import com.rcbg.afku.investmentdiary.marketoperations.entities.OperationType;
import com.rcbg.afku.investmentdiary.marketoperations.repositories.MarketOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Service
@Validated
public class StatisticsService {

    private final MarketOperationRepository marketOperationRepository;
    private final BrokerAccountRepository brokerAccountRepository;

    @Autowired
    public StatisticsService(MarketOperationRepository marketOperationRepository, BrokerAccountRepository brokerAccountRepository) {
        this.marketOperationRepository = marketOperationRepository;
        this.brokerAccountRepository = brokerAccountRepository;
    }

    @Validated
    @Transactional
    public StatisticsDTO getStats(int accountId, @Valid DateRangeParam dateRange){
        if( ! brokerAccountRepository.existsById(accountId) ) {throw new BrokerAccountNotFoundException("Broker account with ID: " + accountId + " not found");}
        SpecificationsBuilder<MarketOperation> builder = new SpecificationsBuilder<>();
        builder.with("operationDate", "<", DateParser.toString(dateRange.getStopDate()));
        builder.with("operationDate", ">", DateParser.toString(dateRange.getStartDate()));
        builder.with("brokerAccount", ":", Integer.toString(accountId));
        Specification<MarketOperation> spec = builder.build();
        StatisticsDTO statsDTO = createStatisticsDtoFromPages(spec);
        statsDTO.setDateRange(dateRange);
        return statsDTO;
    }

    @Validated
    @Transactional
    public StatisticsDTO getStats(@Valid DateRangeParam dateRange){
        SpecificationsBuilder<MarketOperation> builder = new SpecificationsBuilder<>();
        builder.with("operationDate", "<", DateParser.toString(dateRange.getStopDate()));
        builder.with("operationDate", ">", DateParser.toString(dateRange.getStartDate()));
        Specification<MarketOperation> spec = builder.build();
        StatisticsDTO statsDTO = createStatisticsDtoFromPages(spec);
        statsDTO.setDateRange(dateRange);
        return statsDTO;
    }

    private StatisticsDTO createStatisticsDtoFromPages(Specification<MarketOperation> spec){
        Page<MarketOperation> page = marketOperationRepository.findAll(spec, PageRequest.of(0, 50));
        int volumesSold = 0, volumesBought = 0;
        BigDecimal moneySpent = BigDecimal.ZERO, moneyEarned = BigDecimal.ZERO;
        Set<Integer> uniqueSubjectsCollection = new HashSet<>();

        do{
            for(MarketOperation operation: page.getContent()){
                if(operation.getOperationType().equals(OperationType.SELL)){
                    volumesSold += operation.getVolume();
                    BigDecimal convertedVolume = BigDecimal.valueOf(operation.getVolume());
                    moneyEarned = moneyEarned.add(operation.getPricePerOne().multiply(convertedVolume));
                } else {
                    volumesBought += operation.getVolume();
                    BigDecimal convertedVolume = BigDecimal.valueOf(operation.getVolume());
                    moneySpent = moneySpent.add(operation.getPricePerOne().multiply(convertedVolume));
                }
                uniqueSubjectsCollection.add(operation.getMarketSubject().getId());
            }
            if(page.isLast()){break;}
            page = marketOperationRepository.findAll(spec, page.nextPageable());
        }while(page.hasNext());
        StatisticsDTO statsDTO = new StatisticsDTO();
        statsDTO.setMoneyEarned(moneyEarned);
        statsDTO.setMoneySpent(moneySpent);
        statsDTO.setVolumesBought(volumesBought);
        statsDTO.setVolumesSold(volumesSold);
        statsDTO.setUniqueSubjectsOperated(uniqueSubjectsCollection.size());
        return statsDTO;
    }
}
