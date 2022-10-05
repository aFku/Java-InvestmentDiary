package com.rcbg.afku.investmentdiary.brokeraccounts.services;

import com.rcbg.afku.investmentdiary.brokeraccounts.controllers.AccountController;
import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.RequestAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.ResponseAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountCreationException;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountManagementException;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountNotFoundException;
import com.rcbg.afku.investmentdiary.brokeraccounts.repositories.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AccountManagementService {

    private static final Logger logger = LoggerFactory.getLogger(AccountManagementService.class);
    private final AccountRepository repo;

    @Autowired
    public AccountManagementService(AccountRepository accountRepository){
        this.repo = accountRepository;
    }

    public ResponseAccountDTO createAccount(RequestAccountDTO accountDto){
        Account newAccount = new Account();
        if(accountDto.getProvider().isEmpty()){
            throw new AccountCreationException("'provider' field cannot be empty", 400);
        }
        if(accountDto.getAccountId().isEmpty()){
            throw new AccountCreationException("'accountId' field cannot be empty", 400);
        }
        newAccount.setAccountId(accountDto.getAccountId());
        newAccount.setProvider(accountDto.getProvider());
        repo.save(newAccount);
        int id = newAccount.getId();
        boolean created = repo.existsById(id);
        if(!created){
            throw new AccountManagementException("Account cannot be created", 500);
        }
        logger.info(String.format("Created account ID: %d, provider: %s, accountId: %s", id, accountDto.getProvider(), accountDto.getAccountId()));
        return new ResponseAccountDTO(newAccount);
    }

    public boolean deleteAccount(int id){
        boolean exist = repo.existsById(id);
        if(!exist){
            throw new AccountNotFoundException("Account with ID: " + id + " does not exist", 404);
        }
        repo.deleteById(id);
        boolean deleted = !repo.existsById(id);
        if(!deleted){
            throw new AccountManagementException( "Account with ID: " + id + " cannot be deleted", 500);
        }
        logger.info(String.format("Account deleted, id: %d", id));
        return true;
    }

    public ResponseAccountDTO updateAccount(int id, RequestAccountDTO requestAccountDTO){
        Account account = repo.findById(id).orElseThrow(() -> new AccountNotFoundException("Account with ID: " + id + " does not exist", 404));
        String accountId = requestAccountDTO.getAccountId();
        String provider = requestAccountDTO.getProvider();
        account.setAccountId(Objects.equals(accountId, "") ? account.getAccountId(): accountId);
        account.setProvider(Objects.equals(provider, "") ? account.getProvider(): provider);
        repo.save(account);
        return new ResponseAccountDTO(account);
    }
}
