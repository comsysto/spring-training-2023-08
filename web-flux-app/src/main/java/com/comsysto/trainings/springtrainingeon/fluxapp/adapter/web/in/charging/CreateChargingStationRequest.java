package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.in.charging;

import com.comsysto.trainings.springtrainingeon.fluxapp.port.web.in.charging.ChargingStationWebApi;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.ChargingPointName;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.charging.ChargingPoint;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.charging.ChargingPointState;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.common.Address;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.common.ConnectorType;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.common.GeoLocation;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.util.Map;
import java.util.stream.Collectors;

@Value
public class CreateChargingStationRequest {

    @NonNull
    GeoLocation location;
    @NonNull
    Address address;
    @NonNull
    Map<String, ChargingPointRequest> chargingPoints;

    @Value
    // need explicit @JsonCreator as jackson has some legacy behaviour for single arg constructors
    @AllArgsConstructor(onConstructor_ = @JsonCreator)
    public static class ChargingPointRequest {
        ConnectorType connector;
    }

    public ChargingStationWebApi.CreateChargingStationCommand toDomain() {
        return new ChargingStationWebApi.CreateChargingStationCommand(
                location,
                address,
                chargingPoints.entrySet().stream()
                        .map(entry ->
                                new ChargingPoint(
                                        new ChargingPointName(entry.getKey()),
                                        entry.getValue().getConnector(),
                                        ChargingPointState.UNKNOWN)
                        )
                        .collect(Collectors.toSet())
        );
    }

}
