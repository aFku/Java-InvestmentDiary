package com.rcbg.afku.investmentdiary.brokeraccounts.services;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.BrokerAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.BrokerAccountMapper;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.BrokerAccount;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.BrokerAccountNotFoundException;
import com.rcbg.afku.investmentdiary.brokeraccounts.repositories.BrokerAccountRepository;
import com.rcbg.afku.investmentdiary.common.utils.validationgroups.OnCreate;
import com.rcbg.afku.investmentdiary.common.utils.validationgroups.OnUpdate;
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
public class BrokerAccountManagementService {

    private static final Logger logger = LoggerFactory.getLogger(BrokerAccountManagementService.class);
    private final BrokerAccountRepository repo;

    private final BrokerAccountBrowseService browseService;

    @Autowired
    public BrokerAccountManagementService(BrokerAccountRepository brokerAccountRepository, BrokerAccountBrowseService browseService){
        this.repo = brokerAccountRepository;
        this.browseService = browseService;
    }

    @Transactional
    @Validated(OnCreate.class)
    public BrokerAccountDTO createBrokerAccount(@Valid BrokerAccountDTO dto){
        BrokerAccount newBrokerAccount = BrokerAccountMapper.INSTANCE.toEntity(dto);
        repo.save(newBrokerAccount);
        BrokerAccountDTO newDto = BrokerAccountMapper.INSTANCE.toDTO(newBrokerAccount);
        logger.info("Created broker account " + newDto);
        return newDto;
    }

    @Transactional
    public void deleteBrokerAccount(int id){
        try {
            repo.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new BrokerAccountNotFoundException("Broker account with id: " + id + " not found");
        }
        logger.info("Broker account with id: " + id + " deleted");
    }

    @Transactional
    @Validated(OnUpdate.class)
    public BrokerAccountDTO updateAccount(int id, @Valid BrokerAccountDTO dto){
        BrokerAccount brokerAccount = browseService.getBrokerAccountDomainObjectById(id);
        brokerAccount = BrokerAccountMapper.INSTANCE.updateEntity(dto, brokerAccount);
        repo.save(brokerAccount);
        BrokerAccountDTO updatedDto = BrokerAccountMapper.INSTANCE.toDTO(brokerAccount);
        logger.info("Broker account with id: " + id + " updated to " + updatedDto);
        return updatedDto;
    }
}
