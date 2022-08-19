package com.rcbg.afku.investmentdiary.brokeraccounts.services;

import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.brokeraccounts.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
