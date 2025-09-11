package com.demo.exception;

import com.demo.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public class UnprocessableEntityException extends ApplicationException {

    public UnprocessableEntityException(String... messages) {
        super(messages);
    }

    public UnprocessableEntityException(ErrorCode... errors) {
        super(errors);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
