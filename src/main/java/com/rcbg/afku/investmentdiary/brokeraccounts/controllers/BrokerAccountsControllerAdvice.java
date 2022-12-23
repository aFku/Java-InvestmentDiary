package com.rcbg.afku.investmentdiary.brokeraccounts.controllers;

import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.*;
import com.rcbg.afku.investmentdiary.common.controllers.BaseControllerAdvice;
import com.rcbg.afku.investmentdiary.common.responses.BaseApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class BrokerAccountsControllerAdvice extends BaseControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(BrokerAccountsControllerAdvice.class);

    @ExceptionHandler(BrokerAccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<BaseApiErrorResponse> handleBrokerAccountNotFound(BrokerAccountsBaseRuntimeException ex, HttpServletRequest request){
        BaseApiErrorResponse response = processErrorToResponse(logger, ex, request, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}