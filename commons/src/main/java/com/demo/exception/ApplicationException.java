package com.demo.exception;

import com.demo.exception.model.ErrorCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class ApplicationException extends RuntimeException {
    public abstract HttpStatus getHttpStatus();

    private List<String> messages;

    private String errorCode;

    private Map<ErrorCode, String> error;

    protected ApplicationException(){
    }
    protected ApplicationException(String... messages) {
        this.messages = Arrays.asList(messages);
    }

    protected ApplicationException(ErrorCode... errorCodes){
        this.error = Arrays.stream(errorCodes).collect(Collectors.toMap(e -> e, e -> ""));
    }

    protected ApplicationException(Map<ErrorCode, String> errorMap){
        this.error = errorMap;
    }
}
