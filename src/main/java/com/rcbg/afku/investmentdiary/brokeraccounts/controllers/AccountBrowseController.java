package com.rcbg.afku.investmentdiary.brokeraccounts.controllers;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.ReadableAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.brokeraccounts.responses.IdResponse;
import com.rcbg.afku.investmentdiary.brokeraccounts.responses.SuccessResponse;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.AccountBrowseService;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.AccountManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${paths.brokeraccounts.browse-controller}")
public class AccountBrowseController {

    private static final Logger logger = LoggerFactory.getLogger(AccountManagementController.class);
    private final AccountBrowseService browseService;

    @Autowired
    public AccountBrowseController(AccountBrowseService managementService){
        this.browseService = managementService;
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> getAccountById(@PathVariable int id){
        Account account = browseService.findOneAccountById(id);
        if(account == null){
            IdResponse response = new IdResponse(404, "Account with given ID not found", id);
            return new ResponseEntity<Object>(response, new HttpHeaders(), 200);
        }
        ReadableAccountDTO responseData = new ReadableAccountDTO(account);
        SuccessResponse response = new SuccessResponse(200, responseData, "Account found");
        return new ResponseEntity<Object>(response, new HttpHeaders(), 200);
    }
}
