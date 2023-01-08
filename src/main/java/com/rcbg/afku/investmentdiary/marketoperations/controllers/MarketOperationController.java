package com.rcbg.afku.investmentdiary.marketoperations.controllers;

import com.rcbg.afku.investmentdiary.common.search.SpecificationsBuilder;
import com.rcbg.afku.investmentdiary.marketoperations.datatransferobjects.MarketOperationDTO;
import com.rcbg.afku.investmentdiary.marketoperations.entities.MarketOperation;
import com.rcbg.afku.investmentdiary.marketoperations.services.MarketOperationManagementService;
import com.rcbg.afku.investmentdiary.common.datatransferobjects.CommonPaginationDTO;
import com.rcbg.afku.investmentdiary.common.responses.CommonModelPaginationResponse;
import com.rcbg.afku.investmentdiary.common.responses.CommonResourceDeletedResponse;
import com.rcbg.afku.investmentdiary.common.responses.CommonSingleModelResponse;
import com.rcbg.afku.investmentdiary.common.statuses.ResourceDeletedStatus;
import com.rcbg.afku.investmentdiary.marketoperations.services.MarketOperationBrowseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/${api.version}/operations")
public class MarketOperationController {

    private final MarketOperationManagementService managementService;
    private final MarketOperationBrowseService browseService;

    @Autowired
    public MarketOperationController(MarketOperationManagementService managementService, MarketOperationBrowseService browseService) {
        this.managementService = managementService;
        this.browseService = browseService;
    }

    @PostMapping
    ResponseEntity<CommonSingleModelResponse<MarketOperationDTO>> createMarketOperation(HttpServletRequest request, @RequestBody MarketOperationDTO requestDto){
        MarketOperationDTO responseDto = managementService.createMarketOperation(requestDto);
        CommonSingleModelResponse<MarketOperationDTO> response = new CommonSingleModelResponse<MarketOperationDTO>(200, request.getRequestURI(), "object",  responseDto);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @GetMapping
    ResponseEntity<CommonModelPaginationResponse> getAllMarketOperations(HttpServletRequest request, Pageable pageable, @RequestParam(value = "search", required = false) String search){
        CommonPaginationDTO paginationDTO;
        if(search != null) {
            SpecificationsBuilder<MarketOperation> builder = new SpecificationsBuilder<>();
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>|!|~)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
            Specification<MarketOperation> spec = builder.build();
            paginationDTO = browseService.findAllMarketOperationsBySpecification(pageable, spec);
        } else {
            paginationDTO = browseService.findAllMarketOperations(pageable);
        }
        CommonModelPaginationResponse response = new CommonModelPaginationResponse(200, request.getRequestURI(), "list", paginationDTO);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @GetMapping("/{id}")
    ResponseEntity<CommonSingleModelResponse<MarketOperationDTO>> getMarketOperationById(HttpServletRequest request, @PathVariable long id){
        MarketOperationDTO responseDto = browseService.findOneMarketOperationById(id);
        CommonSingleModelResponse<MarketOperationDTO> response = new CommonSingleModelResponse<>(200, request.getRequestURI(), "object", responseDto);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<CommonResourceDeletedResponse> deleteMarketOperation(HttpServletRequest request, @PathVariable long id){
        managementService.deleteMarketOperation(id);
        ResourceDeletedStatus<Long> status = new ResourceDeletedStatus<>(id, true, "marketOperation");
        CommonResourceDeletedResponse response = new CommonResourceDeletedResponse(200, request.getRequestURI(), "object", status);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }
}
