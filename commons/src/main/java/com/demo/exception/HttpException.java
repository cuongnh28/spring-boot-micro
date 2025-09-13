package com.demo.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class HttpException extends ApplicationException {
    private final HttpStatus httpStatus;

    public HttpException(HttpStatus httpStatus, List<String> messages) {
        super(messages.toArray(new String[0]));
        this.httpStatus = httpStatus;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
