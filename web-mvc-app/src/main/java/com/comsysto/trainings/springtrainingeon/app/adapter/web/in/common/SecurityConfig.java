package com.comsysto.trainings.springtrainingeon.app.adapter.web.in.common;

import com.comsysto.trainings.springtrainingeon.app.port.security.out.WebClientQualifier;
import com.comsysto.trainings.springtrainingeon.app.port.security.out.WebClientQualifier.Type;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig
{

	@Bean
	@WebClientQualifier(Type.A)
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		http
			.oauth2ResourceServer(r ->
			{
				r.jwt(j ->
				{
					var authenticationConverter = new JwtAuthenticationConverter();

					var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
					grantedAuthoritiesConverter.setAuthorityPrefix("");
					grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");

					RoleAuthorityConverter roleMapper = new RoleAuthorityConverter();

					authenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter.andThen(roleMapper));
					j.jwtAuthenticationConverter(authenticationConverter);
				});
			});

		return http.build();
	}

}
