package com.comsysto.trainings.springtrainingeon.fluxapp.port.web.in.charging;

import com.comsysto.trainings.springtrainingeon.fluxapp.domain.charging.ChargingPoint;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.charging.ChargingStation;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.charging.ChargingStationId;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.common.Address;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.common.GeoLocation;
import lombok.NonNull;
import lombok.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface ChargingStationWebApi {
    Flux<ChargingStation> getChargingStations();

    Mono<ChargingStation> getChargingStationById(ChargingStationId id);

    Mono<ChargingStation> createChargingStation(CreateChargingStationCommand command);

    @Value
    class CreateChargingStationCommand {
        @NonNull
        GeoLocation location;
        @NonNull
        Address address;
        @NonNull
        Set<ChargingPoint> chargingPoints;
    }
}
