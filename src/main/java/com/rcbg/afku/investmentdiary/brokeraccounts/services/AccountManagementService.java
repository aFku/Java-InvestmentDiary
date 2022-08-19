package com.rcbg.afku.investmentdiary.brokeraccounts.services;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountManagementException;
import com.rcbg.afku.investmentdiary.brokeraccounts.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AccountManagementService {

    private final AccountRepository repo;

    @Autowired
    public AccountManagementService(AccountRepository accountRepository){
        this.repo = accountRepository;
    }

    public int createAccount(String provider, String accountId) throws AccountManagementException {
        Account newAccount = new Account();
        if(provider.isEmpty()){
            throw new AccountManagementException("'provider' field cannot be empty", 400);
        }
        if(accountId.isEmpty()){
            throw new AccountManagementException("'accountId' field cannot be empty", 400);
        }
        newAccount.setAccountId(accountId);
        newAccount.setProvider(provider);
        repo.save(newAccount);
        int id = newAccount.getId();
        boolean created = repo.existsById(id);
        if(!created){
            throw new AccountManagementException("Account cannot be created", 500);
        }
        return id;
    }

    public boolean deleteAccount(int id) throws AccountManagementException{
        boolean exist = repo.existsById(id);
        if(!exist){
            throw new AccountManagementException("Account with ID: " + id + " does not exist", 404);
        }
        repo.deleteById(id);
        boolean deleted = !repo.existsById(id);
        if(!deleted){
            throw new AccountManagementException( "Account with ID: " + id + " cannot be deleted", 500);
        }
        return true;
    }

    public Account updateAccount(int id, String provider, String accountId) throws AccountManagementException{
        Account account = repo.findById(id).orElse(null);
        if(account == null){
            throw new AccountManagementException("Account with ID: " + id + " does not exist", 404);
        }
        account.setAccountId(Objects.equals(accountId, "") ? account.getAccountId(): accountId);
        account.setProvider(Objects.equals(provider, "") ? account.getProvider(): provider);
        repo.save(account);
        return account;
    }
}
