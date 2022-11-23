package com.rcbg.afku.investmentdiary.brokeraccounts.controllers;

import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.*;
import com.rcbg.afku.investmentdiary.common.controllers.BaseControlAdvice;
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
public class BrokerAccountControllerAdvisor extends BaseControlAdvice {

    private static final Logger logger = LoggerFactory.getLogger(BrokerAccountControllerAdvisor.class);

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<BaseApiErrorResponse> handleAccountNotFound(BrokerAccountsBaseRuntimeException ex, HttpServletRequest request){
        BaseApiErrorResponse response = processErrorToResponse(logger, ex, request, HttpStatus.NOT_FOUND);
        return new ResponseEntity<BaseApiErrorResponse>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({BrokerAccountsBaseRuntimeException.class, AccountSearchException.class, AccountCreationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseApiErrorResponse> handleAccountBadRequest(BrokerAccountsBaseRuntimeException ex, HttpServletRequest request){
        BaseApiErrorResponse response = processErrorToResponse(logger, ex, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<BaseApiErrorResponse>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccountManagementException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BaseApiErrorResponse> handleAccountInternalError(BrokerAccountsBaseRuntimeException ex, HttpServletRequest request) {
        BaseApiErrorResponse response = processErrorToResponse(logger, ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<BaseApiErrorResponse>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}