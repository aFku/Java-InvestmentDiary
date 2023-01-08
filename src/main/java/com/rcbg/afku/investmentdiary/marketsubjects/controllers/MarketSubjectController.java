package com.rcbg.afku.investmentdiary.marketsubjects.controllers;
import com.rcbg.afku.investmentdiary.common.datatransferobjects.CommonPaginationDTO;
import com.rcbg.afku.investmentdiary.common.responses.CommonModelPaginationResponse;
import com.rcbg.afku.investmentdiary.common.responses.CommonResourceDeletedResponse;
import com.rcbg.afku.investmentdiary.common.responses.CommonSingleModelResponse;
import com.rcbg.afku.investmentdiary.common.search.SpecificationsBuilder;
import com.rcbg.afku.investmentdiary.common.statuses.ResourceDeletedStatus;
import com.rcbg.afku.investmentdiary.marketsubjects.datatransferobjects.MarketSubjectDTO;
import com.rcbg.afku.investmentdiary.marketsubjects.entities.MarketSubject;
import com.rcbg.afku.investmentdiary.marketsubjects.services.MarketSubjectBrowseService;
import com.rcbg.afku.investmentdiary.marketsubjects.services.MarketSubjectManagementService;
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
@RequestMapping("/${api.version}/subjects")
public class MarketSubjectController {

    private final MarketSubjectManagementService managementService;
    private final MarketSubjectBrowseService browseService;

    @Autowired
    public MarketSubjectController(MarketSubjectManagementService managementService, MarketSubjectBrowseService browseService) {
        this.managementService = managementService;
        this.browseService = browseService;
    }

    @PostMapping
    ResponseEntity<CommonSingleModelResponse<MarketSubjectDTO>> createMarketSubject(HttpServletRequest request, @RequestBody MarketSubjectDTO requestDto){
        MarketSubjectDTO responseDto = managementService.createMarketSubject(requestDto);
        CommonSingleModelResponse<MarketSubjectDTO> response = new CommonSingleModelResponse<MarketSubjectDTO>(200, request.getRequestURI(), "object",  responseDto);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @GetMapping
    ResponseEntity<CommonModelPaginationResponse> getAllMarketSubjects(HttpServletRequest request, Pageable pageable, @RequestParam(value = "search", required = false) String search){
        CommonPaginationDTO paginationDTO;
        if(search != null) {
            SpecificationsBuilder<MarketSubject> builder = new SpecificationsBuilder<>();
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>|!|~)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()){
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
            Specification<MarketSubject> spec = builder.build();
            paginationDTO = browseService.getMarketSubjectsBySpecification(pageable, spec);
        } else {
            paginationDTO = browseService.getAllMarketSubjects(pageable);
        }
        CommonModelPaginationResponse response = new CommonModelPaginationResponse(200, request.getRequestURI(), "list", paginationDTO);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @GetMapping("/{id}")
    ResponseEntity<CommonSingleModelResponse<MarketSubjectDTO>> getMarketSubjectById(HttpServletRequest request, @PathVariable int id){
        MarketSubjectDTO responseDto = browseService.getMarketSubjectById(id);
        CommonSingleModelResponse<MarketSubjectDTO> response = new CommonSingleModelResponse<>(200, request.getRequestURI(), "object", responseDto);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @PutMapping("/{id}")
    ResponseEntity<CommonSingleModelResponse<MarketSubjectDTO>> updateMarketSubjectById(HttpServletRequest request, @PathVariable int id, @RequestBody MarketSubjectDTO requestDto){
        MarketSubjectDTO responseDto = managementService.updateMarketSubjectById(id, requestDto);
        CommonSingleModelResponse<MarketSubjectDTO> response = new CommonSingleModelResponse<>(200, request.getRequestURI(), "object", responseDto);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<CommonResourceDeletedResponse> deleteMarketSubjectById(HttpServletRequest request, @PathVariable int id){
        managementService.deleteMarketSubject(id);
        ResourceDeletedStatus<Integer> status = new ResourceDeletedStatus<Integer>(id, true, "marketSubject");
        CommonResourceDeletedResponse response = new CommonResourceDeletedResponse(200, request.getRequestURI(), "object", status);
        return new ResponseEntity<>(response, new HttpHeaders(), 200);
    }

}
