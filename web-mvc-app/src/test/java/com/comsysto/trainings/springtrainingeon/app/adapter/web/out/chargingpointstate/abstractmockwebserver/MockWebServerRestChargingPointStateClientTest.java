package com.comsysto.trainings.springtrainingeon.app.adapter.web.out.chargingpointstate.abstractmockwebserver;

import com.comsysto.trainings.springtrainingeon.app.adapter.web.out.chargingpointstate.RestChargingPointStateClient;
import com.comsysto.trainings.springtrainingeon.app.domain.ChargingPointName;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingPointState;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingStationId;
import com.comsysto.trainings.springtrainingeon.app.port.web.out.common.restclient.RestClient;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class MockWebServerRestChargingPointStateClientTest extends AbstractMockWebServerClientTest<RestChargingPointStateClient> {


    private final ChargingStationId chargingStationId = new ChargingStationId("some-charging-station-id");
    private final ChargingPointName chargingPointName = new ChargingPointName("some-charging-point-name");


    @Override
    protected RestChargingPointStateClient createClient(RestClient restClient) {
        return new RestChargingPointStateClient(restClient);
    }

    @Test
    void shouldSendAGetCallAndReturnTheResponse_alternative() {
        stubAndVerifyGet(
                "/some-charging-station-id/some-charging-point-name",
                "{ \"state\": \"IDLE\" }"
        );
        var response = client.getChargingPointState(chargingStationId, chargingPointName);
        assertThat(response).isEqualTo(ChargingPointState.IDLE);
    }

}