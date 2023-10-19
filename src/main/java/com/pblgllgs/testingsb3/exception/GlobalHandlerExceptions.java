package com.pblgllgs.testingsb3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalHandlerExceptions {

    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handlerEmployeeAlreadyExistsException(EmployeeAlreadyExistsException ex) {
        return new ResponseEntity<>(new ErrorDTO(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(EmployeeNotExistsException.class)
    public ResponseEntity<ErrorDTO> handlerEmployeeAlreadyExistsException(EmployeeNotExistsException ex) {
        return new ResponseEntity<>(new ErrorDTO(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()),
                HttpStatus.NOT_FOUND
        );
    }
}
