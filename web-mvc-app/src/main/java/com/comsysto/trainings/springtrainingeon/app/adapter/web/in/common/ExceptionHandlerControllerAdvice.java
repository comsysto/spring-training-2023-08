package com.comsysto.trainings.springtrainingeon.app.adapter.web.in.common;

import com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient.exceptions.ConnectRestClientException;
import com.comsysto.trainings.springtrainingeon.app.port.context.out.ContextProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.aot.ContextAotProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionHandlerControllerAdvice {

    private final ContextProvider contextProvider;

    @ExceptionHandler
    protected ResponseEntity<Object> handleConnect(ConnectRestClientException ex) {
        String message = "Failed reaching target system " + ex.getCallParams().getTargetSystem();
        log.error(message + " " + contextProvider.getContext(), ex);
        return
                ResponseEntity
                        .status(HttpStatus.BAD_GATEWAY)
                        .body(message);

    }
}
