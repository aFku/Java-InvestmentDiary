package com.rcbg.afku.investmentdiary.common.controllers;

import com.rcbg.afku.investmentdiary.common.exceptions.ResourcePageNotFoundException;
import com.rcbg.afku.investmentdiary.common.responses.BaseErrorResponse;
import com.rcbg.afku.investmentdiary.common.responses.ResourceNotFoundResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class CommonErrorsControllerAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(CommonErrorsControllerAdvisor.class);

    @ExceptionHandler(ResourcePageNotFoundException.class)
    public ResponseEntity<BaseErrorResponse> handleResourcePageException(ResourcePageNotFoundException ex, HttpServletRequest request){
        logger.error(String.format("%s | %s, resourceType: %s, code: %d", request.getRequestURI(), ex.getMessage(), ex.getResourceType(), ex.getCode()));
        BaseErrorResponse response = new ResourceNotFoundResponse(request.getRequestURI(), ex.getMessage(), ex.getCode(), ex.getResourceType());
        return new ResponseEntity<BaseErrorResponse>(response, new HttpHeaders(), ex.getCode());
    }
}
