package com.rcbg.afku.investmentdiary.subjects.controllers;
import com.rcbg.afku.investmentdiary.brokeraccounts.datatransferobjects.ResponseAccountDTO;
import com.rcbg.afku.investmentdiary.common.responses.CommonModelPaginationResponse;
import com.rcbg.afku.investmentdiary.common.responses.CommonResourceDeletedResponse;
import com.rcbg.afku.investmentdiary.common.responses.CommonSingleModelResponse;
import com.rcbg.afku.investmentdiary.common.statuses.ResourceDeletedStatus;
import com.rcbg.afku.investmentdiary.subjects.datatransferobjects.PaginationStockMarketSubjectDTO;
import com.rcbg.afku.investmentdiary.subjects.datatransferobjects.StockMarketSubjectDTO;
import com.rcbg.afku.investmentdiary.subjects.services.StockMarketSubjectBrowseService;
import com.rcbg.afku.investmentdiary.subjects.services.StockMarketSubjectManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/${api.version}/subjects")
public class SubjectController {

    private static final Logger logger = LoggerFactory.getLogger(SubjectController.class);
    private final StockMarketSubjectManagementService managementService;
    private final StockMarketSubjectBrowseService browseService;

    @Autowired
    public SubjectController(StockMarketSubjectManagementService managementService, StockMarketSubjectBrowseService browseService) {
        this.managementService = managementService;
        this.browseService = browseService;
    }

    @PostMapping
    ResponseEntity<CommonSingleModelResponse<StockMarketSubjectDTO>> createSubject(HttpServletRequest request, @RequestBody StockMarketSubjectDTO requestDto){
        StockMarketSubjectDTO stockMarketSubjectDTO = managementService.createStockMarketSubject(requestDto);
        CommonSingleModelResponse<StockMarketSubjectDTO> response = new CommonSingleModelResponse<StockMarketSubjectDTO>(200, request.getRequestURI(), "object",  stockMarketSubjectDTO);
        return new ResponseEntity<CommonSingleModelResponse<StockMarketSubjectDTO>>(response, new HttpHeaders(), 200);
    }

    @GetMapping
    ResponseEntity<CommonModelPaginationResponse<StockMarketSubjectDTO>> getAllSubjects(HttpServletRequest request,
                                     @RequestParam(value = "page", required = false, defaultValue = "${pagination.default-page-index}") int page,
                                     @RequestParam(value = "size", required = false, defaultValue = "${pagination.default-page-size}") int size,
                                     @RequestParam(value = "sort", required = false, defaultValue = "id") String sort){
        PaginationStockMarketSubjectDTO stockMarketSubjectDTO = browseService.getAllStockMarketSubjects(page, size, sort);
        CommonModelPaginationResponse<StockMarketSubjectDTO> response = new CommonModelPaginationResponse<>(200, request.getRequestURI(), "list", stockMarketSubjectDTO);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @GetMapping("/{id}")
    ResponseEntity<CommonSingleModelResponse<StockMarketSubjectDTO>> getSubjectById(HttpServletRequest request, @PathVariable int id){
        StockMarketSubjectDTO subjectDto = browseService.getStockMarketSubjectById(id);
        CommonSingleModelResponse<StockMarketSubjectDTO> response = new CommonSingleModelResponse<>(200, request.getRequestURI(), "object", subjectDto);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @PutMapping("/{id}")
    ResponseEntity<CommonSingleModelResponse<StockMarketSubjectDTO>> updateSubjectById(HttpServletRequest request, @PathVariable int id, @RequestBody StockMarketSubjectDTO requestDto){
        StockMarketSubjectDTO responseDto = managementService.updateStockMarketSubjectById(id, requestDto);
        CommonSingleModelResponse<StockMarketSubjectDTO> response = new CommonSingleModelResponse<>(200, request.getRequestURI(), "object", responseDto);
        return new ResponseEntity<CommonSingleModelResponse<StockMarketSubjectDTO>>(response, new HttpHeaders(), 200);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<CommonResourceDeletedResponse> deleteAccountById(HttpServletRequest request, @PathVariable int id){
        boolean deleted = managementService.deleteStockMarketSubject(id);
        ResourceDeletedStatus<Integer> status = new ResourceDeletedStatus<Integer>(id, deleted, "stockmarketsubject");
        CommonResourceDeletedResponse response = new CommonResourceDeletedResponse(200, request.getRequestURI(), "object", status);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

}
