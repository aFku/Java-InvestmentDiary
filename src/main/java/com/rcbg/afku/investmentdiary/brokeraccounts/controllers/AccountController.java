package com.rcbg.afku.investmentdiary.brokeraccounts.controllers;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.BrokerAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.AccountBrowseService;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.AccountManagementService;
import com.rcbg.afku.investmentdiary.common.datatransferobjects.CommonPaginationDTO;
import com.rcbg.afku.investmentdiary.common.responses.CommonModelPaginationResponse;
import com.rcbg.afku.investmentdiary.common.responses.CommonResourceDeletedResponse;
import com.rcbg.afku.investmentdiary.common.responses.CommonSingleModelResponse;
import com.rcbg.afku.investmentdiary.common.statuses.ResourceDeletedStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    ResponseEntity<CommonSingleModelResponse<BrokerAccountDTO>> createAccount(HttpServletRequest request, @RequestBody BrokerAccountDTO requestAccountDto){
        BrokerAccountDTO responseAccountDTO = managementService.createAccount(requestAccountDto);
        CommonSingleModelResponse<BrokerAccountDTO> response = new CommonSingleModelResponse<BrokerAccountDTO>(200, request.getRequestURI(), "object",  responseAccountDTO);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<CommonSingleModelResponse<BrokerAccountDTO>> getAccountById(HttpServletRequest request, @PathVariable int id){
        BrokerAccountDTO responseAccountDTO = browseService.findOneAccountById(id);
        CommonSingleModelResponse<BrokerAccountDTO> response = new CommonSingleModelResponse<BrokerAccountDTO>(200, request.getRequestURI(), "object",  responseAccountDTO);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<CommonSingleModelResponse<BrokerAccountDTO>> updateAccountById(HttpServletRequest request, @PathVariable int id, @RequestBody BrokerAccountDTO requestAccountDTO){
        BrokerAccountDTO responseAccountDTO = managementService.updateAccount(id, requestAccountDTO);
        CommonSingleModelResponse<BrokerAccountDTO> response = new CommonSingleModelResponse<BrokerAccountDTO>(200, request.getRequestURI(), "object",  responseAccountDTO);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<CommonResourceDeletedResponse> deleteAccountById(HttpServletRequest request, @PathVariable int id){
        boolean deleted = managementService.deleteAccount(id);
        ResourceDeletedStatus<Integer> status = new ResourceDeletedStatus<>(id, deleted, "account");
        CommonResourceDeletedResponse response = new CommonResourceDeletedResponse(200, request.getRequestURI(), "object", status);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @GetMapping
    ResponseEntity<CommonModelPaginationResponse> getAllAccounts(HttpServletRequest request, Pageable pageable){
        CommonPaginationDTO paginationDTO = browseService.findAllAccounts(pageable);
        CommonModelPaginationResponse response = new CommonModelPaginationResponse(200, request.getRequestURI(), "list", paginationDTO);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }
}
