package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.out.common.restclient;

import reactor.core.publisher.Mono;

import java.util.Optional;

public interface RestCall {
    RestClientImpl.RestCallImpl pathVariable(String name, Object value);

    RestClientImpl.RestCallImpl queryParam(String name, Object value);

    <T> Mono<T> exchangeJsonAndMapTo(Class<T> responseType);

    <T> Mono<Optional<T>> exchangeJsonAndMapToOptional(Class<T> responseType);

    Mono<Void> exchange();
}
