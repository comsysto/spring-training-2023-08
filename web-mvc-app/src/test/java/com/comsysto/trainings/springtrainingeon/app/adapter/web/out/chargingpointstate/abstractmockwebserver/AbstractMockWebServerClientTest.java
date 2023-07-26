package com.comsysto.trainings.springtrainingeon.app.adapter.web.out.chargingpointstate.abstractmockwebserver;

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
import com.comsysto.trainings.springtrainingeon.app.port.web.out.common.restclient.RestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validator;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;


abstract class AbstractMockWebServerClientTest<C> {


    private final RequestId requestId = new RequestId("some-request-id");
    final private List<Runnable> verifications = new ArrayList<>();
    protected final MockWebServer mockWebServer = new MockWebServer();
    protected C client;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
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

        client = createClient(restClient);
    }

     protected abstract C createClient(RestClient restClient);

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
        verifications.forEach(Runnable::run);
    }


    protected void stubAndVerifyGet(String url, String responseJson){
        mockWebServer.enqueue(
                new MockResponse()
                        .setBody(responseJson)
                        .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        );

        verifications.add(() -> {
            try {
                assertThat(mockWebServer.takeRequest(100, TimeUnit.MILLISECONDS).getRequestUrl().uri().getPath())
                        .describedAs("Request uri path")
                        .isEqualTo(url);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

    }
}