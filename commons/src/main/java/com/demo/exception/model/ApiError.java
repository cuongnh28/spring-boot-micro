package com.demo.exception.model;

import java.util.List;

public class ApiError {
    private final int statusCode;
    private final List<String> messages;

    public ApiError(int statusCode, List<String> messages) {
        this.statusCode = statusCode;
        this.messages = messages;
    }

    public ApiError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.messages = List.of(message);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public List<String> getMessages() {
        return messages;
    }
}