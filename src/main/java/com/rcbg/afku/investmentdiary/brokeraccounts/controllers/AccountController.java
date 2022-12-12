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
        return new ResponseEntity<CommonSingleModelResponse<BrokerAccountDTO>>(response, new HttpHeaders(), 200);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<CommonSingleModelResponse<BrokerAccountDTO>> getAccountById(HttpServletRequest request, @PathVariable int id){
        BrokerAccountDTO responseAccountDTO = browseService.findOneAccountById(id);
        CommonSingleModelResponse<BrokerAccountDTO> response = new CommonSingleModelResponse<BrokerAccountDTO>(200, request.getRequestURI(), "object",  responseAccountDTO);
        return new ResponseEntity<CommonSingleModelResponse<BrokerAccountDTO>>(response, new HttpHeaders(), 200);
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<CommonSingleModelResponse<BrokerAccountDTO>> updateAccountById(HttpServletRequest request, @PathVariable int id, @RequestBody BrokerAccountDTO requestAccountDTO){
        BrokerAccountDTO responseAccountDTO = managementService.updateAccount(id, requestAccountDTO);
        CommonSingleModelResponse<BrokerAccountDTO> response = new CommonSingleModelResponse<BrokerAccountDTO>(200, request.getRequestURI(), "object",  responseAccountDTO);
        return new ResponseEntity<CommonSingleModelResponse<BrokerAccountDTO>>(response, new HttpHeaders(), 200);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<CommonResourceDeletedResponse> deleteAccountById(HttpServletRequest request, @PathVariable int id){
        boolean deleted = managementService.deleteAccount(id);
        ResourceDeletedStatus<Integer> status = new ResourceDeletedStatus<>(id, deleted, "account");
        CommonResourceDeletedResponse response = new CommonResourceDeletedResponse(200, request.getRequestURI(), "object", status);
        return new ResponseEntity<CommonResourceDeletedResponse>(response, new HttpHeaders(), 200);
    }

//    @GetMapping("/search")
//    ResponseEntity<CommonModelPaginationResponse<ResponseAccountDTO>> getAccountsByField(HttpServletRequest request,
//                                     @RequestParam(value = "field", required = true) String field,
//                                     @RequestParam(value = "value", required = true) String value,
//                                     Pageable pageable){
//        PaginationAccountDTO paginationDTO = browseService.findAllByField(field, value, pageable);
//        CommonModelPaginationResponse<ResponseAccountDTO> response = new CommonModelPaginationResponse<ResponseAccountDTO>(200, request.getRequestURI(), "list", paginationDTO);
//        return new ResponseEntity<CommonModelPaginationResponse<ResponseAccountDTO>>(response, new HttpHeaders(), 200);
//    }
    @GetMapping
    ResponseEntity<CommonModelPaginationResponse<BrokerAccountDTO>> getAllAccounts(HttpServletRequest request, Pageable pageable){
        CommonPaginationDTO<BrokerAccountDTO> paginationDTO = browseService.findAllAccounts(pageable);
        CommonModelPaginationResponse<BrokerAccountDTO> response = new CommonModelPaginationResponse<BrokerAccountDTO>(200, request.getRequestURI(), "list", paginationDTO);
        return new ResponseEntity<CommonModelPaginationResponse<BrokerAccountDTO>>(response, new HttpHeaders(), 200);
    }
}
