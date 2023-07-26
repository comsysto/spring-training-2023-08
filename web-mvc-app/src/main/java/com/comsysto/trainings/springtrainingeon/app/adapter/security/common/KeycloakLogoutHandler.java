package com.comsysto.trainings.springtrainingeon.app.adapter.security.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
public class KeycloakLogoutHandler implements LogoutHandler {
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        var user = (OidcUser)authentication.getPrincipal();
        String endSessionEndpoint = user.getIssuer() + "/protocol/openid-connect/logout";
        String uri = UriComponentsBuilder
                .fromUriString(endSessionEndpoint)
                .queryParam("id_token_hint", user.getIdToken().getTokenValue())
                .build().toUriString();
        restTemplate.getForEntity(uri, Void.class);
    }
}