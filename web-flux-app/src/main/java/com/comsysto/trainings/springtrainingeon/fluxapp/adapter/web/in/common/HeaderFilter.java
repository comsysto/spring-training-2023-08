package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.in.common;

import com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.common.BearerToken;
import com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.common.RequestId;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class HeaderFilter implements WebFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public @NonNull Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var requestId = Optional.ofNullable(
                        exchange.getRequest().getHeaders().getFirst(RequestId.HEADER_NAME)
                )
                .map(RequestId::new)
                .orElseGet(RequestId::random);

        var bearerToken = Optional.ofNullable(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION)).flatMap(value -> {
            if (value.startsWith(BEARER_PREFIX)) {
                return Optional.of(value.substring(BEARER_PREFIX.length()));
            }
            return Optional.empty();
        }).map(BearerToken::new);

        return chain
                .filter(exchange)
                .contextWrite(context ->
                        context
                                .put(RequestId.class, requestId)
                                .putNonNull(BearerToken.class, bearerToken.orElse(null))
                );
    }
}
