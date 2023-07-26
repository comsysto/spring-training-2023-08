package com.comsysto.trainings.springtrainingeon.app.port.web.in.charging;

import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingPoint;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingStation;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingStationId;
import com.comsysto.trainings.springtrainingeon.app.domain.common.Address;
import com.comsysto.trainings.springtrainingeon.app.domain.common.GeoLocation;
import lombok.NonNull;
import lombok.Value;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ChargingStationWebApi {
    List<ChargingStation> getChargingStations();

    Optional<ChargingStation> getChargingStationById(ChargingStationId id);

    ChargingStation createChargingStation(CreateChargingStationCommand command);

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
