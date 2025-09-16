package com.demo.logging;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import com.demo.constants.CorrelationConstants;
import com.demo.util.CorrelationUtils;

public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String correlationId = CorrelationUtils.currentCorrelationId();
        requestTemplate.header(CorrelationConstants.CONTEXT_CORRELATION_ID.getValue(), correlationId);
    }
}
