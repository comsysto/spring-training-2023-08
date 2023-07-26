package com.comsysto.trainings.springtrainingeon.app.adapter.web.in.charging;

import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingPoint;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingPointState;
import com.comsysto.trainings.springtrainingeon.app.domain.common.ConnectorType;
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
