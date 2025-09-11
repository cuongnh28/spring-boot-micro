package com.demo.exception;

import com.demo.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends ApplicationException {

    public ForbiddenException(String... messages) {
        super(messages);
    }

    public ForbiddenException(ErrorCode... errors) {
        super(errors);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
