package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.common;

import lombok.Value;

import java.util.UUID;

@Value
public class RequestId {
    public static final String HEADER_NAME = "X-Request-Id";
    public static RequestId random(){
        return new RequestId(UUID.randomUUID().toString());
    }

    String value;
}
