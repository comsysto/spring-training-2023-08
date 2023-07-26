package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.out.common.restclient.exceptions;

import com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.out.common.restclient.RestCallParams;

public class ConnectRestClientException extends RestClientException {
    public ConnectRestClientException(RestCallParams callParams, Throwable cause) {
        super(callParams, "Failed connecting to " + callParams.getTargetSystem(), cause);
    }
}
