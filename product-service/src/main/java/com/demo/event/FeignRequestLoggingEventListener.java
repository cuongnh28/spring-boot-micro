package com.demo.event;

import com.demo.event.dto.FeignRequestLoggingEvent;
import com.demo.model.FeignRequestLog;
import com.demo.repo.FeignRequestLogRepository;
import com.demo.util.LogUtils;
import com.demo.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class FeignRequestLoggingEventListener {

    @Autowired
    private FeignRequestLogRepository feignRequestLogRepository;

    @Async
    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleFeignRequest(FeignRequestLoggingEvent event) {
        try {
            MDC.put(RequestUtils.CONTEXT_REQUEST_ID, event.getRequestId());
            FeignRequestLog feignRequestLog = new FeignRequestLog(null, event .getMethod(),event.getUrl(), event.getQuery()
                    , event.getStatus(), event.getRequestTime(), event.getRequestHeader(), event.getPayload()
                    , event.getResponseHeader(),  event.getResponse(), event.getCreatedDate(), event.getRequestId());
            //LogUtils.logJson(event);
            feignRequestLogRepository.save(feignRequestLog);
        } catch (Exception ex) {
            LogUtils.printError(ex);
        }
    }
}
