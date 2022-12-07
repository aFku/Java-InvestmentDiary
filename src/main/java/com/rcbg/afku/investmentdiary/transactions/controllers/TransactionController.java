package com.rcbg.afku.investmentdiary.transactions.controllers;

import com.rcbg.afku.investmentdiary.common.responses.CommonModelPaginationResponse;
import com.rcbg.afku.investmentdiary.common.responses.CommonResourceDeletedResponse;
import com.rcbg.afku.investmentdiary.common.responses.CommonSingleModelResponse;
import com.rcbg.afku.investmentdiary.common.statuses.ResourceDeletedStatus;
import com.rcbg.afku.investmentdiary.transactions.datatransferobjects.PaginationStockMarketTransactionDTO;
import com.rcbg.afku.investmentdiary.transactions.datatransferobjects.StockMarketTransactionDTO;
import com.rcbg.afku.investmentdiary.transactions.services.TransactionBrowseService;
import com.rcbg.afku.investmentdiary.transactions.services.TransactionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/${api.version}/transactions")
public class TransactionController {

    private final TransactionManagementService managementService;
    private final TransactionBrowseService browseService;

    @Autowired
    public TransactionController(TransactionManagementService managementService, TransactionBrowseService browseService) {
        this.managementService = managementService;
        this.browseService = browseService;
    }

    @PostMapping
    ResponseEntity<CommonSingleModelResponse<StockMarketTransactionDTO>> createTransaction(HttpServletRequest request, @RequestBody StockMarketTransactionDTO requestDto){
        StockMarketTransactionDTO stockMarketTransactionDTO = managementService.createTransaction(requestDto);
        CommonSingleModelResponse<StockMarketTransactionDTO> response = new CommonSingleModelResponse<StockMarketTransactionDTO>(200, request.getRequestURI(), "object",  stockMarketTransactionDTO);
        return new ResponseEntity<CommonSingleModelResponse<StockMarketTransactionDTO>>(response, new HttpHeaders(), 200);
    }

    @GetMapping
    ResponseEntity<CommonModelPaginationResponse<PaginationStockMarketTransactionDTO>> getAllTransactions(HttpServletRequest request,
                                          @RequestParam(value = "page", required = false, defaultValue = "${pagination.default-page-index}") int page,
                                          @RequestParam(value = "size", required = false, defaultValue = "${pagination.default-page-size}") int size,
                                          @RequestParam(value = "sort", required = false, defaultValue = "id") String sort){
        PaginationStockMarketTransactionDTO paginationDto = browseService.findAllTransaction(page, size, sort);
        CommonModelPaginationResponse<PaginationStockMarketTransactionDTO> response = new CommonModelPaginationResponse<>(200, request.getRequestURI(), "list", paginationDto);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @GetMapping("/{id}")
    ResponseEntity<CommonSingleModelResponse<StockMarketTransactionDTO>> getTransactionById(HttpServletRequest request, @PathVariable long id){
        StockMarketTransactionDTO transactionDTO = browseService.findOneTransactionById(id);
        CommonSingleModelResponse<StockMarketTransactionDTO> response = new CommonSingleModelResponse<>(200, request.getRequestURI(), "object", transactionDTO);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<CommonResourceDeletedResponse> deleteTransaction(HttpServletRequest request, @PathVariable long id){
        boolean deleted = managementService.deleteTransaction(id);
        ResourceDeletedStatus<Long> status = new ResourceDeletedStatus<>(id, deleted, "transaction");
        CommonResourceDeletedResponse response = new CommonResourceDeletedResponse(200, request.getRequestURI(), "object", status);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }
}
