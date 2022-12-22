package com.rcbg.afku.investmentdiary.brokeraccounts.services;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.BrokerAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.BrokerAccountMapper;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.brokeraccounts.repositories.AccountRepository;
import com.rcbg.afku.investmentdiary.common.utils.validationgroups.OnCreate;
import com.rcbg.afku.investmentdiary.common.utils.validationgroups.OnUpdate;
import com.rcbg.afku.investmentdiary.transactions.exceptions.TransactionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
public class AccountManagementService {

    private static final Logger logger = LoggerFactory.getLogger(AccountManagementService.class);
    private final AccountRepository repo;

    private final AccountBrowseService browseService;

    @Autowired
    public AccountManagementService(AccountRepository accountRepository, AccountBrowseService browseService){
        this.repo = accountRepository;
        this.browseService = browseService;
    }

    @Validated(OnCreate.class)
    public BrokerAccountDTO createAccount(@Valid BrokerAccountDTO dto){
        Account newAccount = BrokerAccountMapper.INSTANCE.toEntity(dto);
        repo.save(newAccount);
        BrokerAccountDTO newDto = BrokerAccountMapper.INSTANCE.toDTO(newAccount);
        logger.info("Created account " + newDto);
        return newDto;
    }

    public boolean deleteAccount(int id){
        try {
            repo.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new TransactionNotFoundException("Account with id: " + id + " not found");
        }
        logger.info("Account with id: " + id + " deleted");
        return true;
    }

    @Validated(OnUpdate.class)
    public BrokerAccountDTO updateAccount(int id, @Valid BrokerAccountDTO dto){
        Account account = browseService.getAccountDomainObjectById(id);
        account = BrokerAccountMapper.INSTANCE.updateEntity(dto, account);
        repo.save(account);
        BrokerAccountDTO updatedDto = BrokerAccountMapper.INSTANCE.toDTO(account);
        logger.info("Account with id: " + id + " updated to " + updatedDto);
        return updatedDto;
    }
}
