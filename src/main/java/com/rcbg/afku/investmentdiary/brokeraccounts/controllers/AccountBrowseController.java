package com.rcbg.afku.investmentdiary.brokeraccounts.controllers;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.ReadableAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.Account;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.AccountSearchException;
import com.rcbg.afku.investmentdiary.brokeraccounts.responses.IdResponse;
import com.rcbg.afku.investmentdiary.brokeraccounts.responses.ListResponse;
import com.rcbg.afku.investmentdiary.brokeraccounts.responses.SimpleResponse;
import com.rcbg.afku.investmentdiary.brokeraccounts.responses.SuccessResponse;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.AccountBrowseService;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.AccountManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountException;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @GetMapping("/search")
    ResponseEntity<Object> searchForAccount(HttpServletRequest request,
                                            @Param("field") String field, @Param("value") String value){
        List<ReadableAccountDTO> accountsDTO = new ArrayList<>();
        try {
            for(Account account : browseService.findAllByField(field, value)){
                accountsDTO.add(new ReadableAccountDTO(account));
            }
        } catch (AccountSearchException | ParseException ex) {
            int code;
            if(ex instanceof AccountSearchException){
                code = ((AccountSearchException) ex).getCode();
            } else {
                code = 400;
            }
            logger.error(String.format("%s | %s", request.getRequestURI(), ex.getMessage()));
            SimpleResponse response = new SimpleResponse(code, ex.getMessage());
            return new ResponseEntity<Object>(response, new HttpHeaders(), 200);
        }
        ListResponse response = new ListResponse(200, accountsDTO, "List of accounts");
        return new ResponseEntity<Object>(response, new HttpHeaders(), 200);
    }

    @GetMapping("/all")
    ResponseEntity<Object> getAllAccounts(){
        List<ReadableAccountDTO> accountsDTO = new ArrayList<>();
        for(Account account : browseService.findAllAccounts()){
            accountsDTO.add(new ReadableAccountDTO(account));
        }
        ListResponse response = new ListResponse(200, accountsDTO, "List of accounts");
        return new ResponseEntity<Object>(response, new HttpHeaders(), 200);
    }

    @GetMapping("/daterange")
    ResponseEntity<Object> getAccountsFromCreationDateRange(HttpServletRequest request){
        List<ReadableAccountDTO> accountsDTO = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        Date stopDate;
        try {
            startDate = df.parse(request.getParameter("start_date"));
            stopDate = df.parse(request.getParameter("stop_date"));
        } catch (ParseException ex) {
            logger.error(String.format("%s | %s", request.getRequestURI(), ex.getMessage()));
            SimpleResponse response = new SimpleResponse(400, "Wrong date format or forbidden characters in params");
            return new ResponseEntity<Object>(response, new HttpHeaders(), 200);
        }
        for(Account account : browseService.findAllCreatedBetween(startDate, stopDate)){
            accountsDTO.add(new ReadableAccountDTO(account));
        }
        ListResponse response = new ListResponse(200, accountsDTO, "List of accounts");
        return new ResponseEntity<Object>(response, new HttpHeaders(), 200);
    }
}
