package com.comsysto.trainings.springtrainingeon.app.adapter.web.out.chargingpointstate;

import com.comsysto.trainings.springtrainingeon.app.adapter.context.out.RequestContextRepository;
import com.comsysto.trainings.springtrainingeon.app.port.context.out.BearerToken;
import com.comsysto.trainings.springtrainingeon.app.port.context.out.Context;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    public static final String MY_WEB_CLIENT = "myWebClient";
    public WebClient.Builder baseClient() {
        return WebClient.builder()
                .defaultHeader("Content-Type", "application/json");
    }

    @Bean
    @Qualifier(MY_WEB_CLIENT)
    public WebClient webClient(RequestContextRepository context) {
        return baseClient()
                .baseUrl("http://localhost:8080/mocks/charging-point-state/")
                .filter((request, next) -> next.exchange(
                        withBearerAuth(request, context.getContext())))
                .build();
    }

    @Bean
    @Qualifier("webClient2")
    public WebClient webClient2(RequestContextRepository context) {
        return baseClient()
                .baseUrl("http://localhost:8081")
                .filter((request, next) -> next.exchange(
                        withBearerAuth(request, context.getContext())))
                .build();
    }

    private static ClientRequest withBearerAuth(ClientRequest request, Context context) {
        return ClientRequest.from(request)
                .header(HttpHeaders.AUTHORIZATION, context.getBearerToken().getValue())
                .header("requestId", context.getRequestId().getValue())
                .build();
    }
}
