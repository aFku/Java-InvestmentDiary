package com.rcbg.afku.investmentdiary.brokeraccounts.controllers;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.BrokerAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.BrokerAccountBrowseService;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.BrokerAccountManagementService;
import com.rcbg.afku.investmentdiary.common.datatransferobjects.CommonPaginationDTO;
import com.rcbg.afku.investmentdiary.common.responses.CommonModelPaginationResponse;
import com.rcbg.afku.investmentdiary.common.responses.CommonResourceDeletedResponse;
import com.rcbg.afku.investmentdiary.common.responses.CommonSingleModelResponse;
import com.rcbg.afku.investmentdiary.common.statuses.ResourceDeletedStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/${api.version}/accounts")
public class BrokerAccountsController {
    private final BrokerAccountManagementService managementService;
    private final BrokerAccountBrowseService browseService;

    @Autowired
    public BrokerAccountsController(BrokerAccountManagementService managementService, BrokerAccountBrowseService browseService){
        this.managementService = managementService;
        this.browseService = browseService;
    }

    @PostMapping
    ResponseEntity<CommonSingleModelResponse<BrokerAccountDTO>> createBrokerAccount(HttpServletRequest request, @RequestBody BrokerAccountDTO requestDto){
        BrokerAccountDTO responseDto = managementService.createBrokerAccount(requestDto);
        CommonSingleModelResponse<BrokerAccountDTO> response = new CommonSingleModelResponse<>(200, request.getRequestURI(), "object",  responseDto);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<CommonSingleModelResponse<BrokerAccountDTO>> getBrokerAccountById(HttpServletRequest request, @PathVariable int id){
        BrokerAccountDTO responseDto = browseService.findOneBrokerAccountById(id);
        CommonSingleModelResponse<BrokerAccountDTO> response = new CommonSingleModelResponse<>(200, request.getRequestURI(), "object",  responseDto);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<CommonSingleModelResponse<BrokerAccountDTO>> updateBrokerAccountById(HttpServletRequest request, @PathVariable int id, @RequestBody BrokerAccountDTO requestDto){
        BrokerAccountDTO responseDto = managementService.updateAccount(id, requestDto);
        CommonSingleModelResponse<BrokerAccountDTO> response = new CommonSingleModelResponse<>(200, request.getRequestURI(), "object",  responseDto);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<CommonResourceDeletedResponse> deleteBrokerAccountById(HttpServletRequest request, @PathVariable int id){
        managementService.deleteBrokerAccount(id);
        ResourceDeletedStatus<Integer> status = new ResourceDeletedStatus<>(id, true, "brokerAccount");
        CommonResourceDeletedResponse response = new CommonResourceDeletedResponse(200, request.getRequestURI(), "object", status);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @GetMapping
    ResponseEntity<CommonModelPaginationResponse> getAllBrokerAccounts(HttpServletRequest request, Pageable pageable){
        CommonPaginationDTO paginationDTO = browseService.findAllBrokerAccounts(pageable);
        CommonModelPaginationResponse response = new CommonModelPaginationResponse(200, request.getRequestURI(), "list", paginationDTO);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }
}