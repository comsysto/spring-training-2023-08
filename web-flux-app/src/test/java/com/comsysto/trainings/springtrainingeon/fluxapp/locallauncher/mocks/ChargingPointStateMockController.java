package com.comsysto.trainings.springtrainingeon.fluxapp.locallauncher.mocks;

import com.comsysto.trainings.springtrainingeon.fluxapp.adapter.web.out.chargingpointstate.RestChargingPointStateClient;
import com.comsysto.trainings.springtrainingeon.fluxapp.domain.charging.ChargingPointState;
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
    private String expectedAuthentication = "Basic " + HttpHeaders.encodeBasicAuth("hans", "peter", StandardCharsets.UTF_8);
    private List<ChargingPointState> chargingPointStates = List.of(ChargingPointState.values());
    private final Random random = new Random();

    @GetMapping("{chargingStationId}/{chargingPointName}")
    public ResponseEntity<?> getState(
            @PathVariable String chargingStationId,
            @PathVariable String chargingPointName,
            @RequestHeader(name = "X-Request-Id", required = false) String requestId,
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authentication
    ) {


        if (!expectedAuthentication.equals(authentication) && !authentication.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        log.warn(
                "MOCK CALL Charging Point State: {}/{}, requestId {}, authentication {}",
                chargingStationId,
                chargingPointName,
                requestId,
                authentication
        );

        var state = chargingPointStates.get(random.nextInt(chargingPointStates.size()));
        return ResponseEntity.ok(new RestChargingPointStateClient.ChargingPointStateResponse(state.name()));
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
