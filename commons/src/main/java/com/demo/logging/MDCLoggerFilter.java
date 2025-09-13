package com.demo.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import com.demo.constants.CorrelationConstants;
import com.demo.util.CorrelationUtils;

import java.io.IOException;

@Slf4j
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MDCLoggerFilter implements Filter {

    @Value("${management.endpoints.web.base-path:x}")
    private String managementEndpoint;

    @Value("${application.logging.trace:true}")
    private boolean appLogTrace;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String correlationId = request.getHeader(CorrelationConstants.HEADER_CORRELATION_ID.getValue());
        if (!StringUtils.hasText(correlationId)) {
            correlationId = CorrelationUtils.generateCorrelationId();
        }
        response.setHeader(CorrelationConstants.HEADER_CORRELATION_ID.getValue(), correlationId);
        MDC.put(CorrelationConstants.CONTEXT_CORRELATION_ID.getValue(), correlationId);
        MDC.put(CorrelationConstants.CONTEXT_REQUEST_URL.getValue(), request.getRequestURI());
        MDC.put(CorrelationConstants.CONTEXT_REQUEST_METHOD.getValue(), request.getMethod());
        MDC.put(CorrelationConstants.CONTEXT_REQUEST_TYPE.getValue(), "request");
        long start = System.currentTimeMillis();
        chain.doFilter(req, res);
        MDC.put(CorrelationConstants.CONTEXT_REQUEST_TYPE.getValue(), "response");
        MDC.put(CorrelationConstants.CONTEXT_RESPONSE_TIME.getValue(), String.valueOf(System.currentTimeMillis() - start));
        MDC.put(CorrelationConstants.CONTEXT_RESPONSE_STATUS.getValue(), String.valueOf(response.getStatus()));
        // Log response time
        if (appLogTrace) {
            log.info(HttpStatus.valueOf(response.getStatus()).name());
        }
        MDC.remove(CorrelationConstants.CONTEXT_RESPONSE_TIME.getValue());
        MDC.remove(CorrelationConstants.CONTEXT_RESPONSE_STATUS.getValue());
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}

