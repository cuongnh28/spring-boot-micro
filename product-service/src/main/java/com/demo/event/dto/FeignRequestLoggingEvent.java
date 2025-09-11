package com.demo.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class FeignRequestLoggingEvent {
    private String method;
    private String url;
    private String query;
    private Integer status;
    private Long requestTime;
    private String requestHeader;
    private String payload;
    private String responseHeader;
    private String response;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date createdDate;
    private String requestId;

    public FeignRequestLoggingEvent(String method, String url, String query, Integer status, Long requestTime
            , String requestHeader, String responseHeader, String requestId) {
        this.method = method;
        this.url = url;
        this.query = query;
        this.status = status;
        this.requestTime = requestTime;
        this.requestHeader = requestHeader;
        this.responseHeader = responseHeader;
        this.requestId = requestId;
        this.createdDate = new Date();
    }

    public void setPayload(byte[] requestBody){
        this.payload = requestBody == null ? null : URLDecoder.decode(new String(requestBody, StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

    public void setResponse(byte[] responseBody){
        this.response = responseBody == null ? null : new String(responseBody, StandardCharsets.UTF_8);
    }
}