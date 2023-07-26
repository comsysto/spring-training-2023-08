package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.security.common;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
class SecurityConfig {
    private final KeycloakLogoutHandler keycloakLogoutHandler;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(c -> {
            c.pathMatchers("/mocks/**").permitAll();
            c.anyExchange().authenticated();

        });

        http.oauth2Login(c -> {
        });

        http.logout(c ->
                c.logoutHandler(keycloakLogoutHandler).logoutUrl("/logout")
        );

        http.oauth2ResourceServer(c ->
                c.jwt(jwtSpec -> {
                        }
                )
        );
        return http.build();
    }
}
