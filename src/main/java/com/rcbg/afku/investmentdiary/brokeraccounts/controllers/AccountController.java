package com.rcbg.afku.investmentdiary.brokeraccounts.controllers;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.RequestAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.ResponseAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.AccountBrowseService;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.AccountManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final AccountManagementService managementService;
    private final AccountBrowseService browseService;

    @Autowired
    public AccountController(AccountManagementService managementService, AccountBrowseService browseService){
        this.managementService = managementService;
        this.browseService = browseService;
    }

    @PostMapping
    ResponseEntity<Object> createAccount(@RequestBody RequestAccountDTO requestAccountDto){
        ResponseAccountDTO responseAccountDTO = managementService.createAccount(requestAccountDto);
        return new ResponseEntity<Object>(responseAccountDTO, new HttpHeaders(), 200);
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> getAccountById(@PathVariable int id){
        ResponseAccountDTO responseAccountDTO = browseService.findOneAccountById(id);
        return new ResponseEntity<Object>(responseAccountDTO, new HttpHeaders(), 200);
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> updateAccountById(@PathVariable int id, @RequestBody RequestAccountDTO requestAccountDTO){
        ResponseAccountDTO responseAccountDTO = managementService.updateAccount(id, requestAccountDTO);
        return new ResponseEntity<Object>(responseAccountDTO, new HttpHeaders(), 200);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteAccountById(@PathVariable int id){
        boolean deleted = managementService.deleteAccount(id);
        HashMap<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("type", "account");
        response.put("deleted", deleted);
        return new ResponseEntity<Object>(response, new HttpHeaders(), 200);
    }

    @GetMapping("/search")
    ResponseEntity<Object> getAccountsByField(@Param("field") String field, @Param("value") String value){
        List<ResponseAccountDTO> responseAccountDTOS = browseService.findAllByField(field, value);
        return new ResponseEntity<Object>(responseAccountDTOS, new HttpHeaders(), 200);
    }

    @GetMapping
    ResponseEntity<Object> getAllAccounts(){
        List<ResponseAccountDTO> responseAccountDTOS = browseService.findAllAccounts();
        return new ResponseEntity<Object>(responseAccountDTOS, new HttpHeaders(), 200);
    }

    @GetMapping("/daterange")
    ResponseEntity<Object> getAccountsFromCreationDateRange(@Param("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                            @Param("stopDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date stopDate){
        List<ResponseAccountDTO> responseAccountDTOS = browseService.findAllCreatedBetween(startDate, stopDate);
        return new ResponseEntity<Object>(responseAccountDTOS, new HttpHeaders(), 200);
    }
}
