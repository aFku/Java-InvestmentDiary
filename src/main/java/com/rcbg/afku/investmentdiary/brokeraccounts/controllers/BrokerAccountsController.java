package com.rcbg.afku.investmentdiary.brokeraccounts.controllers;

import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.BrokerAccountDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.DateRangeParam;
import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.StatisticsDTO;
import com.rcbg.afku.investmentdiary.brokeraccounts.entities.BrokerAccount;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.BrokerAccountBrowseService;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.BrokerAccountManagementService;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.StatisticsService;
import com.rcbg.afku.investmentdiary.brokeraccounts.services.WalletService;
import com.rcbg.afku.investmentdiary.common.datatransferobjects.CommonPaginationDTO;
import com.rcbg.afku.investmentdiary.common.responses.CommonModelPaginationResponse;
import com.rcbg.afku.investmentdiary.common.responses.CommonResourceDeletedResponse;
import com.rcbg.afku.investmentdiary.common.responses.CommonSingleModelResponse;
import com.rcbg.afku.investmentdiary.common.search.SearchOperations;
import com.rcbg.afku.investmentdiary.common.search.SpecificationsBuilder;
import com.rcbg.afku.investmentdiary.common.statuses.ResourceDeletedStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/${api.version}/accounts")
public class BrokerAccountsController {
    private final BrokerAccountManagementService managementService;
    private final BrokerAccountBrowseService browseService;
    private final WalletService walletService;
    private final StatisticsService statisticsService;

    @Autowired
    public BrokerAccountsController(BrokerAccountManagementService managementService, BrokerAccountBrowseService browseService,
                                    WalletService walletService, StatisticsService statisticsService){
        this.managementService = managementService;
        this.browseService = browseService;
        this.walletService = walletService;
        this.statisticsService = statisticsService;
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
    ResponseEntity<CommonModelPaginationResponse> getAllBrokerAccounts(HttpServletRequest request, Pageable pageable, @RequestParam(value = "search", required = false) String search){
        CommonPaginationDTO paginationDTO;
        if(search != null){
            SpecificationsBuilder<BrokerAccount> builder = new SpecificationsBuilder<>();
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>|!|~)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1),
                        matcher.group(2), matcher.group(3));
            }
            Specification<BrokerAccount> spec = builder.build();
            paginationDTO = browseService.findAllBrokerAccountsBySpecification(pageable, spec);
        } else {
            paginationDTO = browseService.findAllBrokerAccounts(pageable);
        }
        CommonModelPaginationResponse response = new CommonModelPaginationResponse(200, request.getRequestURI(), "list", paginationDTO);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @GetMapping(value = "/{id}/wallet")
    ResponseEntity<CommonModelPaginationResponse> getWalletByAccountId(HttpServletRequest request, @PathVariable int id, Pageable pageable){
        CommonPaginationDTO paginationDTO = walletService.getWalletByBrokerAccountId(id, pageable);
        CommonModelPaginationResponse response = new CommonModelPaginationResponse(200, request.getRequestURI(), "list", paginationDTO);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @GetMapping(value = "/wallet")
    ResponseEntity<CommonModelPaginationResponse> getWalletFromAllBrokerAccounts(HttpServletRequest request, Pageable pageable){
        CommonPaginationDTO paginationDTO = walletService.getWalletFromAllBrokerAccounts(pageable);
        CommonModelPaginationResponse response = new CommonModelPaginationResponse(200, request.getRequestURI(), "list", paginationDTO);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @GetMapping(value = "/statistics")
    ResponseEntity<CommonSingleModelResponse<StatisticsDTO>> getStatsFromAllAccounts(HttpServletRequest request, @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam("stopDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date stopDate){
        DateRangeParam dateRange = new DateRangeParam();
        dateRange.setStartDate(startDate); dateRange.setStopDate(stopDate);
        StatisticsDTO statisticsDTO = statisticsService.getStats(dateRange);
        CommonSingleModelResponse<StatisticsDTO> response = new CommonSingleModelResponse<>(200, request.getRequestURI(), "object",  statisticsDTO);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @GetMapping(value = "/{id}/statistics")
    ResponseEntity<CommonSingleModelResponse<StatisticsDTO>> getStatsByAccountId(HttpServletRequest request, @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd")Date startDate, @RequestParam("stopDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date stopDate, @PathVariable int id){
        DateRangeParam dateRange = new DateRangeParam();
        dateRange.setStartDate(startDate); dateRange.setStopDate(stopDate);
        StatisticsDTO statisticsDTO = statisticsService.getStats(id, dateRange);
        CommonSingleModelResponse<StatisticsDTO> response = new CommonSingleModelResponse<>(200, request.getRequestURI(), "object",  statisticsDTO);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

}
