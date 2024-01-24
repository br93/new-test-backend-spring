package com.br93.testbackend.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.br93.testbackend.exception.BucketNotFoundException;
import com.br93.testbackend.exception.CategoryInvalidException;
import com.br93.testbackend.exception.CategoryNotFoundException;
import com.br93.testbackend.exception.ConversionException;
import com.br93.testbackend.exception.ObjectException;
import com.br93.testbackend.exception.ProductInvalidException;
import com.br93.testbackend.exception.ProductNotFoundException;

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

    @ExceptionHandler({ ProductNotFoundException.class })
    public ProblemDetail handleProductNotFoundException(ProductNotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({ConversionException.class})
    public ProblemDetail handleConversionException(ConversionException exception){
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler({ObjectException.class})
    public ProblemDetail handleObjectException(ObjectException exception){
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler({BucketNotFoundException.class})
    public ProblemDetail handleBucketNotFoundException(BucketNotFoundException exception){
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }
}
