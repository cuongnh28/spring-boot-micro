package com.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LogUtils {
    private static final Logger log = LoggerFactory.getLogger(LogUtils.class);

    public static void info(String var1, Object var2) {
        log.info(var1, var2);

    }

    public static void printError(String var1, Object var2) {
//        LogType logType = ContextUtils.removeLogType();
        log.error(var1, var2);
//        if (logType != null) {
//            ContextUtils.setLogType(logType);
//        }

    }

    public static void warn(String var1, Object var2) {
        log.warn(var1, var2);
    }

    public static void info(String var1) {
        log.info(var1);
    }

    public static void printError(Exception ex) {
//        LogType logType = ContextUtils.removeLogType();
        log.error(ex.getMessage(), ex);
//        if (logType != null) {
//            ContextUtils.setLogType(logType);
//        }

    }

    private LogUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
