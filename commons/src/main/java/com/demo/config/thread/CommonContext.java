package com.demo.config.thread;

import java.io.Serializable;

public interface CommonContext extends Serializable {
    String getCorrelationId();
    String getUserId();
    String getUsername();
    void setCorrelationId(String correlationId);
    void setUserId(String userId);
    void setUsername(String username);
}
