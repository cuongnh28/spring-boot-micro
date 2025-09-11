package com.demo.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class HttpException extends ApplicationException {
    private final HttpStatus httpStatus;

    public HttpException(HttpStatus httpStatus, List<String> messages, String errorCode) {
        this.httpStatus = httpStatus;
        this.setMessages(messages);
        this.setErrorCode(errorCode);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
