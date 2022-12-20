package com.rcbg.afku.investmentdiary.brokeraccounts.services;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.BrokerAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.BrokerAccountMapper;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountCreationException;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountManagementException;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountNotFoundException;
import com.rcbg.afku.investmentdiary.brokeraccounts.repositories.AccountRepository;
import com.rcbg.afku.investmentdiary.common.utils.validationgroups.OnCreate;
import com.rcbg.afku.investmentdiary.common.utils.validationgroups.OnUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
public class AccountManagementService {

    private static final Logger logger = LoggerFactory.getLogger(AccountManagementService.class);
    private final AccountRepository repo;

    @Autowired
    public AccountManagementService(AccountRepository accountRepository){
        this.repo = accountRepository;
    }

    @Validated(OnCreate.class)
    public BrokerAccountDTO createAccount(@Valid BrokerAccountDTO accountDto){
        Account newAccount = BrokerAccountMapper.INSTANCE.toEntity(accountDto);
        if(accountDto.getProvider().isEmpty()){
            throw new AccountCreationException("'provider' field cannot be empty");
        }
        if(accountDto.getAccountId().isEmpty()){
            throw new AccountCreationException("'accountId' field cannot be empty");
        }
        repo.save(newAccount);
        int id = newAccount.getId();
        boolean created = repo.existsById(id);
        if(!created){
            throw new AccountManagementException("Account cannot be created");
        }
        logger.info(String.format("Created account ID: %d, provider: %s, accountId: %s", id, accountDto.getProvider(), accountDto.getAccountId()));
        return BrokerAccountMapper.INSTANCE.toDTO(newAccount);
    }

    public boolean deleteAccount(int id){
        boolean exist = repo.existsById(id);
        if(!exist){
            throw new AccountNotFoundException("Account with ID: " + id + " does not exist");
        }
        repo.deleteById(id);
        boolean deleted = !repo.existsById(id);
        if(!deleted){
            throw new AccountManagementException( "Account with ID: " + id + " cannot be deleted");
        }
        logger.info(String.format("Account deleted, id: %d", id));
        return true;
    }

    @Validated(OnUpdate.class)
    public BrokerAccountDTO updateAccount(int id, @Valid BrokerAccountDTO requestAccountDTO){
        Account account = repo.findById(id).orElseThrow(() -> new AccountNotFoundException("Account with ID: " + id + " does not exist"));
        account = BrokerAccountMapper.INSTANCE.updateEntity(requestAccountDTO, account);
        repo.save(account);
        logger.info(String.format("Account updated, id: %d, accountId: %s, provider: %s", id, account.getAccountId(), account.getProvider()));
        return BrokerAccountMapper.INSTANCE.toDTO(account);
    }
}
