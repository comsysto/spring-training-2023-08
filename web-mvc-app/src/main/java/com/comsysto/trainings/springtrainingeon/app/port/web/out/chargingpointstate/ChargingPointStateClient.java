package com.comsysto.trainings.springtrainingeon.app.port.web.out.chargingpointstate;

import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingPointState;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingStationId;
import com.comsysto.trainings.springtrainingeon.app.domain.ChargingPointName;
import reactor.core.publisher.Mono;

public interface ChargingPointStateClient {
    ChargingPointState getChargingPointState(ChargingStationId chargingStationId, ChargingPointName name);
}
