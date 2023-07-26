package com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient;

import reactor.core.publisher.Mono;

import java.util.Optional;

public interface RestCall {
    RestClientImpl.RestCallImpl pathVariable(String name, Object value);

    RestClientImpl.RestCallImpl queryParam(String name, Object value);

    <T> T exchangeJsonAndMapTo(Class<T> responseType);

    <T> Optional<T> exchangeJsonAndMapToOptional(Class<T> responseType);

    void exchange();
}
