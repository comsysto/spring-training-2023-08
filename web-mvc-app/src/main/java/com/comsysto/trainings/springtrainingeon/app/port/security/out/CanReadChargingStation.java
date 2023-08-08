package com.comsysto.trainings.springtrainingeon.app.port.security.out;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@PreAuthorize("hasAnyAuthority(T(com.comsysto.trainings.springtrainingeon.app.adapter.web.in.user.Authority).READ_CHARGING_STATIONS.name())")
public @interface CanReadChargingStation
{}