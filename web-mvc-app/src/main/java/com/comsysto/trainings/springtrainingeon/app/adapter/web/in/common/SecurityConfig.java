package com.comsysto.trainings.springtrainingeon.app.adapter.web.in.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .oauth2ResourceServer(r -> {
                    r.jwt( j -> {
                        var authenticationConverter = new JwtAuthenticationConverter();
                        var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
                        grantedAuthoritiesConverter.setAuthorityPrefix("");
                        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
                        authenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
                        j.jwtAuthenticationConverter(authenticationConverter);
                    });
                });

        return http.build();
    }

}
