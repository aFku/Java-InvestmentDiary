package com.rcbg.afku.investmentdiary.brokeraccounts.controllers;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.PaginationAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.RequestAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.ResponseAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.AccountBrowseService;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.AccountManagementService;
import com.rcbg.afku.investmentdiary.common.responses.MapResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/${api.version}/accounts")
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
    ResponseEntity<SingleAccountResponse> createAccount(HttpServletRequest request, @RequestBody RequestAccountDTO requestAccountDto){
        ResponseAccountDTO responseAccountDTO = managementService.createAccount(requestAccountDto);
        SingleAccountResponse response = new SingleAccountResponse(request.getRequestURI(), "account", 200, responseAccountDTO);
        return new ResponseEntity<SingleAccountResponse>(response, new HttpHeaders(), 200);
    }

    @GetMapping("/{id}")
    ResponseEntity<SingleAccountResponse> getAccountById(HttpServletRequest request, @PathVariable int id){
        ResponseAccountDTO responseAccountDTO = browseService.findOneAccountById(id);
        SingleAccountResponse response = new SingleAccountResponse(request.getRequestURI(), "account", 200, responseAccountDTO);
        return new ResponseEntity<SingleAccountResponse>(response, new HttpHeaders(), 200);
    }

    @PutMapping("/{id}")
    ResponseEntity<SingleAccountResponse> updateAccountById(HttpServletRequest request, @PathVariable int id, @RequestBody RequestAccountDTO requestAccountDTO){
        ResponseAccountDTO responseAccountDTO = managementService.updateAccount(id, requestAccountDTO);
        SingleAccountResponse response = new SingleAccountResponse(request.getRequestURI(), "account", 200, responseAccountDTO);
        return new ResponseEntity<SingleAccountResponse>(response, new HttpHeaders(), 200);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<MapResponse> deleteAccountById(HttpServletRequest request, @PathVariable int id){
        boolean deleted = managementService.deleteAccount(id);
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("type", "account");
        data.put("deleted", deleted);
        MapResponse response = new MapResponse(request.getRequestURI(), 200, data);
        return new ResponseEntity<MapResponse>(response, new HttpHeaders(), 200);
    }

//    @GetMapping("/search")
//    ResponseEntity<Object> getAccountsByField(@Param("field") String field, @Param("value") String value){
//        List<ResponseAccountDTO> responseAccountDTOS = browseService.findAllByField(field, value);
//        return new ResponseEntity<Object>(responseAccountDTOS, new HttpHeaders(), 200);
//    }

    @GetMapping
    ResponseEntity<ListAccountResponse> getAllAccounts(HttpServletRequest request, Pageable pageable){
        PaginationAccountDTO paginationDTO = browseService.findAllAccounts(pageable);
        ListAccountResponse response = new ListAccountResponse(request.getRequestURI(), "Account", 200, paginationDTO);
        return new ResponseEntity<ListAccountResponse>(response, new HttpHeaders(), 200);
    }

//    @GetMapping("/daterange")
//    ResponseEntity<Object> getAccountsFromCreationDateRange(@Param("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
//                                                            @Param("stopDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date stopDate){
//        List<ResponseAccountDTO> responseAccountDTOS = browseService.findAllCreatedBetween(startDate, stopDate);
//        return new ResponseEntity<Object>(responseAccountDTOS, new HttpHeaders(), 200);
//    }
}
