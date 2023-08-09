package com.comsysto.trainings.springtrainingeon.app.locallauncher.mocks;

import com.comsysto.trainings.springtrainingeon.app.adapter.web.out.chargingpointstate.RestChargingPointStateClient;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingPointState;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("mocks/charging-point-state")
@Slf4j
public class ChargingPointStateMockController {
    private final List<ChargingPointState> chargingPointStates = List.of(ChargingPointState.values());
    private final Random random = new Random();

    @GetMapping("{chargingStationId}/{chargingPointName}")
    public ResponseEntity<?> getState(
            @PathVariable String chargingStationId,
            @PathVariable String chargingPointName,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String bearerToken,
            @RequestHeader(value = "requestId", required = false) String requestId
    ) {
        log.warn(
                "MOCK CALL Charging Point State: {}/{}",
                chargingStationId,
                chargingPointName
        );

        var state = chargingPointStates.get(random.nextInt(chargingPointStates.size()));
        return ResponseEntity.ok(Map.of("state", state.name()));
    }
}
