package com.demo.util;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;

public final class MDCUtils {

    public static void setMDCContext(String clientId, String requestId) {
        try {
            if (StringUtils.isEmpty(requestId)) {
                requestId = RequestUtils.generateRequestId();
            }
            MDC.put("requestId", requestId);
        } catch (Throwable tx) {
            // NOP
        }
    }

    public static void clearMDCContext() {
        try {
            MDC.clear();
        } catch (Throwable tx) {
            // NOP
        }
    }

    public static Map<String, String> getCurrentMDCContextMap() {
        try {
            return MDC.getCopyOfContextMap();
        } catch (Throwable tx) {
            return new HashMap<>();
        }
    }

    public static void setMdcContextMap(Map<String, String> contextMap) {
        try {
            if (ObjectUtils.isNotEmpty(contextMap)) {
                MDC.setContextMap(contextMap);
            }
        } catch (Throwable tx) {
            // NOP
        }
    }
}
