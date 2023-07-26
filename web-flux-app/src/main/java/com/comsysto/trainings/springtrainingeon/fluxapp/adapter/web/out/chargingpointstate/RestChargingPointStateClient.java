package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.out.chargingpointstate;

import com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.out.common.restclient.RestClientImpl;
import com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.out.common.restclient.TargetSystem;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.ChargingPointName;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.charging.ChargingPointState;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.charging.ChargingStationId;
import com.comsysto.trainings.springtrainingeon.fluxapp.port.caching.out.Caching;
import com.comsysto.trainings.springtrainingeon.fluxapp.port.web.out.chargingpointstate.ChargingPointStateClient;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RestChargingPointStateClient implements ChargingPointStateClient {
    private final RestClientImpl restClient;
    private final Caching caching;
    private final CacheManager cacheManager;

    @Override
    public Mono<ChargingPointState> getChargingPointState(ChargingStationId chargingStationId, ChargingPointName name) {
        return getChargingPointStateImpl(chargingStationId, name);
//        return caching.cacheMono(
//                getClass().getSimpleName(),
//                chargingStationId.getValue() + "/" + name.getValue(),
//                () -> getChargingPointStateImpl(chargingStationId, name)
//        );
    }

    private Mono<ChargingPointState> getChargingPointStateImpl(ChargingStationId chargingStationId, ChargingPointName name) {
//        var cacheKey = chargingStationId.getValue() + "/" + name.getValue();
//        var cache = cacheManager.getCache(this.getClass().getName());
//        var cached = cache.get(cacheKey, ChargingPointState.class);
//        if (cached != null) {
//            return Mono.just(cached);
//        }

        return restClient
                .get(
                        TargetSystem.CHARGING_POINT_STATE_SYSTEM,
                        "/{chargingStationId}/{chargingPointName}"
                )
                .pathVariable("chargingStationId", chargingStationId.getValue())
                .pathVariable("chargingPointName", name.getValue())
                .exchangeJsonAndMapTo(ChargingPointStateResponse.class)
                .map(response ->
                        ChargingPointState.findForName(response.state).orElse(ChargingPointState.UNKNOWN)
                )
//                .doOnSuccess(state -> cache.put(cacheKey, state))
                ;
    }

    @Value
    @AllArgsConstructor(onConstructor_ = @JsonCreator)
    public static class ChargingPointStateResponse {
        @NotNull
        String state;
    }
}
