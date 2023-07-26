package com.comsysto.trainings.springtrainingeon.fluxapp.domain.charging;

import lombok.Value;

import java.util.UUID;

@Value
public class ChargingStationId {
    public static ChargingStationId random(){
        return new ChargingStationId(UUID.randomUUID().toString());
    }

    String value;
}
