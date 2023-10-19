package com.pblgllgs.testingsb3.exception;

public class EmployeeNotExistsException extends RuntimeException{
    public EmployeeNotExistsException(String message) {
        super(message);
    }

    public EmployeeNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
