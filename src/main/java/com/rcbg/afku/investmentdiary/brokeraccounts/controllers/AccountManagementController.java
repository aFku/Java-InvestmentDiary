package com.rcbg.afku.investmentdiary.brokeraccounts.controllers;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.EditableAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.ReadableAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.ResponseAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountManagementException;
import com.rcbg.afku.investmentdiary.brokeraccounts.responses.IdResponse;
import com.rcbg.afku.investmentdiary.brokeraccounts.responses.SimpleResponse;
import com.rcbg.afku.investmentdiary.brokeraccounts.responses.SuccessResponse;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.AccountManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${paths.brokeraccounts.management-controller}")
public class AccountManagementController {

    private static final Logger logger = LoggerFactory.getLogger(AccountManagementController.class);
    private final AccountManagementService managementService;

    @Autowired
    public AccountManagementController(AccountManagementService managementService){
        this.managementService = managementService;
    }

    @PostMapping("${paths.brokeraccounts.create-endpoint}")
    ResponseEntity<Object> createAccount(HttpServletRequest request, @RequestBody EditableAccountDTO accountDTO){
        int id;
        try {
            id = managementService.createAccount(accountDTO.getAccountId(), accountDTO.getProvider());
        } catch (AccountManagementException ex) {
            logger.error(String.format("%s | %s", request.getRequestURI(), ex.getMessage()));
            SimpleResponse response = new SimpleResponse(ex.getCode(), ex.getMessage());
            return new ResponseEntity<Object>(response, new HttpHeaders(), 200);
        }
        logger.info(String.format("%s | %s", request.getRequestURI(), "Account with ID: " + id + " has been created"));
        ResponseAccountDTO responseData = new ResponseAccountDTO(id, accountDTO.getAccountId(), accountDTO.getAccountId());
        SuccessResponse response = new SuccessResponse(200, responseData, "Account created");
        return new ResponseEntity<Object>(response, new HttpHeaders(), 200);
    }

    @DeleteMapping("${paths.brokeraccounts.delete-endpoint}")
    ResponseEntity<Object> deleteAccount(HttpServletRequest request, @Param("id") int id){
        try {
            managementService.deleteAccount(id);
        } catch (AccountManagementException ex) {
            logger.error(String.format("%s | %s", request.getRequestURI(), ex.getMessage()));
            SimpleResponse response = new SimpleResponse(ex.getCode(), ex.getMessage());
            return new ResponseEntity<Object>(response, new HttpHeaders(), 200);
        }
        logger.info(String.format("%s | %s", request.getRequestURI(), "Account with ID: " + id + " has been deleted"));
        IdResponse response = new IdResponse(200, "Account deleted", id);
        return new ResponseEntity<Object>(response, new HttpHeaders(), 200);
    }

    @PutMapping("${paths.brokeraccounts.update-endpoint}")
    ResponseEntity<Object> updateAccount(HttpServletRequest request, @Param("id") int id, @RequestBody EditableAccountDTO accountDTO){
        Account account;
        try {
            account = managementService.updateAccount(id, accountDTO.getProvider(), accountDTO.getAccountId());
        } catch (AccountManagementException ex) {
            logger.error(String.format("%s | %s", request.getRequestURI(), ex.getMessage()));
            SimpleResponse response = new SimpleResponse(ex.getCode(), ex.getMessage());
            return new ResponseEntity<Object>(response, new HttpHeaders(), 200);
        }
        logger.info(String.format("%s | %s", request.getRequestURI(), "Account with ID: " + id + " has been updated"));
        ReadableAccountDTO responseData = new ReadableAccountDTO(account);
        SuccessResponse response = new SuccessResponse(200, responseData, "Account updated");
        return new ResponseEntity<Object>(response, new HttpHeaders(), 200);
    }

}
