package com.demo.util;

import com.demo.enums.HeaderName;
import org.springframework.http.HttpHeaders;

public class HeaderUtils {
    public static HttpHeaders createJwtHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        String requestId = RequestUtils.currentRequestId();
        headers.add("X-Request-ID", requestId);
        headers.add(HeaderName.JWT, token);
        return headers;
    }
}
