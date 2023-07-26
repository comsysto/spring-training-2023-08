package com.comsysto.trainings.springtrainingeon.fluxapp.application;

import com.comsysto.trainings.springtrainingeon.fluxapp.domain.charging.ChargingPoint;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.charging.ChargingPointState;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.charging.ChargingStation;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.charging.ChargingStationId;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.common.Address;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.common.ConnectorType;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.common.GeoLocation;
import com.comsysto.trainings.springtrainingeon.fluxapp.port.web.in.charging.ChargingStationWebApi;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.ChargingPointName;
import com.comsysto.trainings.springtrainingeon.fluxapp.port.web.out.chargingpointstate.ChargingPointStateClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChargingStationService implements ChargingStationWebApi {

    private final ChargingPointStateClient chargingPointStateClient;
    private final List<ChargingStation> stations = new CopyOnWriteArrayList<>(List.of(
            new ChargingStation(
                    ChargingStationId.random(),
                    new GeoLocation(123, 456),
                    new Address(
                            "Some Street",
                            "42a",
                            "Some City",
                            "12345",
                            "Some Country"
                    ),
                    Set.of(
                            new ChargingPoint(
                                    new ChargingPointName("01"),
                                    ConnectorType.A,
                                    ChargingPointState.UNKNOWN
                            ),
                            new ChargingPoint(
                                    new ChargingPointName("02"),
                                    ConnectorType.A,
                                    ChargingPointState.UNKNOWN
                            ),
                            new ChargingPoint(
                                    new ChargingPointName("03"),
                                    ConnectorType.B,
                                    ChargingPointState.UNKNOWN
                            ),
                            new ChargingPoint(
                                    new ChargingPointName("04"),
                                    ConnectorType.C,
                                    ChargingPointState.UNKNOWN
                            )
                    )
            )
    ));

    @Override
    public Flux<ChargingStation> getChargingStations() {
        return Flux.fromStream(stations.stream()).flatMap(this::updateChargingStation);
    }

    @Override
    public Mono<ChargingStation> getChargingStationById(ChargingStationId id) {
        return stations.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .map(this::updateChargingStation)
                .orElse(Mono.empty());


    }

    private Mono<ChargingStation> updateChargingStation(ChargingStation station) {
        return Flux.fromStream(station.getChargingPoints().stream())
                .flatMap(chargingPoint ->
                        chargingPointStateClient.getChargingPointState(station.getId(), chargingPoint.getName())
                                .map(chargingPoint::withState)
                )
                .collect(Collectors.toSet())
                .map(station::withChargingPoints);
    }

    @Override
    public Mono<ChargingStation> createChargingStation(CreateChargingStationCommand command) {
        var newChargingStation = new ChargingStation(ChargingStationId.random(), command.getLocation(), command.getAddress(), command.getChargingPoints());
        stations.add(newChargingStation);
        return Mono.just(newChargingStation);
    }


}
