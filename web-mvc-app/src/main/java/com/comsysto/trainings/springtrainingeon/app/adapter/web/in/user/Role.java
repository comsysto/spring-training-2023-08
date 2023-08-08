package com.comsysto.trainings.springtrainingeon.app.adapter.web.in.user;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import lombok.Getter;

public enum Role
{
	ADMIN("spring_a", Authority.READ_CHARGING_STATIONS, Authority.WRITE_CHARGING_STATIONS);

	@Getter
	private final String id;
	@Getter
	private final Set<Authority> authorities;

	Role(String id, Authority... authorities)
	{
		this.id = id;
		this.authorities = Set.of(authorities);
	}

	public static Optional<Role> findByName(String roleName)
	{
		return Arrays.stream(Role.values()).filter(role -> role.id.contains(roleName)).findFirst();
	}
}