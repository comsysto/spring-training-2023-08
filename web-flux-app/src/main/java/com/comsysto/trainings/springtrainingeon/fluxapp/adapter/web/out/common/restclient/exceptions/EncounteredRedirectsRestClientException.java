package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.out.common.restclient.exceptions;

import com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.out.common.restclient.RestCallParams;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

public class EncounteredRedirectsRestClientException extends RestClientException {
    @Getter
    private final String body;
    @Getter
    private final HttpStatusCode status;
    @Getter
    private final String location;

    public EncounteredRedirectsRestClientException(RestCallParams callParams, HttpStatusCode status, String location, String body) {
        super(callParams, "Encountered redirect " + status + " to " + location, null);
        this.status = status;
        this.location = location;
        this.body = body;
    }
}
