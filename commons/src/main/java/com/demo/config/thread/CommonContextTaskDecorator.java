package com.demo.config.thread;

import com.demo.context.CommonContextHolder;
import com.demo.util.MDCUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskDecorator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.apache.commons.lang3.SerializationUtils;
import java.util.Map;

@Slf4j
public class CommonContextTaskDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        CommonContext commonContext = SerializationUtils.clone(CommonContextHolder.getContext());
        Map<String, String> contextMap = MDCUtils.getCurrentMDCContextMap();
        log.info("Saving tenant information for thread...");
        return () -> {
            try {
                CommonContextHolder.setContext(commonContext);
                MDCUtils.setMdcContextMap(contextMap);
                log.info("Restoring tenant information for thread...");
                runnable.run();
            } finally {
                MDCUtils.clearMDCContext();
                CommonContextHolder.resetContext();
            }
        };
    }
}