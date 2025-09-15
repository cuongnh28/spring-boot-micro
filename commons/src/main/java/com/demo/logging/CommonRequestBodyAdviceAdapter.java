package com.demo.logging;

import jakarta.servlet.http.HttpServletRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class CommonRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {

    @Value("${management.endpoints.web.base-path:/actuator}")
    private String actuatorBasePath;

    @Value("${app.logging.request:true}")
    private boolean appLogRequest;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        String uri = httpServletRequest.getRequestURI();
        return !uri.startsWith(actuatorBasePath);
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
                                MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        if(appLogRequest) {
            Map<String, Object> logData = new HashMap<>();
            if(httpServletRequest.getQueryString() != null) {
                logData.put("query_params", httpServletRequest.getQueryString());
            }
            String requestBodyAsString;
            try {
                requestBodyAsString = objectMapper.writeValueAsString(body);
            } catch (JsonProcessingException e) {
                requestBodyAsString = String.valueOf(body);
            }
            logData.put("request_body", requestBodyAsString);
            log.info("Request body received", StructuredArguments.entries(logData));
        }
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public Object handleEmptyBody(@Nullable Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                  Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        if(appLogRequest) {
            Map<String, Object> logData = new HashMap<>();
            if(httpServletRequest.getQueryString() != null) {
                logData.put("query_params", httpServletRequest.getQueryString());
            }
            logData.put("request_body", "empty");
            log.info("Empty request body received", StructuredArguments.entries(logData));
        }
        return body;
    }

}
