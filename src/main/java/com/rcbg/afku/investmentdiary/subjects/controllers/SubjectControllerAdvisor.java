package com.rcbg.afku.investmentdiary.subjects.controllers;

import com.rcbg.afku.investmentdiary.common.controllers.BaseControlAdvice;
import com.rcbg.afku.investmentdiary.common.responses.BaseApiErrorResponse;
import com.rcbg.afku.investmentdiary.subjects.exceptions.StockMarketSubjectBaseException;
import com.rcbg.afku.investmentdiary.subjects.exceptions.StockMarketSubjectNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class SubjectControllerAdvisor extends BaseControlAdvice {
    private static final Logger logger = LoggerFactory.getLogger(SubjectControllerAdvisor.class);

    @ExceptionHandler(StockMarketSubjectNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<BaseApiErrorResponse> handleSubjectNotFound(StockMarketSubjectBaseException ex, HttpServletRequest request){
        BaseApiErrorResponse response = processErrorToResponse(logger, ex, request, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
