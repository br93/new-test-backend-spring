package com.br93.testbackend.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.br93.testbackend.exception.CategoryInvalidException;
import com.br93.testbackend.exception.CategoryNotFoundException;
import com.br93.testbackend.exception.ProductInvalidException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ CategoryInvalidException.class })
    public ProblemDetail handleCategoryInvalidException(CategoryInvalidException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler({ ProductInvalidException.class })
    public ProblemDetail handleProductInvalidException(ProductInvalidException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler({ CategoryNotFoundException.class })
    public ProblemDetail handleCategoryNotFoundException(CategoryNotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException exception){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
}