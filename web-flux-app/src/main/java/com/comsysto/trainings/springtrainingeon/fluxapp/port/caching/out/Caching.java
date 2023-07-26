package com.comsysto.trainings.springtrainingeon.fluxapp.port.caching.out;

import reactor.core.publisher.Mono;

import java.util.function.Supplier;

public interface Caching {
    <T> Mono<T> cacheMono(
            String cacheName,
            String cacheKey,
            Supplier<Mono<T>> loadCache
    );
}
