package com.demo.config.resttemplate;

import com.demo.exception.HttpException;
import com.demo.exception.FeignResponseException;
import com.demo.exception.model.ApiError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RestTemplateErrorHandler implements ResponseErrorHandler, ClientHttpRequestInterceptor {
    private static final Logger log = LoggerFactory.getLogger(RestTemplateErrorHandler.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        HttpStatusCode responseStatus = response.getStatusCode();
        return !responseStatus.is2xxSuccessful();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        ApiError errorModel;
        InputStream inputStream;
        String responseBody;
        HttpStatus responseStatus = HttpStatus.valueOf(response.getStatusCode().value());

        try {
            inputStream = response.getBody();
            responseBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            log.info("====RestTemplateResponseErrorHandler.handleError====");
            log.info(response.getStatusText());
            log.info(responseBody);
        } catch (IOException e) {
            throw new HttpException(responseStatus, List.of(responseStatus.getReasonPhrase()), null);
        }

        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            errorModel = mapper.readValue(responseBody, ApiError.class);
        } catch (JsonProcessingException e) {
            throw new HttpException(responseStatus, List.of(responseBody), null);
        }

        throw new FeignResponseException(errorModel);
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);
        if (hasError(response)) {
            handleError(response);
        }
        return response;
    }
}
