package com.rcbg.afku.investmentdiary.brokeraccounts.services;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.PaginationAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.ResponseAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountNotFoundException;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountSearchException;
import com.rcbg.afku.investmentdiary.brokeraccounts.repositories.AccountRepository;
import com.rcbg.afku.investmentdiary.common.utils.PageableManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountBrowseService {

    private final AccountRepository repo;

    @Value("${pagination.max-page-size}")
    private int maxPageSize;

    @Autowired
    public AccountBrowseService(AccountRepository accountRepository){
        this.repo = accountRepository;
    }

    public ResponseAccountDTO findOneAccountById(int id){
        Account account = repo.findById(id).orElseThrow( () -> new AccountNotFoundException("Account with id: " + id + " not found"));
        return new ResponseAccountDTO(account);
    }

    public PaginationAccountDTO findAllAccounts(int page, int size, String sort){
        Page<Account> accounts = repo.findAll(PageableManagement.createPageableObject(page, size, this.maxPageSize, sort));
        return createPaginationResponse(accounts, page);
    }

    public PaginationAccountDTO findAllByField(String field, String value, int page, int size, String sort){
        Pageable pageable = PageableManagement.createPageableObject(page, size, this.maxPageSize, sort);
        Page<Account> accounts;
        switch (field){
            case "provider":
                accounts = repo.findAllByProvider(value, pageable);
                break;
            case "accountId":
                accounts = repo.findAllByAccountId(value, pageable);
                break;
            case "creationDate":
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    accounts = repo.findAllByCreationDate(df.parse(value), pageable);
                } catch (ParseException e) {
                    throw new AccountSearchException(e.getMessage());
                }
                break;
            default:
                throw new AccountSearchException("Invalid field name '" + field + "'");
        }
        return createPaginationResponse(accounts, page);
    }

    private List<ResponseAccountDTO> convertListOfAccountsToListOfDTOS(List<Account> accounts){
        return accounts.stream().map(ResponseAccountDTO::new).collect( Collectors.toList());
    }

    private PaginationAccountDTO createPaginationResponse(Page<Account> accounts, int page){
        if( page >= accounts.getTotalPages()){
            throw new IllegalArgumentException("There are no page with index " + page + " for this resource. Last index is " + (accounts.getTotalPages() - 1));
        }
        ArrayList<ResponseAccountDTO> data = (ArrayList<ResponseAccountDTO>) convertListOfAccountsToListOfDTOS(accounts.getContent());
        return new PaginationAccountDTO(page, accounts.getTotalPages(), accounts.getSize(),
                accounts.getTotalElements(), accounts.isLast(), data);
    }
}
