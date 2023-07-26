package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.out.common.restclient.exceptions;

import com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.out.common.restclient.RestCallParams;
import lombok.Getter;

public class DeserializationFailedRestClientException extends RestClientException {
    @Getter
    private final Class<?> responseType;
    @Getter
    private final String body;

    public DeserializationFailedRestClientException(RestCallParams callParams, Class<?> targetClass, String body, Throwable cause) {
        super(callParams, "Serialization of response to " + targetClass + "failed: " + cause.getMessage(), cause);
        this.responseType = targetClass;
        this.body = body;
    }
}
