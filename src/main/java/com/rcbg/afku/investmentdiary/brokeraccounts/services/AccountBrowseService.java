package com.rcbg.afku.investmentdiary.brokeraccounts.services;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.ResponseAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountNotFoundException;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountSearchException;
import com.rcbg.afku.investmentdiary.brokeraccounts.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        Account account = repo.findById(id).orElseThrow( () -> new AccountNotFoundException("Account with id: " + id + " not found", 404));
        return new ResponseAccountDTO(account);
    }

    public List<ResponseAccountDTO> findAllAccounts(){
        return convertListOfAccountsToListOfDTOS(repo.findAll());
    }

    public List<ResponseAccountDTO> findAllByField(String field, String value){
        List<ResponseAccountDTO> responseAccountDTOS = new ArrayList<>();
        switch (field){
            case "provider":
                responseAccountDTOS = convertListOfAccountsToListOfDTOS(repo.findAllByProvider(value));
                break;
            case "accountId":
                responseAccountDTOS = convertListOfAccountsToListOfDTOS(repo.findAllByAccountId(value));
                break;
            case "creationDate":
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    responseAccountDTOS = convertListOfAccountsToListOfDTOS(repo.findAllByCreationDate(df.parse(value)));
                } catch (ParseException e) {
                    throw new AccountSearchException(e.getMessage(), 400);
                }
                break;
            default:
                throw new AccountSearchException("Invalid field name '" + field + "'", 400);
        }
        return responseAccountDTOS;
    }

    public List<ResponseAccountDTO> findAllCreatedBetween(Date start, Date stop){
        return convertListOfAccountsToListOfDTOS(repo.findAllByCreationDateBetween(start, stop));
    }

    private List<ResponseAccountDTO> convertListOfAccountsToListOfDTOS(List<Account> accounts){
        return accounts.stream().map(ResponseAccountDTO::new).collect( Collectors.toList());
    }
}
