package com.comsysto.trainings.springtrainingeon.app.adapter.web.out.chargingpointstate.mockwebserver;

import com.comsysto.trainings.springtrainingeon.app.adapter.web.common.RequestId;
import com.comsysto.trainings.springtrainingeon.app.adapter.web.out.chargingpointstate.RestChargingPointStateClient;
import com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient.RestClientConfiguration;
import com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient.RestClientImpl;
import com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common.restclient.RestErrorHandler;
import com.comsysto.trainings.springtrainingeon.app.domain.ChargingPointName;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingPointState;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingStationId;
import com.comsysto.trainings.springtrainingeon.app.port.context.out.Context;
import com.comsysto.trainings.springtrainingeon.app.port.context.out.ContextProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validator;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Set;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;


class MockWebServerRestChargingPointStateClientTest {


    private final ChargingStationId chargingStationId = new ChargingStationId("some-charging-station-id");
    private final ChargingPointName chargingPointName = new ChargingPointName("some-charging-point-name");

    private final RequestId requestId = new RequestId("some-request-id");
    private MockWebServer mockWebServer;
    private RestChargingPointStateClient client;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {

        mockWebServer = new MockWebServer();
        mockWebServer.start();

        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        var validator = Mockito.mock(Validator.class);
        Mockito.when(validator.validate(Mockito.any())).thenReturn(Set.of());

        var baseUri = new URI("http://localhost:" + mockWebServer.getPort());

        var systemConfiguration = new RestClientConfiguration.SystemSettings(baseUri, Optional.empty(), false);
        RestClientConfiguration configuration = targetSystem -> systemConfiguration;

        var restErrorHandler = new RestErrorHandler(objectMapper);

        var contextProvider = Mockito.mock(ContextProvider.class);
        Mockito.when(contextProvider.getContext()).thenReturn(new Context(requestId, Optional.empty()));

        RestClientImpl restClient = new RestClientImpl(configuration, objectMapper, validator, restErrorHandler, contextProvider);
        restClient.initWebClients();

        client = new RestChargingPointStateClient(restClient);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void shouldSendAGetCallAndReturnTheResponse() throws InterruptedException {
        mockWebServer.enqueue(
                new MockResponse()
                        .setBody("{ \"state\": \"IDLE\" }")
                        .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        );

        var response = client.getChargingPointState(chargingStationId, chargingPointName);
        assertThat(response).isEqualTo(ChargingPointState.IDLE);


        assertThat(mockWebServer.takeRequest().getRequestUrl().uri().getPath()).isEqualTo("/some-charging-station-id/some-charging-point-name");
    }

}