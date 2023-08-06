package com.comsysto.trainings.springtrainingeon.app.adapter.web.out.chargingpointstate;

import com.comsysto.trainings.springtrainingeon.app.domain.ChargingPointName;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingPointState;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingStationId;
import com.comsysto.trainings.springtrainingeon.app.port.web.out.chargingpointstate.ChargingPointStateClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestChargingPointStateClient implements ChargingPointStateClient {

    @Override
    public ChargingPointState getChargingPointState(ChargingStationId chargingStationId, ChargingPointName name) {
        return ChargingPointState.UNKNOWN;

    }

}
