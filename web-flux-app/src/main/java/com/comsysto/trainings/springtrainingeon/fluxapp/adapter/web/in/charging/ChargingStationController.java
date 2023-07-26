package com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.in.charging;

import com.comsysto.trainings.springtrainingeon.fluxapp.port.web.in.charging.ChargingStationWebApi;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.charging.ChargingStationId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("charging-stations")
@RequiredArgsConstructor
public class ChargingStationController {

    private final ChargingStationWebApi api;

    @GetMapping
    public Flux<ChargingStationResponse> getChargingStation() {
        return api
                .getChargingStations()
                .map(ChargingStationResponse::new);
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<ChargingStationResponse>> getChargingStationById(@PathVariable String id) {
        return api
                .getChargingStationById(new ChargingStationId(id))
                .map(ChargingStationResponse::new)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PostMapping
    public Mono<ResponseEntity<ChargingStationResponse>> postChargingStation(@RequestBody CreateChargingStationRequest request){
        return api
                .createChargingStation(request.toDomain())
                .map(ChargingStationResponse::new)
                .map(ResponseEntity::ok);
    }

}
