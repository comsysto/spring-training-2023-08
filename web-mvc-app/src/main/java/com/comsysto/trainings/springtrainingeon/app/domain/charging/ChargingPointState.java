package com.comsysto.trainings.springtrainingeon.app.domain.charging;

import java.util.Arrays;
import java.util.Optional;

public enum ChargingPointState {
    IN_USE,
    IDLE,
    ERROR,
    UNKNOWN;

    public static Optional<ChargingPointState> findForName(String name) {
        return Arrays.stream(values())
                .filter(s -> s.name().equals(name))
                .findFirst();
    }
}
