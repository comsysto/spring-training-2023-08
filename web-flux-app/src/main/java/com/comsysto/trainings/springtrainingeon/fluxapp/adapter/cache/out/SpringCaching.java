package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.cache.out;

import com.comsysto.trainings.springtrainingeon.fluxapp.port.caching.out.Caching;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class SpringCaching implements Caching {

    private final CacheManager cacheManager;

    @Override
    public <T> Mono<T> cacheMono(
            String cacheName,
            String cacheKey,
            Supplier<Mono<T>> loadCache
    ) {
        var cache = cacheManager.getCache(cacheName);
        //noinspection unchecked
        return Mono.fromSupplier(() -> (T) cache.get(cacheKey, Object.class))
                        .subscribeOn(Schedulers.boundedElastic())
                        .flatMap(cached -> {
                            if (cached != null) {
                                return Mono.just(cached);
                            } else {
                                return loadCache.get().doOnSuccess(value ->
                                        Schedulers.boundedElastic().schedule(() -> cache.put(cacheKey, value))
                                );
                            }
                        });
    }

}
