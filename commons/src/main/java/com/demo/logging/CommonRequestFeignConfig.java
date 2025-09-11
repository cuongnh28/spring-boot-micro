package com.demo.logging;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import com.demo.util.RequestUtils;

public class CommonRequestFeignConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String requestId = RequestUtils.currentRequestId();
        requestTemplate.header(RequestUtils.HEADER_REQUEST_ID, requestId);
    }
}
