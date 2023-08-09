package com.comsysto.trainings.springtrainingeon.app.adapter.web.out.common;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RestClient {

    private final TargetSystemConfigProvider targetSystemConfigProvider;
    private Map<TargetSystem, WebClient> webClients;

    public void initWebClients() {
        webClients = Arrays.stream(TargetSystem.values()).collect(
                Collectors.toUnmodifiableMap(
                        Function.identity(),
                        targetSystem -> {
                            var uri = targetSystemConfigProvider.getUri(targetSystem);
                            return WebClient.create(uri.toString());
                        }
                )
        );
    }
}
