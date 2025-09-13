package com.demo.exception;

import com.demo.exception.model.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public abstract class ApplicationException extends RuntimeException {
    public abstract HttpStatus getHttpStatus();

    private final List<String> messages;
    private final Map<ErrorCode, String> error;

    protected ApplicationException() {
        this.messages = null;
        this.error = null;
    }

    protected ApplicationException(String... messages) {
        this.messages = Arrays.asList(messages);
        this.error = null;
    }

    protected ApplicationException(ErrorCode... errorCodes) {
        this.messages = null;
        this.error = Arrays.stream(errorCodes).collect(Collectors.toMap(e -> e, e -> ""));
    }

    protected ApplicationException(Map<ErrorCode, String> errorMap) {
        this.messages = null;
        this.error = errorMap;
    }
}
