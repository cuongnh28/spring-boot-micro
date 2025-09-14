package com.demo.exception;

import org.springframework.http.HttpStatus;

public class UnprocessableEntityException extends ApplicationException {

    public UnprocessableEntityException() {
        super();
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }


    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
