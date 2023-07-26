package com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient;

import com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient.exceptions.ConnectRestClientException;
import com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient.exceptions.DeserializationFailedRestClientException;
import com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient.exceptions.InvalidResponseRestClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class RestErrorHandler {

    private final ObjectMapper objectMapper;

    public Throwable mapErrors(RestCallParams callParams, Throwable t) {
        if (t instanceof WebClientRequestException) {
            return new ConnectRestClientException(callParams, t);
        }
        if (t instanceof DeserializationFailedRestClientException e) {
            log.error(
                    "Failed deserialization failed!\n" +
                            "responseType:\t" + e.getResponseType() + "\n" +
                            getBodyDebugString(e.getBody()) +
                            getCallDebugString(e.getCallParams())
            );
        }
        if (t instanceof InvalidResponseRestClientException e) {
            log.error(
                    "Failed deserialization failed!\n" +
                            "response:\t" + e.getResponse() + "\n" +
                            getViolationsDebugString(e.getViolations()) +
                            getBodyDebugString(e.getBody()) +
                            getCallDebugString(e.getCallParams())
            );

        }
        return t;
    }

    private String getCallDebugString(RestCallParams callParams) {
        return "Call:\n" +
                "\t targetSystem:\t" + callParams.getTargetSystem() + "\n" +
                "\t method:\t" + callParams.getMethod() + "\n" +
                "\t path:\t" + callParams.getPath() + "\n" +
                "\t pathVariables:\t" + callParams.getPathVariables() + "\n" +
                "\t queryParameter:\t" + callParams.getQueryParameter() + "\n";
    }

    private String getBodyDebugString(String body) {
        String formated;
        try {
            formated = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectMapper.readTree(body));
        } catch (JsonProcessingException e) {
            formated = body;
        }

        return "Body:\n" +
                formated.lines()
                        .collect(Collectors.joining("\n\t", "\t", "\n"));


    }

    private  String getViolationsDebugString(Set<? extends ConstraintViolation<?>> violations) {
        return "Violations:\n" +
                violations.stream()
                        .map(v -> v.getPropertyPath() + ":\t" + v.getMessage())
                        .collect(Collectors.joining("\n\t", "\t", "\n"));
    }
}
