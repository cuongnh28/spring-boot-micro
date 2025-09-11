package com.demo.exception;

import com.demo.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public class FormValidateException extends ApplicationException {

    public FormValidateException(String... messages) {
        super(messages);
    }

    public FormValidateException(ErrorCode... errors) {
        super(errors);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
