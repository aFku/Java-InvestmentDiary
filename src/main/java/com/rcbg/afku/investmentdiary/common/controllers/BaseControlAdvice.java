package com.rcbg.afku.investmentdiary.common.controllers;

import com.rcbg.afku.investmentdiary.common.exceptions.InvestmentDiaryBaseException;
import com.rcbg.afku.investmentdiary.common.responses.BaseApiErrorResponse;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class BaseControlAdvice extends ResponseEntityExceptionHandler {

    protected BaseApiErrorResponse processErrorToResponse(Logger logger, RuntimeException ex, HttpServletRequest request, HttpStatus code){
        logger.error(String.format("%s | %s, code: %d", request.getRequestURI(), ex.getMessage(), code.value()));
        return new BaseApiErrorResponse(code.value(), request.getRequestURI(), ex.getMessage());
    }

    protected BaseApiErrorResponse processMultipleErrorsToResponse(Logger logger, List<String> messages, HttpServletRequest request, HttpStatus code){
        logger.error(String.format("%s | %s, code: %d", request.getRequestURI(), messages, code.value()));
        return new BaseApiErrorResponse(code.value(), request.getRequestURI(), messages);
    }
}
