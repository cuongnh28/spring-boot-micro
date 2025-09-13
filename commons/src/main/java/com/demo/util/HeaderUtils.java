package com.demo.util;

import com.demo.constants.CorrelationConstants;
import com.demo.enums.HeaderName;
import org.springframework.http.HttpHeaders;

public class HeaderUtils {
    public static HttpHeaders createJwtHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        String correlationId = CorrelationUtils.currentCorrelationId();
        headers.add(CorrelationConstants.HEADER_CORRELATION_ID.getValue(), correlationId);
        headers.add(HeaderName.JWT, token);
        return headers;
    }
}
