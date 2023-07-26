package com.comsysto.trainings.springtrainingeon.app.adapter.web.out.chargingpointstate.wiremock;

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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(
        webEnvironment = WebEnvironment.NONE,
        classes = {WireMockRestChargingPointStateClientTest.App.class, RestChargingPointStateClient.class, RestClientImpl.class, RestErrorHandler.class}
)
@AutoConfigureWireMock(port = 0)
class WireMockRestChargingPointStateClientTest {
    @SpringBootApplication
    public static class App {

        @Bean
        public String baseUri(@Value("${wiremock.server.port}") int wiremockPort) {
            return String.format("http://localhost:%s", wiremockPort);
        }

        @Bean
        public RestClientConfiguration restClientConfiguration(String baseUri) throws URISyntaxException {
            var systemSettings = new RestClientConfiguration.SystemSettings(new URI(baseUri), Optional.empty(), false);
            return targetSystem -> systemSettings;
        }

    }

    @Autowired
    private RestChargingPointStateClient client;

    @MockBean
    private ContextProvider contextProvider;


    private final ChargingStationId chargingStationId = new ChargingStationId("some-charging-station-id");
    private final ChargingPointName chargingPointName = new ChargingPointName("some-charging-point-name");

    private final RequestId requestId = new RequestId("some-request-id");

    @Autowired
    private String baseUri;

    @BeforeEach
    void setUp() {
        Mockito.when(contextProvider.getContext()).thenReturn(new Context(requestId, Optional.empty()));
    }

    @Test
    void testWithWireMock() {
//		var urlPath = createStubForChargingPointState(chargingStationId.getValue(), chargingPointName.getValue(), ChargingPointState.IDLE);
        stubFor(
                get("/some-charging-station-id/some-charging-point-name")
                        .willReturn(
                                aResponse()
                                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                        .withBody("{ \"state\": \"IDLE\" }")
                        )
        );

        var response = client.getChargingPointState(chargingStationId, chargingPointName);
        assertThat(response).isEqualTo(ChargingPointState.IDLE);


//		verify(1, getRequestedFor(urlEqualTo(urlPath)).withBasicAuth(new BasicCredentials("hans", "peter")));
//		assertThat(response).isNotNull();
//		assertThat(response).isEqualByComparingTo(ChargingPointState.IDLE);
    }
}