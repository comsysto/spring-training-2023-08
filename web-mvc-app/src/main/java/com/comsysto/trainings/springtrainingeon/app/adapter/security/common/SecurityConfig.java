package com.comsysto.trainings.springtrainingeon.app.adapter.security.common;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Profile("!test")
class SecurityConfig {
    private final KeycloakLogoutHandler keycloakLogoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/mocks/**").permitAll()
                        .anyRequest().authenticated()
                )
                .logout((logout) -> logout
                        .addLogoutHandler(keycloakLogoutHandler)
                        .logoutUrl("/logout").permitAll()
                )
                .oauth2ResourceServer( c -> c.jwt( jwtSpec -> {}));

        return http.build();
    }
}
