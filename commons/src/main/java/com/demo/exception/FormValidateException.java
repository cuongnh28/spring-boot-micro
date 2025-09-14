package com.demo.exception;

import org.springframework.http.HttpStatus;

public class FormValidateException extends ApplicationException {

    public FormValidateException() {
        super();
    }

    public FormValidateException(String message) {
        super(message);
    }


    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
