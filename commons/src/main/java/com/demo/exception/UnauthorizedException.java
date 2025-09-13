package com.demo.exception;

import com.demo.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApplicationException {

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(ErrorCode error) {
        super(error);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
