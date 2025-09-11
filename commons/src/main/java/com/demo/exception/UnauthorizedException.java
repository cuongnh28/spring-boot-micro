package com.demo.exception;

import com.demo.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public class UnauthorizedException  extends ApplicationException {

    public UnauthorizedException() {

    }
    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, String errorCode) {
        super(message);
        this.setErrorCode(errorCode);
    }

    public UnauthorizedException(ErrorCode error) {
        super(error);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
