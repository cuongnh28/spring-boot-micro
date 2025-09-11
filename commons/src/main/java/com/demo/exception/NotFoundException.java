package com.demo.exception;

import com.demo.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class NotFoundException extends ApplicationException {
    public NotFoundException() {
        super("Object not found");
    }
    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(ErrorCode... error) {
        super(error);
    }

    public NotFoundException(Map<ErrorCode, String> errorMap){
        super(errorMap);
    }
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
