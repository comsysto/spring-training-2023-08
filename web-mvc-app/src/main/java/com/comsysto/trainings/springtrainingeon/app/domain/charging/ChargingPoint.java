package com.comsysto.trainings.springtrainingeon.app.domain.charging;

import com.comsysto.trainings.springtrainingeon.app.domain.ChargingPointName;
import com.comsysto.trainings.springtrainingeon.app.domain.common.ConnectorType;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

@Value
@With
public class ChargingPoint {
    @NonNull
    ChargingPointName name;
    @NonNull
    ConnectorType connector;
    @NonNull
    ChargingPointState state;
}
