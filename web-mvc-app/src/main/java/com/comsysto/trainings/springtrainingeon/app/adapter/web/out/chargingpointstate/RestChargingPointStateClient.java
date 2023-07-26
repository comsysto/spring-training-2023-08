package com.comsysto.trainings.springtrainingeon.app.adapter.web.out.chargingpointstate;

import com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient.RestClientImpl;
import com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient.TargetSystem;
import com.comsysto.trainings.springtrainingeon.app.domain.ChargingPointName;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingPointState;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingStationId;
import com.comsysto.trainings.springtrainingeon.app.port.web.out.chargingpointstate.ChargingPointStateClient;
import com.comsysto.trainings.springtrainingeon.app.port.web.out.common.restclient.RestClient;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RestChargingPointStateClient implements ChargingPointStateClient {
    private final RestClient restClient;

    @Override
    public ChargingPointState getChargingPointState(ChargingStationId chargingStationId, ChargingPointName name) {
        var response = restClient
                .get(
                        TargetSystem.CHARGING_POINT_STATE_SYSTEM,
                        "/{chargingStationId}/{chargingPointName}"
                )
                .pathVariable("chargingStationId", chargingStationId.getValue())
                .pathVariable("chargingPointName", name.getValue())
                .exchangeJsonAndMapTo(ChargingPointStateResponse.class);

        return ChargingPointState.findForName(response.state).orElse(ChargingPointState.UNKNOWN);

    }

    @Value
    @AllArgsConstructor(onConstructor_ = @JsonCreator)
    public static class ChargingPointStateResponse {
        @NotNull
        String state;
    }
}
