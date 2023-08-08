package com.comsysto.trainings.springtrainingeon.app.adapter.web.in.common;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.comsysto.trainings.springtrainingeon.app.domain.user.User;
import com.comsysto.trainings.springtrainingeon.app.domain.user.UserId;
import com.comsysto.trainings.springtrainingeon.app.port.security.out.UserProvider;

@Component
public class SpringUserProvider implements UserProvider
{
	@Override
	public User currentUser()
	{
		JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

		Jwt principal = (Jwt) token.getPrincipal();
		return new User(
			new UserId(principal.getId()),
			principal.getId(),
			principal.getId());
	}
}
