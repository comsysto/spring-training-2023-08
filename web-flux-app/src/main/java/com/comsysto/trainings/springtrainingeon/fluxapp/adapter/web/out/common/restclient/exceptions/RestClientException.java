package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.out.common.restclient.exceptions;

import com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.out.common.restclient.RestCallParams;
import lombok.Getter;

public abstract class RestClientException extends RuntimeException {

    @Getter
    private final RestCallParams callParams;

    protected RestClientException(RestCallParams callParams, String message, Throwable cause) {
        super(message, cause);
        this.callParams = callParams;
    }

}
