package com.comsysto.trainings.springtrainingeon.fluxapp.domain.charging;

import com.comsysto.trainings.springtrainingeon.fluxapp.domain.common.Address;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.common.GeoLocation;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
@With
public class ChargingStation {
    @NonNull
    ChargingStationId id;
    @NonNull
    GeoLocation location;
    @NonNull
    Address address;
    @NonNull
    Set<ChargingPoint> chargingPoints;

    public ChargingStation withAddedChargingPoint(ChargingPoint chargingPoint) {
        return withChargingPoints(
                Stream
                        .concat(this.chargingPoints.stream(), Stream.of(chargingPoint))
                        .collect(Collectors.toUnmodifiableSet())
        );
    }
}
