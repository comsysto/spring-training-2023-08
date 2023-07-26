package com.comsysto.trainings.springtrainingeon.app.application;

import com.comsysto.trainings.springtrainingeon.app.domain.ChargingPointName;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingPoint;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingPointState;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingStation;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingStationId;
import com.comsysto.trainings.springtrainingeon.app.domain.common.Address;
import com.comsysto.trainings.springtrainingeon.app.domain.common.ConnectorType;
import com.comsysto.trainings.springtrainingeon.app.domain.common.GeoLocation;
import com.comsysto.trainings.springtrainingeon.app.port.web.in.charging.ChargingStationWebApi;
import com.comsysto.trainings.springtrainingeon.app.port.web.out.chargingpointstate.ChargingPointStateClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public List<ChargingStation> getChargingStations() {
        return stations.stream().map(this::updateChargingStation).toList();
    }

    @Override
    public Optional<ChargingStation> getChargingStationById(ChargingStationId id) {
        return stations.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .map(this::updateChargingStation);


    }

    private ChargingStation updateChargingStation(ChargingStation station) {
        var chargingPoints = station.getChargingPoints().stream()
                .map(chargingPoint -> {
                    var state = chargingPointStateClient.getChargingPointState(station.getId(), chargingPoint.getName());
                    return chargingPoint.withState(state);
                })
                .collect(Collectors.toSet());
        return station.withChargingPoints(chargingPoints);

    }

    @Override
    public ChargingStation createChargingStation(CreateChargingStationCommand command) {
        var newChargingStation = new ChargingStation(ChargingStationId.random(), command.getLocation(), command.getAddress(), command.getChargingPoints());
        stations.add(newChargingStation);
        return newChargingStation;
    }
}
