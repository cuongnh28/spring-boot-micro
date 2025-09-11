package com.demo.exception.model;

import java.util.List;

public class ApiError {
    private int statusCode;
    private List<String> messages;
    private String errorCode;

    public ApiError() {
    }

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

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
