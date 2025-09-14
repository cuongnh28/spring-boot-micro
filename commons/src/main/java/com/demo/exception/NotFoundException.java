package com.demo.exception;

import org.springframework.http.HttpStatus;


public class NotFoundException extends ApplicationException {
    public NotFoundException() {
        super("Object not found");
    }
    public NotFoundException(String message) {
        super(message);
    }


    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
