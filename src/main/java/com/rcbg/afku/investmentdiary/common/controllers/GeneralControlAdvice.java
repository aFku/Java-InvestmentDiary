package com.rcbg.afku.investmentdiary.common.controllers;

import com.rcbg.afku.investmentdiary.brokeraccounts.controllers.BrokerAccountControllerAdvisor;
import com.rcbg.afku.investmentdiary.brokeraccounts.exceptions.BrokerAccountsBaseRuntimeException;
import com.rcbg.afku.investmentdiary.common.exceptions.PaginationPageNotFoundException;
import com.rcbg.afku.investmentdiary.common.responses.BaseApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GeneralControlAdvice extends BaseControlAdvice {

    private static final Logger logger = LoggerFactory.getLogger(GeneralControlAdvice.class);

    @ExceptionHandler({IllegalArgumentException.class, PropertyReferenceException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseApiErrorResponse> handleBadRequest(RuntimeException ex, HttpServletRequest request){
        BaseApiErrorResponse response = processErrorToResponse(logger, ex, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<BaseApiErrorResponse>(response, new HttpHeaders(), 400);
    }

    @ExceptionHandler({PaginationPageNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<BaseApiErrorResponse> handleNotFound(RuntimeException ex, HttpServletRequest request){
        BaseApiErrorResponse response = processErrorToResponse(logger, ex, request, HttpStatus.NOT_FOUND);
        return new ResponseEntity<BaseApiErrorResponse>(response, new HttpHeaders(), 404);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseApiErrorResponse> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request){
        List<String> constraintViolationMessages = new ArrayList<>();
        for(ConstraintViolation<?> violation : ex.getConstraintViolations()){
            String[] fields = violation.getPropertyPath().toString().split("\\.");
            String fieldName = fields[fields.length-1];
            constraintViolationMessages.add(fieldName + " : " + violation.getMessage());
        }
        BaseApiErrorResponse response = processMultipleErrorsToResponse(logger, constraintViolationMessages, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<BaseApiErrorResponse>(response, new HttpHeaders(), 400);
    }
}
