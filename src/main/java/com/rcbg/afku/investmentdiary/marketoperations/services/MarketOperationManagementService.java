package com.rcbg.afku.investmentdiary.marketoperations.services;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.BrokerAccount;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.BrokerAccountBrowseService;
import com.rcbg.afku.investmentdiary.marketoperations.exceptions.MarketOperationSellVolumeBeyondStateException;
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
        SubjectToAccountAction relationAction = checkRelationBetweenAccountAndSubject(brokerAccount.getId(), subject.getId(), dto);
        repo.save(transaction);
        MarketOperationDTO newDto = MarketOperationMapper.INSTANCE.toDTO(transaction);
        logger.info("Created transaction " + newDto);
        if (relationAction != null) {
            relateSubjectWithAccount(relationAction, brokerAccount, subject);
        }
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

    private SubjectToAccountAction checkRelationBetweenAccountAndSubject(int accountId, int subjectId, MarketOperationDTO operationDTO){
        int ownedVolume = repo.calculateNumberOfVolumesForAccount(accountId, subjectId);
        switch (operationDTO.getOperationType()){
            case "BUY":
                if(ownedVolume > 0) { return SubjectToAccountAction.NONE; }
                return SubjectToAccountAction.ADD;
            case "SELL":
                if (ownedVolume - operationDTO.getVolume() < 0) {
                    throw new MarketOperationSellVolumeBeyondStateException(
                        "Trying to sell: " + operationDTO.getVolume() + " when owned: " + ownedVolume);
                } else if (ownedVolume - operationDTO.getVolume() == 0) { return SubjectToAccountAction.REMOVE;}
                return SubjectToAccountAction.NONE;
            default:
                return null;
        }
    }

    private void relateSubjectWithAccount(SubjectToAccountAction action, BrokerAccount account, MarketSubject subject){
        switch (action){
            case ADD:
                account.addOwnedMarketSubject(subject);
                logger.info("Added subject with ID: " + subject.getId() + " to account with ID: " + account.getAccountId());
                break;
            case REMOVE:
                account.removeOwnedMarketSubject(subject);
                logger.info("Removed subject with ID: " + subject.getId() + " from account with ID: " + account.getAccountId());
                break;
            default:
        }
    }
}