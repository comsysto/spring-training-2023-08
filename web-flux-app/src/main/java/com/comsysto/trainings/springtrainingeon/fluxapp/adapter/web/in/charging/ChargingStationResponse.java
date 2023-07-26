package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.in.charging;

import com.comsysto.trainings.springtrainingeon.fluxapp.domain.charging.ChargingStation;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.common.Address;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.common.GeoLocation;
import lombok.NonNull;
import lombok.Value;

import java.util.Map;
import java.util.stream.Collectors;

@Value
public class ChargingStationResponse {

    public ChargingStationResponse(ChargingStation station) {
        this.id = station.getId().getValue();
        this.location = station.getLocation();
        this.address = station.getAddress();
        this.chargingPoints = station.getChargingPoints().stream()
                .collect(Collectors.toMap(
                        chargingPoint -> chargingPoint.getName().getValue(),
                                ChargingPointResponse::new
                        )
                );
    }

    @NonNull
    String id;
    @NonNull
    GeoLocation location;
    @NonNull
    Address address;
    @NonNull
    Map<String, ChargingPointResponse> chargingPoints;
}
