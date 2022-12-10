package com.rcbg.afku.investmentdiary.brokeraccounts.services;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.ResponseAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountNotFoundException;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountSearchException;
import com.rcbg.afku.investmentdiary.brokeraccounts.repositories.AccountRepository;
import com.rcbg.afku.investmentdiary.common.datatransferobjects.CommonPaginationDTO;
import com.rcbg.afku.investmentdiary.common.utils.PageableManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountBrowseService {

    private final AccountRepository repo;

    @Autowired
    public AccountBrowseService(AccountRepository accountRepository){
        this.repo = accountRepository;
    }

    public ResponseAccountDTO findOneAccountById(int id){
        return new ResponseAccountDTO(getAccountDomainObjectById(id));
    }

    public CommonPaginationDTO<ResponseAccountDTO> findAllAccounts(Pageable pageable){
        Page<Account> accounts = repo.findAll(pageable);
        return PageableManagement.createPaginationDTO(accounts);
    }

//    public PaginationAccountDTO findAllByField(String field, String value, Pageable pageable){
//        Page<Account> accounts;
//        switch (field){
//            case "provider":
//                accounts = repo.findAllByProvider(value, pageable);
//                break;
//            case "accountId":
//                accounts = repo.findAllByAccountId(value, pageable);
//                break;
//            case "creationDate":
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                try {
//                    accounts = repo.findAllByCreationDate(df.parse(value), pageable);
//                } catch (ParseException e) {
//                    throw new AccountSearchException(e.getMessage());
//                }
//                break;
//            default:
//                throw new AccountSearchException("Invalid field name '" + field + "'");
//        }
//        return createPaginationResponse(accounts);
//    }

    public Account getAccountDomainObjectById(int id){
        return repo.findById(id).orElseThrow( () -> new AccountNotFoundException("Account with id: " + id + " not found"));
    }
}
