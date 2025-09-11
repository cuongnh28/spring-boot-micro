package com.demo.exception.model;

import java.util.List;

public class ApiError {
    private int statusCode;
    private List<String> messages;
    private String errorCode;

    public ApiError(int statusCode, List<String> messages, String errorCode){
        this.statusCode = statusCode;
        this.messages = messages;
        this.errorCode = errorCode;
    }

    public ApiError(int statusCode, List<String> messages){
        this.statusCode = statusCode;
        this.messages = messages;
    }

    public ApiError(int statusCode, String message, String errorCode){
        this.statusCode = statusCode;
        this.messages = List.of(message);
        this.errorCode = errorCode;
    }

    public ApiError(int statusCode, String message){
        this.statusCode = statusCode;
        this.messages = List.of(message);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
