package com.demo.config.thread;

import com.demo.statics.DeviceType;

import java.io.Serializable;

// use to set to context holder
public interface CommonContext extends Serializable {
    DeviceType getDeviceType();
    String getAccountId();
    String getClientId();
    String getRequestId();

//    Set it after login successful
    void setDeviceType();
    void setAccountId();
    void setClientId();
    void setRequestId(String requestId);

}
