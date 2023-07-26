package com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient.exceptions;

import com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient.RestCallParams;
import jakarta.validation.ConstraintViolation;
import lombok.Getter;

import java.util.Set;

public class InvalidResponseRestClientException extends RestClientException {
    @Getter
    private final String body;
    @Getter
    private final Object response;
    @Getter
    private final Set<? extends ConstraintViolation<?>> violations;

    public InvalidResponseRestClientException(RestCallParams callParams, Object response, Set<? extends ConstraintViolation<?>> violations, String body) {
        super(callParams, "Response is invalid", null);
        this.response = response;
        this.violations = violations;
        this.body = body;
    }
}
