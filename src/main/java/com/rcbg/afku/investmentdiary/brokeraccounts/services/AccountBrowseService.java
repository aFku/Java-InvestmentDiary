package com.rcbg.afku.investmentdiary.brokeraccounts.services;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.BrokerAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.BrokerAccountMapper;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountNotFoundException;
import com.rcbg.afku.investmentdiary.brokeraccounts.repositories.AccountRepository;
import com.rcbg.afku.investmentdiary.common.datatransferobjects.CommonPaginationDTO;
import com.rcbg.afku.investmentdiary.common.utils.PageableManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AccountBrowseService {

    private final AccountRepository repo;

    @Autowired
    public AccountBrowseService(AccountRepository accountRepository){
        this.repo = accountRepository;
    }

    public BrokerAccountDTO findOneAccountById(int id){
        return BrokerAccountMapper.INSTANCE.toDTO(getAccountDomainObjectById(id));
    }

    public CommonPaginationDTO<BrokerAccountDTO> findAllAccounts(Pageable pageable){
        Page<Account> accounts = repo.findAll(pageable);
        return PageableManagement.createPaginationDTO(accounts);
    }

    public Account getAccountDomainObjectById(int id){
        return repo.findById(id).orElseThrow( () -> new AccountNotFoundException("Account with id: " + id + " not found"));
    }
}
