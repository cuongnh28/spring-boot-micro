package com.demo.exception.model;

import lombok.Getter;

import java.util.List;

@Getter
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

}