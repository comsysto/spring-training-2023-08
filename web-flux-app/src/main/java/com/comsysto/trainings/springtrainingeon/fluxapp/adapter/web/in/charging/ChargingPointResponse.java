package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.in.charging;

import com.comsysto.trainings.springtrainingeon.fluxapp.domain.charging.ChargingPoint;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.charging.ChargingPointState;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.common.ConnectorType;
import lombok.Value;

@Value
public class ChargingPointResponse {
    public ChargingPointResponse(ChargingPoint point) {
        this.connectorType = point.getConnector();
        this.state = point.getState();
    }

    ConnectorType connectorType;
    ChargingPointState state;
}
