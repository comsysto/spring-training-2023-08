package com.comsysto.trainings.springtrainingeon.fluxapp.port.web.out.chargingpointstate;

import com.comsysto.trainings.springtrainingeon.fluxapp.domain.charging.ChargingPointState;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.charging.ChargingStationId;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.ChargingPointName;
import reactor.core.publisher.Mono;

public interface ChargingPointStateClient {
    Mono<ChargingPointState> getChargingPointState(ChargingStationId chargingStationId, ChargingPointName name);
}
