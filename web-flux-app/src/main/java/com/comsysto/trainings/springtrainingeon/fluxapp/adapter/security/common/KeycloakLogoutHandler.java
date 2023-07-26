package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.security.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Component
public class KeycloakLogoutHandler implements ServerLogoutHandler {
    private final WebClient webClient = WebClient.create();

    @Override
    public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
        var user = (OidcUser)authentication.getPrincipal();
        String endSessionEndpoint = user.getIssuer() + "/protocol/openid-connect/logout";
        String uri = UriComponentsBuilder
                .fromUriString(endSessionEndpoint)
                .queryParam("id_token_hint", user.getIdToken().getTokenValue())
                .build().toUriString();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .toBodilessEntity()
                .mapNotNull(value -> null);
    }
}