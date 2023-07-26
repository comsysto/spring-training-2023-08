package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.in.common;

import com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.out.common.restclient.exceptions.ConnectRestClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerControllerAdvice  {

    @ExceptionHandler
    protected Mono<ResponseEntity<Object>> handleConflict(ConnectRestClientException ex) {

        return Mono.deferContextual(context -> {
            String message = "Failed reaching target system " + ex.getCallParams().getTargetSystem();

            log.error(message + " " + context, ex);
            return Mono.just(
                    ResponseEntity
                            .status(HttpStatus.BAD_GATEWAY)
                            .body(message)
            );
        });
    }
}
