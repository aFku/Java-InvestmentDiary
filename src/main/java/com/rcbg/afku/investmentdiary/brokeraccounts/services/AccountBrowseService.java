package com.rcbg.afku.investmentdiary.brokeraccounts.services;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountSearchException;
import com.rcbg.afku.investmentdiary.brokeraccounts.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountBrowseService {

    private final AccountRepository repo;

    @Autowired
    public AccountBrowseService(AccountRepository accountRepository){
        this.repo = accountRepository;
    }

    public Account findOneAccountById(int id){
        return repo.findById(id).orElse(null);
    }

    public List<Account> findAllAccounts(){
        return repo.findAll();
    }

    public List<Account> findAllByField(String field, String value) throws ParseException, AccountSearchException {
        List<Account> accounts = new ArrayList<>();
        switch (field){
            case "provider":
                accounts = repo.findAllByProvider(value);
                break;
            case "accountId":
                accounts = repo.findAllByAccountId(value);
                break;
            case "creationDate":
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                accounts = repo.findAllByCreationDate(df.parse(value));
                break;
            default:
                throw new AccountSearchException("Invalid field name '" + field + "'", 400);
        }
        return accounts;
    }

    public List<Account> findAllCreatedBetween(Date start, Date stop){
        return repo.findAllByCreationDateBetween(start, stop);
    }
}
