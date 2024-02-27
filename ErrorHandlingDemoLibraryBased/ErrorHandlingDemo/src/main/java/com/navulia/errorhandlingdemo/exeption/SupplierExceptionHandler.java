package com.navulia.errorhandlingdemo.exeption;

import com.navulia.genericerrorhandler.dto.ErrorResponseDto;
import com.navulia.genericerrorhandler.exception.GlobalExceptionHandler;
import com.navulia.genericerrorhandler.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
@Order(1)
public class SupplierExceptionHandler extends GlobalExceptionHandler {

    //Domain specific exception handler for SupplierAlreadyExistsException
    @ExceptionHandler(value=SupplierAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleSupplierAlreadyExistsException(SupplierAlreadyExistsException ex){
        log.error(HttpStatus.CONFLICT + ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDto(HttpStatus.CONFLICT.value(),
                ex.getMessage()));
    }

    //Override ResourceNotFoundException & provide custom error
    @Override
    @ExceptionHandler(value= ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex){
        log.error(HttpStatus.NOT_FOUND + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(HttpStatus.NOT_FOUND.value(),
                "Supplier Not Found"));
    }

}
