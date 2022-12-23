package com.rcbg.afku.investmentdiary.marketoperations.services;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.BrokerAccount;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.BrokerAccountBrowseService;
import com.rcbg.afku.investmentdiary.marketsubjects.entities.MarketSubject;
import com.rcbg.afku.investmentdiary.marketsubjects.services.MarketSubjectBrowseService;
import com.rcbg.afku.investmentdiary.marketoperations.datatransferobjects.MarketOperationDTO;
import com.rcbg.afku.investmentdiary.marketoperations.datatransferobjects.MarketOperationMapper;
import com.rcbg.afku.investmentdiary.marketoperations.entities.MarketOperation;
import com.rcbg.afku.investmentdiary.marketoperations.exceptions.MarketOperationNotFoundException;
import com.rcbg.afku.investmentdiary.marketoperations.repositories.MarketOperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Validated
public class MarketOperationManagementService {

    private static final Logger logger = LoggerFactory.getLogger(MarketOperationManagementService.class);

    private final MarketOperationRepository repo;
    private final BrokerAccountBrowseService brokerAccountBrowseService;
    private final MarketSubjectBrowseService marketSubjectBrowseService;

    @Autowired
    public MarketOperationManagementService(MarketOperationRepository transactionRepository, BrokerAccountBrowseService brokerAccountBrowseService, MarketSubjectBrowseService marketSubjectBrowseService){
        this.repo = transactionRepository;
        this.brokerAccountBrowseService = brokerAccountBrowseService;
        this.marketSubjectBrowseService = marketSubjectBrowseService;
    }

    @Transactional
    public MarketOperationDTO createMarketOperation(@Valid MarketOperationDTO dto){
        BrokerAccount brokerAccount = brokerAccountBrowseService.getBrokerAccountDomainObjectById(dto.getAccountId());
        MarketSubject subject = marketSubjectBrowseService.getMarketSubjectDomainObjectById(dto.getSubjectId());
        MarketOperation transaction = MarketOperationMapper.INSTANCE.toEntity(dto, brokerAccount, subject);
        repo.save(transaction);
        MarketOperationDTO newDto = MarketOperationMapper.INSTANCE.toDTO(transaction);
        logger.info("Created transaction " + newDto);
        return newDto;
    }

    @Transactional
    public void deleteMarketOperation(long id){
        try{
            repo.deleteById(id);
        } catch(EmptyResultDataAccessException e) {
            throw new MarketOperationNotFoundException("Market operation with id: " + id + " not found");
        }
        logger.info("Market operation with id: " + id + " deleted");
    }
}