package com.comsysto.trainings.springtrainingeon.app.adapter.web.in.common;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.comsysto.trainings.springtrainingeon.app.adapter.web.in.user.Role;

public class RoleAuthorityConverter implements Converter<Collection<GrantedAuthority>, Collection<GrantedAuthority>>
{
	@Override
	public Collection<GrantedAuthority> convert(Collection<GrantedAuthority> source)
	{
		return source.stream().flatMap(grantedAuthority ->
		{
			String roleName = grantedAuthority.getAuthority();
			Optional<Role> potentialRole = Role.findByName(roleName);

			return potentialRole.map(RoleAuthorityConverter::convertRoleToGrantedAuthority).orElse(Stream.of());
		}).toList();
	}

	@NotNull
	private static Stream<GrantedAuthority> convertRoleToGrantedAuthority(Role role)
	{
		return role.getAuthorities()
			.stream()
			.map(authority -> (GrantedAuthority) new SimpleGrantedAuthority(authority.name()));
	}
}
