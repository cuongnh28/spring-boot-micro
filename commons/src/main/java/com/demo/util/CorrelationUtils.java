package com.demo.util;

import com.demo.constants.CorrelationConstants;
import org.slf4j.MDC;

import java.util.UUID;

public class CorrelationUtils {

    private CorrelationUtils() {
    }

    public static String generateCorrelationId(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String currentCorrelationId(){
        return MDC.get(CorrelationConstants.CONTEXT_CORRELATION_ID.getValue());
    }
}

