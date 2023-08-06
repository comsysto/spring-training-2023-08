package com.comsysto.trainings.springtrainingeon.app.adapter.web.in.charging;

import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingStationId;
import com.comsysto.trainings.springtrainingeon.app.port.web.in.charging.ChargingStationWebApi;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("charging-stations")
@RequiredArgsConstructor
public class ChargingStationController {

    private final ChargingStationWebApi api;

    @GetMapping
    public List<ChargingStationResponse> getChargingStation() {
        return api
                .getChargingStations()
                .stream()
                .map(ChargingStationResponse::new)
                .toList();
    }

    @GetMapping("{id}")
    public ResponseEntity<ChargingStationResponse> getChargingStationById(@PathVariable String id) {
        return api
                .getChargingStationById(new ChargingStationId(id))
                .map(ChargingStationResponse::new)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ChargingStationResponse> postChargingStation(@RequestBody CreateChargingStationRequest request) {
        var chargingStation = api
                .createChargingStation(request.toDomain());
        return ResponseEntity.ok(new ChargingStationResponse(chargingStation));
    }

}
