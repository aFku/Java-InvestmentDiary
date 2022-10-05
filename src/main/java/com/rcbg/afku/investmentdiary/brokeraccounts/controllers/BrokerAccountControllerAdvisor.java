package com.rcbg.afku.investmentdiary.brokeraccounts.controllers;

import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class BrokerAccountControllerAdvisor extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(BrokerAccountControllerAdvisor.class);

    @ExceptionHandler(BrokerAccountsBaseRuntimeException.class)
    public ResponseEntity<Object> handleAccountBadRequest(BrokerAccountsBaseRuntimeException ex, HttpServletRequest request){
        logger.error(String.format("%s | %s, code: %d", request.getRequestURI(), ex.getMessage(), ex.getCode()));
        ErrorResponse response = new ErrorResponse(ex.getMessage(), ex.getCode());
        return new ResponseEntity<Object>(response, new HttpHeaders(), ex.getCode());
    }
}