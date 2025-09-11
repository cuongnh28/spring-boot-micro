package com.demo.feign;

import com.demo.event.dto.FeignRequestLoggingEvent;
import com.demo.util.RequestUtils;
import com.google.gson.Gson;
import feign.Logger;
import feign.Request;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.ApplicationEventPublisher;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class CustomFeignRequestLogging extends Logger {

    private final ApplicationEventPublisher publisher;

    public CustomFeignRequestLogging(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        // TODO document why this method is empty
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime)
            throws IOException {
        String method = response.request().httpMethod().name();
        String url = response.request().requestTemplate().path();
        String query = response.request().requestTemplate().queryLine();
        query = URLDecoder.decode(query, StandardCharsets.UTF_8);
        Integer status = response.status();
        String requestId = MDC.get(RequestUtils.CONTEXT_REQUEST_ID);
        Gson gson = new Gson();
        String requestHeader = response.request().headers() != null ? gson.toJson(response.request().headers()) : null;
        String responseHeader = response.headers() != null ? gson.toJson(response.headers()) : null;
        FeignRequestLoggingEvent feignEvent = new FeignRequestLoggingEvent(method, url, query, status, elapsedTime
                , requestHeader, responseHeader, requestId);
        byte[] requestBody = response.request().body();
        byte[] responseBody = response.body() != null ? response.body().asInputStream().readAllBytes() : null;
        feignEvent.setPayload(requestBody);
        feignEvent.setResponse(responseBody);
        // Log to DB
        try {
            String contentType = (response.headers().get("content-type") != null ? response.headers().get("content-type") : "").toString();
            if (contentType.contains("application/pdf") && responseBody != null) {
                feignEvent.setResponse(Base64.getEncoder().encodeToString(responseBody).getBytes());
            }
        } catch (Exception e) {
        }
        publisher.publishEvent(feignEvent);
        return response.toBuilder().body(responseBody).build();
    }

    @Override
    protected void log(String configKey, String format, Object... args) {
        log.info(format(configKey, format, args));
    }

    protected String format(String configKey, String format, Object... args) {
        return String.format(methodTag(configKey) + format, args);
    }
}